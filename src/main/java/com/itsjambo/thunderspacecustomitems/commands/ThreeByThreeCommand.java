package com.itsjambo.thunderspacecustomitems.commands;

import com.itsjambo.thunderspacecustomitems.ThunderSpaceCustomItems;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ThreeByThreeCommand implements CommandExecutor, TabCompleter {

    private final ThunderSpaceCustomItems plugin;

    public ThreeByThreeCommand(ThunderSpaceCustomItems plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be used by players.");
            return true;
        }

        if (args.length < 3) {
            sender.sendMessage("Usage: /tsci-3x3 [name] [description] [material]");
            return true;
        }

        Player player = (Player) sender;
        String name = args[0];
        String description = args[1];
        Material material = Material.matchMaterial(args[2].toUpperCase());
        if (material == null) {
            player.sendMessage("Invalid material.");
            return true;
        }

        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name + " 3x3");
        List<String> lore = new ArrayList<>();
        lore.add(description);
        meta.setLore(lore);
        item.setItemMeta(meta);

        player.getInventory().addItem(item);
        saveItemToConfig(name, description, material, 3);

        player.sendMessage("3x3 breaking item created and added to your inventory.");
        return true;
    }

    private void saveItemToConfig(String name, String description, Material material, int radius) {
        int id = plugin.getConfigManager().getItemsConfig().getConfigurationSection("items").getKeys(false).size() + 1;
        String path = "items.id-" + id;

        plugin.getConfigManager().getItemsConfig().set(path + ".name", name);
        plugin.getConfigManager().getItemsConfig().set(path + ".description", description);
        plugin.getConfigManager().getItemsConfig().set(path + ".material", material.toString());
        plugin.getConfigManager().getItemsConfig().set(path + ".radius", radius);

        plugin.getConfigManager().saveConfig();
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> suggestions = new ArrayList<>();
        if (args.length == 3) {
            for (Material material : Material.values()) {
                suggestions.add(material.name());
            }
        }
        return suggestions;
    }
}