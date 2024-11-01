package dev.lone64.roseframework.spigot.command.annotation;

import dev.lone64.roseframework.spigot.command.type.Path;

import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Route {
    Path path();
}