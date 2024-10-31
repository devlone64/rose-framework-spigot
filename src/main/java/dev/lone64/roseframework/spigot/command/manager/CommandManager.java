package dev.lone64.roseframework.spigot.command.manager;

import dev.lone64.roseframework.spigot.command.annotation.MainCommand;
import dev.lone64.roseframework.spigot.command.root.RootCommand;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

@Setter
@Getter
@AllArgsConstructor
public class CommandManager {

    private String title;
    private String format;

    private final JavaPlugin plugin;
    private final Map<String, RootCommand> rootCommands = new HashMap<>();
    private final Map<String, PluginCommand> registeredCommands = new HashMap<>();

    public CommandManager(JavaPlugin plugin) {
        this.plugin = plugin;

        this.title = "&a&l%s".formatted(plugin.getName());
        this.format = "&e/%NAME% %SUB_NAME%: &f%DESCRIPTION%";
    }

    public void registerCommand(Object command) {
        registerCommands(command);
    }
    
    public void registerCommands(Object... commands) {
        var commandMap = getCommandMap();
        if (commandMap != null) {
            for (var command : commands) {
                var commandClass = command.getClass();
                var mainCommand = commandClass.getAnnotation(MainCommand.class);
                if (commandClass.isAnnotationPresent(MainCommand.class)) {
                    unregisterCommands(command);

                    var pluginCommand = setupPluginCommand(mainCommand.label());
                    if (pluginCommand != null) {
                        var rootCommand = new RootCommand(this.plugin, command, this);
                        rootCommand.setupPluginCommand(pluginCommand);

                        commandMap.register(this.plugin.getName(), pluginCommand);

                        this.rootCommands.put(mainCommand.label(), rootCommand);
                        this.registeredCommands.put(mainCommand.label(), pluginCommand);

                        for (var label : mainCommand.aliases()) {
                            var aliasCommand = setupPluginCommand(label);
                            if (aliasCommand != null) {
                                aliasCommand.setUsage(mainCommand.usage());
                                aliasCommand.setDescription(mainCommand.description());
                                aliasCommand.setPermission(mainCommand.permission());
                                aliasCommand.setPermissionMessage(mainCommand.permissionMessage());
                                aliasCommand.setExecutor(pluginCommand.getExecutor());
                                aliasCommand.setTabCompleter(pluginCommand.getTabCompleter());
                                
                                commandMap.register(this.plugin.getName(), aliasCommand);
                                this.registeredCommands.put(label, aliasCommand);
                            }
                        }
                    }
                }
            }
        }
    }

    public void unregisterCommand(Object command) {
        unregisterCommands(command);
    }

    public void unregisterCommands(Object... commands) {
        for (var command : commands) {
            var commandClass = command.getClass();
            var mainCommand = commandClass.getAnnotation(MainCommand.class);
            if (commandClass.isAnnotationPresent(MainCommand.class)) {
                unregister(mainCommand.label());
                for (var alias : mainCommand.aliases()) {
                    unregister(alias);
                }
            }
        }
    }

    public RootCommand getRootCommand(Object command) {
        for (var rootCommand : this.rootCommands.values()) {
            if (rootCommand.getCommand().equals(command)) {
                return rootCommand;
            }
        }
        return null;
    }

    private void unregister(String commandName) {
        var commandMap = getCommandMap();
        if (commandMap != null) {
            var command = commandMap.getCommand(commandName);
            if (command != null) {
                if (command.getName().equalsIgnoreCase(commandName)) {
                    command.unregister(commandMap);
                    this.rootCommands.remove(commandName);
                    this.registeredCommands.remove(commandName);
                }
            }
        }
    }

    private PluginCommand setupPluginCommand(String name) {
        try {
            var commandMap = getCommandMap();
            if (commandMap != null) {
                var constructor = PluginCommand.class.getDeclaredConstructor(String.class, Plugin.class);
                constructor.setAccessible(true);

                var pluginCommand = constructor.newInstance(name, this.plugin);
                commandMap.register(this.plugin.getName(), pluginCommand);
                return pluginCommand;
            }
            return null;
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | java.lang.reflect.InvocationTargetException e) {
            return null;
        }
    }

    private CommandMap getCommandMap() {
        try {
            var f = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            f.setAccessible(true);
            return (CommandMap) f.get(Bukkit.getServer());
        } catch (Exception e) {
            return null;
        }
    }

}