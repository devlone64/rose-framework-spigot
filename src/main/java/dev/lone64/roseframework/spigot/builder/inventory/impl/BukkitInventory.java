package dev.lone64.roseframework.spigot.builder.inventory.impl;

import dev.lone64.roseframework.spigot.builder.inventory.annotation.Gui;
import dev.lone64.roseframework.spigot.builder.inventory.data.InventoryProvider;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

public class BukkitInventory implements InventoryProvider<Inventory>, InventoryHolder {

    private final Inventory inventory;

    private final int rows;
    private final String name;
    private final InventoryType type;

    public BukkitInventory() {
        if (getClass().isAnnotationPresent(Gui.class)) {
            Gui data = getClass().getAnnotation(Gui.class);
            this.rows = data.rows();
            this.name = data.name();
            this.type = data.type();
            this.inventory = createInventory();
        } else {
            throw new RuntimeException("Gui annotation not found: %s".formatted(getClass().getSimpleName()));
        }
    }

    @Override
    public boolean show(Player player) {
        onPrevInit(getInventory(), player);
        onInit(getInventory(), player);
        player.openInventory(getInventory());
        onInitLater(getInventory(), player);
        return true;
    }

    @Override
    public boolean update(Player player) {
        onPrevInit(getInventory(), player);
        onInit(getInventory(), player);
        onInitLater(getInventory(), player);
        return true;
    }

    @Override
    public boolean showAndUpdate(Player player) {
        return show(player) && update(player);
    }

    @Override
    public Inventory createInventory() {
        Inventory inventory;
        if (this.type == InventoryType.CHEST) {
            inventory = Bukkit.createInventory(this, this.rows * 9, this.name);
        } else {
            inventory = Bukkit.createInventory(this, this.type, this.name);
        }
        return inventory;
    }

    @Override
    public @NotNull Inventory getInventory() {
        return inventory;
    }

}