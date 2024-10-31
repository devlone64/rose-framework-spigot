package dev.lone64.roseframework.spigot.spigot;

import dev.lone64.roseframework.spigot.RoseModule;
import org.bukkit.Bukkit;
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

    public static void register(Permission permission) {
        Bukkit.getPluginManager().addPermission(permission);
    }

    public static <T> void register(RoseModule module, Class<T> aClass, T t) {
        Bukkit.getServicesManager().register(aClass, t, module, ServicePriority.Normal);
    }

    public static BukkitTask async(RoseModule module, Runnable runnable) {
        return Bukkit.getScheduler().runTaskAsynchronously(module, runnable);
    }

    public static BukkitTask sync(RoseModule module, Runnable runnable) {
        return Bukkit.getScheduler().runTask(module, runnable);
    }

    public static BukkitTask asyncLater(RoseModule module, Runnable runnable, long ticks) {
        return Bukkit.getScheduler().runTaskLaterAsynchronously(module, runnable, ticks);
    }

    public static BukkitTask asyncTimer(RoseModule module, Runnable runnable, long delay, long ticks) {
        return Bukkit.getScheduler().runTaskTimerAsynchronously(module, runnable, delay, ticks);
    }

    public static BukkitTask syncLater(RoseModule module, Runnable runnable, long ticks) {
        return Bukkit.getScheduler().runTaskLater(module, runnable, ticks);
    }

    public static BukkitTask syncTimer(RoseModule module, Runnable runnable, long delay, long ticks) {
        return Bukkit.getScheduler().runTaskTimer(module, runnable, delay, ticks);
    }

    public static boolean isQueued(int taskId) {
        return Bukkit.getScheduler().isQueued(taskId);
    }

    public static boolean isCurrentlyRunning(int taskId) {
        return Bukkit.getScheduler().isCurrentlyRunning(taskId);
    }

}