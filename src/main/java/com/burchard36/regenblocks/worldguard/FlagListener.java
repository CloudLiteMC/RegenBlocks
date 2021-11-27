package com.burchard36.regenblocks.worldguard;

import com.burchard36.regenblocks.RegenBlocks;
import com.burchard36.regenblocks.config.DefaultConfig;
import com.sk89q.worldguard.bukkit.event.block.BreakBlockEvent;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import org.bukkit.Location;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;


public class FlagListener implements Listener {

    private final DefaultConfig config;

    public FlagListener(final RegenBlocks plugin) {
        this.config = plugin.getDefaultConfig();
    }

    @EventHandler(priority = EventPriority.LOW)
    public final void onBlockBreak(final BreakBlockEvent event) {
        if (event.getCause().getFirstPlayer() == null) return;

        final BlockBreakEvent blockBreakEvent = ((BlockBreakEvent) event.getOriginalEvent());
        if (blockBreakEvent == null) return;
        final Location loc = blockBreakEvent.getBlock().getLocation();
        if (this.hasStateFlag(loc, GuardFlag.ALLOW_BLOCK_BREAK)) {
            final String permission = "worldguard.region.bypass." + loc.getWorld().getName();
            if (!config.canRegenMaterial(event.getEffectiveMaterial())
                    && !event.getCause().getFirstPlayer().hasPermission(permission)) {
                event.setResult(Event.Result.DENY);
            } else event.setResult(Event.Result.ALLOW);
        }
    }

    private boolean hasStateFlag(final Location location, final StateFlag flag) {
        final RegionQuery query = WorldGuard.getInstance().getPlatform().getRegionContainer().createQuery();
        com.sk89q.worldedit.util.Location worldEditLocation = BukkitAdapter.adapt(location);
        return query.testState(worldEditLocation, null, flag);
    }
}
