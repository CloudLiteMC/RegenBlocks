package com.burchard36.regenblocks.worldguard;

import com.burchard36.regenblocks.RegenBlocks;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.flags.StringFlag;
import com.sk89q.worldguard.protection.flags.registry.FlagConflictException;
import com.sk89q.worldguard.protection.flags.registry.FlagRegistry;

public class GuardFlag {

    private final RegenBlocks plugin;
    public static StringFlag ALLOW_BLOCK_BREAK;

    public GuardFlag(final RegenBlocks plugin) {
        this.plugin = plugin;

        FlagRegistry registry = WorldGuard.getInstance().getFlagRegistry();
        try {
            // create a flag with the name "my-custom-flag", defaulting to true
            StringFlag flag = new StringFlag("allow-block-break");
            registry.register(flag);
            ALLOW_BLOCK_BREAK = flag; // only set our field if there was no error
        } catch (FlagConflictException e) {
            e.printStackTrace();
        }
    }

}
