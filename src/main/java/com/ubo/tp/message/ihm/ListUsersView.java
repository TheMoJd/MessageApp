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
    private final GridBagConstraints gbc = new GridBagConstraints();
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

        updateListUsers();
    }

    private void updateListUsers() {
        removeAll();

        add(searchView, new GridBagConstraints(
                0, 0, 1, 1, 1.0, 0,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(5, 5, 5, 5), 0, 0));

        int row = 1;
        for (User user : users) {
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
    }

    @Override
    public void unFollow(User user) {
    }
}