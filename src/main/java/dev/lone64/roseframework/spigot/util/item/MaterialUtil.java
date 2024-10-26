package dev.lone64.roseframework.spigot.util.item;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

public class MaterialUtil {

    public static boolean isAir(final Block block) {
        return block == null || isAir(block.getType());
    }

    public static boolean isAir(@Nullable ItemStack item) {
        return item == null || isAir(item.getType());
    }

    public static boolean isAir(final Material material) {
        return material == null || isAir(material.name());
    }

    public static boolean isAir(final String materialName) {
        return materialName == null || "AIR".equals(materialName) || "CAVE_AIR".equals(materialName) || "VOID_AIR".equals(materialName) || "LEGACY_AIR".equals(materialName);
    }

}