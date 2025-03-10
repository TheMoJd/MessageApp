package com.ubo.tp.message.ihm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class SearchView extends JPanel {

    private JTextField searchField;
    private JButton searchButton;

    public SearchView() {
        init();
    }

    private void init() {
        setLayout(new GridBagLayout());
        setBackground(Color.WHITE);

        searchField = new JTextField(20);
        searchButton = new JButton("Rechercher");

        add(searchField, new GridBagConstraints(
                0, 0, 1, 1, 1, 0,
                GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                new Insets(5, 5, 5, 5), 0, 0));

        add(searchButton, new GridBagConstraints(
                1, 0, 1, 1, 0, 0,
                GridBagConstraints.EAST, GridBagConstraints.NONE,
                new Insets(5, 5, 5, 5), 0, 0));
    }

    /**
     * Ajoute un écouteur sur le bouton de recherche.
     */
    public void addSearchActionListener(ActionListener listener) {
        searchButton.addActionListener(listener);
    }

    /**
     * Retourne le texte saisi dans le champ de recherche.
     */
    public String getSearchQuery() {
        return searchField.getText();
    }
}
