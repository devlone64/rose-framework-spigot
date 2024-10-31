package dev.lone64.roseframework.spigot.util.entity;

import dev.lone64.roseframework.spigot.RoseModule;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class EntityUtil {

    public static void hideAll(RoseModule module, Entity entity) {
        for (Player loopPlayer : Bukkit.getOnlinePlayers()) {
            loopPlayer.hideEntity(module, entity);
        }
    }

    public static void showAll(RoseModule module, Entity entity) {
        for (Player loopPlayer : Bukkit.getOnlinePlayers()) {
            loopPlayer.showEntity(module, entity);
        }
    }

    public static void hideAll(RoseModule module, List<Player> players, Entity entity) {
        for (Player loopPlayer : players) {
            loopPlayer.hideEntity(module, entity);
        }
    }

    public static void showAll(RoseModule module, List<Player> players, Entity entity) {
        for (Player loopPlayer : players) {
            loopPlayer.showEntity(module, entity);
        }
    }

    public static <T extends Entity> T spawnEntity(Location location, Class<T> type) {
        if (location.getWorld() == null) return null;
        return location.getWorld().spawn(location, type);
    }

    public static CompletableFuture<Entity> setCircleMoving(RoseModule module, Player player, Entity entity) {
        CompletableFuture<Entity> future = new CompletableFuture<>();
        new BukkitRunnable() {
            int count = 10;
            double angle = 0;
            @Override
            public void run() {
                if (!player.isOnline() || count == 0) {
                    cancel();
                    future.complete(entity);
                    return;
                }

                angle += Math.PI / 16;
                double x = player.getLocation().getX() + 1 * Math.cos(angle);
                double z = player.getLocation().getZ() + 1 * Math.sin(angle);
                double y = player.getLocation().getY() + 1;

                float yaw = player.getLocation().getYaw();
                float pitch = player.getLocation().getPitch();

                entity.teleport(new Location(player.getWorld(), x, y, z, yaw, pitch));
                count--;
            }
        }.runTaskTimer(module, 0L, 2L);
        return future;
    }

    public static void removeEntity(World world, int entityId) {
        Entity entity = getEntityById(world, entityId);
        if (entity != null) entity.remove();
    }

    public static void removeEntity(World world, UUID uniqueId) {
        Entity entity = getEntityByUniqueId(world, uniqueId);
        if (entity != null) entity.remove();
    }

    public static Entity getEntityById(World world, int entityId) {
        for (Entity entity : world.getEntities()) {
            if (entity.getEntityId() == entityId) {
                return entity;
            }
        }
        return null;
    }

    public static Entity getEntityByUniqueId(World world, UUID uniqueId) {
        for (Entity entity : world.getEntities()) {
            if (entity.getUniqueId().equals(uniqueId)) {
                return entity;
            }
        }
        return null;
    }

}