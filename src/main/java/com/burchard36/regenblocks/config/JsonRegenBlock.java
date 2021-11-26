package com.burchard36.regenblocks.config;

import com.google.gson.annotations.SerializedName;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;

public class JsonRegenBlock {

    @SerializedName(value = "regenerating_material")
    public String regenMaterial;

    @SerializedName(value = "world_name")
    public String regenWorldName;

    @SerializedName(value = "temporary_material")
    public String tempMaterial;

    @SerializedName(value = "reset_time_seconds")
    public int regenTime;

    public JsonRegenBlock(final Material regenMaterial,
                          final World regenWorld,
                          final Material tempMaterial,
                          final int regenTime) {
        this.regenMaterial = regenMaterial.name();
        this.regenWorldName = regenWorld.getName();
        this.tempMaterial = tempMaterial.name();
        this.regenTime = regenTime;
    }

    public final Material getRegenMaterial() {
        return Material.valueOf(this.regenMaterial);
    }

    public final World getRegenWorld() {
        return Bukkit.getWorld(this.regenWorldName);
    }

    public final Material getTemporaryMaterial() {
        return Material.valueOf(this.tempMaterial);
    }

    public final int getRegenTime() {
        return this.regenTime;
    }
}
