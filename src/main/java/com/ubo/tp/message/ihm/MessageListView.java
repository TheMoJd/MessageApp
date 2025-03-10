package com.ubo.tp.message.ihm;

import com.ubo.tp.message.controller.message.IMessage;
import com.ubo.tp.message.controller.message.IMessageObserver;
import com.ubo.tp.message.core.session.ISession;
import com.ubo.tp.message.datamodel.Message;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class MessageListView extends JPanel implements IMessage {

  private ISession session;
  private Set<Message> messages;
  protected JPanel messagesPanel;
  protected long dateTime;
  private JTextField messageField;
  private JButton sendButton;

  public MessageListView(ISession session) {
    this.session = session;
    messages = new HashSet<>();
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
          messages.add(message);
          messageField.setText("");
          refreshView();
        }
      }
    });

    this.updateDateTime();
    this.setVisible(true);
  }

  public void refreshView() {
    messagesPanel.removeAll();
    Set<Message> messagesSorted = new HashSet<>(messages);
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
  public void listMessagesChanged(Set<Message> referenceList) {
    this.messages = referenceList;
    this.refreshView();
  }
}
