package dev.lone64.roseframework.spigot.util.head;

import com.cryptomorin.xseries.XMaterial;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import dev.lone64.roseframework.spigot.nms.NMSMaterial;
import dev.lone64.roseframework.spigot.nms.NmsVersion;
import dev.lone64.roseframework.spigot.util.other.Request;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerProfile;
import org.bukkit.profile.PlayerTextures;

import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Base64;
import java.util.UUID;

public class HeadUtil {

    public static ItemStack getHeadItem() {
        return NMSMaterial.getItemStack(XMaterial.PLAYER_HEAD);
    }

    public static ItemStack getTexturedHead(String value) {
        ItemStack headItem = getHeadItem();
        applyTexture(headItem, value);
        return headItem;
    }

    private static void applyTexture(ItemStack itemStack, String value) {
        if (NmsVersion.getCurrentVersion().isNewHead()) {
            applyHigherTexture(itemStack, value);
        } else {
            applyLowerTexture(itemStack, value);
        }
    }

    private static void applyLowerTexture(ItemStack itemStack, String value) {
        SkullMeta headMeta = (SkullMeta) itemStack.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), "paperframework_head");
        profile.getProperties().put("textures", new Property("textures", value));
        if (headMeta != null) {
            try {
                Field profileField = headMeta.getClass().getDeclaredField("profile");
                profileField.setAccessible(true);
                profileField.set(headMeta, profile);
            } catch (IllegalArgumentException | NoSuchFieldException | SecurityException | IllegalAccessException ignored) { }
        }
        itemStack.setItemMeta(headMeta);
    }

    private static void applyHigherTexture(ItemStack itemStack, String value) {
        SkullMeta headMeta = (SkullMeta) itemStack.getItemMeta();
        if (headMeta != null) {
            headMeta.setOwnerProfile(getProfile(value));
        }
        itemStack.setItemMeta(headMeta);
    }

    private static PlayerProfile getProfile(String value) {
        PlayerProfile profile = Bukkit.createPlayerProfile(UUID.randomUUID(), "paperframework_head");
        PlayerTextures textures = profile.getTextures();
        URL urlObject;
        try {
            urlObject = getUrlFromBase64(value);
        } catch (MalformedURLException e) {
            throw new RuntimeException("Invalid URL", e);
        }
        textures.setSkin(urlObject);
        profile.setTextures(textures);
        return profile;
    }

    private static URL getUrlFromBase64(String base64) throws MalformedURLException {
        String decoded = new String(Base64.getDecoder().decode(base64));
        return Request.createURL(decoded.substring("{\"textures\":{\"SKIN\":{\"url\":\"".length(), decoded.length() - "\"}}}".length()));
    }

}