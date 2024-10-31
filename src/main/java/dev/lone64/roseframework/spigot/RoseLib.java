package dev.lone64.roseframework.spigot;

import dev.lone64.roseframework.spigot.command.manager.CommandManager;

import java.io.File;

public class RoseLib {

    public static RoseLib getWrapper() {
        return new RoseLib();
    }

    public File getDataFolder(RoseModule module) {
        return module.getDataFolder();
    }

    public File getDataFolder(RoseModule module, String path) {
        return new File(module.getDataFolder(), path);
    }

    public CommandManager getCommandManager() {
        return RoseModule.getWrapper().getCommandManager();
    }

}