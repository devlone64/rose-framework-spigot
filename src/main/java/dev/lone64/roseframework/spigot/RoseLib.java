package dev.lone64.roseframework.spigot;

import java.io.File;

public class RoseLib {

    public static RoseLib getInstance() {
        return new RoseLib();
    }

    public File getDataFolder(RoseModule module) {
        return module.getDataFolder();
    }

    public File getDataFolder(RoseModule module, String path) {
        return new File(module.getDataFolder(), path);
    }

}