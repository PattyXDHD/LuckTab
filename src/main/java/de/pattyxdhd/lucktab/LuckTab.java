package de.pattyxdhd.lucktab;

import de.pattyxdhd.lucktab.commands.LuckTabCommand;
import de.pattyxdhd.lucktab.data.Data;
import de.pattyxdhd.lucktab.listener.ChatListener;
import de.pattyxdhd.lucktab.listener.JoinListener;
import de.pattyxdhd.lucktab.listener.QuitListener;
import de.pattyxdhd.lucktab.listener.UpdateListener;
import de.pattyxdhd.lucktab.utils.UserObject;
import lombok.Getter;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.event.EventSubscription;
import net.luckperms.api.event.node.NodeMutateEvent;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class LuckTab extends JavaPlugin {

    @Getter
    private static LuckTab instance;

    @Getter
    private static LuckPerms luckPermsApi;

    private EventSubscription<NodeMutateEvent> nodeMutateSubscription;

    @Override
    public void onEnable() {
        instance = this;

        loadConfig();
        loadLuckPerms();
        reloadUserObjects();
        loadListener(Bukkit.getPluginManager());
        loadCommands();

        String version = Bukkit.getVersion();

        Bukkit.getConsoleSender().sendMessage("");
        Bukkit.getConsoleSender().sendMessage("  §b    ___");
        Bukkit.getConsoleSender().sendMessage("  §b|    |    §bLuckTab §2v" + getDescription().getVersion());
        Bukkit.getConsoleSender().sendMessage("  §b|___ |    §8Running on " + version);
        Bukkit.getConsoleSender().sendMessage("");
    }

    @Override
    public void onDisable() {
        if (nodeMutateSubscription != null) {
            nodeMutateSubscription.close();
            nodeMutateSubscription = null;
        }
        Bukkit.getScheduler().cancelTasks(this);

        log("§7Disabling LuckTab v" + getDescription().getVersion());
    }

    public void log(final String message) {
        Bukkit.getConsoleSender().sendMessage(Data.getPrefix() + message);
    }

    public void loadConfig() {
        saveDefaultConfig();
    }

    private void loadLuckPerms() {
        final Plugin luckPerms = Bukkit.getPluginManager().getPlugin("LuckPerms");

        if (luckPerms != null && luckPerms.isEnabled()) {
            RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
            if (provider != null) {
                luckPermsApi = provider.getProvider();
            }

            if (getConfig().getBoolean("autoUpdate", true)) {
                nodeMutateSubscription = getLuckPermsApi().getEventBus().subscribe(NodeMutateEvent.class, UpdateListener::onNodeMutate);
            }

            return;
        }

        log("§4LuckPerms konnte nicht gefunden werden...");
        log("§4Das Plugin wird nun deaktiviert.");
        Bukkit.getPluginManager().disablePlugin(this);
    }

    private void reloadUserObjects() {
        UserObject.getUserObjects().clear();
        Bukkit.getOnlinePlayers().forEach(player -> {
            UserObject.getUserObjects().put(player.getUniqueId(), UserObject.convert(player));
        });
    }

    private void loadListener(final PluginManager pluginManager) {
        pluginManager.registerEvents(new JoinListener(), this);
        pluginManager.registerEvents(new QuitListener(), this);

        if (getConfig().getBoolean("useChatFormat", true)) {
            pluginManager.registerEvents(new ChatListener(), this);
        }
    }

    private void loadCommands() {
        PluginCommand luckTabCommand = getCommand("lucktab");
        luckTabCommand.setExecutor(new LuckTabCommand());
        luckTabCommand.setTabCompleter(new LuckTabCommand());
    }

}
