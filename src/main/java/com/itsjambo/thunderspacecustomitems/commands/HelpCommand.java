package com.itsjambo.thunderspacecustomitems.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class HelpCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        sender.sendMessage("§aThunderSpaceCustomItems Help:");
        sender.sendMessage("§a/tsci-create [name] [description] [material] [enchantment]:[level] - Create a custom item.");
        sender.sendMessage("§a/tsci-reload - Reload the plugin configuration.");
        sender.sendMessage("§a/tsci-3x3 [name] [description] [material] - Create a custom item that breaks blocks in a 3x3 radius.");
        sender.sendMessage("§a/tsci-5x5 [name] [description] [material] - Create a custom item that breaks blocks in a 5x5 radius.");
        sender.sendMessage("§a/tsci-give [nickname] [id] - Give a custom item to a player by ID.");
        sender.sendMessage("§a/tsci-addenchantment [id] [enchantment]:[level] - Add an enchantment to an existing item.");
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return Collections.emptyList();
    }
}