package com.ubo.tp.message.ihm;

import com.ubo.tp.message.controller.user.IUser;
import com.ubo.tp.message.controller.user.IUserObserver;
import com.ubo.tp.message.controller.user.UserController;
import com.ubo.tp.message.datamodel.User;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class UserView extends JPanel implements IUser {

    private final UserController userController;
    private final User user;
    private JLabel avatarLabel;
    private JLabel tagLabel;
    private JLabel nameLabel;
    private JButton followButton; // Bouton unique de suivi
    private final List<IUserObserver> observers = new ArrayList<>();

    public UserView(User user, UserController userController) {
        this.userController = userController;
        this.addObserver(userController);
        this.user = user;
        init();
        manageAction();
    }

    private void init() {
        setLayout(new GridBagLayout());
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));

        avatarLabel = new JLabel();
        avatarLabel.setPreferredSize(new Dimension(50, 50));
        avatarLabel.setOpaque(true);
        avatarLabel.setBackground(Color.LIGHT_GRAY);
        avatarLabel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));

        tagLabel = new JLabel("@" + user.getUserTag());
        nameLabel = new JLabel(user.getName());
        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));

        // Bouton de suivi unique
        followButton = new JButton();
        updateFollowButtonLabel();

        add(avatarLabel, new GridBagConstraints(
                0, 0, 1, 2, 0, 0,
                GridBagConstraints.WEST, GridBagConstraints.NONE,
                new Insets(10, 10, 10, 10), 0, 0));

        add(tagLabel, new GridBagConstraints(
                1, 0, 1, 1, 1, 0,
                GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                new Insets(5, 5, 0, 5), 0, 0));

        add(nameLabel, new GridBagConstraints(
                1, 1, 1, 1, 1, 0,
                GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                new Insets(0, 5, 5, 5), 0, 0));

        add(followButton, new GridBagConstraints(
                2, 0, 1, 2, 0, 0,
                GridBagConstraints.EAST, GridBagConstraints.NONE,
                new Insets(10, 10, 10, 10), 0, 0));
    }

    private void manageAction() {
        followButton.addActionListener(e -> {
            // Si l'utilisateur est suivi, le désabonner, sinon l'abonner
            if (userController.isFollowing(user)) {
                unFollow(user);
            } else {
                follow(user);
            }
            updateFollowButtonLabel();
        });
    }

    private void updateFollowButtonLabel() {
        if (userController.isFollowing(user)) {
            followButton.setText("Ne plus suivre");
        } else {
            followButton.setText("Suivre");
        }
    }

    @Override
    public void addObserver(IUserObserver observer) {
        this.observers.add(observer);
    }

    @Override
    public void removeObserver(IUserObserver observer) {
        this.observers.remove(observer);
    }

    @Override
    public void listUsers() {
        // Pas d'action ici dans ce contexte
    }

    @Override
    public void follow(User user) {
        for (IUserObserver observer : observers) {
            observer.notifyFollow(user);
        }
    }

    @Override
    public void unFollow(User user) {
        for (IUserObserver observer : observers) {
            observer.notifyUnFollow(user);
        }
    }
}
