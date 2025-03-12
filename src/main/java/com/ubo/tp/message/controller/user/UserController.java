package com.ubo.tp.message.controller.user;

import com.ubo.tp.message.controller.search.ISearchObserver;
import com.ubo.tp.message.core.database.IDatabase;
import com.ubo.tp.message.core.session.Session;
import com.ubo.tp.message.datamodel.User;
import com.ubo.tp.message.ihm.ListUsersView;

import java.util.HashSet;
import java.util.Set;

public class UserController implements IUserObserver, ISearchObserver {

    private final ListUsersView listUsersView;
    private final IDatabase database;
    private final Session session;
    private Set<User> users;

    public UserController(IDatabase database, Session session) {
        this.database = database;
        this.session = session;
        this.users = database.getUsers();
        this.listUsersView = new ListUsersView(this);
        this.listUsersView.addObserver(this);
        this.listUsersView.addSearchObserver(this);
    }

    @Override
    public void notifyListUsers() {
        users = listUsers();
        listUsersView.setUsers(users);
    }

    @Override
    public void notifyFollow(User user) {
        User currentUser = session.getConnectedUser();
        if (!isFollowing(user)) {
            currentUser.addFollowing(user.getUserTag());
        }
    }

    @Override
    public void notifyUnFollow(User user) {
        User currentUser = session.getConnectedUser();
        if (isFollowing(user)) {
            currentUser.removeFollowing(user.getUserTag());
        }
    }

    private Set<User> listUsers() {
        Set<User> users = database.getUsers();
        users.remove(session.getConnectedUser());
        return users;
    }

    public ListUsersView getListUsersView() {
        return listUsersView;
    }

    public boolean isFollowing(User user) {
        User currentUser = session.getConnectedUser();
        return currentUser.getFollows().contains(user.getUserTag());
    }

    @Override
    public void notifySearch(String searchString) {
        Set<User> filteredUsers = new HashSet<>();

        if (searchString.isEmpty()) {
            // Si le champ est vide, on affiche tous les utilisateurs
            filteredUsers.addAll(users);
        } else {
            for (User user : users) {
                // On vérifie si le tag ou le nom de l'utilisateur contient la chaîne recherchée
                if ((user.getUserTag() != null && user.getUserTag().toLowerCase().contains(searchString))
                        || (user.getName() != null && user.getName().toLowerCase().contains(searchString))) {
                    filteredUsers.add(user);
                }
            }
        }
        listUsersView.refreshUserView(filteredUsers);
    }
}
