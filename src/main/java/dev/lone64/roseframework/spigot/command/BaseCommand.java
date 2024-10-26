package dev.lone64.roseframework.spigot.command;

import dev.lone64.roseframework.spigot.command.provider.SenderProvider;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public interface BaseCommand extends SenderProvider {
    default boolean perform(CommandSender sender, String[] args) { return false; }
    default List<String> complete(CommandSender sender, String[] args) { return List.of(); }

    default boolean perform(Player player, String[] args) { return false; }
    default List<String> complete(Player player, String[] args) { return List.of(); }
}