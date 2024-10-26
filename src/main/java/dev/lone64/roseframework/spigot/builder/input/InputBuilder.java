package dev.lone64.roseframework.spigot.builder.input;

import dev.lone64.roseframework.spigot.builder.input.exception.InputAlreadyException;
import dev.lone64.roseframework.spigot.builder.input.listener.InputListener;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.*;

public class InputBuilder {

    private static final Map<UUID, InputListener> data = new HashMap<>();

    public static void set(InputListener listener) throws InputAlreadyException {
        if (is(listener.getPlayer())) throw new InputAlreadyException("이미 실행 중인 이벤트가 존재합니다.");
        data.put(listener.getPlayer().getUniqueId(), listener.onPrevInit(listener.getPlayer()));
        listener.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 99999999, 255, true));
    }

    public static InputListener remove(Player player) {
        if (player.hasPotionEffect(PotionEffectType.SLOW))
            player.removePotionEffect(PotionEffectType.SLOW);
        return data.remove(player.getUniqueId());
    }

    public static InputListener get(Player player) {
        return data.get(player.getUniqueId());
    }

    public static boolean is(Player player) {
        return data.containsKey(player.getUniqueId());
    }

    public static List<InputListener> getList() {
        return new ArrayList<>(data.values());
    }

}