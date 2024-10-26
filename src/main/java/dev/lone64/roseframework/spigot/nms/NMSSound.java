package dev.lone64.roseframework.spigot.nms;

import com.cryptomorin.xseries.XSound;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class NMSSound {

    public static void play(XSound sound, Entity entity) {
        sound.play(entity);
    }

    public static void play(XSound sound, Location location) {
        sound.play(location);
    }

    public static void play(XSound sound, Entity entity, float volume, float pitch) {
        sound.play(entity, volume, pitch);
    }

    public static void play(XSound sound, Location location, float volume, float pitch) {
        sound.play(location, volume, pitch);
    }

    public static void stop(XSound sound, Player player) {
        sound.stopSound(player);
    }

    public static Sound getSound(XSound sound) {
        return sound.parseSound();
    }

    public static XSound or(XSound sound, XSound orSound) {
        return sound.or(orSound);
    }

    public static boolean isSupported(XSound sound) {
        return sound.isSupported();
    }

}