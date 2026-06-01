package de.pattyxdhd.lucktab.listener;

import de.pattyxdhd.lucktab.LuckTab;
import de.pattyxdhd.lucktab.nms.BetterNMS;
import de.pattyxdhd.lucktab.utils.UserObject;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.regex.Pattern;

public class ChatListener implements Listener {

    @EventHandler
    public void onChat(final AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        UserObject userObject = UserObject.getUserObject(player.getUniqueId());
        String message = event.getMessage().replace("%", "%%");
        String chatPrefix = userObject.getChatPrefix();
        String chatSuffix = userObject.getChatSuffix();

        event.setMessage(translateColorCodes(event.getPlayer(), message));

        String format = LuckTab.getInstance().getConfig().getString("chatFormat", "%name% &8» &7%message%");
        if (format != null) {
            format = format
                    .replace("&", "§")
                    .replace("%name%", chatPrefix + "%1$s" + chatSuffix)
                    .replace("%message%", translateColorCodes(event.getPlayer(), "%2$s"))
                    .replace("%ping%", BetterNMS.getPing(player) + "ms")
                    .replace("%displayName%", event.getPlayer().getDisplayName())
                    .replace("%group%", userObject.getGroup());
            event.setFormat(format);
        }
    }

    private static final Pattern chatColorPattern = Pattern.compile("(?i)&([0-9A-FR])");
    private static final Pattern chatMagicPattern = Pattern.compile("(?i)&([K])");
    private static final Pattern chatBoldPattern = Pattern.compile("(?i)&([L])");
    private static final Pattern chatStrikethroughPattern = Pattern.compile("(?i)&([M])");
    private static final Pattern chatUnderlinePattern = Pattern.compile("(?i)&([N])");
    private static final Pattern chatItalicPattern = Pattern.compile("(?i)&([O])");

    public static String translateColorCodes(final Player player, final String message) {
        if (!LuckTab.getInstance().getConfig().getBoolean("useColorTranslate", true)) {
            return message;
        }
        String newstring = message;
        if (player.hasPermission("lucktab.chat.format.color")) {
            newstring = chatColorPattern.matcher(newstring).replaceAll("§$1");
        }
        if (player.hasPermission("lucktab.chat.format.magic")) {
            newstring = chatMagicPattern.matcher(newstring).replaceAll("§$1");
        }
        if (player.hasPermission("lucktab.chat.format.bold")) {
            newstring = chatBoldPattern.matcher(newstring).replaceAll("§$1");
        }
        if (player.hasPermission("lucktab.chat.format.strikethrough")) {
            newstring = chatStrikethroughPattern.matcher(newstring).replaceAll("§$1");
        }
        if (player.hasPermission("lucktab.chat.format.underline")) {
            newstring = chatUnderlinePattern.matcher(newstring).replaceAll("§$1");
        }
        if (player.hasPermission("lucktab.chat.format.italic")) {
            newstring = chatItalicPattern.matcher(newstring).replaceAll("§$1");
        }
        return newstring;
    }

}
