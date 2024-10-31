package dev.lone64.roseframework.spigot.command.context;

import dev.lone64.roseframework.spigot.RoseLib;
import dev.lone64.roseframework.spigot.command.data.Path;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommandContext {

    private final Object command;

    public Object set(Path path, String message) {
        var root = RoseLib.getWrapper().getCommandManager().getRootCommand(getCommand());
        if (root != null) root.getCommandPaths().put(path, message);
        return getCommand();
    }

}