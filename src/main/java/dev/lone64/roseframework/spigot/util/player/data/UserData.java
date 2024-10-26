package dev.lone64.roseframework.spigot.util.player.data;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@Setter
@Getter
public class UserData {
    private Player player;

    private int foodLevel;

    private double health;
    private double maxHealth;

    private int level;
    private float experience;

    private GameMode gameMode;

    private ItemStack mainHand;
    private ItemStack offHand;
    private ItemStack[] itemStorage;
    private ItemStack[] armorStorage;
}