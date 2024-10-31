package dev.lone64.roseframework.spigot.util;

import dev.lone64.roseframework.spigot.RoseModule;
import dev.lone64.roseframework.spigot.util.message.Component;
import org.bukkit.Bukkit;

public class Console {

    public static void log(RoseModule module, String message) {
        Bukkit.getConsoleSender().sendMessage(Component.from("&7[%s] &r%s".formatted(module.getName(), message)));
    }

    public static void info(RoseModule module, String message) {
        Bukkit.getConsoleSender().sendMessage(Component.from("&7[%s] %s".formatted(module.getName(), message)));
    }

    public static void warning(RoseModule module, String message) {
        Bukkit.getConsoleSender().sendMessage(Component.from("&e[%s] %s".formatted(module.getName(), message)));
    }

    public static void error(RoseModule module, String message) {
        Bukkit.getConsoleSender().sendMessage(Component.from("&c[%s] %s".formatted(module.getName(), message)));
    }

}