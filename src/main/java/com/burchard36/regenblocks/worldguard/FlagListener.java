package com.burchard36.regenblocks.worldguard;

import com.sk89q.worldguard.bukkit.event.block.BreakBlockEvent;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.association.RegionAssociable;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.flags.StringFlag;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FlagListener implements Listener {

    public FlagListener() {
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public final void onBlockBreak(final BreakBlockEvent event) {
        final Location loc = event.getBlocks().get(0).getLocation();
        if (this.hasStringFlag(loc, GuardFlag.ALLOW_BLOCK_BREAK)) {
            final String stringFlag = this.getStringFlag(loc, GuardFlag.ALLOW_BLOCK_BREAK);
            final List<Material> materials = this.getFlagValues(stringFlag);
            if (!materials.contains(event.getEffectiveMaterial())) {
                event.setResult(Event.Result.DENY);
            } else event.setResult(Event.Result.ALLOW);
        }
    }

    final List<Material> getFlagValues(final String flagValue) {
        final List<String> values = Arrays.asList(flagValue.split(" "));
        final List<Material> materials = new ArrayList<>();
        values.forEach((value) -> {
            final Material mat = Material.getMaterial(value);
            if (Material.getMaterial(value) != null) {
                materials.add(mat);
            }
        });
        return materials;
    }

    private String getStringFlag(final Location location, final StringFlag flag) {
        final RegionQuery query = WorldGuard.getInstance().getPlatform().getRegionContainer().createQuery();
        com.sk89q.worldedit.util.Location worldEditLocation = BukkitAdapter.adapt(location);
        return query.queryValue(worldEditLocation, (RegionAssociable) null, flag);
    }

    private boolean hasStringFlag(final Location location, final StringFlag flag) {
        return this.getStringFlag(location, flag) != null;
    }

}
