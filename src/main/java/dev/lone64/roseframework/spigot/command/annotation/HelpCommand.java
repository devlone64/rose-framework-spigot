package dev.lone64.roseframework.spigot.command.annotation;

import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface HelpCommand {
    String permission() default "";
    String permissionMessage() default "&cYou do not have permission to execute this command!";

    boolean console() default false;
    String consoleMessage() default "&cThis command cannot be executed to console!";
}