package dev.lone64.roseframework.spigot.command.extension;

import dev.lone64.roseframework.spigot.util.array.ArrayUtil;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import static dev.lone64.roseframework.spigot.util.message.Component.from;

@Getter
public abstract class PageExt {

    private int current;

    public boolean onInit(Player player, int current) {
        setCurrent(current);
        if (getItems().isEmpty()) return false;
        player.sendMessage(from("<GRADIENT:ffffff>━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━</GRADIENT:555555>"));
        getItems().forEach(item -> player.sendMessage(from(item)));
        player.sendMessage(from("<GRADIENT:ffffff>━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━</GRADIENT:555555>"));
        return true;
    }

    public boolean onInit(Player player, int current, String title) {
        setCurrent(current);
        if (getItems().isEmpty()) return false;
        player.sendMessage(from("&r%s &7(%s/%s)".formatted(title, getCurrent(), getLast())));
        player.sendMessage(from("<GRADIENT:ffffff>&m━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━</GRADIENT:555555>"));
        getItems().forEach(item -> player.sendMessage(from(item)));
        player.sendMessage(from("<GRADIENT:ffffff>&m━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━</GRADIENT:555555>"));
        return true;
    }

    public void onInit(Player player, int current, Consumer<Integer> consumer) {
        setCurrent(current);
        if (getItems().isEmpty()) {
            consumer.accept(current);
            return;
        }
        player.sendMessage(from("<GRADIENT:ffffff>&m━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━</GRADIENT:555555>"));
        getItems().forEach(item -> player.sendMessage(from(item)));
        player.sendMessage(from("<GRADIENT:ffffff>&m━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━</GRADIENT:555555>"));
    }

    public void onInit(Player player, int current, String title, Consumer<Integer> consumer) {
        setCurrent(current);
        if (getItems().isEmpty()) {
            consumer.accept(current);
            return;
        }
        player.sendMessage(from("&r%s &7(%s/%s)".formatted(title, getCurrent(), getLast())));
        player.sendMessage(from("<GRADIENT:ffffff>&m━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━</GRADIENT:555555>"));
        getItems().forEach(item -> player.sendMessage(from(item)));
        player.sendMessage(from("<GRADIENT:ffffff>&m━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━</GRADIENT:555555>"));
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public int getLast() {
        final List<List<String>> compounds = new ArrayUtil<String>().getChunkedList(getHelps(), getItemCount());
        return compounds.isEmpty() ? 1 : compounds.size();
    }

    public List<String> getItems() {
        final Map<Integer, List<String>> compounds = new HashMap<>();
        final List<List<String>> paginates = new ArrayUtil<String>().getChunkedList(getHelps(), getItemCount());
        for (int i = 0; i < paginates.size(); i++) {
            compounds.put(i + 1, paginates.get(i));
        }
        return compounds.get(getCurrent()) != null ? compounds.get(getCurrent()) : new ArrayList<>();
    }

    public abstract int getItemCount();
    public abstract List<String> getHelps();

}