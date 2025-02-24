package com.itsjambo.thunderspacecustomitems.listeners;

import com.itsjambo.thunderspacecustomitems.ThunderSpaceCustomItems;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class BlockBreakListener implements Listener {

    private final ThunderSpaceCustomItems plugin;

    public BlockBreakListener(ThunderSpaceCustomItems plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        ItemStack itemInHand = player.getInventory().getItemInMainHand();

        if (itemInHand == null || !itemInHand.hasItemMeta()) {
            return;
        }

        String displayName = itemInHand.getItemMeta().getDisplayName();

        if (displayName.contains("3x3")) {
            handleBlockBreak(event, 1);
        } else if (displayName.contains("5x5")) {
            handleBlockBreak(event, 2);
        }
    }

    private void handleBlockBreak(BlockBreakEvent event, int radius) {
        Block block = event.getBlock();
        Player player = event.getPlayer();

        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    Block relativeBlock = block.getRelative(x, y, z);
                    if (!relativeBlock.equals(block)) {
                        relativeBlock.breakNaturally(player.getInventory().getItemInMainHand());
                    }
                }
            }
        }
    }
}