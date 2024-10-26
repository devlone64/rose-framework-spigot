package dev.lone64.roseframework.spigot.util.nms;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Objects;

public class ItemNmsUtil {

    private final ItemStack stack;

    public ItemNmsUtil(ItemStack stack) {
        this.stack = stack;
    }

    public ItemNmsUtil setCompound(String name, String val) {
        ItemMeta meta = this.stack.getItemMeta();
        if (meta != null) {
            String tag = meta.getLocalizedName();
            meta.setLocalizedName(tag + "[" + name + ":" + val + "]/");
            this.stack.setItemMeta(meta);
        }
        return this;
    }

    public String getCompound(String name) {
        ItemMeta meta = this.stack.getItemMeta();
        if (meta != null) {
            for (String s : meta.getLocalizedName().split("/")) {
                if (s.contains(name)) return s.replace(name + ":", "").replace("[", "")
                        .replace("]", "").replace("/", "");
            }
        }
        return null;
    }

    public ItemStack getItemStack() {
        return stack;
    }

    public String toString() {
        return Objects.requireNonNull(this.stack.getItemMeta()).getLocalizedName();
    }

}
