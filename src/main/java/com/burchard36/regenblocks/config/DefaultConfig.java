package com.burchard36.regenblocks.config;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DefaultConfig {

    @SerializedName(value = "regen_blocks")
    public List<JsonRegenBlock> regenBlocks;

}
