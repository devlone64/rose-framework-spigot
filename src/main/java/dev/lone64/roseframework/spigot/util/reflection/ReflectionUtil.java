package dev.lone64.roseframework.spigot.util.reflection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectionUtil {
    
    public static String asNMSCopy(String version, Class<?> nmsItemStackClass, Object nmsItemStack) {
        try {
            switch (version) {
                case "v1_17_R1", "v1_17_R2", "v1_18_R1" -> {
                    Method method = nmsItemStackClass.getMethod("n");
                    return method.invoke(nmsItemStack).toString();
                }
                case "v1_18_R2", "v1_18_R3" -> {
                    Method method = nmsItemStackClass.getMethod("o");
                    return method.invoke(nmsItemStack).toString();
                }
                case "v1_19_R1", "v1_19_R2", "v1_19_R3" -> {
                    Method method = nmsItemStackClass.getMethod("p");
                    return method.invoke(nmsItemStack).toString();
                }
                case "v1_20_R1", "v1_20_R2", "v1_20_R3" -> {
                    Method method = nmsItemStackClass.getMethod("q");
                    return method.invoke(nmsItemStack).toString();
                }
                case "v1_20_R4" -> {
                    Method method = nmsItemStackClass.getMethod("t");
                    return method.invoke(nmsItemStack).toString();
                }
            }
            return null;
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            return null;
        }
    }
    
}