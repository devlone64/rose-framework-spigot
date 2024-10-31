package dev.lone64.roseframework.spigot.command.root;

import dev.lone64.roseframework.spigot.command.annotation.command.HelpCommand;
import dev.lone64.roseframework.spigot.command.annotation.command.MainCommand;
import dev.lone64.roseframework.spigot.command.annotation.command.SubCommand;
import dev.lone64.roseframework.spigot.command.annotation.command.TabCommand;
import dev.lone64.roseframework.spigot.command.annotation.filter.CommandFilter;
import dev.lone64.roseframework.spigot.command.data.Filter;
import dev.lone64.roseframework.spigot.command.extension.PageExt;
import dev.lone64.roseframework.spigot.command.manager.CommandManager;
import dev.lone64.roseframework.spigot.command.util.CommandUtil;
import dev.lone64.roseframework.spigot.util.message.Component;
import dev.lone64.roseframework.spigot.util.number.NumberUtil;
import lombok.Getter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Method;
import java.util.*;

@Getter
public class RootCommand extends PageExt {

    private final JavaPlugin plugin;

    private final Object command;
    private final Class<?> commandClass;
    private final MainCommand mainCommand;
    private final CommandManager commandManager;

    private final Map<String, SubCommand> userSubCommands = new HashMap<>();
    private final Map<String, SubCommand> adminSubCommands = new HashMap<>();

    private final Map<Filter, String> commandFilters = new HashMap<>();
    private final Map<Class<?>, Method> commandMethods = new HashMap<>();

    public RootCommand(JavaPlugin plugin, Object command, CommandManager commandManager) {
        this.plugin = plugin;

        this.command = command;
        this.commandClass = command.getClass();
        this.mainCommand = this.commandClass.getAnnotation(MainCommand.class);
        this.commandManager = commandManager;

        initMethods();
        for (var type : Filter.values()) {
            if (!this.commandFilters.containsKey(type)) {
                this.commandFilters.put(type, type.getMessage());
            }
        }
    }

    public void setupPluginCommand(PluginCommand command) {
        command.setUsage(this.mainCommand.usage());
        command.setDescription(this.mainCommand.description());
        command.setAliases(Arrays.asList(this.mainCommand.aliases()));
        command.setPermission(this.mainCommand.permission());
        command.setPermissionMessage(this.mainCommand.permissionMessage());
        command.setExecutor(this::performCommand);
        command.setTabCompleter(this::performTabCompleter);
    }

    private boolean performCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!this.commandManager.getRegisteredCommands().containsKey(label)) {
            var message = this.commandFilters.get(Filter.NOT_REGISTERED);
            sender.sendMessage(Component.from(message));
            return true;
        } else if (this.mainCommand.console()) {
            sender.sendMessage(Component.from(this.mainCommand.consoleMessage()));
            return true;
        } else if (args.length == 0) {
            if (this.mainCommand.autoHelp()) {
                var message = this.commandFilters.get(Filter.NOT_FOUND_PAGE);
                onInit(sender, 1, this.commandManager.getTitle(), page ->
                        sender.sendMessage(Component.from(message)));
                return true;
            }

            var method = this.commandMethods.get(HelpCommand.class);
            if (this.commandMethods.containsKey(HelpCommand.class)) {
                return CommandUtil.invokeBoolean(method, this.command, sender);
            }
            var message = this.commandFilters.get(Filter.NOT_SETUP_HELP);
            sender.sendMessage(Component.from(message));
            return true;
        } else if (NumberUtil.getIntegerOrNull(args[0]) != null && this.mainCommand.autoHelp()) {
            var current = NumberUtil.getIntegerOrElse(args[0], 1);
            var message = this.commandFilters.get(Filter.NOT_FOUND_PAGE);
            onInit(sender, current, this.commandManager.getTitle(), page ->
                    sender.sendMessage(Component.from(message)));
            return true;
        }

        for (var method : this.commandClass.getDeclaredMethods()) {
            if (method.isAnnotationPresent(SubCommand.class)) {
                var subCommand = method.getAnnotation(SubCommand.class);
                if (args[0].equalsIgnoreCase(subCommand.label())) {
                    if (subCommand.console() && sender instanceof ConsoleCommandSender) {
                        sender.sendMessage(Component.from(subCommand.consoleMessage()));
                        return true;
                    } else if (!subCommand.permission().isEmpty() && !sender.hasPermission(subCommand.permission())) {
                        sender.sendMessage(Component.from(subCommand.permissionMessage()));
                        return true;
                    }
                    var arguments = new String[args.length - 1];
                    System.arraycopy(args, 1, arguments, 0, arguments.length);
                    return CommandUtil.invokeBoolean(method, this.command, sender, arguments);
                }
            }
        }
        var message = this.commandFilters.get(Filter.NOT_FOUND_CMD);
        sender.sendMessage(Component.from(message));
        return true;
    }

    private List<String> performTabCompleter(CommandSender sender, Command command, String label, String[] args) {
        var method = this.commandMethods.get(TabCommand.class);
        if (this.commandMethods.containsKey(TabCommand.class)) {
            var tabCommand = method.getAnnotation(TabCommand.class);
            if (tabCommand.console() && sender instanceof ConsoleCommandSender) {
                return new ArrayList<>();
            } else if (args.length == 1) {
                if (!this.mainCommand.permission().isEmpty() && !sender.hasPermission(this.mainCommand.permission())) {
                    return new ArrayList<>(userSubCommands.keySet());
                }
                var subCommands = new ArrayList<String>();
                if (!this.userSubCommands.isEmpty())
                    subCommands.addAll(new ArrayList<>(userSubCommands.keySet()));
                if (!this.adminSubCommands.isEmpty())
                    subCommands.addAll(new ArrayList<>(adminSubCommands.keySet()));
                return subCommands;
            }
            var index = args.length - 1;
            return CommandUtil.invokeList(method, this.command, sender, index, args[0]);
        }
        return new ArrayList<>();
    }

    private void initMethods() {
        for (var method : this.commandClass.getDeclaredMethods()) {
            if (method.isAnnotationPresent(HelpCommand.class)) {
                this.commandMethods.put(HelpCommand.class, method);
            } else if (method.isAnnotationPresent(SubCommand.class)) {
                var cmd = method.getAnnotation(SubCommand.class);
                this.userSubCommands.put(cmd.label(), cmd);
                if (!cmd.permission().isEmpty()) {
                    this.adminSubCommands.put(cmd.label(), cmd);
                }
            } else if (method.isAnnotationPresent(TabCommand.class)) {
                this.commandMethods.put(TabCommand.class, method);
            } else if (method.isAnnotationPresent(CommandFilter.class)) {
                for (var type : Filter.values()) {
                    var message = CommandUtil.invokeString(method, this.command, type);
                    if (message != null) this.commandFilters.put(type, message);
                }
            }
        }
    }

    @Override
    protected List<String> getHelps(CommandSender sender) {
        var helps = new ArrayList<String>();
        for (var userCmd : this.userSubCommands.values()) {
            helps.add(this.commandManager.getFormat().replace("%NAME%", this.mainCommand.label())
                    .replace("%SUB_NAME%", userCmd.label()).replace("%DESCRIPTION%", userCmd.description()));
        }
        for (var adminCmd : this.adminSubCommands.values()) {
            if (sender.hasPermission(adminCmd.permission())) {
                helps.add(this.commandManager.getFormat().replace("%NAME%", this.mainCommand.label())
                        .replace("%SUB_NAME%", adminCmd.label()).replace("%DESCRIPTION%", adminCmd.description()));
            }
        }
        return helps;
    }

}