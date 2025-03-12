package com.ubo.tp.message.ihm;

import javax.swing.*;
import java.io.File;

public class FileChooserView extends JFileChooser {

    private int fileSelectionMode;

    public FileChooserView(int fileSelectionMode) {
        this.fileSelectionMode = fileSelectionMode;
        initFileChooser();
    }

    private void initFileChooser() {
        this.setFileSelectionMode(fileSelectionMode);
        this.showDialog(null, "Sélectionner");
    }

    public File getSelectedDirectory() {
        return this.getSelectedFile();
    }
}