package com.ubo.tp.message.ihm;

import java.io.File;

import com.ubo.tp.message.core.EntityManager;
import com.ubo.tp.message.core.database.IDatabase;
import com.ubo.tp.message.core.directory.IWatchableDirectory;
import com.ubo.tp.message.core.directory.WatchableDirectory;

/**
 * Classe principale l'application.
 *
 * @author S.Lucas
 */
public class MessageApp {

	/**
	 * Base de données.
	 */
	protected IDatabase mDatabase;

	/**
	 * Gestionnaire des entités contenu dans la base de données.
	 */
	protected EntityManager mEntityManager;

	/**
	 * Vue principale de l'application (fenêtre).
	 */
	protected MessageAppMainView mMainView;

	/**
	 * Surveillance de répertoire.
	 */
	protected IWatchableDirectory mWatchableDirectory;

	/**
	 * Répertoire d'échange.
	 */
	protected String mExchangeDirectoryPath;

	/**
	 * Constructeur.
	 */
	public MessageApp(IDatabase database, EntityManager entityManager) {
		this.mDatabase = database;
		this.mEntityManager = entityManager;
	}

	/**
	 * Initialisation de l'application.
	 */
	public void init() {
		// 1) Appliquer le Look & Feel AVANT la création des composants
		//    pour qu'ils prennent tout de suite le style du système.
		MessageAppMainView.initLookAndFeel();

		// 2) Créer et configurer la fenêtre principale (IHM)
		this.initGui();

		// 3) Ajouter un observateur pour afficher les modifications en console
		mDatabase.addObserver(new ConsoleDatabaseObserver());

		// 4) Initialiser le répertoire d'échange (vide ici, à adapter)
		this.initDirectory();
	}

	/**
	 * Crée la fenêtre principale de l'application.
	 */
	protected void initGui() {
		// Instancie la fenêtre (avec menus, logo, etc.)
		this.mMainView = new MessageAppMainView();
	}

	/**
	 * Initialisation du répertoire d'échange
	 * (depuis la conf ou depuis un file chooser).
	 */
	protected void initDirectory() {
		// Vous pouvez appeler initDirectory("chemin") ici
		// si vous avez déjà un chemin défini, ou demander à l'utilisateur via un JFileChooser.
	}

	/**
	 * Vérifie si le répertoire est valide pour l'échange.
	 */
	protected boolean isValideExchangeDirectory(File directory) {
		return directory != null
				&& directory.exists()
				&& directory.isDirectory()
				&& directory.canRead()
				&& directory.canWrite();
	}

	/**
	 * Initialisation du répertoire d'échange à partir d'un chemin donné.
	 */
	protected void initDirectory(String directoryPath) {
		mExchangeDirectoryPath = directoryPath;
		mWatchableDirectory = new WatchableDirectory(directoryPath);
		mEntityManager.setExchangeDirectory(directoryPath);

		mWatchableDirectory.initWatching();
		mWatchableDirectory.addObserver(mEntityManager);
	}

	/**
	 * Affiche la fenêtre principale de l'application.
	 */
	public void show() {
		if (mMainView != null) {
			this.mMainView.setVisible(true);
		}
	}
}