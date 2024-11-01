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

    boolean helpAvailable() default false;
    boolean consoleAvailable() default false;
}