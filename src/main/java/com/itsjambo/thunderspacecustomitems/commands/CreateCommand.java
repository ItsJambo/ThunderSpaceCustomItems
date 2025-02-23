package com.itsjambo.thunderspacecustomitems.commands;

import com.itsjambo.thunderspacecustomitems.ThunderSpaceCustomItems;
import com.itsjambo.thunderspacecustomitems.utils.ColorParser;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class CreateCommand implements CommandExecutor, TabCompleter {

    private final ThunderSpaceCustomItems plugin;

    public CreateCommand(ThunderSpaceCustomItems plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ColorParser.parse("&#08FB52[✘] This command can only be used by players."));
            return true;
        }

        if (args.length < 4) {
            sender.sendMessage(ColorParser.parse("&#08FB52[✘] Usage: /tsci-create [name] [description] [material] [enchantment]:[level]"));
            return true;
        }

        Player player = (Player) sender;
        String name = ColorParser.parse(args[0]);
        String description = ColorParser.parse(args[1]);
        Material material = Material.matchMaterial(args[2].toUpperCase());
        if (material == null) {
            player.sendMessage(ColorParser.parse("&#08FB52[✘] Invalid material."));
            return true;
        }

        String[] enchantmentParts = args[3].split(":");
        if (enchantmentParts.length != 2) {
            player.sendMessage(ColorParser.parse("&#08FB52[✘] Invalid enchantment format."));
            return true;
        }
        Enchantment enchantment = Enchantment.getByName(enchantmentParts[0].toUpperCase());
        int level;
        try {
            level = Integer.parseInt(enchantmentParts[1]);
        } catch (NumberFormatException e) {
            player.sendMessage(ColorParser.parse("&#08FB52[✘] Invalid enchantment level."));
            return true;
        }

        if (enchantment == null) {
            player.sendMessage(ColorParser.parse("&#08FB52[✘] Invalid enchantment."));
            return true;
        }

        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        List<String> lore = new ArrayList<>();
        lore.add(description);
        meta.setLore(lore);
        item.setItemMeta(meta);
        item.addUnsafeEnchantment(enchantment, level);

        player.getInventory().addItem(item);
        saveItemToConfig(name, description, material, enchantment, level);

        player.sendMessage(ColorParser.parse("&#08FB52[✔] Item created and added to your inventory."));
        return true;
    }

    private void saveItemToConfig(String name, String description, Material material, Enchantment enchantment, int level) {
        if (plugin.getConfigManager().getItemsConfig().getConfigurationSection("items") == null) {
            plugin.getConfigManager().getItemsConfig().createSection("items");
        }
        int id = plugin.getConfigManager().getItemsConfig().getConfigurationSection("items").getKeys(false).size() + 1;
        String path = "items.id-" + id;

        plugin.getConfigManager().getItemsConfig().set(path + ".name", name);
        plugin.getConfigManager().getItemsConfig().set(path + ".description", description);
        plugin.getConfigManager().getItemsConfig().set(path + ".material", material.toString());

        List<Map<String, Object>> enchantments = new ArrayList<>();
        Map<String, Object> enchantmentData = new HashMap<>();
        enchantmentData.put("name", enchantment.getName());
        enchantmentData.put("level", level);
        enchantments.add(enchantmentData);

        plugin.getConfigManager().getItemsConfig().set(path + ".enchantments", enchantments);

        plugin.getConfigManager().saveConfig();
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String[] args) {
        List<String> suggestions = new ArrayList<>();
        if (args.length == 3) {
            for (Material material : Material.values()) {
                suggestions.add(material.name());
            }
        }
        return suggestions;
    }
}