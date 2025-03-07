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
    private JButton followButton;
    private JButton unfollowButton;
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

        followButton = new JButton("Suivre");
        unfollowButton = new JButton("Ne plus suivre");

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

        addFollowButton();
        addUnfollowButton();
    }

    private void manageAction() {
        followButton.addActionListener(e -> follow(user));
        unfollowButton.addActionListener(e -> unFollow(user));
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

    public void addFollowButton() {
        add(followButton, new GridBagConstraints(
                2, 0, 1, 2, 0, 0,
                GridBagConstraints.EAST, GridBagConstraints.NONE,
                new Insets(10, 10, 10, 10), 0, 0));
    }

    public void removeFollowButton() {
        remove(followButton);
    }

    public void addUnfollowButton() {
        add(unfollowButton, new GridBagConstraints(
                3, 0, 1, 2, 0, 0,
                GridBagConstraints.EAST, GridBagConstraints.NONE,
                new Insets(10, 10, 10, 10), 0, 0));
    }

    public void removeUnfollowButton() {
        remove(unfollowButton);
    }
}