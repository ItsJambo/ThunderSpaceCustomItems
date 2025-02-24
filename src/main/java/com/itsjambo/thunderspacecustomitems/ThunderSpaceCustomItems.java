package com.itsjambo.thunderspacecustomitems;

import com.itsjambo.thunderspacecustomitems.commands.*;
import com.itsjambo.thunderspacecustomitems.config.ConfigManager;
import com.itsjambo.thunderspacecustomitems.listeners.BlockBreakListener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class ThunderSpaceCustomItems extends JavaPlugin {

    private ConfigManager configManager;

    @Override
    public void onEnable() {
        try {
            saveDefaultConfig();
            configManager = new ConfigManager(this);
            configManager.loadConfig();

            Objects.requireNonNull(getCommand("tsci-create")).setExecutor(new CreateCommand(this));
            Objects.requireNonNull(getCommand("tsci-reload")).setExecutor(new ReloadCommand(this));
            Objects.requireNonNull(getCommand("tsci-3x3")).setExecutor(new ThreeByThreeCommand(this));
            Objects.requireNonNull(getCommand("tsci-5x5")).setExecutor(new FiveByFiveCommand(this));
            Objects.requireNonNull(getCommand("tsci-give")).setExecutor(new GiveCommand(this));
            Objects.requireNonNull(getCommand("tsci-help")).setExecutor(new HelpCommand());
            Objects.requireNonNull(getCommand("tsci-addenchantment")).setExecutor(new AddEnchantmentCommand(this));

            Objects.requireNonNull(getCommand("tsci-create")).setTabCompleter(new CreateCommand(this));
            Objects.requireNonNull(getCommand("tsci-reload")).setTabCompleter(new ReloadCommand(this));
            Objects.requireNonNull(getCommand("tsci-3x3")).setTabCompleter(new ThreeByThreeCommand(this));
            Objects.requireNonNull(getCommand("tsci-5x5")).setTabCompleter(new FiveByFiveCommand(this));
            Objects.requireNonNull(getCommand("tsci-give")).setTabCompleter(new GiveCommand(this));
            Objects.requireNonNull(getCommand("tsci-help")).setTabCompleter(new HelpCommand());
            Objects.requireNonNull(getCommand("tsci-addenchantment")). setTabCompleter(new AddEnchantmentCommand(this));

            getServer().getPluginManager().registerEvents(new BlockBreakListener(this), this);
        } catch (Exception e) {
            getLogger().severe("Failed to enable ThunderSpaceCustomItems plugin: " + e.getMessage());
            e.printStackTrace();
            getServer().getPluginManager().disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {
        if (configManager != null) {
            configManager.saveConfig();
        }
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }
}