package dev.lone64.roseframework.spigot.util.file;

import dev.lone64.roseframework.spigot.RoseModule;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

public class FileUtil {

    public static boolean createFile(RoseModule module, String name) {
        try {
            File folder = module.getDataFolder();
            if (!folder.exists()) {
                if (!folder.mkdir()) {
                    module.getLogger().severe("Cloud not create to '%s'".formatted(folder.getPath()));
                }
            }

            File file = new File(module.getDataFolder(), name.replace(module.getDataFolder().getPath() + "\\", ""));
            if (file.exists()) return false;
            if (module.getResource("default/" + name.replace(module.getDataFolder().getPath() + "\\", "")) != null) {
                saveResource(module, "default/" + name.replace(module.getDataFolder().getPath() + "\\", ""), false);
                return true;
            } else {
                return file.createNewFile();
            }
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean createFolder(RoseModule module, String name) {
        File file = new File(module.getDataFolder(), name.replace(module.getDataFolder().getPath() + "\\", ""));
        if (file.exists()) return false;
        return file.mkdir();
    }

    public static boolean deleteFile(RoseModule module, String name) {
        File file = new File(module.getDataFolder(), name.replace(module.getDataFolder().getPath() + "\\", ""));
        if (!file.exists()) return false;
        return file.delete();
    }

    public static boolean deleteFolder(RoseModule module, String name) {
        try {
            File folder = new File(module.getDataFolder(), name.replace(module.getDataFolder().getPath() + "\\", ""));
            if (!folder.exists()) return false;
            FileUtils.deleteDirectory(folder);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static boolean exists(RoseModule module, String name) {
        File file = new File(module.getDataFolder(), name);
        return file.exists();
    }

    public static boolean isDirectory(RoseModule module, String name) {
        File directory = new File(module.getDataFolder(), name);
        if (!directory.exists()) return false;
        return Files.isDirectory(directory.toPath());
    }

    public static List<String> getFiles(String name) {
        String[] strings = new File(name).list();
        if (strings != null) return Arrays.stream(strings).toList();
        return new ArrayList<>();
    }

    public static void saveResource(RoseModule module, String resourcePath, boolean replace) {
        if (resourcePath != null && !resourcePath.isEmpty()) {
            resourcePath = resourcePath.replace('\\', '/');
            InputStream in = module.getResource(resourcePath);
            if (in == null) {
                throw new IllegalArgumentException("The embedded resource '" + resourcePath + "' cannot be found.");
            } else {
                File outFile = new File(module.getDataFolder(), resourcePath.replace("default/", ""));
                int lastIndex = resourcePath.lastIndexOf(47);
                if (!resourcePath.contains("default/")) {
                    File outDir = new File(module.getDataFolder(), resourcePath.substring(0, Math.max(lastIndex, 0)).replace("default/", ""));
                    if (!outDir.exists()) {
                        if (!outDir.mkdirs()) {
                            module.getLogger().severe("Cloud not create " + outDir.getName());
                        }
                    }
                }

                try {
                    if (outFile.exists() && !replace) {
                        module.getLogger().log(Level.WARNING, "Could not save " + outFile.getName() + " to " + outFile + " because " + outFile.getName() + " already exists.");
                    } else {
                        OutputStream out = new FileOutputStream(outFile);
                        byte[] buf = new byte[1024];

                        int len;
                        while((len = in.read(buf)) > 0) {
                            out.write(buf, 0, len);
                        }

                        out.close();
                        in.close();
                    }
                } catch (IOException e) {
                    module.getLogger().log(Level.SEVERE, "Could not save " + outFile.getName() + " to " + outFile, e);
                }
            }
        } else {
            throw new IllegalArgumentException("ResourcePath cannot be null or empty");
        }
    }

}