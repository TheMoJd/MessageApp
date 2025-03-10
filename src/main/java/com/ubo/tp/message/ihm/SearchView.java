package com.ubo.tp.message.ihm;

import com.ubo.tp.message.datamodel.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;

public class SearchView extends JPanel {

    private JTextField searchField;
    private JButton searchButton;
    private JList<User> userListView;
    private DefaultListModel<User> listModel;
    private List<User> allUsers; // liste complète des utilisateurs

    public SearchView() {
        allUsers = new ArrayList<>();
        init();
    }

    private void init() {
        setLayout(new GridBagLayout());
        setBackground(Color.WHITE);

        // Création du champ de recherche et du bouton
        searchField = new JTextField(20);
        searchButton = new JButton("Rechercher");

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Positionnement du champ de recherche
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(searchField, gbc);

        // Positionnement du bouton de recherche
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        add(searchButton, gbc);

        // Création et configuration du modèle et de la liste pour afficher les résultats
        listModel = new DefaultListModel<>();
        userListView = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(userListView);
        scrollPane.setPreferredSize(new Dimension(300, 200));

        // Positionnement du composant de liste sous le champ de recherche
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;
        add(scrollPane, gbc);

        // Ajout d'un ActionListener sur le bouton pour lancer la recherche
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performSearch();
            }
        });
    }

    /**
     * Permet de fournir la liste complète des utilisateurs.
     * @param users la liste des utilisateurs à rechercher.
     */
    public void setUserList(List<User> users) {
        this.allUsers = users;
        // Affiche initialement tous les utilisateurs
        updateListModel(users);
    }

    /**
     * Effectue le filtrage des utilisateurs en fonction du texte saisi.
     */
    private void performSearch() {
        String query = searchField.getText().trim().toLowerCase();
        List<User> filteredUsers = new ArrayList<>();

        for (User user : allUsers) {
            // On vérifie si le tag ou le nom de l'utilisateur contient la chaîne recherchée
            if ((user.getUserTag() != null && user.getUserTag().toLowerCase().contains(query))
                    || (user.getName() != null && user.getName().toLowerCase().contains(query))) {
                filteredUsers.add(user);
            }
        }

        updateListModel(filteredUsers);
    }

    /**
     * Met à jour le modèle de la JList avec la liste des utilisateurs fournie.
     * @param users la liste des utilisateurs à afficher.
     */
    private void updateListModel(List<User> users) {
        listModel.clear();
        for (User user : users) {
            listModel.addElement(user);
        }
    }
}
