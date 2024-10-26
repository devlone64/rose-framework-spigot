package dev.lone64.roseframework.spigot.builder.item;

import dev.lone64.roseframework.spigot.util.message.Component;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.*;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.List;

public class ItemBuilder {

    private final ItemStack item;

    public ItemBuilder(ItemStack item) {
        this.item = item;
    }

    public ItemBuilder(Material material) {
        this.item = new ItemStack(material);
    }

    public ItemBuilder setType(Material type) {
        this.item.setType(type);
        return this;
    }

    public ItemBuilder setAmount(int amount) {
        this.item.setAmount(amount);
        return this;
    }

    public ItemBuilder setDamage(int damage) {
        var meta = (Damageable) this.item.getItemMeta();
        if (meta != null) meta.setDamage(-damage);
        this.item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setDisplayName(String displayName) {
        ItemMeta meta = this.item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(Component.from(displayName));
        }
        this.item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setLore(List<String> stringList) {
        ItemMeta meta = this.item.getItemMeta();
        if (meta != null) {
            meta.setLore(Component.from(stringList));
        }
        this.item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setLore(String s) {
        ItemMeta meta = this.item.getItemMeta();
        if (meta != null) {
            List<String> stringList = meta.getLore() != null ? meta.getLore() : new ArrayList<>();
            stringList.add(Component.from(s));
            meta.setLore(stringList);
        }
        this.item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder addAttributeModifier(Attribute attribute, AttributeModifier modifier) {
        ItemMeta meta = this.item.getItemMeta();
        if (meta != null) {
            meta.addAttributeModifier(attribute, modifier);
        }
        this.item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder addEnchantment(Enchantment enchantment, int i) {
        ItemMeta meta = this.item.getItemMeta();
        if (meta != null) {
            meta.addEnchant(enchantment, i, false);
        }
        this.item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder addEnchantment(Enchantment enchantment) {
        ItemMeta meta = this.item.getItemMeta();
        if (meta != null) {
            meta.removeEnchant(enchantment);
        }
        this.item.setItemMeta(meta);
        return this;
    }

    public boolean hasEnchant(Enchantment enchantment) {
        ItemMeta meta = this.item.getItemMeta();
        if (meta != null) {
            return meta.hasEnchant(enchantment);
        }
        return false;
    }

    public ItemBuilder addItemFlag(ItemFlag flag) {
        ItemMeta meta = this.item.getItemMeta();
        if (meta != null) {
            meta.addItemFlags(flag);
        }
        this.item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder removeItemFlag(ItemFlag flag) {
        ItemMeta meta = this.item.getItemMeta();
        if (meta != null) {
            meta.removeItemFlags(flag);
        }
        this.item.setItemMeta(meta);
        return this;
    }

    public boolean hasItemFlag(ItemFlag flag) {
        ItemMeta meta = this.item.getItemMeta();
        if (meta != null) {
            return meta.hasItemFlag(flag);
        }
        return false;
    }

    public ItemBuilder addPotion(PotionEffect effect) {
        PotionMeta meta = (PotionMeta) this.item.getItemMeta();
        if (meta != null) {
            meta.addCustomEffect(effect, true);
        }
        this.item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder addPotion(PotionEffect effect, boolean overwrite) {
        PotionMeta meta = (PotionMeta) this.item.getItemMeta();
        if (meta != null) {
            meta.addCustomEffect(effect, overwrite);
        }
        this.item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setOwner(String playerName) {
        SkullMeta meta = (SkullMeta) this.item.getItemMeta();
        if (meta != null) {
            meta.setOwner(playerName);
        }
        this.item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setOwner(OfflinePlayer player) {
        SkullMeta meta = (SkullMeta) this.item.getItemMeta();
        if (meta != null) {
            meta.setOwningPlayer(player);
        }
        this.item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setUnbreakable(boolean unbreakable) {
        ItemMeta meta = this.item.getItemMeta();
        if (meta != null) {
            meta.setUnbreakable(unbreakable);
        }
        this.item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setLeatherColor(Color color) {
        if (this.item.getType() != Material.LEATHER_BOOTS || this.item.getType() != Material.LEATHER_CHESTPLATE || this.item.getType() != Material.LEATHER_HELMET || this.item.getType() != Material.LEATHER_LEGGINGS) {
            throw new IllegalArgumentException("color() only applicable for leather armor!");
        } else {
            LeatherArmorMeta meta = (LeatherArmorMeta) this.item.getItemMeta();
            if (meta != null) {
                meta.setColor(color);
            }
            this.item.setItemMeta(meta);
            return this;
        }
    }

    public ItemStack getItemStack() {
        return item;
    }
    
}