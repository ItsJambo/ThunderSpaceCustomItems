package com.itsjambo.thunderspacecustomitems.commands;

import com.itsjambo.thunderspacecustomitems.ThunderSpaceCustomItems;
import com.itsjambo.thunderspacecustomitems.utils.ColorParser;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class GiveCommand implements CommandExecutor, TabCompleter {

    private final ThunderSpaceCustomItems plugin;

    public GiveCommand(ThunderSpaceCustomItems plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (args.length < 2) {
            sender.sendMessage(ColorParser.parse("&#08FB52[✘] Usage: /tsci-give [nickname] [id]"));
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage(ColorParser.parse("&#08FB52[✘] Player not found."));
            return true;
        }

        String id = "id-" + args[1];
        ConfigurationSection itemSection = plugin.getConfigManager().getItemsConfig().getConfigurationSection("items." + id);
        if (itemSection == null) {
            sender.sendMessage(ColorParser.parse("&#08FB52[✘] Item ID not found."));
            return true;
        }

        String name = itemSection.getString("name");
        String description = itemSection.getString("description");
        Material material = Material.matchMaterial(Objects.requireNonNull(itemSection.getString("material")));
        if (material == null) {
            sender.sendMessage(ColorParser.parse("&#08FB52[✘] Invalid material in config."));
            return true;
        }

        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        List<String> lore = new ArrayList<>();
        lore.add(description);
        meta.setLore(lore);
        item.setItemMeta(meta);

        List<Map<?, ?>> enchantments = itemSection.getMapList("enchantments");
        for (Map<?, ?> enchantmentData : enchantments) {
            String enchantmentName = (String) enchantmentData.get("name");
            int level = (int) enchantmentData.get("level");
            Enchantment enchantment = Enchantment.getByName(enchantmentName);
            if (enchantment != null) {
                item.addUnsafeEnchantment(enchantment, level);
            }
        }

        target.getInventory().addItem(item);
        sender.sendMessage(ColorParser.parse("&#08FB52[✔] Item given to " + target.getName() + "."));
        return true;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String[] args) {
        List<String> suggestions = new ArrayList<>();
        if (args.length == 1) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                suggestions.add(player.getName());
            }
        } else if (args.length == 2) {
            ConfigurationSection itemsSection = plugin.getConfigManager().getItemsConfig().getConfigurationSection("items");
            if (itemsSection != null) {
                for (String key : itemsSection.getKeys(false)) {
                    suggestions.add(key.replace("id-", ""));
                }
            }
        }
        return suggestions;
    }
}