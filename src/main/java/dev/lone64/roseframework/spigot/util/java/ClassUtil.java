package dev.lone64.roseframework.spigot.util.java;

import org.atteo.classindex.ClassIndex;

import java.lang.annotation.Annotation;

public class ClassUtil {

    public static Class<?> forName(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    public static <T> T getConstructor(Class<T> clazz, Object... initargs) {
        try {
            return clazz.getConstructor().newInstance(initargs);
        } catch (Exception e) {
            return null;
        }
    }

    public static <A extends Annotation> A getAnnotation(Class<?> mainClass, Class<A> annotationClass) {
        return mainClass.getAnnotation(annotationClass);
    }

    public static <A extends Annotation> boolean isAnnotationPresent(Class<?> mainClass, Class<A> annotationClass) {
        return mainClass.isAnnotationPresent(annotationClass);
    }

    public static <T> Iterable<Class<? extends T>> getSubclasses(Class<T> mainClass) {
        return ClassIndex.getSubclasses(mainClass, mainClass.getClassLoader());
    }

}