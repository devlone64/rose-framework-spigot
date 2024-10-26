package dev.lone64.roseframework.spigot.builder.inventory.impl;

import dev.lone.itemsadder.api.FontImages.FontImageWrapper;
import dev.lone.itemsadder.api.FontImages.TexturedInventoryWrapper;
import dev.lone64.roseframework.spigot.builder.inventory.annotation.Gui;
import dev.lone64.roseframework.spigot.builder.inventory.data.InventoryProvider;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

public class CustomInventory implements InventoryProvider<TexturedInventoryWrapper>, InventoryHolder {

    private final TexturedInventoryWrapper inventory;

    private final int rows;
    private final int offset;
    private final int nameOffset;

    private final String name;
    private final String texturedId;
    private final InventoryType type;

    public CustomInventory() {
        if (getClass().isAnnotationPresent(Gui.class)) {
            Gui data = getClass().getAnnotation(Gui.class);
            this.rows = data.rows();
            this.offset = data.offset();
            this.nameOffset = data.nameOffset();

            this.name = data.name();
            this.texturedId = data.textureId();
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
        this.inventory.showInventory(player);
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
    public TexturedInventoryWrapper createInventory() {
        TexturedInventoryWrapper inventory;
        if (this.type == InventoryType.CHEST) {
            inventory = new TexturedInventoryWrapper(
                    this,
                    this.rows * 9,
                    this.name,
                    new FontImageWrapper(this.texturedId),
                    this.nameOffset,
                    this.offset
            );
        } else {
            inventory = new TexturedInventoryWrapper(
                    this,
                    this.type,
                    this.name,
                    new FontImageWrapper(this.texturedId),
                    this.nameOffset,
                    this.offset
            );
        }
        return inventory;
    }

    @Override
    public @NotNull Inventory getInventory() {
        return inventory.getInternal();
    }

}