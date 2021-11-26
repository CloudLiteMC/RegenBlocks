package com.burchard36.regenblocks.config;

import com.burchard36.json.JsonDataFile;
import com.google.gson.annotations.SerializedName;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class DefaultConfig extends JsonDataFile {

    @SerializedName(value = "regen_blocks")
    public List<JsonRegenBlock> regenBlocks;

    public DefaultConfig(JavaPlugin plugin, String pathToFile) {
        super(plugin, pathToFile);

        this.regenBlocks = new ArrayList<>();
        World world = Bukkit.getWorld("rpg_world");
        if (world == null) {
            world = Bukkit.getWorld("world");
            if (world == null) return;

        }
        this.regenBlocks.add(new JsonRegenBlock(Material.SPRUCE_WOOD, world, Material.BEDROCK, 3));
        this.regenBlocks.add(new JsonRegenBlock(Material.SPRUCE_LOG, world, Material.BEDROCK, 3));
    }

    /**
     * Returns true if the regen material exists
     * @param material Material to check and see if it can regen
     * @return true if Material exists
     */
    public final boolean canRegenMaterial(final Material material) {
        return this.getRegenBlock(material) != null;
    }

    /**
     * Gets the regen block is one exist
     * @param material Material to check
     * @return null if it doesn't exists, JsonRegenBlock if it does
     */
    public final JsonRegenBlock getRegenBlock(final Material material) {
        JsonRegenBlock block = null;
        for (final JsonRegenBlock jsonBlock : this.regenBlocks) {
            if (jsonBlock.getRegenMaterial() == material) block = jsonBlock;
        }
        return block;
    }
}
