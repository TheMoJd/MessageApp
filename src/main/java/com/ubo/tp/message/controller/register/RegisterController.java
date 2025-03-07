package com.ubo.tp.message.controller.register;

import com.ubo.tp.message.core.database.IDatabase;
import com.ubo.tp.message.core.session.Session;
import com.ubo.tp.message.datamodel.User;
import com.ubo.tp.message.ihm.RegisterView;

import java.util.UUID;

public class RegisterController implements IRegisterObserver {

    private final RegisterView registerView;
    private final IDatabase database;
    private final Session session;

    public RegisterController(IDatabase database, Session session) {
        this.registerView = new RegisterView();
        this.database = database;
        this.session = session;
        registerView.addObserver(this);
    }

    @Override
    public void notifyRegister(User createdUser, String confirmPassword) {
        if (!isPasswordValid(createdUser.getUserPassword(), confirmPassword)) {
            registerView.setError("Les mots de passe ne correspondent pas");
            return;
        }

        if (!isUniqueUser(createdUser)) {
            registerView.setError("Utilisateur déjà existant");
            return;
        }

        User newUser = createUser(createdUser);
        session.connect(newUser);
    }

    public RegisterView getRegisterView() {
        return registerView;
    }

    private boolean isUniqueUser(User user) {
        return database.getUsers().stream().noneMatch(u -> u.getName().equals(user.getName()));
    }

    private boolean isPasswordValid(String password1, String password2) {
        return password1.equals(password2);
    }

    private User createUser(User user) {
        User newUser = new User(UUID.randomUUID(), user.getUserTag(), user.getUserPassword(), user.getName(), user.getFollows(), user.getAvatarPath());
        database.addUser(newUser);
        return newUser;
    }
}
