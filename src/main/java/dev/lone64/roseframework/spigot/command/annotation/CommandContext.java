package dev.lone64.roseframework.spigot.command.annotation;

import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface CommandContext {
    String not_registered() default "&cThis command is not registered!";
    String not_setup_help() default "&cThe help command is not set for this command!";
    String not_found_page() default "&cPlease make sure that the command you entered is correct and enter it!";
    String not_found_command() default "&cThe page could not be found!";
}