package com.ubo.tp.message.controller.message;

import com.ubo.tp.message.core.database.IDatabase;
import com.ubo.tp.message.core.session.Session;
import com.ubo.tp.message.datamodel.Message;
import com.ubo.tp.message.ihm.MessageListView;

public class MessageController implements IMessageObserver {

  MessageListView messageListView;

  IDatabase database;

  Session session;

  public MessageController(IDatabase database, Session session) {
    this.messageListView = new MessageListView(session);
    this.database = database;
    this.session = session;
  }

  public MessageListView getMessageListView() {
    return messageListView;
  }

  @Override
  public void notifyMessage(String message) {
    database.addMessage(new Message(session.getConnectedUser(), message));
    messageListView.refreshView();
  }
}
