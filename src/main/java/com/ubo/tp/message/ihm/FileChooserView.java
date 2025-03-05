package com.ubo.tp.message.ihm;

import javax.swing.*;
import java.io.File;

public class FileChooserView extends JFileChooser {

    public FileChooserView() {
        initFileChooser();
    }

    private void initFileChooser() {
        this.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        this.showDialog(null, "Sélectionner");
    }

    public File getSelectedDirectory() {
        return this.getSelectedFile();
    }
}