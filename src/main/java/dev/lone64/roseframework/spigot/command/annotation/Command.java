package dev.lone64.roseframework.spigot.command.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Command {
    String name();
    String usage() default "";
    String comment() default "";
    String[] aliases() default {};
    String permission() default "";
    boolean consoleAvailable() default false;
}