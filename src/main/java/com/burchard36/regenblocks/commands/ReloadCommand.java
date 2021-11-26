package com.burchard36.regenblocks.commands;

import com.burchard36.command.ApiCommand;
import com.burchard36.regenblocks.RegenBlocks;
import org.bukkit.entity.Player;

import java.util.ArrayList;

import static com.burchard36.ApiLib.convert;

public class ReloadCommand {

    private final ApiCommand command;
    private final RegenBlocks plugin;

    public ReloadCommand(final RegenBlocks plugin) {
        this.plugin = plugin;
        this.command = new ApiCommand("regenblocks",
                "Main command for the plugin",
                "&e&o/regenblocks",
                new ArrayList<>())
                .onPlayerSender((playerSent) -> {
                    final Player player = playerSent.getSendingPlayer();

                    if (!player.hasPermission("regenblocks.command")) {
                        player.sendMessage(convert("&cYou do not have permission to execute this command!"));
                        return;
                    }

                    if (playerSent.args().size() == 0) {
                        player.sendMessage(convert("&7&oThe only command is really &e/regenblocks reload&7&o it reloads the plugins"));
                    } else if (playerSent.args().size() == 1) {
                        final String args = playerSent.args().get(0);
                        if (args.equalsIgnoreCase("reload")) {

                            if (!player.hasPermission("regenblocks.reload")) {
                                player.hasPermission(convert("&cYou do not have permission to execute this command!"));
                                return;
                            }

                            player.sendMessage(convert("&aReloading plugin. . ."));
                            this.reloadPlugin();
                            player.sendMessage(convert("&aSuccessfully reloaded plugin!"));
                        }
                    } else player.sendMessage(convert("&e&o/regenblocks"));
                })
                .onConsoleSender((consoleSent) -> {
                    if (consoleSent.args().size() == 0) {
                        consoleSent.getConsoleSender().sendMessage(convert("&7&oThe only command is really &e/regenblocks reload&7&o it reloads the plugins"));
                    } else if (consoleSent.args().size() == 1) {
                        final String args = consoleSent.args().get(0);
                        if (args.equalsIgnoreCase("reload")) {

                            consoleSent.getConsoleSender().sendMessage(convert("&aReloading plugin. . ."));
                            this.reloadPlugin();
                            consoleSent.getConsoleSender().sendMessage(convert("&aSuccessfully reloaded plugin!"));
                        }
                    } else consoleSent.getConsoleSender().sendMessage(convert("&e&o/regenblocks"));
                });
    }

    private void reloadPlugin() {
        this.plugin.reloadPlugin();
    }

    public final ApiCommand getCommand() {
        return this.command;
    }

}
