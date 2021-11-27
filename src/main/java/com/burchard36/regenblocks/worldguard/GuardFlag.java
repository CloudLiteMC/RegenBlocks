package com.burchard36.regenblocks.worldguard;

import com.burchard36.regenblocks.RegenBlocks;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.flags.registry.FlagConflictException;
import com.sk89q.worldguard.protection.flags.registry.FlagRegistry;

public class GuardFlag {

    private final RegenBlocks plugin;
    public static StateFlag ALLOW_BLOCK_BREAK;

    public GuardFlag(final RegenBlocks plugin) {
        this.plugin = plugin;

        FlagRegistry registry = WorldGuard.getInstance().getFlagRegistry();
        try {
            StateFlag flag = new StateFlag("allow-regen-block-break", true);
            registry.register(flag);
            ALLOW_BLOCK_BREAK = flag;
        } catch (FlagConflictException e) {
            e.printStackTrace();
        }
    }

}
