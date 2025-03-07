package com.ubo.tp.message.controller.register;

import com.ubo.tp.message.datamodel.User;

public interface IRegister {

    /**
     * Ajoute un observateur à la session.
     *
     * @param observer
     */
    void addObserver(IRegisterObserver observer);

    /**
     * Retire un observateur à la session.
     *
     * @param observer
     */
    void removeObserver(IRegisterObserver observer);

    void register(User createdUser, String confirmPassword);
}
