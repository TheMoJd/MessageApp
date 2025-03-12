package com.ubo.tp.message.controller.message;

import com.ubo.tp.message.controller.search.ISearchObserver;
import com.ubo.tp.message.core.EntityManager;
import com.ubo.tp.message.core.database.IDatabase;
import com.ubo.tp.message.core.session.Session;
import com.ubo.tp.message.datamodel.Message;
import com.ubo.tp.message.ihm.MessageListView;

import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MessageController implements IMessageObserver, ISearchObserver {

  MessageListView messageListView;

  IDatabase database;

  Session session;

  private EntityManager mEntityManager;

  public MessageController(IDatabase database, Session session, EntityManager entityManager) {
    this.messageListView = new MessageListView(session);
    this.messageListView.addMessageObserver(this);
    this.messageListView.addSearchObserver(this);
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

  public Set<Message> getFilteredMessages(String searchString) {
    Set<Message> messagesList = this.database.getMessages();
    Predicate<Message> streamsPredicate;

    // Multi-filtres possibles (séparés par des espaces)
    for (String filter : searchString.split("\\s+")) {
      if (searchString.isEmpty()) {
        streamsPredicate = message -> true;
      } else if (filter.charAt(0) == '@') {
        // messages émis par cet utilisateur et ceux dans lesquels l'utilisateur est cité
        streamsPredicate = message -> message.getSender().getUserTag().equals(filter.substring(1)) ||
                message.getUserTags().contains(filter.substring(1));
      } else if (filter.charAt(0) == '#') {
        // messages contenant ce tag
        streamsPredicate = message -> message.getTags().contains(filter.substring(1));
      } else {
        // union des critères précédents
        streamsPredicate = message -> message.getSender().getUserTag().equals(filter) ||
                message.getUserTags().contains(filter) || message.getTags().contains(filter);
      }

      messagesList = messagesList.stream()
              .filter(streamsPredicate)
              .collect(Collectors.toSet());
    }

    return messagesList;
  }

  @Override
  public void notifySearch(String searchString) {
    messageListView.refreshView(getFilteredMessages(searchString));
  }
}
