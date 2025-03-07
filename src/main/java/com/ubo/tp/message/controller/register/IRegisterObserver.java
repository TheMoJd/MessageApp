package com.ubo.tp.message.controller.register;

import com.ubo.tp.message.datamodel.User;

public interface IRegisterObserver {

    void notifyRegister(User createdUser, String confirmPassword);
}
