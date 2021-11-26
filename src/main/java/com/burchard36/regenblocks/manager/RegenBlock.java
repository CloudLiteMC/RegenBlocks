package com.burchard36.regenblocks.manager;

import com.burchard36.regenblocks.RegenBlocks;
import com.burchard36.regenblocks.config.JsonRegenBlock;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class RegenBlock {

    private final BukkitTask runningTask;
    private final Block block;
    private final RegenManager manager;
    private final JsonRegenBlock regenBlock;

    public RegenBlock(final Block block,
                      final JsonRegenBlock jsonBlock,
                      final RegenManager manager) {
        this.block = block;
        this.manager = manager;
        this.regenBlock = jsonBlock;
        new BukkitRunnable() {
            @Override
            public void run() {
                block.setType(regenBlock.getTemporaryMaterial());
            }
        }.runTaskLater(RegenBlocks.INSTANCE, 1);

        this.runningTask = new BukkitRunnable() {
            @Override
            public void run() {
                RegenBlock.this.cancel();
                manager.removeRegenBlock(RegenBlock.this);
            }
        }.runTaskLater(RegenBlocks.INSTANCE, 20L * this.regenBlock.getRegenTime());
    }

    public final Location getBlockLocation() {
        return this.block.getLocation();
    }

    public final Block getBlock() {
        return this.block;
    }

    public final void cancel() {
        this.runningTask.cancel();
        this.block.setType(regenBlock.getRegenMaterial());
    }

    public final boolean isCancelled() {
        return this.runningTask.isCancelled();
    }
}
