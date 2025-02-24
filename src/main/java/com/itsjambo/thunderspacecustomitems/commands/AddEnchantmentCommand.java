package com.itsjambo.thunderspacecustomitems.commands;

import com.itsjambo.thunderspacecustomitems.ThunderSpaceCustomItems;
import com.itsjambo.thunderspacecustomitems.utils.ColorParser;
import org.bukkit.Color;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddEnchantmentCommand implements CommandExecutor, TabCompleter {

    private final ThunderSpaceCustomItems plugin;

    public AddEnchantmentCommand(ThunderSpaceCustomItems plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (args.length < 2) {
            sender.sendMessage(ColorParser.parse("&#08FB52[✘] pUsage: /tsci-addenchantment [id] [enchantment]:[level]"));
            return true;
        }

        String id = "id-" + args[0];
        String[] enchantmentParts = args[1].split(":");
        if (enchantmentParts.length != 2) {
            sender.sendMessage(ColorParser.parse("&#08FB52[✘] Invalid enchantment format."));
            return true;
        }
        Enchantment enchantment = Enchantment.getByName(enchantmentParts[0].toUpperCase());
        int level;
        try {
            level = Integer.parseInt(enchantmentParts[1]);
        } catch (NumberFormatException e) {
            sender.sendMessage(ColorParser.parse("&#08FB52[✘] Invalid enchantment level."));
            return true;
        }

        if (enchantment == null) {
            sender.sendMessage(ColorParser.parse("&#08FB52[✘] Invalid enchantment."));
            return true;
        }

        ConfigurationSection itemSection = plugin.getConfigManager().getItemsConfig().getConfigurationSection("items." + id);
        if (itemSection == null) {
            sender.sendMessage(ColorParser.parse("&#08FB52[✘] Item ID not found."));
            return true;
        }

        List<Map<String, Object>> enchantments = (List<Map<String, Object>>) itemSection.getList("enchantments");
        if (enchantments == null) {
            enchantments = new ArrayList<>();
        }

        Map<String, Object> enchantmentData = new HashMap<>();
        enchantmentData.put("name", enchantment.getName());
        enchantmentData.put("level", level);
        enchantments.add(enchantmentData);

        itemSection.set("enchantments", enchantments);

        plugin.getConfigManager().saveConfig();
        sender.sendMessage(ColorParser.parse("&#08FB52[✔] Enchantment added to item ID " + id + "&#08FB52."));
        return true;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String[] args) {
        List<String> suggestions = new ArrayList<>();
        if (args.length == 1) {
            ConfigurationSection itemsSection = plugin.getConfigManager().getItemsConfig().getConfigurationSection("items");
            if (itemsSection != null) {
                for (String key : itemsSection.getKeys(false)) {
                    suggestions.add(key.replace("id-", ""));
                }
            }
        } else if (args.length == 2) {
            for (Enchantment enchantment : Enchantment.values()) {
                suggestions.add(enchantment.getName() + ":1");
            }
        }
        return suggestions;
    }
}