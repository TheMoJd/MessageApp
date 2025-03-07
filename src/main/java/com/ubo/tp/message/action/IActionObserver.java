package com.ubo.tp.message.action;

public interface IActionObserver {

    void notifyLoginAction();

    void notifyExitAction();

    void notifyRegisterAction();

    void notifyDisconnectAction();

    void notifyProfileAction();

    void notifyListUsersAction();
}
