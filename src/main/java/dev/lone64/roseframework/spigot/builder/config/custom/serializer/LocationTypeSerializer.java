package dev.lone64.roseframework.spigot.builder.config.custom.serializer;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.serialize.TypeSerializer;

import java.lang.reflect.Type;

public class LocationTypeSerializer implements TypeSerializer<Location> {

    @Override
    public void serialize(Type type, @Nullable Location value, ConfigurationNode node) throws SerializationException {
        if (value == null || value.getWorld() == null) {
            node.raw(null);
            return;
        }

        node.node("world").set(String.class, value.getWorld().getName());
        node.node("x").set(Double.class, value.getX());
        node.node("y").set(Double.class, value.getY());
        node.node("z").set(Double.class, value.getZ());
        node.node("yaw").set(Float.class, value.getYaw());
        node.node("pitch").set(Float.class, value.getPitch());
    }

    @Override
    public Location deserialize(Type type, ConfigurationNode node) throws SerializationException {
        final String worldValue = node.node("world").getString();
        if (worldValue == null || worldValue.isEmpty()) {
            throw new SerializationException("No world value present!");
        }

        return new Location(
                Bukkit.getWorld(node.node("world").getString("world")),
                node.node("x").getDouble(),
                node.node("y").getDouble(),
                node.node("z").getDouble(),
                node.node("yaw").getFloat(),
                node.node("pitch").getFloat());
    }

}