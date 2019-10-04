package com.gmail.lukacat100.CommandOnAction.Configs;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;

public class CustomConfig {
    private FileConfiguration customConfig = null;
    private File customConfigFile = null;
    private JavaPlugin plugin;
    private String filename;
    private String filepath;

    public CustomConfig(JavaPlugin plugin, String filepath){
        this.plugin = plugin;
        this.filepath = filepath;
        File file = new File(filepath);
        this.filename = file.getName();

    }
    public void reloadCustomConfig() throws UnsupportedEncodingException {
        if (customConfigFile == null) {
            customConfigFile = new File(plugin.getDataFolder(), filepath);
        }
        customConfig = YamlConfiguration.loadConfiguration(customConfigFile);


        // Look for defaults in the jar
        InputStream configResource = plugin.getResource(filename);
        if(configResource == null)
            return;
        Reader defConfigStream = new InputStreamReader(configResource, StandardCharsets.UTF_8);
        if (defConfigStream == null) {
            return;
        }
        YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
        customConfig.setDefaults(defConfig);

    }
    public FileConfiguration getCustomConfig() throws UnsupportedEncodingException {
        if (customConfig == null) {
            reloadCustomConfig();
        }
        return customConfig;
    }
    public void saveCustomConfig() {
        if (customConfig == null || customConfigFile == null) {
            return;
        }
        try {
            getCustomConfig().save(customConfigFile);
        } catch (IOException ex) {
            plugin.getLogger().log(Level.SEVERE, "Could not save config to " + customConfigFile, ex);
        }
    }
    public void saveDefaultConfig() {
        if (customConfigFile == null) {
            customConfigFile = new File(plugin.getDataFolder(), filepath);
        }
        if (!customConfigFile.exists()) {
            plugin.saveResource(filename, false);
        }
    }
}
