package com.itsjambo.thunderspacecustomitems.commands;

import com.itsjambo.thunderspacecustomitems.ThunderSpaceCustomItems;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;

import java.util.ArrayList;
import java.util.List;

public class AddEnchantmentCommand implements CommandExecutor, TabCompleter {

    private final ThunderSpaceCustomItems plugin;

    public AddEnchantmentCommand(ThunderSpaceCustomItems plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 2) {
            sender.sendMessage("Usage: /tsci-addenchantment [id] [enchantment]:[level]");
            return true;
        }

        String id = "id-" + args[0];
        String[] enchantmentParts = args[1].split(":");
        if (enchantmentParts.length != 2) {
            sender.sendMessage("Invalid enchantment format.");
            return true;
        }
        Enchantment enchantment = Enchantment.getByName(enchantmentParts[0].toUpperCase());
        int level;
        try {
            level = Integer.parseInt(enchantmentParts[1]);
        } catch (NumberFormatException e) {
            sender.sendMessage("Invalid enchantment level.");
            return true;
        }

        if (enchantment == null) {
            sender.sendMessage("Invalid enchantment.");
            return true;
        }

        ConfigurationSection itemSection = plugin.getConfigManager().getItemsConfig().getConfigurationSection("items." + id);
        if (itemSection == null) {
            sender.sendMessage("Item ID not found.");
            return true;
        }

        String enchantmentString = itemSection.getString("enchantments");
        if (enchantmentString == null) {
            enchantmentString = "";
        } else {
            enchantmentString += " | ";
        }
        enchantmentString += enchantment.getName() + " | " + level + " level";
        itemSection.set("enchantments", enchantmentString);

        plugin.getConfigManager().saveConfig();
        sender.sendMessage("Enchantment added to item ID " + id + ".");
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
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