package dev.lone64.roseframework.spigot.command.annotation.command;

import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MainCommand {
    String label();
    String usage() default "/<command>";
    String description() default "No description provided.";
    String[] aliases() default { };

    String permission() default "";
    String permissionMessage() default "&cYou do not have permission to execute this command!";

    boolean console() default false;
    String consoleMessage() default "&cThis command cannot be executed to console!";

    boolean autoHelp() default false;
}