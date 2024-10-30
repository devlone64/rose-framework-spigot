package dev.lone64.roseframework.spigot.util.player;

import dev.lone64.roseframework.spigot.RoseLib;
import dev.lone64.roseframework.spigot.util.player.data.UserData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class PlayerUtil {

    public static void updatePlayer(UserData userData) {
        userData.getPlayer().setFoodLevel(userData.getFoodLevel());
        userData.getPlayer().setHealth(userData.getHealth());
        userData.getPlayer().setHealthScale(userData.getMaxHealth());
        userData.getPlayer().setLevel(userData.getLevel());
        userData.getPlayer().setExp(userData.getExperience());
        userData.getPlayer().setGameMode(userData.getGameMode());
        userData.getPlayer().getInventory().setItemInMainHand(userData.getMainHand());
        userData.getPlayer().getInventory().setItemInOffHand(userData.getOffHand());
        userData.getPlayer().getInventory().setStorageContents(userData.getItemStorage());
        userData.getPlayer().getInventory().setArmorContents(userData.getArmorStorage());
    }

    public static void hideAll(Player player) {
        for (Player loopPlayer : Bukkit.getOnlinePlayers()) {
            loopPlayer.hidePlayer(RoseLib.getInstance(), player);
        }
    }

    public static void showAll(Player player) {
        for (Player loopPlayer : Bukkit.getOnlinePlayers()) {
            loopPlayer.showPlayer(RoseLib.getInstance(), player);
        }
    }

    public static void hideAll(List<Player> players, Player player) {
        for (Player loopPlayer : players) {
            loopPlayer.hidePlayer(RoseLib.getInstance(), player);
        }
    }

    public static void showAll(List<Player> players, Player player) {
        for (Player loopPlayer : players) {
            loopPlayer.showPlayer(RoseLib.getInstance(), player);
        }
    }

    public static boolean hasAvaliableSlot(Player player) {
        return Arrays.asList(player.getInventory().getStorageContents()).contains(null);
    }

    public static UserData getUserData(Player player) {
        UserData userData = new UserData();
        userData.setPlayer(player);
        userData.setFoodLevel(player.getFoodLevel());
        userData.setHealth(player.getHealth());
        userData.setHealth(player.getHealthScale());
        userData.setLevel(player.getLevel());
        userData.setExperience(player.getExp());
        userData.setGameMode(player.getGameMode());
        userData.setMainHand(player.getInventory().getItemInMainHand());
        userData.setOffHand(player.getInventory().getItemInOffHand());
        userData.setItemStorage(player.getInventory().getStorageContents());
        userData.setArmorStorage(player.getInventory().getArmorContents());
        return userData;
    }

}