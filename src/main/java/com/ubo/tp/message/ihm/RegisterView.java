package com.ubo.tp.message.ihm;

import com.ubo.tp.message.controller.register.IRegister;
import com.ubo.tp.message.controller.register.IRegisterObserver;
import com.ubo.tp.message.datamodel.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class RegisterView extends JPanel implements IRegister {
    private final JTextField nameField;
    private final JTextField tagField;
    private final JPasswordField passwordField;
    private final JPasswordField confirmPasswordField;
    private final JButton registerButton;
    private final JButton loginButton;
    private final JLabel errorLabel;

    private final List<IRegisterObserver> observers = new ArrayList<>();

    public RegisterView() {
        setSize(700, 500);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Titre
        JLabel titleLabel = new JLabel("MessageApp - Inscription");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(titleLabel, gbc);

        // Champ nom
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.gridx = 0;
        add(new JLabel("Nom :"), gbc);

        nameField = new JTextField(15);
        gbc.gridx = 1;
        add(nameField, gbc);

        // Champ Tag
        gbc.gridwidth = 1;
        gbc.gridy = 2;
        gbc.gridx = 0;
        add(new JLabel("Nom d'utilisateur :"), gbc);

        tagField = new JTextField(15);
        gbc.gridx = 1;
        add(tagField, gbc);

        // Champ mot de passe
        gbc.gridy = 3;
        gbc.gridx = 0;
        add(new JLabel("Mot de passe :"), gbc);

        passwordField = new JPasswordField(15);
        gbc.gridx = 1;
        add(passwordField, gbc);

        // Champ confirmation mot de passe
        gbc.gridy = 4;
        gbc.gridx = 0;
        add(new JLabel("Confirmer le mot de passe :"), gbc);

        confirmPasswordField = new JPasswordField(15);
        gbc.gridx = 1;
        add(confirmPasswordField, gbc);

        // Bouton inscription
        registerButton = new JButton("S'inscrire");
        gbc.gridy = 5;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(registerButton, gbc);

        // Bouton connexion
        loginButton = new JButton("Connexion");
        gbc.gridy = 6;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(loginButton, gbc);

        errorLabel = new JLabel();
        errorLabel.setForeground(Color.RED);
        gbc.gridy = 7;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        add(errorLabel, gbc);

        setVisible(true);
        manageAction();
    }

    public void addLoginListener(ActionListener listener) {
        loginButton.addActionListener(listener);
    }

    private void manageAction() {
        registerButton.addActionListener(e -> {
            User user = new User(null, tagField.getText(), new String(passwordField.getPassword()), nameField.getText(), new HashSet<>(), "");
            register(user, new String(confirmPasswordField.getPassword()));
        });
    }

    public JTextField getTagField() {
        return tagField;
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }

    public JPasswordField getConfirmPasswordField() {
        return confirmPasswordField;
    }

    public JButton getRegisterButton() {
        return registerButton;
    }

    public void removeError() {
        errorLabel.setText("");
    }

    public List<IRegisterObserver> getObservers() {
        return observers;
    }

    public void setError(String error) {
        errorLabel.setText(error);
    }

    @Override
    public void addObserver(IRegisterObserver observer) {
        this.observers.add(observer);
    }

    @Override
    public void removeObserver(IRegisterObserver observer) {
        this.observers.remove(observer);
    }

    @Override
    public void register(User createdUser, String confirmPassword) {
        for (IRegisterObserver observer : observers) {
            observer.notifyRegister(createdUser, confirmPassword);
        }
    }
}