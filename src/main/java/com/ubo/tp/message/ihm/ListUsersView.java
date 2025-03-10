package com.ubo.tp.message.ihm;

import com.ubo.tp.message.controller.user.IUser;
import com.ubo.tp.message.controller.user.IUserObserver;
import com.ubo.tp.message.controller.user.UserController;
import com.ubo.tp.message.datamodel.User;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ListUsersView extends JPanel implements IUser {

    private UserController userController;
    private Set<User> users = new HashSet<>();
    private final List<IUserObserver> observers = new ArrayList<>();
    private SearchView searchView;

    public ListUsersView(UserController userController) {
        this.userController = userController;

        setLayout(new GridBagLayout());
        setBackground(Color.WHITE);

        // Ajout de la barre de recherche
        searchView = new SearchView();
        add(searchView, new GridBagConstraints(
                0, 0, 1, 1, 1.0, 0,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(5, 5, 5, 5), 0, 0));

        // Association d'un listener pour déclencher la recherche
        searchView.addSearchActionListener(e -> performSearch());

        updateListUsers();
    }

    /**
     * Met à jour l'affichage en listant tous les utilisateurs.
     */
    private void updateListUsers() {
        removeAll();

        // On réajoute la barre de recherche en haut
        add(searchView, new GridBagConstraints(
                0, 0, 1, 1, 1.0, 0,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(5, 5, 5, 5), 0, 0));

        int row = 1;
        // Affiche chaque utilisateur via son UserView
        for (User user : users) {
            add(new UserView(user, userController), new GridBagConstraints(
                    0, row++, 1, 1, 1.0, 0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(5, 5, 5, 5), 0, 0));
        }

        revalidate();
        repaint();
    }

    /**
     * Effectue le filtrage des utilisateurs en fonction du texte saisi dans la SearchView.
     */
    private void performSearch() {
        String query = searchView.getSearchQuery().toLowerCase().trim();
        Set<User> filteredUsers = new HashSet<>();

        if (query.isEmpty()) {
            // Si le champ est vide, on affiche tous les utilisateurs
            filteredUsers = users;
        } else {
            for (User user : users) {
                // On vérifie si le tag ou le nom de l'utilisateur contient la chaîne recherchée
                if ((user.getUserTag() != null && user.getUserTag().toLowerCase().contains(query))
                        || (user.getName() != null && user.getName().toLowerCase().contains(query))) {
                    filteredUsers.add(user);
                }
            }
        }

        // Mise à jour de l'affichage avec les utilisateurs filtrés
        removeAll();

        // Réajout de la barre de recherche
        add(searchView, new GridBagConstraints(
                0, 0, 1, 1, 1.0, 0,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(5, 5, 5, 5), 0, 0));

        int row = 1;
        for (User user : filteredUsers) {
            add(new UserView(user, userController), new GridBagConstraints(
                    0, row++, 1, 1, 1.0, 0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(5, 5, 5, 5), 0, 0));
        }

        revalidate();
        repaint();
    }

    public Set<User> getUsers() {
        return users;
    }

    /**
     * Définit la liste complète des utilisateurs et met à jour l'affichage.
     */
    public void setUsers(Set<User> users) {
        this.users = users;
        updateListUsers();
    }

    @Override
    public void addObserver(IUserObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(IUserObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void listUsers() {
        for (IUserObserver observer : observers) {
            observer.notifyListUsers();
        }
        updateListUsers();
    }

    @Override
    public void follow(User user) {
        // Implémentation éventuelle pour suivre un utilisateur
    }

    @Override
    public void unFollow(User user) {
        // Implémentation éventuelle pour ne plus suivre un utilisateur
    }
}
