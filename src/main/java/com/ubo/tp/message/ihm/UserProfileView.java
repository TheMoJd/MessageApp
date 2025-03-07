package com.ubo.tp.message.ihm;

import com.ubo.tp.message.datamodel.User;

import javax.swing.*;
import java.awt.*;

import static com.ubo.tp.message.common.Constants.AVATAR_PATH;

public class UserProfileView extends JPanel {

    private final JLabel nameLabel;
    private final JLabel userTagLabel;
    private final JLabel followsLabel;
    private final JLabel avatarLabel;

    public UserProfileView(User user) {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        avatarLabel = new JLabel();
        initAvatar(user.getAvatarPath() == null ? AVATAR_PATH : user.getAvatarPath());
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(avatarLabel, gbc);

        nameLabel = new JLabel("Nom: " + user.getName());
        gbc.gridy++;
        gbc.gridwidth = 1;
        add(nameLabel, gbc);

        userTagLabel = new JLabel("Tag: @" + user.getUserTag());
        gbc.gridy++;
        add(userTagLabel, gbc);

        followsLabel = new JLabel("Suis: " + String.join(", ", user.getFollows()));
        gbc.gridy++;
        add(followsLabel, gbc);
    }

    private void initAvatar(String avatarPath) {
        ImageIcon avatarIcon = new ImageIcon(avatarPath);
        Image image = avatarIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        avatarLabel.setIcon(new ImageIcon(image));
    }
}