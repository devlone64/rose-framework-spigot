package dev.lone64.roseframework.spigot.command.manager;

import dev.lone64.roseframework.spigot.RoseModule;
import dev.lone64.roseframework.spigot.command.BaseCommand;
import dev.lone64.roseframework.spigot.command.data.CommandData;
import dev.lone64.roseframework.spigot.command.executor.CmdExecutor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class CommandManager {

    private final List<CommandData> commandDataList = new ArrayList<>();
    private final RoseModule module;

    public void registerCommand(BaseCommand command) {
        register(new CommandData(getModule(), command));
    }

    public void registerCommands(BaseCommand... commands) {
        for (var cmd : commands) register(new CommandData(getModule(), cmd));
    }

    public void registerCommands(List<BaseCommand> commands) {
        registerCommands(commands.toArray(new BaseCommand[0]));
    }

    private void register(CommandData commandData) {
        var commandMap = getCommandMap();
        if (commandMap != null) {
            unregister(commandData.getName());

            var command = createCommand(commandData.getName());
            if (command != null) {
                command.setExecutor(new CmdExecutor(commandData));
                command.setTabCompleter(new CmdExecutor(commandData));

                command.setName(commandData.getName());
                command.setUsage(commandData.getUsage());
                command.setDescription(commandData.getComment());
                command.setAliases(commandData.getAliases());
                command.setPermission(commandData.getPermission());
                commandMap.register(getModule().getName(), command);
                getCommandDataList().add(commandData);
            }
        }
    }

    public void unregister(String name) {
        try {
            var field = getField("commandMap");
            field.setAccessible(true);

            var commandMap = (CommandMap) field.get(Bukkit.getServer());
            var command = commandMap.getCommand(name);
            if (command != null) command.unregister(commandMap);
        } catch (Exception e) {
            getModule().getLogger().severe(e.getMessage());
        }
    }

    public PluginCommand createCommand(String name) {
        try {
            var field = getField("commandMap");
            field.setAccessible(true);

            var commandMap = (CommandMap) field.get(Bukkit.getServer());
            var cmdCons = PluginCommand.class.getDeclaredConstructor(String.class, Plugin.class);
            cmdCons.setAccessible(true);

            var command = cmdCons.newInstance(name, getModule());
            commandMap.register(getModule().getName(), command);
            return command;
        } catch (Exception e) {
            getModule().getLogger().severe(e.getMessage());
            return null;
        }
    }

    private CommandMap getCommandMap() {
        try {
            var field = getField("commandMap");
            field.setAccessible(true);
            return (CommandMap) field.get(Bukkit.getServer());
        } catch (Exception e) {
            getModule().getLogger().severe(e.getMessage());
            return null;
        }
    }

    private Field getField(String name) throws NoSuchFieldException {
        return getModule().getServer().getClass().getDeclaredField("commandMap");
    }

}