package com.ubo.tp.message.controller.login;

import com.ubo.tp.message.core.database.IDatabase;
import com.ubo.tp.message.core.session.Session;
import com.ubo.tp.message.datamodel.User;
import com.ubo.tp.message.ihm.LoginView;
import com.ubo.tp.message.ihm.RegisterFrame;

import javax.swing.*;
import java.util.Set;

public class LoginController implements ILoginObserver {

    private final LoginView loginView;
    private final IDatabase database;
    private final Session session;

    public LoginController(LoginView loginView, IDatabase database, Session session) {
        this.loginView = loginView;
        this.database = database;
        this.session = session;
        loginView.addObserver(this);
    }

    @Override
    public void notifyLogin(User connectedUser) {
        User foundUser = database.getUsers().stream()
                .filter(u -> u.getUserTag().equalsIgnoreCase(connectedUser.getUserTag()) &&
                        u.getUserPassword().equals(connectedUser.getUserPassword()))
                .findFirst()
                .orElse(null);

        if (foundUser != null) {
            session.connect(foundUser);
            JOptionPane.showMessageDialog(loginView,
                    "Connexion réussie ! Bienvenue, " + foundUser.getName() + " 🎉",
                    "Connexion réussie",
                    JOptionPane.INFORMATION_MESSAGE);
            // Fermer la fenêtre après une connexion réussie (optionnel)
            SwingUtilities.getWindowAncestor(loginView).dispose();
        } else {
            JOptionPane.showMessageDialog(loginView,
                    "Tag ou mot de passe incorrect.",
                    "Erreur de connexion",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void notifyRegister() {
        // Ouvrir la vue d'inscription dans l'EDT
        javax.swing.SwingUtilities.invokeLater(() -> new RegisterFrame(this.database));
    }

    /**
     * Check if the user is correct
     *
     * @param user the user to check
     * @return true if the user is correct
     */
    private boolean isCorrectUser(User user) {
        Set<User> usersDb = database.getUsers();

        for (User u : usersDb) {
            if (u.getName().equals(user.getName()) && u.getUserPassword().equals(user.getUserPassword())) {
                return true;
            }
        }
        return false;
    }

    public LoginView getLoginView() {
        return loginView;
    }

    public Session getSession() {
        return session;
    }
}
