package com.ubo.tp.message.controller;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import com.ubo.tp.message.action.IActionObserver;
import com.ubo.tp.message.config.ConfigManager;
import com.ubo.tp.message.controller.login.LoginController;
import com.ubo.tp.message.controller.message.MessageController;
import com.ubo.tp.message.controller.register.RegisterController;
import com.ubo.tp.message.controller.user.UserController;
import com.ubo.tp.message.core.EntityManager;
import com.ubo.tp.message.core.database.IDatabase;
import com.ubo.tp.message.core.database.IDatabaseObserver;
import com.ubo.tp.message.core.directory.IWatchableDirectory;
import com.ubo.tp.message.core.directory.WatchableDirectory;
import com.ubo.tp.message.core.session.ISessionObserver;
import com.ubo.tp.message.core.session.Session;
import com.ubo.tp.message.datamodel.Message;
import com.ubo.tp.message.datamodel.User;
import com.ubo.tp.message.ihm.*;

/**
 * Classe principale l'application.
 *
 * @author S.Lucas
 */
public class MessageAppController implements IDatabaseObserver, ISessionObserver, IActionObserver {
	/**
	 * Base de données.
	 */
	protected IDatabase mDatabase;

	/**
	 * Gestionnaire des entités contenu de la base de données.
	 */
	protected EntityManager mEntityManager;

	/**
	 * Vue principale de l'application.
	 */
	protected MessageAppMainView mMainView;

	/**
	 * Classe de surveillance de répertoire
	 */
	protected IWatchableDirectory mWatchableDirectory;

	/**
	 * Répertoire d'échange de l'application.
	 */
	protected String mExchangeDirectoryPath;

	/**
	 * Nom de la classe de l'UI.
	 */
	protected String mUiClassName;

	protected LoginController mLoginController;

	protected RegisterController mRegisterController;

	protected UserController mUserController;

	protected MessageController mMessageController;

	protected Session mSession;

	/**
	 * Constructeur.
	 *
	 * @param entityManager
	 * @param database
	 */
	public MessageAppController(IDatabase database, EntityManager entityManager) {
		this.mDatabase = database;
		this.mEntityManager = entityManager;
		this.mSession = new Session();

		mLoginController = new LoginController(database, mSession);
		mRegisterController = new RegisterController(database, mSession, entityManager);
		mUserController = new UserController(database, mSession);
		mMessageController = new MessageController(database, mSession, entityManager);

		mSession.addObserver(this);
		database.addObserver(this);
	}

	/**
	 * Initialisation de l'application.
	 */
	public void init() {
		// Init du look and feel de l'application
		this.initLookAndFeel();

		// Initialisation de l'IHM
		this.initGui();

		// Initialisation du répertoire d'échange
		this.initDirectory();
	}

	/**
	 * Initialisation du look and feel de l'application.
	 */
	protected void initLookAndFeel() {
	}

	/**
	 * Initialisation de l'interface graphique.
	 */
	protected void initGui() {
		this.mMainView = new MessageAppMainView();
		this.mMainView.addObserver(this);
	}

	protected void exit() {
		System.exit(0);
	}

	/**
	 * Initialisation du répertoire d'échange (depuis la conf ou depuis un file
	 * chooser). <br/>
	 * <b>Le chemin doit obligatoirement avoir été saisi et être valide avant de
	 * pouvoir utiliser l'application</b>
	 */
	protected void initDirectory() {
		mExchangeDirectoryPath = ConfigManager.getProperty("EXCHANGE_DIRECTORY");

		if (mExchangeDirectoryPath == null || mExchangeDirectoryPath.isEmpty()) {

			FileChooserView fileChooserView = new FileChooserView();
			File selectedDirectory = fileChooserView.getSelectedDirectory();

			if (isValideExchangeDirectory(selectedDirectory)) {
				ConfigManager.setProperty("EXCHANGE_DIRECTORY", selectedDirectory.getAbsolutePath());
			}
		}

		initDirectory(ConfigManager.getProperty("EXCHANGE_DIRECTORY"));

	}

	/**
	 * Indique si le fichier donné est valide pour servir de répertoire d'échange
	 *
	 * @param directory , Répertoire à tester.
	 */
	protected boolean isValideExchangeDirectory(File directory) {
		// Valide si répertoire disponible en lecture et écriture
		return directory != null && directory.exists() && directory.isDirectory() && directory.canRead()
				&& directory.canWrite();
	}

