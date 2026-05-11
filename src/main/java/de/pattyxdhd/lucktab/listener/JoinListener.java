package de.pattyxdhd.lucktab.listener;

import de.pattyxdhd.lucktab.utils.PlayerConverter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void onJoin(final PlayerJoinEvent event){
        final Player player = event.getPlayer();

        PlayerConverter.setTabPrefix(player);
    }

}
