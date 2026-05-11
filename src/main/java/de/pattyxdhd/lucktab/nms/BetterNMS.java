package de.pattyxdhd.lucktab.nms;

import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class BetterNMS extends NMSBase implements NMS {

    @Override
    public Integer getPing(Player player) {
        try {
            Method getPing = player.getClass().getMethod("getPing");
            return (Integer) getPing.invoke(player);
        } catch (Exception ignored) {}

        try {
            Object entityPlayer = player.getClass().getMethod("getHandle").invoke(player);
            Field ping = entityPlayer.getClass().getDeclaredField("ping");
            ping.setAccessible(true);
            return ping.getInt(entityPlayer);
        } catch (Exception ignored) {}

        return 0;
    }

}
