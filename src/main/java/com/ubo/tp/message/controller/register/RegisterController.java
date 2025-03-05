package com.ubo.tp.message.controller.register;

import com.ubo.tp.message.core.database.IDatabase;
import com.ubo.tp.message.datamodel.User;
import com.ubo.tp.message.ihm.RegisterView;

import javax.swing.*;

public class RegisterController implements IRegisterObserver {
    private final RegisterView registerView;
    private final IDatabase database;

    public RegisterController(RegisterView registerView, IDatabase database) {
        this.registerView = registerView;
        this.database = database;
        // Inscription du contrôleur en tant qu'observateur de la vue
        this.registerView.addObserver(this);
    }

    @Override
    public void notifyRegisterAccount(User newUser) {
        // Vérifier si un utilisateur avec le même nom existe déjà
        boolean exists = database.getUsers().stream()
                .anyMatch(u -> u.getName().equalsIgnoreCase(newUser.getName()));
        if (exists) {
            JOptionPane.showMessageDialog(registerView, "Nom d'utilisateur déjà utilisé.", "Erreur", JOptionPane.ERROR_MESSAGE);
        } else {
            // Ajouter l'utilisateur à la base de données
            database.addUser(newUser);
            JOptionPane.showMessageDialog(registerView, "Compte créé avec succès.", "Succès", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
