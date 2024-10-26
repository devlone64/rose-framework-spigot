package dev.lone64.roseframework.spigot.nms;

import com.cryptomorin.xseries.XPotion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

public class NMSPotion {

    public static PotionEffect getEffect(XPotion potion, int duration, int amplifier) {
        return potion.buildPotionEffect(duration, amplifier);
    }

    public static PotionType getType(XPotion potion) {
        return potion.getPotionType();
    }

    public static PotionEffectType getEffectType(XPotion potion) {
        return potion.getPotionEffectType();
    }

    public static XPotion or(XPotion potion, XPotion orPotion) {
        return potion.or(orPotion);
    }

    public static boolean isSupported(XPotion potion) {
        return potion.isSupported();
    }

}