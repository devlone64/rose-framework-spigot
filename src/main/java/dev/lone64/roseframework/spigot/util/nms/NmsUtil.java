package dev.lone64.roseframework.spigot.util.nms;

import dev.lone64.roseframework.spigot.util.reflection.ReflectionUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class NmsUtil {

    private static String version;

    private static Class<?> craftPlayerClass;
    private static Class<?> craftItemStackClass;

    static {
        try {
            version = Bukkit.getServer().getClass().getName().replace(".", ",").split(",")[3];
            craftPlayerClass = Class.forName("org.bukkit.craftbukkit." + version + ".entity.CraftPlayer");
            craftItemStackClass = Class.forName("org.bukkit.craftbukkit." + version + ".inventory.CraftItemStack");
        } catch (ClassNotFoundException e) {
            // logger
        }
    }

    public static void setGameProfileNickname(Player player, String nickname) {
        try {
            Object craftPlayer = craftPlayerClass.cast(player);
            Method method = craftPlayerClass.getMethod("getProfile");
            Object gameProfile = method.invoke(craftPlayer);
            Field field = gameProfile.getClass().getDeclaredField("name");
            field.setAccessible(true);
            field.set(gameProfile, nickname);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | NoSuchFieldException ignored) { }
    }

    public static String getGameItemPath(ItemStack is) {
        try {
            Method method = craftItemStackClass.getDeclaredMethod("asNMSCopy", ItemStack.class);
            Object nmsItemStack = method.invoke(null, is);
            Class<?> nmsItemStackClass = nmsItemStack.getClass();
            return ReflectionUtil.asNMSCopy(version, nmsItemStackClass, nmsItemStack);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            return null;
        }
    }

}