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
        // Vérifier si le nom d'utilisateur est déjà utilisé
        boolean usernameExists = database.getUsers().stream()
                .anyMatch(u -> u.getName().equalsIgnoreCase(newUser.getName()));

        // Vérifier si le tag est déjà utilisé
        boolean tagExists = database.getUsers().stream()
                .anyMatch(u -> u.getUserTag().equalsIgnoreCase(newUser.getUserTag()));

        if (usernameExists) {
            JOptionPane.showMessageDialog(registerView,
                    "Nom d'utilisateur déjà utilisé. Veuillez en choisir un autre.",
                    "Erreur d'inscription",
                    JOptionPane.ERROR_MESSAGE);
        } else if (tagExists) {
            JOptionPane.showMessageDialog(registerView,
                    "Le tag " + newUser.getUserTag() + " est déjà pris. Veuillez en choisir un autre.",
                    "Erreur d'inscription",
                    JOptionPane.ERROR_MESSAGE);
        } else {
            // Ajouter l'utilisateur à la base de données
            database.addUser(newUser);
            JOptionPane.showMessageDialog(registerView,
                    "Compte créé avec succès ! Bienvenue, " + newUser.getName() + " 🎉",
                    "Inscription réussie",
                    JOptionPane.INFORMATION_MESSAGE);
            // Fermer la fenêtre après succès
            SwingUtilities.getWindowAncestor(registerView).dispose();
        }
    }
}
