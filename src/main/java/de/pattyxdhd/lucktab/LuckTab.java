package de.pattyxdhd.lucktab;

import com.google.common.collect.Lists;
import de.pattyxdhd.lucktab.commands.PluginReloadCommand;
import de.pattyxdhd.lucktab.config.ConfigManager;
import de.pattyxdhd.lucktab.data.Data;
import de.pattyxdhd.lucktab.listener.ChatListener;
import de.pattyxdhd.lucktab.listener.JoinListener;
import de.pattyxdhd.lucktab.listener.UpdateListener;
import de.pattyxdhd.lucktab.nms.BetterNMS;
import de.pattyxdhd.lucktab.nms.NMS;
import de.pattyxdhd.lucktab.utils.PlayerConverter;
import de.pattyxdhd.lucktab.utils.UserObject;
import lombok.Getter;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.event.user.UserDataRecalculateEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class LuckTab extends JavaPlugin {

    @Getter
    private static LuckTab instance;

    @Getter
    private LuckPerms luckPermsApi;

    @Getter
    private static ConfigManager configManager;

    @Getter
    private static List<UserObject> userObjects = Lists.newArrayList();

    @Getter
    private static NMS nms;

    @Override
    public void onEnable() {

        instance = this;
        configManager = new ConfigManager(this).copyDefaults();

        loadNMS();
        loadLuckPerms();
        loadListener(Bukkit.getPluginManager());
        loadCommands();
        loadUserObjects();

        log("§aPlugin geladen.");
        log("§9Version: §bv" + getDescription().getVersion());
    }

    @Override
    public void onDisable() {

    }

    private void loadListener(final PluginManager pluginManager){
        pluginManager.registerEvents(new JoinListener(), this);
        if(configManager.getBoolean("useChatFormat") && configManager.exist("useChatFormat")){
            pluginManager.registerEvents(new ChatListener(), this);
        }
    }

    private void loadCommands(){
        getCommand("lucktabreload").setExecutor(new PluginReloadCommand());
    }

    private void loadLuckPerms(){
        final Plugin luckPerms = Bukkit.getPluginManager().getPlugin("LuckPerms");

        if(luckPerms != null && luckPerms.isEnabled()){
            RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
            if (provider != null) {
                luckPermsApi = provider.getProvider();
            }

            if(configManager.getBoolean("autoUpdate") && configManager.exist("autoUpdate")){
                getLuckPermsApi().getEventBus().subscribe(UserDataRecalculateEvent.class, UpdateListener::updateEvent);
            }

            return;
        }

        log("§4LuckPerms konnte nicht gefunden werden...");
        log("§4Das Plugin wird nun deaktiviert.");
        Bukkit.getPluginManager().disablePlugin(this);
    }

    private void loadNMS(){
//        String version = getServer().getClass().getPackage().getName().replace(".", "-").split("-")[3];
        String version = Bukkit.getVersion();
        log("Der Server läuft auf der Version §e" + version + "§7.");
        nms = new BetterNMS();
    }

    private void loadUserObjects(){
        List<String> list = getConfigManager().getStringList("prefixes");
        AtomicInteger count = new AtomicInteger(1);

        list.forEach(string -> {
            getUserObjects().add(new UserObject(string).setId(count.getAndIncrement()));
        });
    }

    public void log(final String message){
        Bukkit.getConsoleSender().sendMessage(Data.getPrefix() + message);
    }

    public void reloadPluginConfig() {
        reloadConfig();

        configManager = new ConfigManager(this);

        userObjects.clear();
        loadUserObjects();

        for (Player player : Bukkit.getOnlinePlayers()) {
            PlayerConverter.setTabPrefix(player);
        }
    }

}
