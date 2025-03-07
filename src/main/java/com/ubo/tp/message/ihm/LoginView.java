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
    private final JTextField tagField;
    private final JPasswordField passwordField;
    private final JButton loginButton;
    private final List<ILoginObserver> observers = new ArrayList<>();
    private final JLabel errorLabel;

    private User user;

    public LoginView() {
        setSize(400, 250);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel titleLabel = new JLabel("MessageApp - Connexion");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(titleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.gridx = 0;
        add(new JLabel("Nom d'utilisateur :"), gbc);

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
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(loginButton, gbc);

        errorLabel = new JLabel();
        errorLabel.setForeground(Color.RED);
        gbc.gridy = 4;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        add(errorLabel, gbc);

        loginButton.addActionListener(e -> {
            user = new User(UUID.randomUUID(),  tagField.getText(), new String(passwordField.getPassword()), null, null, null);
            login(user);
        });

        setVisible(true);
    }

    public JTextField getTagField() {
        return tagField;
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }

    public JButton getLoginButton() {
        return loginButton;
    }

    public void removeError() {
        errorLabel.setText("");
    }

    public void setError(String error) {
        errorLabel.setText(error);
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
}