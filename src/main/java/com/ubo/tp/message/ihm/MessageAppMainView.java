// src/main/java/com/ubo/tp/message/ihm/MessageAppMainView.java
package com.ubo.tp.message.ihm;

import com.ubo.tp.message.action.IAction;
import com.ubo.tp.message.action.IActionObserver;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MessageAppMainView extends JFrame implements IAction {
    private static final int WINDOW_WIDTH = 1024;
    private static final int WINDOW_HEIGHT = 768;
    private static final int PADDING = 5;

    private final NavBarView navBar;
    private final JPanel contentPanel;
    private final JPanel centerPanel;

    private final List<IActionObserver> observers = new ArrayList<>();

    public MessageAppMainView() {
        setTitle("Message Application");
        setIconImage(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("images/logo_50.png"))).getImage());
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(PADDING, PADDING, PADDING, PADDING);

        navBar = new NavBarView();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.weighty = 0.0;
        add(navBar, gbc);

        contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        contentPanel.setBackground(Color.LIGHT_GRAY);

        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        add(contentPanel, gbc);

        centerPanel = new JPanel(new GridBagLayout());
        JButton loginButton = new JButton("Connexion");
        JButton registerButton = new JButton("Inscription");
        centerPanel.add(loginButton, new GridBagConstraints());
        centerPanel.add(registerButton, new GridBagConstraints());

        contentPanel.add(centerPanel, BorderLayout.CENTER);

        initMenuBar();
        setVisible(true);
        manageAction();
    }

    private void initMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        JMenuItem exitMenuItem = new JMenuItem("Exit", new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("images/exitIcon_20.png"))));
        exitMenuItem.addActionListener(e -> System.exit(0));
        fileMenu.add(exitMenuItem);

        JMenu helpMenu = new JMenu("Help");
        JMenuItem aboutMenuItem = new JMenuItem("About", new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("images/logo_20.png"))));
        aboutMenuItem.addActionListener(e -> showAboutDialog());
        helpMenu.add(aboutMenuItem);

        menuBar.add(fileMenu);
        menuBar.add(helpMenu);

        setJMenuBar(menuBar);
    }

    private void showAboutDialog() {
        JOptionPane.showMessageDialog(this, "UBO M2-TIIL\nDépartement Informatique", "About", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("images/logo_50.png"))));
    }

    private void manageAction() {
        // Add action listeners for the buttons in the center panel
        for (Component component : centerPanel.getComponents()) {
            if (component instanceof JButton) {
                JButton button = (JButton) component;
                if (button.getText().equals("Connexion")) {
                    button.addActionListener(e -> loginAction());
                } else if (button.getText().equals("Inscription")) {
                    button.addActionListener(e -> registerAction());
                }
            }
        }

        // Add action listeners for the buttons in the navbar
        navBar.getLogoutButton().addActionListener(e -> disconnectAction());
        navBar.getProfilButton().addActionListener(e -> profileAction());
        navBar.getUsersButton().addActionListener(e -> listUsersAction());
        }


    public void removeView() {
        contentPanel.removeAll();
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    public void addView(JPanel view) {
        contentPanel.removeAll();
        contentPanel.add(view, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    @Override
    public void addObserver(IActionObserver observer) {
        this.observers.add(observer);
    }

    @Override
    public void removeObserver(IActionObserver observer) {
        this.observers.remove(observer);
    }

    @Override
    public void exitAction() {
        for (IActionObserver observer : observers) {
            observer.notifyExitAction();
        }
    }

    @Override
    public void loginAction() {
        for (IActionObserver observer : observers) {
            observer.notifyLoginAction();
        }
    }

    @Override
    public void registerAction() {
        for (IActionObserver observer : observers) {
            observer.notifyRegisterAction();
        }
    }

    @Override
    public void disconnectAction() {
        for (IActionObserver observer : observers) {
            observer.notifyDisconnectAction();
        }
    }

    @Override
    public void profileAction() {
        for (IActionObserver observer : observers) {
            observer.notifyProfileAction();
        }
    }

    @Override
    public void listUsersAction() {
        for (IActionObserver observer : observers) {
            observer.notifyListUsersAction();
        }
    }

    public NavBarView getNavBar() {
        return navBar;
    }

    public JPanel getCenterPanel() {
        return centerPanel;
    }
}