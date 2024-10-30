package dev.lone64.roseframework.spigot;

import dev.lone64.roseframework.spigot.command.manager.CommandManager;

import java.io.File;
import java.util.logging.Logger;

public class RoseLib {

    public static final RoseModule INSTANCE = RoseModule.INSTANCE;

    public static Logger getLogger() {
        return INSTANCE.getLogger();
    }
    public static File getDataFolder() {
        return INSTANCE.getDataFolder();
    }
    public static CommandManager getCommandManager() {
        return INSTANCE.getCommandManager();
    }

}