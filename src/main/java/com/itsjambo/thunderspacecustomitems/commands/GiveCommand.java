package com.itsjambo.thunderspacecustomitems.commands;

import com.itsjambo.thunderspacecustomitems.ThunderSpaceCustomItems;
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

import java.util.ArrayList;
import java.util.List;

public class GiveCommand implements CommandExecutor, TabCompleter {

    private final ThunderSpaceCustomItems plugin;

    public GiveCommand(ThunderSpaceCustomItems plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 2) {
            sender.sendMessage("Usage: /tsci-give [nickname] [id]");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage("Player not found.");
            return true;
        }

        String id = "id-" + args[1];
        ConfigurationSection itemSection = plugin.getConfigManager().getItemsConfig().getConfigurationSection("items." + id);
        if (itemSection == null) {
            sender.sendMessage("Item ID not found.");
            return true;
        }

        String name = itemSection.getString("name");
        String description = itemSection.getString("description");
        Material material = Material.matchMaterial(itemSection.getString("material"));
        if (material == null) {
            sender.sendMessage("Invalid material in config.");
            return true;
        }

        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        List<String> lore = new ArrayList<>();
        lore.add(description);
        meta.setLore(lore);
        item.setItemMeta(meta);

        String enchantmentString = itemSection.getString("enchantments");
        if (enchantmentString != null && !enchantmentString.isEmpty()) {
            String[] enchantmentParts = enchantmentString.split(" \\| ");
            for (String part : enchantmentParts) {
                String[] enchantmentData = part.split(" ");
                if (enchantmentData.length == 3) {
                    Enchantment enchantment = Enchantment.getByName(enchantmentData[0]);
                    if (enchantment != null) {
                        try {
                            int level = Integer.parseInt(enchantmentData[2].replace("level", "").trim());
                            item.addUnsafeEnchantment(enchantment, level);
                        } catch (NumberFormatException e) {
                            sender.sendMessage("Invalid enchantment level in config for ID " + id + ".");
                        }
                    }
                }
            }
        }

        target.getInventory().addItem(item);
        sender.sendMessage("Item given to " + target.getName() + ".");
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
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