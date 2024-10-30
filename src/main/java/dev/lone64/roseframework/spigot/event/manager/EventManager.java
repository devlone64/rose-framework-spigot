package dev.lone64.roseframework.spigot.event.manager;

import dev.lone64.roseframework.spigot.RoseModule;
import dev.lone64.roseframework.spigot.event.BaseListener;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class EventManager {

    private final List<BaseListener> events = new ArrayList<>();
    private final RoseModule module;

    public void registerEvent(BaseListener listener) {
        this.events.add(register(listener));
    }

    public void registerEvents(BaseListener... listeners) {
        for (var listener : listeners) this.events.add(register(listener));
    }

    public void registerEvents(List<BaseListener> listeners) {
        registerEvents(listeners.toArray(new BaseListener[0]));
    }

    private BaseListener register(BaseListener listener) {
        Bukkit.getPluginManager().registerEvents(listener, getModule());
        return listener;
    }

}