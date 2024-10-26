package dev.lone64.roseframework.spigot.util.team;

import dev.lone64.roseframework.spigot.util.message.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class ScoreboardTeams {

    private static final Scoreboard scoreboard = Objects.requireNonNull(Bukkit.getScoreboardManager()).getMainScoreboard();

    public static Team setTeam(String name) {
        Team team = scoreboard.getTeam(name);
        if (team == null) {
            team = scoreboard.registerNewTeam(name);
        }
        return team;
    }

    public static Team setTeam(String name, ChatColor color) {
        Team team = scoreboard.getTeam(name);
        if (team == null) {
            team = scoreboard.registerNewTeam(name);
        }
        team.setColor(color);
        return team;
    }

    public static Team setTeam(String name, String prefix, String suffix) {
        Team team = scoreboard.getTeam(name);
        if (team == null) {
            team = scoreboard.registerNewTeam(name);
        }
        team.setPrefix(Component.from(prefix));
        team.setSuffix(Component.from(suffix));
        return team;
    }

    public static Team setTeam(String name, String prefix, String suffix, ChatColor color) {
        Team team = scoreboard.getTeam(name);
        if (team == null) {
            team = scoreboard.registerNewTeam(name);
        }
        team.setColor(color);
        team.setPrefix(Component.from(prefix));
        team.setSuffix(Component.from(suffix));
        return team;
    }

    public static Team setTeam(String name, Consumer<Team> consumer) {
        Team team = scoreboard.getTeam(name);
        if (team == null) {
            team = scoreboard.registerNewTeam(name);
        }
        consumer.accept(team);
        return team;
    }

    public static boolean removeTeam(String name) {
        Team team = getTeam(name);
        if (team == null) return false;
        team.unregister();
        return true;
    }

    public static boolean removeTeam(Player player) {
        Team team = getTeam(player);
        if (team == null) return false;
        team.unregister();
        return true;
    }

    public static Team getTeam(String name) {
        return scoreboard.getTeam(name);
    }

    public static Team getTeam(Player player) {
        return scoreboard.getEntryTeam(player.getName());
    }

    public static boolean isTeam(String name) {
        return getTeam(name) != null;
    }

    public static boolean isTeam(Player player) {
        return getTeam(player) != null;
    }

    public static List<Team> getTeams() {
        return new ArrayList<>(scoreboard.getTeams());
    }

}