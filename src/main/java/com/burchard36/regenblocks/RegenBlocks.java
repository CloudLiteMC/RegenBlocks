package com.burchard36.regenblocks;

import com.burchard36.Api;
import com.burchard36.ApiLib;
import com.burchard36.json.PluginDataMap;
import com.burchard36.json.PluginJsonWriter;
import com.burchard36.regenblocks.commands.ReloadCommand;
import com.burchard36.regenblocks.config.Configs;
import com.burchard36.regenblocks.config.DefaultConfig;
import com.burchard36.regenblocks.manager.RegenManager;
import com.burchard36.regenblocks.worldguard.FlagListener;
import com.burchard36.regenblocks.worldguard.GuardFlag;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

public final class RegenBlocks extends JavaPlugin implements Api {

    public static RegenBlocks INSTANCE;
    private ApiLib lib;
    private RegenManager regenManager;
    private FlagListener flagListener;

    @Override
    public void onEnable() {
        INSTANCE = this;
        this.lib = new ApiLib().initializeApi(this);
        final PluginJsonWriter writer = this.lib.getPluginDataManager().getJsonWriter();
        this.lib.getPluginDataManager().registerPluginMap(Configs.DEFAULT, new PluginDataMap(writer));

        this.lib.getPluginDataManager().loadDataFileToMap(Configs.DEFAULT, "default_config", new DefaultConfig(this, "/config.json"));
        this.regenManager = new RegenManager(this);

        ReloadCommand command = new ReloadCommand(this);
        ApiLib.registerCommand(command.getCommand());
    }

    @Override
    public void onLoad() {
        final GuardFlag flags = new GuardFlag(this);
        this.flagListener = new FlagListener();
        Bukkit.getServer().getPluginManager().registerEvents(this.flagListener, this);
    }

    @Override
    public void onDisable() {
        this.regenManager.serverShutdown();
        this.regenManager = null;
        this.lib = null;

        HandlerList.unregisterAll(this.flagListener);

    }

    public void reloadPlugin() {
        this.onDisable();
        this.onEnable();
    }

    public DefaultConfig getDefaultConfig() {
        return (DefaultConfig) this.lib.getPluginDataManager().getDataFileFromMap(Configs.DEFAULT, "default_config");
    }

    @Override
    public boolean isDebug() {
        return true;
    }
}
