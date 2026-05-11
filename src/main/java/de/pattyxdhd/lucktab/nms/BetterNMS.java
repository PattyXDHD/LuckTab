package de.pattyxdhd.lucktab.nms;

import org.bukkit.entity.Player;

public class BetterNMS extends NMSBase implements NMS {

    @Override
    public Integer getPing(Player player) {
        return player.getPing();
    }

}
