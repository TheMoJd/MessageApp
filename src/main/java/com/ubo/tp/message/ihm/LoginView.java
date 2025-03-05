package com.ubo.tp.message.ihm;

import com.ubo.tp.message.controller.login.ILogin;
import com.ubo.tp.message.controller.login.ILoginObserver;
import com.ubo.tp.message.datamodel.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class LoginView extends JPanel implements ILogin {
    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private final JButton loginButton;
    private final JButton registerButton;
    private final List<ILoginObserver> observers = new ArrayList<>();

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

        usernameField = new JTextField(15);
        gbc.gridx = 1;
        add(usernameField, gbc);

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

        registerButton = new JButton("Inscription");
        gbc.gridy = 4;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        add(registerButton, gbc);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                user = new User(new UUID(0, 1), null, new String(passwordField.getPassword()), usernameField.getText(), null, null);
                login(user);
            }
        });

        registerButton.addActionListener(e -> {
            registerAction();
        });

        setVisible(true);
    }

    public JTextField getUsernameField() {
        return usernameField;
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }

    public JButton getLoginButton() {
        return loginButton;
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