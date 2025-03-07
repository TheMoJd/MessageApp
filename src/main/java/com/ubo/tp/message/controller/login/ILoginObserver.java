package com.ubo.tp.message.controller.login;

import com.ubo.tp.message.datamodel.User;

public interface ILoginObserver {

    void notifyLogin(User connectedUser);

}
