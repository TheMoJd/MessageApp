package com.ubo.tp.message.ihm;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Objects;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

/**
 * Fenêtre principale de l'application.
 */
public class MessageAppMainView extends JFrame {

    /**
     * Constructeur de la fenêtre principale.
     */
    public MessageAppMainView() {
        // Définit le titre de la fenêtre
        super("MessageApp");

        // Définir l'icône de l'application (logo)

        ImageIcon appIcon = new ImageIcon(("src/main/resources/images/logo_50.png"));
        setIconImage(appIcon.getImage());

        // Configuration initiale
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 600); // taille par défaut
        this.setLocationRelativeTo(null); // centre la fenêtre à l'écran

        // Ajout d'une barre de menu
        this.createMenuBar();
    }

    /**
     * Méthode de construction de la barre de menu.
     */
    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        // ----- Menu Fichier -----
        JMenu menuFichier = new JMenu("Fichier");

        // Élément Quitter
        JMenuItem menuItemQuitter = new JMenuItem("Quitter");
        menuItemQuitter.addActionListener(e -> System.exit(0));
        menuFichier.add(menuItemQuitter);

        // Élément Choisir répertoire d'échange
        JMenuItem menuItemSelectDir = new JMenuItem("Choisir répertoire d'échange");
        menuItemSelectDir.addActionListener(e -> {
            // Ouvrir le sélecteur de fichiers/répertoires
            showExchangeDirectoryChooser();
        });
        menuFichier.add(menuItemSelectDir);

        // Ajout du menu Fichier à la barre
        menuBar.add(menuFichier);

        // ----- Menu Aide (?) -----
        JMenu menuAide = new JMenu("?");
        JMenuItem menuItemAPropos = new JMenuItem("A propos");
        menuItemAPropos.addActionListener(e -> showAboutDialog());
        menuAide.add(menuItemAPropos);

        menuBar.add(menuAide);

        setJMenuBar(menuBar);
    }


    /**
     * Montre la boîte de dialogue "A propos".
     */
    private void showAboutDialog() {
        // Possibilité de personnaliser davantage un JDialog ou JOptionPane
        ImageIcon aboutIcon = new ImageIcon(getClass().getResource("src/main/resources/images/editIcon_20.png"));
        String message = "<html><h2>Mon Application</h2>"
                + "<p>Version 1.0</p>"
                + "<p>Développée par Moi</p></html>";

        JOptionPane.showMessageDialog(this,
                message,
                "A propos",
                JOptionPane.INFORMATION_MESSAGE,
                aboutIcon);
    }

    /**
     * Affiche un sélecteur de répertoire (ou de fichier) pour choisir le répertoire d'échange.
     */
    private void showExchangeDirectoryChooser() {
        JFileChooser fileChooser = new JFileChooser();
        // On veut choisir un répertoire
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.setDialogTitle("Choisir un répertoire d'échange");

        int returnValue = fileChooser.showOpenDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedDirectory = fileChooser.getSelectedFile();
            if (selectedDirectory != null) {
                // À vous de décider comment transmettre ce chemin à votre MessageApp
                // Par exemple, vous pourriez appeler une méthode initDirectory(...) côté MessageApp
                System.out.println("Répertoire sélectionné : " + selectedDirectory.getAbsolutePath());
            }
        }
    }


    /**
     * Change le look and feel de l'application.
     */
    public static void initLookAndFeel() {
        try {
            // Exemple : le look and feel "Nimbus" (si dispo dans votre JRE)
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // En cas d'erreur, on ne fait rien, on garde le L&F par défaut
            e.printStackTrace();
        }
    }
}
