package com.ubo.tp.message.controller.user;

import com.ubo.tp.message.core.database.IDatabase;
import com.ubo.tp.message.core.session.Session;
import com.ubo.tp.message.datamodel.User;
import com.ubo.tp.message.ihm.ListUsersView;

import java.util.Set;

public class UserController implements IUserObserver {

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
}
