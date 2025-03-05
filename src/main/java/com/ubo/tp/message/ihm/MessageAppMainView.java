package com.ubo.tp.message.ihm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Objects;

public class MessageAppMainView extends JFrame {

    private Button registerbutton = new Button("Inscription");

    public MessageAppMainView() {
        setTitle("Message Application");
        setIconImage(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("images/logo_50.png"))).getImage());

        setSize(1024, 768);

//        this.addWindowListener(new WindowAdapter() {
//            @Override
//            public void windowClosed(WindowEvent e) {
//                super.windowClosed(e);
//            }
//        });
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initMenuBar();

    }

    private void initMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        // File Menu
        JMenu fileMenu = new JMenu("File");
        JMenuItem exitMenuItem = new JMenuItem("Exit", new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("images/exitIcon_20.png"))));
        exitMenuItem.addActionListener(e -> System.exit(0));
        fileMenu.add(exitMenuItem);

        // Help Menu
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

    public void addLoginPane(LoginView loginView) {
        getContentPane().add(loginView);
        revalidate();
        repaint();
    }

    public void removeLoginPane(LoginView loginView) {
        getContentPane().remove(loginView);
        revalidate();
        repaint();
    }
}