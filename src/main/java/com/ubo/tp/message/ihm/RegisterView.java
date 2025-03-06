package com.ubo.tp.message.ihm;

import com.ubo.tp.message.controller.register.IRegister;
import com.ubo.tp.message.controller.register.IRegisterObserver;
import com.ubo.tp.message.datamodel.User;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RegisterView extends JPanel implements IRegister {
    private final JTextField usernameField;
    private final JTextField tagField; // Nouveau champ pour le tag
    private final JPasswordField passwordField;
    private final JPasswordField confirmPasswordField;
    private final JButton registerButton;
    private final List<IRegisterObserver> observers = new ArrayList<>();

    public RegisterView() {
        setPreferredSize(new Dimension(400, 350));
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Titre
        JLabel titleLabel = new JLabel("Création de compte", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(titleLabel, gbc);

        // Champ Nom d'utilisateur
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.gridx = 0;
        add(new JLabel("Nom d'utilisateur:"), gbc);
        usernameField = new JTextField(15);
        gbc.gridx = 1;
        add(usernameField, gbc);

        // Champ Tag (@ identifiant unique)
        gbc.gridy = 2;
        gbc.gridx = 0;
        add(new JLabel("Tag (@tag) :"), gbc);
        tagField = new JTextField(15);
        gbc.gridx = 1;
        add(tagField, gbc);

        // Champ Mot de passe
        gbc.gridy = 3;
        gbc.gridx = 0;
        add(new JLabel("Mot de passe:"), gbc);
        passwordField = new JPasswordField(15);
        gbc.gridx = 1;
        add(passwordField, gbc);

        // Champ Confirmer mot de passe
        gbc.gridy = 4;
        gbc.gridx = 0;
        add(new JLabel("Confirmer mot de passe:"), gbc);
        confirmPasswordField = new JPasswordField(15);
        gbc.gridx = 1;
        add(confirmPasswordField, gbc);

        // Bouton Enregistrer
        registerButton = new JButton("Créer un compte");
        gbc.gridy = 5;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        add(registerButton, gbc);

        // Action du bouton avec validation
        registerButton.addActionListener(e -> {
            if (validateFields()) {
                String username = usernameField.getText().trim();
                String tag = tagField.getText().trim();
                String password = new String(passwordField.getPassword());

                // Création d'un utilisateur avec un UUID généré aléatoirement
                User newUser = new User(UUID.randomUUID(), tag, password, username, new java.util.HashSet<>(), "");

                // Notifier les observateurs de la demande d'enregistrement
                registerAccount(newUser);
            }
        });
    }

    /**
     * Vérifie que les champs sont remplis et valides.
     */
    private boolean validateFields() {
        String username = usernameField.getText().trim();
        String tag = tagField.getText().trim();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());

        if (username.isEmpty() || tag.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Tous les champs doivent être remplis.",
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this,
                    "Les mots de passe ne correspondent pas.",
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
    public void addObserver(IRegisterObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(IRegisterObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void registerAccount(User newUser) {
        for (IRegisterObserver observer : observers) {
            observer.notifyRegisterAccount(newUser);
        }
    }
}
