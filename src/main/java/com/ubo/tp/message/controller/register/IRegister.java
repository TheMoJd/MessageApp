package com.ubo.tp.message.controller.register;

import com.ubo.tp.message.datamodel.User;

/**
 * Interface définissant les opérations de création de compte.
 */
public interface IRegister {
    void addObserver(IRegisterObserver observer);
    void removeObserver(IRegisterObserver observer);
    void registerAccount(User newUser);
}
