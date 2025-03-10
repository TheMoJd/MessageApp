package com.ubo.tp.message.controller.message;

import com.ubo.tp.message.datamodel.Message;

import java.util.Set;

public interface IMessage {

  void listMessagesChanged(Set<Message> referenceList);

}
