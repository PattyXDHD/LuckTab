package de.pattyxdhd.lucktab.nms;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NMSBase {

    private final String nmsName = "NMS";

    public Class<?> getNMSClass(String name) {
        try {
            String fullname = "net.minecraft.server." + Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3] + "." + name;
            return Class.forName(fullname);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(nmsName).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public Class<?> getCraftBukkitClass(String name) {
        String fullname = "org.bukkit.craftbukkit." + Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3] + "." + name;
        try {
            return Class.forName(fullname);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(nmsName).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public void setValue(Object obj, String name, Object value) {
        try {
            Field field = obj.getClass().getDeclaredField(name);
            field.setAccessible(true);
            field.set(obj, value);
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException ex) {
            Logger.getLogger(nmsName).log(Level.SEVERE, null, ex);
        }
    }

    public Object getValue(Object obj, String name) {
        try {
            Field field = obj.getClass().getDeclaredField(name);
            field.setAccessible(true);
            return field.get(obj);
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException ex) {
            Logger.getLogger(nmsName).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public Field getField(Object obj, String name) {
        try {
            Field field = obj.getClass().getDeclaredField(name);
            field.setAccessible(true);
            return field;
        } catch (NoSuchFieldException | SecurityException ex) {
            Logger.getLogger(nmsName).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public void sendPacket(Object packet, Player player) {
        try {
            Object handle = player.getClass().getMethod("getHandle").invoke(player);
            Object playerConnetction = handle.getClass().getField("playerConnection").get(handle);

            playerConnetction.getClass().getMethod("sendPacket", getNMSClass("Packet")).invoke(playerConnetction, packet);
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException |
                 InvocationTargetException | NoSuchFieldException ex) {
            Logger.getLogger(nmsName).log(Level.SEVERE, null, ex);
        }
    }

    public void sendPacket(Object packet, List<Player> players) {
        players.forEach((player) -> {
            sendPacket(packet, player);
        });
    }

    public void sendPacket(Object packet) {
        Bukkit.getOnlinePlayers().forEach((player) -> {
            sendPacket(packet, player);
        });
    }

    public Object getCraftPlayer(Player player) {
        Class<?> craftPlayerClazz = getCraftBukkitClass("entity.CraftPlayer");
        return craftPlayerClazz.cast(player);
    }

}
