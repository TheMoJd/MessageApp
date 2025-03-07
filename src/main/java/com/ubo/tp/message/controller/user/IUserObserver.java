package com.ubo.tp.message.controller.user;

import com.ubo.tp.message.datamodel.User;

public interface IUserObserver {

    void notifyListUsers();

    void notifyFollow(User user);

    void notifyUnFollow(User user);
}
