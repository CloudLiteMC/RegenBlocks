package com.burchard36.regenblocks;

import com.burchard36.Api;
import com.burchard36.ApiLib;
import com.burchard36.json.PluginDataMap;
import com.burchard36.json.PluginJsonWriter;
import com.burchard36.regenblocks.commands.ReloadCommand;
import com.burchard36.regenblocks.config.Configs;
import com.burchard36.regenblocks.config.DefaultConfig;
import com.burchard36.regenblocks.manager.RegenManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class RegenBlocks extends JavaPlugin implements Api {

    public static RegenBlocks INSTANCE;
    private ApiLib lib;
    private RegenManager regenManager;

    @Override
    public void onEnable() {
        INSTANCE = this;
        this.loadPlugin(false);
    }

    @Override
    public void onDisable() {
        this.regenManager.serverShutdown();
    }

    private void loadPlugin(final boolean isReload) {
        this.lib = new ApiLib().initializeApi(this);
        final PluginJsonWriter writer = this.lib.getPluginDataManager().getJsonWriter();
        this.lib.getPluginDataManager().registerPluginMap(Configs.DEFAULT, new PluginDataMap(writer));

        this.lib.getPluginDataManager().loadDataFileToMap(Configs.DEFAULT, "default_config", new DefaultConfig(this, "/config.json"));
        this.regenManager = new RegenManager(this);

        if (!isReload) {
            ReloadCommand command = new ReloadCommand(this);
            ApiLib.registerCommand(command.getCommand());
        }
    }

    public void reloadPlugin() {
        this.onDisable();
        this.loadPlugin(true);
    }

    public DefaultConfig getDefaultConfig() {
        return (DefaultConfig) this.lib.getPluginDataManager().getDataFileFromMap(Configs.DEFAULT, "default_config");
    }

    @Override
    public boolean isDebug() {
        return true;
    }
}
