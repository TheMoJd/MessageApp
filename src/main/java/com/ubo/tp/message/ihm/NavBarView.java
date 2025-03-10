package com.ubo.tp.message.ihm;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class NavBarView extends JPanel {

    private final JPanel buttonPanel;

    private final JButton logoutButton;
    private final JButton profilButton;
    private final JButton usersButton;


    public NavBarView() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(1024, 50));

        JLabel logoLabel = new JLabel(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("images/logo_50.png"))));
        logoLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        logoutButton = new JButton("Déconnexion");
        profilButton = new JButton("Profil");
        usersButton = new JButton("Utilisateurs");

        // Ajout des éléments à la navbar
        add(logoLabel, BorderLayout.WEST);
        add(buttonPanel, BorderLayout.EAST);
    }



    public JButton getLogoutButton() {
        return logoutButton;
    }

    public JButton getProfilButton() {
        return profilButton;
    }

    public JButton getUsersButton() {
        return usersButton;
    }

    public void removeLogoutButton() {
        this.buttonPanel.remove(logoutButton);
    }

    public void removeProfilButton() {
        this.buttonPanel.remove(profilButton);
    }

    public void addProfilButton() {
        this.buttonPanel.add(profilButton);
    }

    public void addLogoutButton() {
        this.buttonPanel.add(logoutButton);
    }

    public void addUsersButton() {
        this.buttonPanel.add(usersButton);
    }

    public void removeUsersButton() {
        this.buttonPanel.remove(usersButton);
    }
}