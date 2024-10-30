package dev.lone64.roseframework.spigot;

import dev.lone64.roseframework.spigot.armorequip.XMaterial;
import dev.lone64.roseframework.spigot.armorequip.listener.ArmorerListener;
import dev.lone64.roseframework.spigot.armorequip.listener.DispenserArmorListener;
import dev.lone64.roseframework.spigot.builder.input.InputBuilder;
import dev.lone64.roseframework.spigot.builder.inventory.impl.BukkitInventory;
import dev.lone64.roseframework.spigot.builder.inventory.impl.CustomInventory;
import dev.lone64.roseframework.spigot.command.manager.CommandManager;
import dev.lone64.roseframework.spigot.spigot.Spigot;
import dev.lone64.roseframework.spigot.util.Console;
import dev.lone64.roseframework.spigot.util.message.Component;
import dev.lone64.roseframework.spigot.util.version.VersionUtil;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

@Getter
public class RoseModule extends JavaPlugin implements Listener {

    public static String PREFIX;

    public static Logger LOGGER;
    public static RoseModule INSTANCE;

    private final CommandManager commandManager = new CommandManager(this);

    public RoseModule() {
        INSTANCE = this;
        LOGGER = getLogger();
        PREFIX = "<GRADIENT:FF9633>{PREFIX}</GRADIENT:FFD633>&r";
    }

    @Override
    public void onLoad() {
        load();
    }

    @Override
    public void onEnable() {
        if (VersionUtil.isSupportVersion()) {
            Bukkit.getPluginManager().disablePlugin(this);
            Console.error("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            Console.error("%s 플러그인은 %s 버전을 지원하지 않습니다.".formatted(getName(), VersionUtil.getVersion()));
            Console.error("올바른 버전에서 플러그인을 사용해주세요.");
            Console.error("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            return;
        }

        Spigot.register(this);
        Spigot.register(new DispenserArmorListener());
        Spigot.register(new ArmorerListener(XMaterial.getBlockedMaterials()));
        enable();
    }

    @Override
    public void onDisable() {
        disable();
    }

    public void load() { }
    public void enable() { }
    public void disable() { }

    @EventHandler(priority= EventPriority.HIGH, ignoreCancelled=true)
    public void onPlayerQuit(PlayerQuitEvent event) {
        if (InputBuilder.is(event.getPlayer())) {
            InputBuilder.remove(event.getPlayer());
        }
    }

    @EventHandler(priority= EventPriority.HIGH, ignoreCancelled=true)
    public void onCommandPreprocess(PlayerCommandPreprocessEvent event) {
        var player = event.getPlayer();
        for (var commandData : getCommandManager().getCommandDataList()) {
            if (commandData.getName() != null && commandData.getName().toLowerCase().contains(event.getMessage().replace("/", ""))) {
                if (commandData.getPermission() != null && !commandData.getPermission().isEmpty()) {
                    if (!player.hasPermission(commandData.getPermission())) {
                        event.setCancelled(true);
                        player.sendMessage(Component.from(commandData.getBaseCommand().onPermissionRequest(player)));
                    }
                }
            }
        }
    }

    @EventHandler(priority= EventPriority.HIGH, ignoreCancelled=true)
    public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
        var listener = InputBuilder.get(event.getPlayer());
        if (InputBuilder.is(event.getPlayer())) {
            event.setCancelled(true);
            if (listener.onInit(event.getPlayer(), event.getMessage())) {
                Spigot.sync(() -> InputBuilder.remove(event.getPlayer()));
            }
        }
    }

    @EventHandler(priority= EventPriority.HIGH, ignoreCancelled=true)
    public void onPlayerMove(PlayerMoveEvent event) {
        var listener = InputBuilder.get(event.getPlayer());
        if (InputBuilder.is(event.getPlayer())) {
            var location = event.getPlayer().getLocation().clone().subtract(0, 1, 0);
            if (location.getBlock().getType() == Material.AIR && listener.onCancel(event.getPlayer())) {
                InputBuilder.remove(event.getPlayer());
            }
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player player) {
            if (event.getInventory().getHolder() instanceof BukkitInventory inventory) {
                inventory.onClick(event);
                inventory.onClick(event, player);
            }

            if (event.getInventory().getHolder() instanceof CustomInventory inventory) {
                inventory.onClick(event);
                inventory.onClick(event, player);
            }
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (event.getPlayer() instanceof Player player) {
            if (event.getInventory().getHolder() instanceof BukkitInventory inventory) {
                inventory.onClose(event);
                inventory.onClose(event, player);
            }

            if (event.getInventory().getHolder() instanceof CustomInventory inventory) {
                inventory.onClose(event);
                inventory.onClose(event, player);
            }
        }
    }

}