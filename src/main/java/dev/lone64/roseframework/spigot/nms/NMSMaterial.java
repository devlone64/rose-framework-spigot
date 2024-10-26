package dev.lone64.roseframework.spigot.nms;

import com.cryptomorin.xseries.XMaterial;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class NMSMaterial {

    public static ItemStack setType(XMaterial material, ItemStack itemStack) {
        return material.setType(itemStack);
    }

    public static Material getMaterial(XMaterial material) {
        return material.parseMaterial();
    }

    public static ItemStack getItemStack(XMaterial material) {
        return material.parseItem();
    }

    public static XMaterial or(XMaterial material, XMaterial orMaterial) {
        return material.or(orMaterial);
    }

    public static boolean isSupported(XMaterial material) {
        return material.isSupported();
    }

}