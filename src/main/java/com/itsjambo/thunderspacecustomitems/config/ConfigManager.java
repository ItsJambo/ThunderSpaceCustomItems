package com.itsjambo.thunderspacecustomitems.config;

import com.itsjambo.thunderspacecustomitems.ThunderSpaceCustomItems;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigManager {

    private final ThunderSpaceCustomItems plugin;
    private FileConfiguration itemsConfig;
    private File itemsConfigFile;

    public ConfigManager(ThunderSpaceCustomItems plugin) {
        this.plugin = plugin;
    }

    public void loadConfig() {
        plugin.saveDefaultConfig();
        itemsConfigFile = new File(plugin.getDataFolder(), "items.yml");
        if (!itemsConfigFile.exists()) {
            plugin.saveResource("items.yml", false);
        }
        itemsConfig = YamlConfiguration.loadConfiguration(itemsConfigFile);
    }

    public FileConfiguration getItemsConfig() {
        return itemsConfig;
    }

    public void saveConfig() {
        try {
            itemsConfig.save(itemsConfigFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reloadConfig() {
        itemsConfig = YamlConfiguration.loadConfiguration(itemsConfigFile);
    }
}