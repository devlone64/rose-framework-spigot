package dev.lone64.roseframework.spigot.spigot;

import dev.lone64.roseframework.spigot.RoseModule;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.scheduler.BukkitTask;

public class Spigot {

    public static void enablePlugin(Plugin plugin) {
        Bukkit.getPluginManager().enablePlugin(plugin);
    }

    public static void disablePlugin(Plugin plugin) {
        Bukkit.getPluginManager().disablePlugin(plugin);
    }

    public static void register(Listener... listeners) {
        for (Listener listener : listeners) {
            Bukkit.getPluginManager().registerEvents(listener, RoseModule.INSTANCE);
        }
    }

    public static void register(Permission permission) {
        Bukkit.getPluginManager().addPermission(permission);
    }

    public static <T> void register(Class<T> aClass, T t) {
        Bukkit.getServicesManager().register(aClass, t, RoseModule.INSTANCE, ServicePriority.Normal);
    }

    public static BukkitTask async(Runnable runnable) {
        return Bukkit.getScheduler().runTaskAsynchronously(RoseModule.INSTANCE, runnable);
    }

    public static BukkitTask sync(Runnable runnable) {
        return Bukkit.getScheduler().runTask(RoseModule.INSTANCE, runnable);
    }

    public static BukkitTask asyncLater(Runnable runnable, long ticks) {
        return Bukkit.getScheduler().runTaskLaterAsynchronously(RoseModule.INSTANCE, runnable, ticks);
    }

    public static BukkitTask asyncTimer(Runnable runnable, long delay, long ticks) {
        return Bukkit.getScheduler().runTaskTimerAsynchronously(RoseModule.INSTANCE, runnable, delay, ticks);
    }

    public static BukkitTask syncLater(Runnable runnable, long ticks) {
        return Bukkit.getScheduler().runTaskLater(RoseModule.INSTANCE, runnable, ticks);
    }

    public static BukkitTask syncTimer(Runnable runnable, long delay, long ticks) {
        return Bukkit.getScheduler().runTaskTimer(RoseModule.INSTANCE, runnable, delay, ticks);
    }

    public static boolean isQueued(int taskId) {
        return Bukkit.getScheduler().isQueued(taskId);
    }

    public static boolean isCurrentlyRunning(int taskId) {
        return Bukkit.getScheduler().isCurrentlyRunning(taskId);
    }

}