package dev.lone64.roseframework.spigot.command.annotation.command;

import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SubCommand {
    String label();
    String description() default "No description provided.";
    String permission() default "";
    boolean consoleAvailable() default true;
}