	/**
	 * Initialisation du répertoire d'échange.
	 *
	 * @param directoryPath
	 */
	protected void initDirectory(String directoryPath) {
		mExchangeDirectoryPath = directoryPath;
		mWatchableDirectory = new WatchableDirectory(directoryPath);
		mEntityManager.setExchangeDirectory(directoryPath);

		mWatchableDirectory.initWatching();
		mWatchableDirectory.addObserver(mEntityManager);
	}

	public void show() {
		if (mMainView != null) {
			mMainView.setVisible(true);
		}
	}

	@Override
	public void notifyMessageAdded(Message message) {
		System.out.println("Nouveau message ajouté : " + message.getText());
		if (mSession.getConnectedUser() != null) {
			mMessageController.getMessageListView().refreshView(mDatabase.getMessages());
			// Notification d'un nouveau message
			if (this.mMessageController.getMessageListView().getDateTime() < message.getEmissionDate() && this.mSession.getConnectedUser().isFollowing(message.getSender())) {
				if (SystemTray.isSupported()) {
					SystemTray tray = SystemTray.getSystemTray();
					Image icon = Toolkit.getDefaultToolkit().getImage("src/main/resources/images/logo_20.png");
					PopupMenu popupMenu = new PopupMenu();
					MenuItem exitItem = new MenuItem("Quitter");
					exitItem.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							System.exit(0);
						}
					});
					popupMenu.add(exitItem);

					// Création de l'objet TrayIcon
					TrayIcon trayIcon = new TrayIcon(icon, "Notification", popupMenu);
					try {
						tray.add(trayIcon);
					} catch (AWTException e) {
						System.err.println("Impossible d'ajouter TrayIcon au SystemTray");
					}

					// Affichage de la notification
					trayIcon.displayMessage("Nouveau message de \"" + message.getSender().getName() + "\"", message.getText(), TrayIcon.MessageType.INFO);
				} else {
					System.err.println("SystremTray n'est pas pris en charge !");
				}
			}
		}
	}

	@Override
	public void notifyMessageDeleted(Message message) {
		System.out.println("Message supprimé : " + message.getText());
	}

	@Override
	public void notifyMessageModified(Message message) {
		System.out.println("Message modifié : " + message.getText());
	}

	@Override
	public void notifyUserAdded(User user) {
		System.out.println("Nouvel utilisateur ajouté : " + user.getUserTag());
	}

	@Override
	public void notifyUserDeleted(User user) {
		System.out.println("Utilisateur supprimé : " + user.getUserTag());
	}

	@Override
	public void notifyUserModified(User user) {
		System.out.println("Utilisateur modifié : " + user.getUserTag());
	}

	@Override
	public void notifyLogin(User connectedUser) {
		mMainView.removeView();
		mMainView.getNavBar().addProfilButton();
		mMainView.getNavBar().addUsersButton();
		mMainView.getNavBar().addLogoutButton();
		mMainView.getNavBar().addMessageButton();
	}

	@Override
	public void notifyLogout() {
		mMainView.getNavBar().removeLogoutButton();
		mMainView.getNavBar().removeProfilButton();
		mMainView.getNavBar().removeUsersButton();
		mMainView.getNavBar().removeMessageButton();
		mMainView.removeView();
		mMainView.addView(mMainView.getCenterPanel());
	}

	@Override
	public void notifyLoginAction() {
		mMainView.addView(mLoginController.getLoginView());
		mLoginController.getLoginView().addRegisterListener(e -> notifyRegisterAction());
	}

	@Override
	public void notifyExitAction() {
	}

	@Override
	public void notifyRegisterAction() {
		mMainView.addView(mRegisterController.getRegisterView());
		mRegisterController.getRegisterView().addLoginListener(e -> notifyLoginAction());
	}

	@Override
	public void notifyDisconnectAction() {
		mSession.disconnect();
	}

	@Override
	public void notifyProfileAction() {
		mMainView.addView(new UserProfileView(mSession.getConnectedUser()));
	}

	@Override
	public void notifyListUsersAction() {
		mUserController.getListUsersView().listUsers();
		mMainView.addView(mUserController.getListUsersView());
	}

	@Override
	public void notifyListMessageAction() {
		mMainView.addView(mMessageController.getMessageListView());
		mMessageController.getMessageListView().refreshView(mDatabase.getMessages());
	}
}
