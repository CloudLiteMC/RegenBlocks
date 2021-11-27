package com.burchard36.regenblocks.manager;

import com.burchard36.regenblocks.config.DefaultConfig;
import com.burchard36.regenblocks.config.JsonRegenBlock;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.List;

public class RegenEvents implements Listener {

    private final RegenManager manager;

    public RegenEvents(final RegenManager manager) {
        this.manager = manager;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onBlockBreak(final BlockBreakEvent event) {
        final Block blockBroken = event.getBlock();
        final Player breakingPlayer = event.getPlayer();
        final DefaultConfig config = manager.getConfig();

        if (this.manager.regenBlockExists(blockBroken.getLocation())) return;

        if (breakingPlayer.hasPermission("regenblocks.break") && breakingPlayer.isSneaking()) return;

        if (config.canRegenMaterial(blockBroken.getType())) {
            final JsonRegenBlock configBlock = config.getRegenBlock(blockBroken.getType());
            if (configBlock.getRegenWorld().getUID() == blockBroken.getWorld().getUID()) {
                final RegenBlock block = new RegenBlock(blockBroken, config.getRegenBlock(blockBroken.getType()), this.manager);
                this.manager.addRegenBlock(block);
            }
        }
    }
}
