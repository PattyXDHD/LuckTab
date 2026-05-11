package de.pattyxdhd.lucktab.listener;

import de.pattyxdhd.lucktab.utils.PlayerConverter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class UpdateListener {

    public static void updateEvent(final net.luckperms.api.event.user.UserDataRecalculateEvent event) {

        final UUID uuid = event.getUser().getUniqueId();
        final Player player = Bukkit.getPlayer(uuid);

        try {
            if(player != null){
                PlayerConverter.setTabPrefix(player);
            }
        }catch (Exception ignored){
            ignored.printStackTrace();
        }
    }

}
