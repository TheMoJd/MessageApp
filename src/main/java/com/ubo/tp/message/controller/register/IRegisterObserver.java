package com.ubo.tp.message.controller.register;

import com.ubo.tp.message.datamodel.User;

/**
 * Interface des observateurs de la création de compte.
 */
public interface IRegisterObserver {
    void notifyRegisterAccount(User newUser);
}
