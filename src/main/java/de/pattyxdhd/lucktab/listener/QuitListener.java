package de.pattyxdhd.lucktab.listener;

import de.pattyxdhd.lucktab.utils.UserObject;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitListener implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        UserObject.getUserObjects().remove(event.getPlayer().getUniqueId());
    }

}
