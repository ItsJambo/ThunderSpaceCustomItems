package com.itsjambo.thunderspacecustomitems.commands;

import com.itsjambo.thunderspacecustomitems.ThunderSpaceCustomItems;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class ReloadCommand implements CommandExecutor, TabCompleter {

    private final ThunderSpaceCustomItems plugin;

    public ReloadCommand(ThunderSpaceCustomItems plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        plugin.getConfigManager().reloadConfig();
        sender.sendMessage("Configuration reloaded.");
        return true;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String[] args) {
        return Collections.emptyList();
    }
}