package com.ubo.tp.message.ihm;

import com.ubo.tp.message.controller.message.IMessage;
import com.ubo.tp.message.controller.message.IMessageObserver;
import com.ubo.tp.message.core.session.ISession;
import com.ubo.tp.message.datamodel.Message;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class MessageListView extends JPanel implements IMessage {

  private ISession session;
  protected JPanel messagesPanel;
  protected long dateTime;
  private JTextField messageField;
  private JButton sendButton;
  private List<IMessageObserver> observers;

  public MessageListView(ISession session, Set<Message> messages) {
    this.session = session;
    observers = new ArrayList<>();
    setLayout(new BorderLayout());
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
        if (!messageText.isEmpty()) {
          Message message = new Message(session.getConnectedUser(), messageText);
          for (IMessageObserver observer : observers) {
            observer.notifyMessage(message);
          }
          messageField.setText("");
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
  public void addObserver(IMessageObserver observer) {
    observers.add(observer);
  }

  @Override
  public void removeObserver(IMessageObserver observer) {
    observers.remove(observer);
  }

  @Override
  public void listMessagesChanged(Set<Message> referenceList) {
  }
}
