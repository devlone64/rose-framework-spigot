package dev.lone64.roseframework.spigot.util.team;

import dev.lone64.roseframework.spigot.util.message.Component;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class PlayerTeams {

    public static void update(Player player, String prefix, String suffix, ChatColor color) {
        Scoreboard scoreboard = player.getScoreboard();
        Team team = scoreboard.getTeam(player.getUniqueId().toString());
        if (team == null)
            team = scoreboard.registerNewTeam(player.getUniqueId().toString());
        if (!team.hasEntry(player.getName()))
            team.addEntry(player.getName());
        team.setPrefix(Component.from(prefix));
        team.setSuffix(Component.from(suffix));
        team.setColor(color);
    }

    public static void update(Player player, String prefix, String suffix) {
        Scoreboard scoreboard = player.getScoreboard();
        Team team = scoreboard.getTeam(player.getUniqueId().toString());
        if (team == null)
            team = scoreboard.registerNewTeam(player.getUniqueId().toString());
        if (!team.hasEntry(player.getName()))
            team.addEntry(player.getName());
        team.setPrefix(Component.from(prefix));
        team.setSuffix(Component.from(suffix));
    }

    public static void update(Player player, ChatColor color) {
        Scoreboard scoreboard = player.getScoreboard();
        Team team = scoreboard.getTeam(player.getUniqueId().toString());
        if (team == null)
            team = scoreboard.registerNewTeam(player.getUniqueId().toString());
        if (!team.hasEntry(player.getName()))
            team.addEntry(player.getName());
        team.setColor(color);
    }

    public static void remove(Player player) {
        Scoreboard scoreboard = player.getScoreboard();
        Team team = scoreboard.getTeam(player.getUniqueId().toString());
        if (team == null) return;
        team.unregister();
    }

    public static Team getTeam(Player player) {
        Scoreboard scoreboard = player.getScoreboard();
        return scoreboard.getTeam(player.getUniqueId().toString());
    }

    public static boolean isTeam(Player player) {
        return getTeam(player) != null;
    }

}