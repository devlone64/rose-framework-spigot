package dev.lone64.roseframework.spigot.command.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class CommandUtil {

    public static String invokeString(Field field, Object arg) {
        try {
            field.setAccessible(true);
            return (String) field.get(arg);
        } catch (IllegalAccessException e) {
            return null;
        }
    }

    public static String invokeString(Method method, Object arg, Object... args) {
        try {
            method.setAccessible(true);
            return (String) method.invoke(arg, args);
        } catch (InvocationTargetException | IllegalAccessException e) {
            return null;
        }
    }

    public static boolean invokeBoolean(Method method, Object arg, Object... args) {
        try {
            method.setAccessible(true);
            return (boolean) method.invoke(arg, args);
        } catch (InvocationTargetException | IllegalAccessException e) {
            return false;
        }
    }

    public static List<String> invokeList(Method method, Object arg, Object... args) {
        try {
            method.setAccessible(true);
            return (List<String>) method.invoke(arg, args);
        } catch (InvocationTargetException | IllegalAccessException e) {
            return new ArrayList<>();
        }
    }

}