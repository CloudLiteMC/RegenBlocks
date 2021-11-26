package com.burchard36.regenblocks.manager;

import com.burchard36.regenblocks.RegenBlocks;
import com.burchard36.regenblocks.config.DefaultConfig;
import org.bukkit.Location;
import org.bukkit.event.HandlerList;

import java.util.HashMap;

public class RegenManager {

    private final DefaultConfig config;
    private final RegenEvents events;
    private final HashMap<Location, RegenBlock> blocks;

    public RegenManager(final RegenBlocks plugin) {
        this.config = plugin.getDefaultConfig();
        this.events = new RegenEvents(this);
        this.blocks = new HashMap<>();

        plugin.getServer().getPluginManager().registerEvents(this.events, plugin);
    }

    /**
     * Handle server shutdowns, in this case we need to change broken blocks back to the original material immediately
     */
    public final void serverShutdown() {
        HandlerList.unregisterAll(this.events);
        this.blocks.values().forEach(RegenBlock::cancel);
        this.blocks.clear();
    }

    public final boolean regenBlockExists(final Location location) {
        return this.blocks.get(location) != null;
    }

    public final void addRegenBlock(final RegenBlock block) {
        this.blocks.putIfAbsent(block.getBlockLocation(), block);
    }

    public final void removeRegenBlock(final RegenBlock block) {
        this.blocks.remove(block.getBlockLocation(), block);
    }

    public final DefaultConfig getConfig() {
        return this.config;
    }

}
