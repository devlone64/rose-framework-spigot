package dev.lone64.roseframework.spigot.builder.inventory.annotation;

import org.bukkit.event.inventory.InventoryType;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Gui {
    int rows() default 6;
    String name() default "";
    InventoryType type() default InventoryType.CHEST;

    /*
    * 아래 부분은 ItemsAdder의 Gui에 필요한 부분입니다.
    */
    int offset() default 0;
    int nameOffset() default 0;
    String textureId() default "";
}