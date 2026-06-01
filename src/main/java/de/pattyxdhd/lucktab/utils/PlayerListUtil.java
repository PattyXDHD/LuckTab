package de.pattyxdhd.lucktab.utils;

import de.pattyxdhd.lucktab.LuckTab;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class PlayerListUtil {

    private static UserObject getUserObject(final Player player) {
        return UserObject.getUserObjects().get(player.getUniqueId());
    }

    public static void updatePlayer(Player player) {
        UserObject.getUserObjects().put(player.getUniqueId(), UserObject.convert(player));
        PlayerListUtil.setTabPrefix(player);
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
        final String teamName = userObject.getWeight();
        final String entry = player.getName();

        Team team = scoreboard.getTeam(teamName);
        if (team == null) {
            team = scoreboard.registerNewTeam(teamName);
        }

        for (UserObject object : UserObject.getUserObjects().values()) {
            Team oldTeam = scoreboard.getTeam(object.getWeight());
            if (oldTeam != null && oldTeam.hasEntry(entry) && !oldTeam.getName().equals(teamName)) {
                oldTeam.removeEntry(entry);
            }
        }

        team.setPrefix(userObject.getTabPrefix());
        team.setSuffix(userObject.getSuffix());

        if (!team.hasEntry(entry)) {
            team.addEntry(entry);
        }

        player.setPlayerListName(userObject.getTabPrefix() + player.getName() + userObject.getTabSuffix());
    }

}
