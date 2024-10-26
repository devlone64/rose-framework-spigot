package dev.lone64.roseframework.spigot.nms;

import com.cryptomorin.xseries.XEnchantment;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class NMSEnchantment {

    public static Enchantment getEnchant(XEnchantment enchantment) {
        return enchantment.getEnchant();
    }

    public static ItemStack getBook(XEnchantment enchantment, int level) {
        return enchantment.getBook(level);
    }

    public static XEnchantment or(XEnchantment enchantment, XEnchantment orEnchantment) {
        return enchantment.or(orEnchantment);
    }

    public static boolean isSupported(XEnchantment enchantment) {
        return enchantment.isSupported();
    }

}