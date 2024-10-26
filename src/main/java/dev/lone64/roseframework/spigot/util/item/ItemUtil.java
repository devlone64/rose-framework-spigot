package dev.lone64.roseframework.spigot.util.item;

import dev.lone64.roseframework.spigot.util.nms.NmsUtil;
import dev.lone64.roseframework.spigot.util.other.Base64;
import dev.lone64.roseframework.spigot.util.other.Request;
import dev.lone64.roseframework.spigot.util.version.VersionUtil;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.json.simple.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class ItemUtil {

    public static List<ItemStack> sortItems(List<ItemStack> itemList) {
        Map<String, List<ItemStack>> itemMap = new HashMap<>();
        for (ItemStack item : itemList) {
            String key = item.getType() + ":" + item.getDurability() + ":" + item.getItemMeta().toString();
            itemMap.putIfAbsent(key, new ArrayList<>());
            List<ItemStack> stackList = itemMap.get(key);
            int amount = item.getAmount();
            for (ItemStack stack : stackList) {
                int space = 64 - stack.getAmount();
                if (space > 0) {
                    int toAdd = Math.min(space, amount);
                    stack.setAmount(stack.getAmount() + toAdd);
                    amount -= toAdd;
                    if (amount <= 0) {
                        break;
                    }
                }
            }
            while (amount > 0) {
                int toAdd = Math.min(64, amount);
                ItemStack newStack = item.clone();
                newStack.setAmount(toAdd);
                stackList.add(newStack);
                amount -= toAdd;
            }
        }
        itemList = new ArrayList<>();
        for (List<ItemStack> stacks : itemMap.values()) {
            itemList.addAll(stacks);
        }
        return itemList;
    }


    public static ItemStack getUsed(ItemStack is) {
        if (is.getAmount() == 1) {
            return null;
        } else {
            is.setAmount(is.getAmount() - 1);
            return is;
        }
    }

    public static ItemStack getUsed(ItemStack is, int amount) {
        if (is.getAmount() == 1) {
            return null;
        } else {
            is.setAmount(is.getAmount() - amount);
            return is;
        }
    }

    public static ItemStack getAmount(ItemStack is, int amount) {
        is.setAmount(amount);
        return is;
    }

    public static int getAmount(Player player, ItemStack is) {
        int amount = 0;
        for (int i = 0; i < 36; ++i) {
            ItemStack itemSlot = player.getInventory().getItem(i);
            if (itemSlot != null && itemSlot.isSimilar(is)) {
                amount += itemSlot.getAmount();
            }
        }
        return amount;
    }

    public static String getLocalizedName(ItemStack is, Locale locale) {
        final String version = "v" + VersionUtil.getVersion();
        final String itemPath = NmsUtil.getGameItemPath(is);
        if (itemPath != null) {
            final JSONObject object = Request.getObject("https://raw.githubusercontent.com/devlone64/mc-assets/main/langs/%s/%s.json".formatted(version, locale.name()));
            if (object != null) {
                final String itemName = object.get(itemPath).toString();
                if (itemName != null) return itemName;
            }
        }

        final ItemMeta meta = is.getItemMeta();
        if (meta == null) {
            return is.getType().name();
        }

        return meta.getDisplayName();
    }

    public static String encode(ItemStack itemStack) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            GZIPOutputStream gzipOutputStream = new GZIPOutputStream(byteArrayOutputStream);
            ObjectOutputStream objectOutputStream = new BukkitObjectOutputStream(gzipOutputStream);
            objectOutputStream.writeObject(itemStack);
            objectOutputStream.close();
            return Base64.encode(byteArrayOutputStream.toByteArray());
        } catch (IOException e) {
            return null;
        }
    }

    public static ItemStack decode(String string) {
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(Base64.decode(string));
            GZIPInputStream gzipInputStream = new GZIPInputStream(byteArrayInputStream);
            ObjectInputStream objectInputStream = new BukkitObjectInputStream(gzipInputStream);
            ItemStack item = (ItemStack) objectInputStream.readObject();
            objectInputStream.close();
            return item;
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }

    public static ItemMeta getItemMeta(ItemStack itemStack) {
        return itemStack.getItemMeta();
    }

    public static boolean isItemSimilar(ItemStack first, ItemStack twice) {
        ItemMeta firstMeta = getItemMeta(first);
        ItemMeta twiceMeta = getItemMeta(twice);
        if (!(firstMeta.hasDisplayName() && twiceMeta.hasDisplayName()))
            return first.getType() == twice.getType();
        return first.getType() == twice.getType() && firstMeta.getDisplayName().equalsIgnoreCase(twiceMeta.getDisplayName());
    }

    public static boolean isSameItem(ItemStack first, ItemStack twice) {
        if (first == null || twice == null) return false;
        return first.getType() == twice.getType() &&
                first.getAmount() == twice.getAmount() &&
                (first.getItemMeta() == null ? twice.getItemMeta() == null : first.getItemMeta().equals(twice.getItemMeta()));
    }

    public enum Locale {
        ko_KR, en_US
    }

}