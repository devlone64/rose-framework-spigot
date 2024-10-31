package dev.lone64.roseframework.spigot.command.annotation.filters;

import dev.lone64.roseframework.spigot.command.data.Filter;

import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CommandFilter {
    Filter type();
}