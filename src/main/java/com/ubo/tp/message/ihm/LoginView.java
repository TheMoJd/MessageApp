package com.ubo.tp.message.ihm;

import com.ubo.tp.message.controller.login.ILogin;
import com.ubo.tp.message.controller.login.ILoginObserver;
import com.ubo.tp.message.datamodel.User;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class LoginView extends JPanel implements ILogin {
    private final JTextField tagField; // Champ pour le tag (@tag)
    private final JPasswordField passwordField;
    private final JButton loginButton;
    private final JButton registerButton;
    private final List<ILoginObserver> observers = new ArrayList<>();

    public LoginView() {
        setPreferredSize(new Dimension(400, 250));
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("MessageApp - Connexion", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(titleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.gridx = 0;
        add(new JLabel("Tag (@tag) :"), gbc);

        tagField = new JTextField(15);
        gbc.gridx = 1;
        add(tagField, gbc);

        gbc.gridy = 2;
        gbc.gridx = 0;
        add(new JLabel("Mot de passe :"), gbc);

        passwordField = new JPasswordField(15);
        gbc.gridx = 1;
        add(passwordField, gbc);

        loginButton = new JButton("Se connecter");
        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        add(loginButton, gbc);

        registerButton = new JButton("Inscription");
        gbc.gridy = 4;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        add(registerButton, gbc);

        loginButton.addActionListener(e -> {
            if (validateFields()) {
                User user = new User(UUID.randomUUID(), tagField.getText().trim(), new String(passwordField.getPassword()), "", null, null);
                login(user);
            }
        });

        registerButton.addActionListener(e -> registerAction());
    }

    /**
     * Vérifie que les champs ne sont pas vides avant d'envoyer les données.
     */
    private boolean validateFields() {
        String tag = tagField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (tag.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Veuillez remplir tous les champs.",
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!tag.matches("^@[a-zA-Z0-9_]{3,15}$")) {
            JOptionPane.showMessageDialog(this,
                    "Le tag doit commencer par '@' et contenir entre 3 et 15 caractères alphanumériques.",
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    @Override
    public void addObserver(ILoginObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(ILoginObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void login(User connectedUser) {
        for (ILoginObserver observer : observers) {
            observer.notifyLogin(connectedUser);
        }
    }

    @Override
    public void registerAction() {
        for (ILoginObserver observer : observers) {
            observer.notifyRegister();
        }
    }
}
