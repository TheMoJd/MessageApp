package com.ubo.tp.message.controller.login;

import com.ubo.tp.message.core.database.IDatabase;
import com.ubo.tp.message.core.session.Session;
import com.ubo.tp.message.datamodel.User;
import com.ubo.tp.message.ihm.LoginView;

import java.util.Set;

public class LoginController implements ILoginObserver {

    private final LoginView loginView;
    private final IDatabase database;
    private final Session session;

    public LoginController(IDatabase database, Session session) {
        this.loginView = new LoginView();
        this.database = database;
        this.session = session;
        loginView.addObserver(this);
    }

    @Override
    public void notifyLogin(User connectedUser) {
        User userDb = searchUser(connectedUser);

        if (userDb == null) {
            loginView.setError("Identifiant ou mot de passe incorrect");
            return;
        }
        session.connect(userDb);
    }

    /**
     * Check if the user is correct
     *
     * @param user the user to check
     * @return true if the user is correct
     */
    private User searchUser(User user) {
        Set<User> usersDb = database.getUsers();

        for (User u : usersDb) {
            if (u.getUserTag().equals(user.getUserTag()) && u.getUserPassword().equals(user.getUserPassword())) {
                return u;
            }
        }
        return null;
    }

    public LoginView getLoginView() {
        return loginView;
    }

    public Session getSession() {
        return session;
    }
}
