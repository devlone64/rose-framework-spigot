package dev.lone64.roseframework.spigot.command.loader;

import dev.lone64.roseframework.spigot.command.annotation.command.MainCommand;
import dev.lone64.roseframework.spigot.command.util.CommandUtil;
import dev.lone64.roseframework.spigot.util.message.Component;
import lombok.Getter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;

@Getter
public class CommandLoader implements CommandExecutor {

    private final Object object;
    private final Method method;
    private final MainCommand mainCommand;

    public CommandLoader(Object object, Method method) {
        this.object = object;
        this.method = method;
        this.mainCommand = method.getAnnotation(MainCommand.class);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String arg, @NotNull String[] args) {
        if (!this.mainCommand.permission().isEmpty() && !sender.hasPermission(this.mainCommand.permission())) {
            sender.sendMessage(Component.from(this.mainCommand.permissionMessage()));
            return true;
        }
        return CommandUtil.invokeBoolean(this.method, this.object, sender, args);
    }

}