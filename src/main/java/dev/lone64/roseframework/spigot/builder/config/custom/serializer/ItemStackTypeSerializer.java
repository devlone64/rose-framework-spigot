package dev.lone64.roseframework.spigot.builder.config.custom.serializer;

import dev.lone64.roseframework.spigot.util.item.ItemUtil;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.serialize.TypeSerializer;

import java.lang.reflect.Type;

public class ItemStackTypeSerializer implements TypeSerializer<ItemStack> {

    @Override
    public void serialize(Type type, @Nullable ItemStack value, ConfigurationNode node) throws SerializationException {
        if (value == null) {
            node.raw(null);
            return;
        }
        node.set(String.class, ItemUtil.encode(value));
    }

    @Override
    public ItemStack deserialize(Type type, ConfigurationNode node) throws SerializationException {
        final String value = node.getString();
        if (value == null || value.isEmpty()) {
            throw new SerializationException("No value present!");
        }
        return ItemUtil.decode(value);
    }

}