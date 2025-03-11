package com.ubo.tp.message.controller.message;

import com.ubo.tp.message.core.EntityManager;
import com.ubo.tp.message.core.database.IDatabase;
import com.ubo.tp.message.core.session.Session;
import com.ubo.tp.message.datamodel.Message;
import com.ubo.tp.message.ihm.MessageListView;

public class MessageController implements IMessageObserver {

  MessageListView messageListView;

  IDatabase database;

  Session session;

  private EntityManager mEntityManager;

  public MessageController(IDatabase database, Session session, EntityManager entityManager) {
    this.messageListView = new MessageListView(session, database.getMessages());
    this.messageListView.addObserver(this);
    this.database = database;
    this.session = session;
    this.mEntityManager = entityManager;
  }

  public MessageListView getMessageListView() {
    return messageListView;
  }

  @Override
  public void notifyMessage(Message message) {
    database.addMessage(message);
    mEntityManager.writeMessageFile(message);
    messageListView.refreshView(database.getMessages());
  }
}
