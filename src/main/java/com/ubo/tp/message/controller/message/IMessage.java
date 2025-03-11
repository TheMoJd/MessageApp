package com.ubo.tp.message.controller.message;

import com.ubo.tp.message.controller.login.ILoginObserver;
import com.ubo.tp.message.datamodel.Message;

import java.util.Set;

public interface IMessage {

  void addObserver(IMessageObserver observer);

  void removeObserver(IMessageObserver observer);

  void listMessagesChanged(Set<Message> referenceList);

}
