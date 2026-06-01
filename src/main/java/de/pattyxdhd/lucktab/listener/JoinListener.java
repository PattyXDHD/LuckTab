package de.pattyxdhd.lucktab.listener;

import de.pattyxdhd.lucktab.utils.PlayerListUtil;
import de.pattyxdhd.lucktab.utils.UserObject;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        UserObject.getUserObjects().put(player.getUniqueId(), UserObject.convert(player));
        PlayerListUtil.setTabPrefix(player);
    }


}
