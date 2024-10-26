package dev.lone64.roseframework.spigot;

import dev.lone64.roseframework.spigot.command.manager.CommandManager;

import java.io.File;
import java.util.logging.Logger;

public class RoseLib {

    public static final RoseModule INSTANCE = RoseModule.INSTANCE;

    public static final Logger LOGGER = INSTANCE.getLogger();
    public static final File DATA_FOLDER = INSTANCE.getDataFolder();

    public static CommandManager getCommandManager() {
        return INSTANCE.getCommandManager();
    }

}