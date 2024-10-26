package dev.lone64.roseframework.spigot.builder.inventory.extension;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

public interface EventExt {
    default void onClick(InventoryClickEvent event) { }
    default void onClose(InventoryCloseEvent event) { }

    default void onClick(InventoryClickEvent event, Player player) { }
    default void onClose(InventoryCloseEvent event, Player player) { }
}