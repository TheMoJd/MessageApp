package com.ubo.tp.message.controller.login;

import com.ubo.tp.message.core.database.IDatabase;
import com.ubo.tp.message.core.session.Session;
import com.ubo.tp.message.datamodel.User;
import com.ubo.tp.message.ihm.LoginView;
import com.ubo.tp.message.ihm.RegisterFrame;

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
        if (isCorrectUser(connectedUser)) {
            session.connect(connectedUser);
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
