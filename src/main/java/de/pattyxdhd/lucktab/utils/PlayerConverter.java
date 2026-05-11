package de.pattyxdhd.lucktab.utils;

import de.pattyxdhd.lucktab.LuckTab;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.NodeType;
import net.luckperms.api.node.types.InheritanceNode;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.Set;
import java.util.stream.Collectors;

public class PlayerConverter {

    public static String getGroup(final Player player) {
        return getUserObject(player).getGroup();
    }

    public static String getChatPrefix(final Player player) {
        return getUserObject(player).getChatPrefix();
    }

    private static UserObject getUserObject(final Player player) {
        for (UserObject userObject : LuckTab.getUserObjects()) {
            if (PlayerConverter.isPlayerInGroup(player, userObject.getGroup())) {
                return userObject;
            }
        }
        return LuckTab.getUserObjects().get(LuckTab.getUserObjects().size() -1);
    }

    public static void setTabPrefix(final Player player) {
        Bukkit.getScheduler().runTask(LuckTab.getInstance(), () -> {
            for (Player target : Bukkit.getOnlinePlayers()) {
                updatePlayerOnScoreboard(player.getScoreboard(), target);
            }

            for (Player target : Bukkit.getOnlinePlayers()) {
                if (target == player) {
                    continue;
                }
                updatePlayerOnScoreboard(target.getScoreboard(), player);
            }
        });
    }

    private static void updatePlayerOnScoreboard(final Scoreboard scoreboard, final Player player) {
        final UserObject userObject = getUserObject(player);
        final String teamName = userObject.getId();
        final String entry = player.getName();

        Team team = scoreboard.getTeam(teamName);
        if (team == null) {
            team = scoreboard.registerNewTeam(teamName);
        }

        for (UserObject object : LuckTab.getUserObjects()) {
            Team oldTeam = scoreboard.getTeam(object.getId());
            if (oldTeam != null && oldTeam.hasEntry(entry) && !oldTeam.getName().equals(teamName)) {
                oldTeam.removeEntry(entry);
            }
        }

        team.setPrefix(userObject.getTabPrefix());

        if (!team.hasEntry(entry)) {
            team.addEntry(entry);
        }

        player.setPlayerListName(userObject.getChatPrefix() + player.getName());
    }

    public static boolean isPlayerInGroup(final Player player, final String group) {
        User user = LuckTab.getInstance().getLuckPermsApi().getUserManager().getUser(player.getUniqueId());

        assert user != null;
        Set<String> groups = user.getNodes().stream()
                .filter(NodeType.INHERITANCE::matches)
                .map(NodeType.INHERITANCE::cast)
                .map(InheritanceNode::getGroupName)
                .collect(Collectors.toSet());

        return groups.contains(group);
    }

}
