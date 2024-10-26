package dev.lone64.roseframework.spigot.builder.config.custom.processor;

import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.objectmapping.meta.Processor;
import org.spongepowered.configurate.serialize.SerializationException;

import java.util.Collection;
import java.util.Map;

public class DeleteOnEmptyProcessor implements Processor<Object> {

    @Override
    public void process(final Object value, final ConfigurationNode destination) {
        if (value == null) {
            return;
        }

        try {
            if (value instanceof Map && ((Map<?, ?>) value).isEmpty()) {
                destination.set(null);
            } else if (value instanceof Collection && ((Collection<?>) value).isEmpty()) {
                destination.set(null);
            } else if (value instanceof CharSequence && ((CharSequence) value).length() == 0) {
                destination.set(null);
            }
        } catch (SerializationException ignored) {}
    }

}