package com.ubo.tp.message.ihm;

import com.ubo.tp.message.controller.message.IMessage;
import com.ubo.tp.message.controller.message.IMessageObserver;
import com.ubo.tp.message.controller.search.ISearch;
import com.ubo.tp.message.controller.search.ISearchObserver;
import com.ubo.tp.message.core.session.ISession;
import com.ubo.tp.message.datamodel.Message;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class MessageListView extends JPanel implements IMessage, ISearch {

  private ISession session;
  protected JPanel messagesPanel;
  protected long dateTime;
  private JTextField messageField;
  private JButton sendButton;
  private JTextField searchField;
  private List<IMessageObserver> messageObservers;
  private List<ISearchObserver> searchObservers;

  public long getDateTime() {
    return dateTime;
  }

  public MessageListView(ISession session) {
    this.session = session;
    messageObservers = new ArrayList<>();
    searchObservers = new ArrayList<>();
    setLayout(new BorderLayout());

    // Search panel
    JPanel searchPanel = new JPanel(new BorderLayout());
    searchField = new JTextField();
    JButton searchButton = new JButton("Search");
    searchPanel.add(searchField, BorderLayout.CENTER);
    searchPanel.add(searchButton, BorderLayout.EAST);
    add(searchPanel, BorderLayout.NORTH);

    searchButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        search(searchField.getText());
      }
    });

    // Message panel
    messagesPanel = new JPanel();
    messagesPanel.setLayout(new BoxLayout(messagesPanel, BoxLayout.Y_AXIS));
    JScrollPane scrollPane = new JScrollPane(messagesPanel);
    add(scrollPane, BorderLayout.CENTER);

    JPanel inputPanel = new JPanel();
    inputPanel.setLayout(new BorderLayout());
    messageField = new JTextField();
    sendButton = new JButton("Send");
    inputPanel.add(messageField, BorderLayout.CENTER);
    inputPanel.add(sendButton, BorderLayout.EAST);
    add(inputPanel, BorderLayout.SOUTH);

    sendButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String messageText = messageField.getText();
        if ((!messageText.isEmpty()) && (messageText.length() <= 200)) {
          Message message = new Message(session.getConnectedUser(), messageText);
          for (IMessageObserver observer : messageObservers) {
            observer.notifyMessage(message);
          }
          messageField.setText("");
        } else {
          JOptionPane.showMessageDialog(null, "la taille du message doit faire moins de 200 caractères (le message actuel fait " + messageText.length() + ")", "Error", JOptionPane.ERROR_MESSAGE);
        }
      }
    });
    this.updateDateTime();
    this.setVisible(true);
  }

  public void refreshView(Set<Message> messages) {
    messagesPanel.removeAll();
    Set<Message> messagesSorted = new TreeSet<>((msg1, msg2) -> Long.compare(msg2.getEmissionDate(), msg1.getEmissionDate()));
    messagesSorted.addAll(messages);
    for (Message message : messagesSorted) {
      boolean userMessage = message.getSender().getUserTag().equals(session.getConnectedUser().getUserTag());
      boolean newFollowedMsg = message.getEmissionDate() > System.currentTimeMillis() && session.getConnectedUser().isFollowing(message.getSender());
      messagesPanel.add(new MessageView(message, userMessage, newFollowedMsg));
    }
    revalidate();
    repaint();
  }

  public void updateDateTime() {
    this.dateTime = new Date().getTime();
  }

  @Override
  public void addMessageObserver(IMessageObserver observer) {
    messageObservers.add(observer);
  }

  @Override
  public void removeMessageObserver(IMessageObserver observer) {
    messageObservers.remove(observer);
  }

  @Override
  public void addSearchObserver(ISearchObserver observer) {
    searchObservers.add(observer);
  }

  @Override
  public void removeSearchObserver(ISearchObserver observer) {
    searchObservers.remove(observer);
  }

  @Override
  public void search(String searchString) {
    for (ISearchObserver observer : searchObservers) {
      observer.notifySearch(searchString);
    }
  }
}
