package dev.lone64.roseframework.spigot.util.version;

import dev.lone64.roseframework.spigot.RoseModule;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VersionUtil {

    private static final List<String> SUPPORT_VERSIONS = new ArrayList<>();

    public static void setSupportVersions(List<String> supportVersions) {
        SUPPORT_VERSIONS.clear();
        SUPPORT_VERSIONS.addAll(supportVersions);
    }

    public static void setSupportVersions(String... supportVersions) {
        SUPPORT_VERSIONS.clear();
        SUPPORT_VERSIONS.addAll(List.of(supportVersions));
    }

    public static int getMcVersion() {
        Pattern versionPattern = Pattern.compile("1\\.(\\d{1,2})(?:\\.(\\d{1,2}))?");
        Matcher versionMatcher = versionPattern.matcher(Bukkit.getVersion());
        int mcVersion = 0;
        if (versionMatcher.find()) {
            try {
                String patchText = versionMatcher.group(2);
                int minor = Integer.parseInt(versionMatcher.group(1));
                int patch = (patchText == null || patchText.isEmpty()) ? 0 : Integer.parseInt(patchText);
                mcVersion = (minor * 100) + patch;
            } catch (NumberFormatException e) {
                RoseModule.INSTANCE.getLogger().warning(e.getMessage());
            }
        }
        return mcVersion;
    }

    public static String getServerVersion() {
        return Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
    }

    public static String getVersion() {
        return Bukkit.getServer().getBukkitVersion().replace("-R0.1-SNAPSHOT", "");
    }

    public static boolean isSupportVersion() {
        for (String s : SUPPORT_VERSIONS) {
            if (s.equalsIgnoreCase(getVersion())) {
                return true;
            }
        }
        return false;
    }

}