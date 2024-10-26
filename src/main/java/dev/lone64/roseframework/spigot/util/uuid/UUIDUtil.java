package dev.lone64.roseframework.spigot.util.uuid;

import dev.lone64.roseframework.spigot.util.other.Base64;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.*;
import java.util.UUID;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class UUIDUtil {

    public static String getString(UUID uuid) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            GZIPOutputStream gzipOutputStream = new GZIPOutputStream(byteArrayOutputStream);
            ObjectOutputStream objectOutputStream = new BukkitObjectOutputStream(gzipOutputStream);
            objectOutputStream.writeObject(uuid);
            objectOutputStream.close();
            return Base64.encode(byteArrayOutputStream.toByteArray());
        } catch (IOException e) {
            return null;
        }
    }

    public static UUID getUUID(String string) {
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(Base64.decode(string));
            GZIPInputStream gzipInputStream = new GZIPInputStream(byteArrayInputStream);
            ObjectInputStream objectInputStream = new BukkitObjectInputStream(gzipInputStream);
            UUID uuid = (UUID) objectInputStream.readObject();
            objectInputStream.close();
            return uuid;
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }

    public static UUID from(final String name) {
        for (final OfflinePlayer p : Bukkit.getOfflinePlayers()) {
            if (p.getName() != null && p.getName().equals(name)) {
                return p.getUniqueId();
            }
        }
        return null;
    }

}