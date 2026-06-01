package de.pattyxdhd.lucktab.commands;

import de.pattyxdhd.lucktab.LuckTab;
import de.pattyxdhd.lucktab.data.Data;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LuckTabCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!sender.hasPermission("lucktab.admin")) {
            sender.sendMessage(Data.getPrefix() + "§cYou don''t have Permission for that.");
            return false;
        }

        if (args.length == 1) {
            switch (args[0]) {
                case "help":
                    sendHelp(sender, true);
                    return false;
                case "reload":
                    reloadConfig(sender);
                    return false;
                case "resetall":
                    resetAll(sender);
                    return false;
                default:
                    sendHelp(sender, false);
                    return false;
            }
        } else {
            sendHelp(sender, false);
        }
        return false;

    }

    private void sendHelp(CommandSender sender, boolean inDetail) {

        sender.sendMessage("");
        sender.sendMessage("§7-----------§8[ §bLuckTab §8]§7-----------");
        sender.sendMessage("");
        sender.sendMessage(" §a/lucktab help");
        sender.sendMessage("   §7-> Shows the Full help Page (with instructions for usage)");
        sender.sendMessage(" §a/lucktab reload");
        sender.sendMessage("   §7-> Reloads the plugin configuration.");
        sender.sendMessage(" §a/lucktab resetall");
        sender.sendMessage("   §7-> Provides instructions on how to uninstall the plugin correctly.");
        sender.sendMessage("");
        if (inDetail) {
            sender.sendMessage("§7-----------§8[ §eInstructions for Usage: §8]§7-----------");
            sender.sendMessage("");
            sender.sendMessage("§7To assign a prefix to a group:");
            sender.sendMessage(" §e/lp group default meta setprefix 100 \"§6Your Prefix§e\"");
            sender.sendMessage("  §7-> the *100* is the weight of the Prefix. You can ignore that.");
            sender.sendMessage("§7To assign a chat-prefix to a group:");
            sender.sendMessage(" §e/lp group default meta set chatprefix \"§6Your Chat-Prefix§e\"");
            sender.sendMessage("");
            sender.sendMessage("§7To assign a suffix to a group:");
            sender.sendMessage(" §e/lp group default meta setsuffix 100 \"§6Your Suffix§e\"");
            sender.sendMessage("  §7-> the *100* is the weight of the Suffix. You can ignore that.");
            sender.sendMessage("§7To assign a chat-suffix to a group:");
            sender.sendMessage(" §e/lp group default meta set chatsuffix \"§6Your Chat-Suffix§e\"");
            sender.sendMessage("");
            sender.sendMessage("§7To sort the groups in the player list, you need to set the group weight:");
            sender.sendMessage(" §e/lp group default setweight 300");
            sender.sendMessage("   §7(300=onBottom, 200=inMiddle, 100=onTop)");
            sender.sendMessage("   §7-> higher number = lower position");
            sender.sendMessage("");
        }

    }

    private void reloadConfig(CommandSender sender) {
        LuckTab.getInstance().reloadConfig();
        LuckTab.getInstance().loadConfig();
        sender.sendMessage(Data.getPrefix() + "§aYou have successfully reloaded the plugin.");
    }

    private void resetAll(CommandSender sender) {
        sender.sendMessage("");
        sender.sendMessage("§7-----------§8[ §bLuckTab §8]§7-----------");
        sender.sendMessage("");
        sender.sendMessage(" §7To remove the prefixes/suffixes after uninstalling ");
        sender.sendMessage(" §7the plugin, you need to delete the following file. ");
        sender.sendMessage(" §cThe server must be stopped before doing so.");
        sender.sendMessage("");
        sender.sendMessage("  §7-> §e%SERVER_FOLDER%\\world\\data\\scoreboard.dat");
        sender.sendMessage("");
    }

    private static final List<String> commandList = Arrays.asList("help", "reload", "resetall");

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (!(sender instanceof Player)) {
            return null;
        }

        if (args.length == 1) {
            String prefix = args[0].toLowerCase();
            List<String> out = new ArrayList<>();
            for (String s : commandList) {
                if (s.startsWith(prefix))
                    out.add(s);
            }
            return out;
        }
        return new ArrayList<>();
    }
}
