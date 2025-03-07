package com.ubo.tp.message.controller.user;

import com.ubo.tp.message.datamodel.User;

public interface IUser {

    /**
     * Ajoute un observateur à la session.
     *
     * @param observer
     */
    void addObserver(IUserObserver observer);

    /**
     * Retire un observateur à la session.
     *
     * @param observer
     */
    void removeObserver(IUserObserver observer);

    void listUsers();

    void follow(User user);

    void unFollow(User user);
}
