package dev.lone64.roseframework.spigot.command.consumer;

import org.bukkit.entity.Player;

@FunctionalInterface
public interface StringConsumer {
    String accept(Player player);
}