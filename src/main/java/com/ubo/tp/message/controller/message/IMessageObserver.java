package com.ubo.tp.message.controller.message;

import com.ubo.tp.message.datamodel.Message;

public interface IMessageObserver {

    void notifyMessage(Message message);

}
