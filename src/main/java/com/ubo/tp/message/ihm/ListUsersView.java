package com.ubo.tp.message.ihm;

import com.ubo.tp.message.controller.search.ISearch;
import com.ubo.tp.message.controller.search.ISearchObserver;
import com.ubo.tp.message.controller.user.IUser;
import com.ubo.tp.message.controller.user.IUserObserver;
import com.ubo.tp.message.controller.user.UserController;
import com.ubo.tp.message.datamodel.User;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class ListUsersView extends JPanel implements IUser, ISearch {

    private UserController userController;
    private Set<User> users = new HashSet<>();
    private final List<IUserObserver> observers = new ArrayList<>();
    private List<ISearchObserver> searchObservers = new ArrayList<>();
    private SearchView searchView;

    public ListUsersView(UserController userController) {
        this.userController = userController;

        setLayout(new GridBagLayout());
        setBackground(Color.WHITE);

        searchView = new SearchView();
        add(searchView, new GridBagConstraints(
                0, 0, 1, 1, 1.0, 0,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(5, 5, 5, 5), 0, 0));

        searchView.addSearchActionListener(e -> search(searchView.getSearchQuery().toLowerCase().trim()));

        refreshUserView(users);
    }

    public void refreshUserView(Set<User> filteredUsers) {
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

    /**
     * Définit la liste complète des utilisateurs et met à jour l'affichage.
     */
    public void setUsers(Set<User> users) {
        this.users = users;
        refreshUserView(users);
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
        refreshUserView(users);
    }

    @Override
    public void follow(User user) {
        // Implémentation éventuelle pour suivre un utilisateur
    }

    @Override
    public void unFollow(User user) {
        // Implémentation éventuelle pour ne plus suivre un utilisateur
    }

    @Override
    public void addSearchObserver(ISearchObserver observer) {
        searchObservers.add(observer);
    }

    @Override
    public void removeSearchObserver(ISearchObserver observer) {
        searchObservers.remove(observer);
    }

    @Override
    public void search(String searchString) {
        for (ISearchObserver observer : searchObservers) {
            observer.notifySearch(searchString);
        }
    }
}
