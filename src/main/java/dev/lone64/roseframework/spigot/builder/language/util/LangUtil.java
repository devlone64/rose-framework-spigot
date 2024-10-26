package dev.lone64.roseframework.spigot.builder.language.util;

import dev.lone64.roseframework.spigot.builder.language.data.Language;
import dev.lone64.roseframework.spigot.builder.language.storage.CachedLangList;
import dev.lone64.roseframework.spigot.builder.language.storage.CachedUserList;
import dev.lone64.roseframework.spigot.util.message.Component;
import org.bukkit.entity.Player;

import java.util.List;

public class LangUtil {

    public static String getMsg(Player player, String path) {
        Language language = CachedUserList.get(player.getUniqueId());
        if (language == null) return null;
        return Component.from(language.getString(path));
    }

    public static String getMsg(Player player, String path, String def) {
        Language language = CachedUserList.get(player.getUniqueId());
        if (language == null) return null;
        return Component.from(language.getString(path, def));
    }

    public static List<String> getMsgList(Player player, String path) {
        Language language = CachedUserList.get(player.getUniqueId());
        if (language == null) return null;
        return Component.from(language.getStringList(path));
    }

    public static String getMsg(String lang, String path) {
        Language language = CachedLangList.get(lang);
        if (language == null) return null;
        return Component.from(language.getString(path));
    }

    public static String getMsg(String lang, String path, String def) {
        Language language = CachedLangList.get(lang);
        if (language == null) return null;
        return Component.from(language.getString(path, def));
    }

    public static List<String> getMsgList(String lang, String path) {
        Language language = CachedLangList.get(lang);
        if (language == null) return null;
        return Component.from(language.getStringList(path));
    }

}