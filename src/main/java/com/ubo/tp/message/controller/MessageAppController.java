package com.ubo.tp.message.controller;

import java.io.File;

import com.ubo.tp.message.core.EntityManager;
import com.ubo.tp.message.core.database.IDatabase;
import com.ubo.tp.message.core.directory.IWatchableDirectory;
import com.ubo.tp.message.core.directory.WatchableDirectory;
import com.ubo.tp.message.ihm.MessageAppMainView;

import com.ubo.tp.message.controller.login.LoginController;
import com.ubo.tp.message.core.database.IDatabaseObserver;
import com.ubo.tp.message.core.session.ISessionObserver;
import com.ubo.tp.message.core.session.Session;
import com.ubo.tp.message.datamodel.Message;
import com.ubo.tp.message.datamodel.User;
import com.ubo.tp.message.ihm.FileChooserView;
import com.ubo.tp.message.ihm.LoginView;

/**
 * Classe principale l'application.
 *
 * @author S.Lucas
 */
public class MessageAppController implements IDatabaseObserver, ISessionObserver {
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
		mLoginController = new LoginController(new LoginView(), database, mSession);
		mSession.addObserver(this);
		database.addObserver(this);
	}

	/**
	 * Initialisation de l'application.
	 */
	public void init() {
		this.initLookAndFeel();

		this.initGui();

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

		if (mLoginController.getSession().getConnectedUser() == null) {
			mMainView.addLoginPane(mLoginController.getLoginView());
		}
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
		if (mExchangeDirectoryPath == null) {

			FileChooserView fileChooserView = new FileChooserView();

			File selectedDirectory = fileChooserView.getSelectedDirectory();

			if (isValideExchangeDirectory(selectedDirectory)) {
				initDirectory(selectedDirectory.getAbsolutePath());
			}
		}

		System.out.println("Exchange directory : " + mExchangeDirectoryPath);
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
		this.mExchangeDirectoryPath = directoryPath;
		this.mWatchableDirectory = new WatchableDirectory(directoryPath);
		this.mEntityManager.setExchangeDirectory(directoryPath);

		this.mWatchableDirectory.initWatching();
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
		System.out.println(connectedUser.getName() + " connected!");
		mMainView.removeLoginPane(mLoginController.getLoginView());
	}

	@Override
	public void notifyLogout() {

	}

	public Session getmSession() {
		return mSession;
	}
}
