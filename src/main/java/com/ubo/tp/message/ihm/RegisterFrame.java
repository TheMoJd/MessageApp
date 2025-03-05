package com.ubo.tp.message.ihm;

import com.ubo.tp.message.core.database.IDatabase;
import com.ubo.tp.message.controller.register.RegisterController;

import javax.swing.*;
import java.awt.*;

public class RegisterFrame extends JFrame {
    public RegisterFrame(IDatabase database) {
        super("Création de compte");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(450, 350);
        setLocationRelativeTo(null);

        // Instanciation du panneau d'inscription
        RegisterView registerView = new RegisterView();
        add(registerView, BorderLayout.CENTER);

        // Créer et attacher le contrôleur d'inscription avec l'instance de la base de données
        new RegisterController(registerView, database);

        // Rendre visible la fenêtre
        setVisible(true);
    }
}
