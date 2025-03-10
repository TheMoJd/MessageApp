package com.ubo.tp.message.ihm;

import com.ubo.tp.message.datamodel.Message;
import com.ubo.tp.message.datamodel.User;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class MessageView extends JPanel implements MouseListener {

  protected Color defaultBackgroundColor;

  protected LineBorder defaultLineBorder;

  public MessageView(Message message, boolean ownMessage, boolean followed) {
    this.setLayout(new GridBagLayout());
    this.removeAll();
    this.addMouseListener(this);

    // bordures
    this.setOpaque(true);
    this.defaultLineBorder = new LineBorder(Color.BLACK, 1, true);
    this.setBorder(this.defaultLineBorder);
    if (ownMessage) {
      this.defaultBackgroundColor = Color.CYAN;
    } else if (followed) {
      this.defaultBackgroundColor = Color.PINK;
    } else {
      this.defaultBackgroundColor = Color.WHITE;
    }
    this.setBackground(this.defaultBackgroundColor);

    // nom + tag
    User user = message.getSender();
    JLabel userDetails = new JLabel(user.getName() + " (" + user.getUserTag() + ")", SwingConstants.LEFT);
    Font boldFont = new Font(userDetails.getFont().getName(), Font.BOLD, userDetails.getFont().getSize());
    userDetails.setFont(boldFont);
    this.add(userDetails, new GridBagConstraints(1, 0, 1, 1, 1, 1, GridBagConstraints.WEST,
        GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));

    // date
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
    Date date = new Date(message.getEmissionDate());
    JLabel dateMsg = new JLabel(date.toString(), SwingConstants.RIGHT);
    this.add(dateMsg, new GridBagConstraints(2, 0, 1, 1, 1, 1, GridBagConstraints.EAST,
        GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));

    // contenu
    JLabel content = new JLabel("<html>"+ message.getText() +"</html>");
    this.add(content, new GridBagConstraints(1, 1, 2, 1, 1, 1, GridBagConstraints.WEST,
        GridBagConstraints.BOTH, new Insets(10, 5, 5, 5), 0, 0));

    this.setVisible(true);
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    // rien
  }

  @Override
  public void mousePressed(MouseEvent e) {
    // rien
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    // rien
  }

  @Override
  public void mouseEntered(MouseEvent e) {
    this.setBackground(Color.lightGray);
    this.setBorder(new LineBorder(Color.RED, 1, true));
  }

  @Override
  public void mouseExited(MouseEvent e) {
    this.setBackground(this.defaultBackgroundColor);
    this.setBorder(this.defaultLineBorder);
  }
}
