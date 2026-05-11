package de.pattyxdhd.lucktab.commands;

import de.pattyxdhd.lucktab.LuckTab;
import de.pattyxdhd.lucktab.data.Data;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PluginReloadCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if(!(commandSender instanceof Player)){
            commandSender.sendMessage("Du musst ein Spieler sein.");
            return false;
        }

        final Player player = ((Player) commandSender);

        if (player.hasPermission("lucktab.reload")) {

            LuckTab.getInstance().reloadPluginConfig();
            LuckTab.getInstance().log("§aConfig neu geladen.");
            player.sendMessage(Data.getPrefix() + "§aConfig neu geladen.");

        }else {
            player.sendMessage(Data.getPrefix() + "§cDazu hast du keinen Zugriff.");
        }

        return false;
    }

}
