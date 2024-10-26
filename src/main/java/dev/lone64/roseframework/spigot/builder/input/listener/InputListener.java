package dev.lone64.roseframework.spigot.builder.input.listener;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.entity.Player;

@Getter
@AllArgsConstructor
public abstract class InputListener {

    private final Player player;

    public abstract InputListener onPrevInit(Player player);
    public abstract boolean onInit(Player player, String value);
    public abstract boolean onCancel(Player player);

}