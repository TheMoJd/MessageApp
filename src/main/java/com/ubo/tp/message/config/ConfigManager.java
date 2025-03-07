package com.ubo.tp.message.config;

import java.io.*;
import java.util.Properties;

public class ConfigManager {
  private static final String CONFIG_FILE = "src/main/resources/configuration.properties";
  private static Properties properties = new Properties();

  // Chargement des propriétés
  static {
    loadProperties();
  }

  private static void loadProperties() {
    try (InputStream input = new FileInputStream(CONFIG_FILE)) {
      properties.load(input);
    } catch (IOException e) {
      e.printStackTrace();
      throw new RuntimeException("Impossible de charger le fichier de configuration.");
    }
  }

  // Récupérer une valeur
  public static String getProperty(String key) {
    return properties.getProperty(key);
  }

  // Modifier une valeur et la sauvegarder
  public static void setProperty(String key, String value) {
    properties.setProperty(key, value);
    saveProperties();
  }

  private static void saveProperties() {
    try (OutputStream output = new FileOutputStream(CONFIG_FILE)) {
      properties.store(output, "Mise à jour des configurations");
    } catch (IOException e) {
      e.printStackTrace();
      throw new RuntimeException("Impossible de sauvegarder le fichier de configuration.");
    }
  }
}
