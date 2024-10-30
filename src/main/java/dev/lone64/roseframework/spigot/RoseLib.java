package dev.lone64.roseframework.spigot;

import java.util.logging.Logger;

public class RoseLib {

    public static RoseModule getInstance() {
        return RoseModule.getPlugin(RoseModule.class);
    }

    public static Logger getLogger() {
        return getInstance().getLogger();
    }

}