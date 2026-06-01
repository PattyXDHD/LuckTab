package de.pattyxdhd.lucktab.listener;

import de.pattyxdhd.lucktab.LuckTab;
import de.pattyxdhd.lucktab.utils.PlayerListUtil;
import de.pattyxdhd.lucktab.utils.UserObject;
import net.luckperms.api.event.node.NodeMutateEvent;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class UpdateListener {

    static Long timeDifferenz = 0L;

    public static void onNodeMutate(NodeMutateEvent event) {
        if ((timeDifferenz + 300) >= System.currentTimeMillis()) return;
        timeDifferenz = System.currentTimeMillis();

        if (event.getTarget() instanceof User) {
            User user = (User) event.getTarget();
            final Player player = Bukkit.getPlayer(user.getUniqueId());

            Bukkit.getScheduler().runTaskLater(LuckTab.getInstance(), () -> {
                try {
                    if (player != null) {
                        PlayerListUtil.updatePlayer(player);
                    }
                } catch (Exception ignored) {
                }
            }, 10L);
        }

        if (event.getTarget() instanceof Group) {
            Group group = (Group) event.getTarget();

            Bukkit.getScheduler().runTaskLater(LuckTab.getInstance(), () -> {
                Bukkit.getOnlinePlayers().forEach(player -> {
                    UserObject userObject = UserObject.getUserObject(player.getUniqueId());
                    if (userObject.isInGroup(group.getName())) {
                        PlayerListUtil.updatePlayer(player);
                    }
                });
            }, 10L);
        }

    }

}
