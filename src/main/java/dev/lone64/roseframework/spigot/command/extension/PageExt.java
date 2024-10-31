package dev.lone64.roseframework.spigot.command.extension;

import dev.lone64.roseframework.spigot.util.array.ArrayUtil;
import dev.lone64.roseframework.spigot.util.message.Component;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

@Setter
@Getter
public abstract class PageExt {

    private int current;

    protected boolean onInit(CommandSender sender, int current) {
        setCurrent(current);
        if (getItems(sender).isEmpty()) return false;
        sender.sendMessage(Component.from("<GRADIENT:ffffff>&m━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━</GRADIENT:555555>"));
        getItems(sender).forEach(item -> sender.sendMessage(Component.from(item)));
        sender.sendMessage(Component.from("<GRADIENT:ffffff>&m━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━</GRADIENT:555555>"));
        return true;
    }

    protected boolean onInit(CommandSender sender, int current, String title) {
        setCurrent(current);
        if (getItems(sender).isEmpty()) return false;
        sender.sendMessage(Component.from("&r%s &7(%s/%s)".formatted(title, getCurrent(), getLast(sender))));
        sender.sendMessage(Component.from("<GRADIENT:ffffff>&m━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━</GRADIENT:555555>"));
        getItems(sender).forEach(item -> sender.sendMessage(Component.from(item)));
        sender.sendMessage(Component.from("<GRADIENT:ffffff>&m━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━</GRADIENT:555555>"));
        return true;
    }

    protected void onInit(CommandSender sender, int current, Consumer<Integer> consumer) {
        setCurrent(current);
        if (getItems(sender).isEmpty()) {
            consumer.accept(current);
            return;
        }
        sender.sendMessage(Component.from("<GRADIENT:ffffff>&m━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━</GRADIENT:555555>"));
        getItems(sender).forEach(item -> sender.sendMessage(Component.from(item)));
        sender.sendMessage(Component.from("<GRADIENT:ffffff>&m━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━</GRADIENT:555555>"));
    }

    protected void onInit(CommandSender sender, int current, String title, Consumer<Integer> consumer) {
        setCurrent(current);
        if (getItems(sender).isEmpty()) {
            consumer.accept(current);
            return;
        }
        sender.sendMessage(Component.from("&r%s &7(%s/%s)".formatted(title, getCurrent(), getLast(sender))));
        sender.sendMessage(Component.from("<GRADIENT:ffffff>&m━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━</GRADIENT:555555>"));
        getItems(sender).forEach(item -> sender.sendMessage(Component.from(item)));
        sender.sendMessage(Component.from("<GRADIENT:ffffff>&m━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━</GRADIENT:555555>"));
    }

    protected int getLast(CommandSender sender) {
        final List<List<String>> compounds = new ArrayUtil<String>().getChunkedList(getHelps(sender), 5);
        return compounds.isEmpty() ? 1 : compounds.size();
    }

    protected List<String> getItems(CommandSender sender) {
        final Map<Integer, List<String>> compounds = new HashMap<>();
        final List<List<String>> paginates = new ArrayUtil<String>().getChunkedList(getHelps(sender), 5);
        for (int i = 0; i < paginates.size(); i++) {
            compounds.put(i + 1, paginates.get(i));
        }
        return compounds.get(getCurrent()) != null ? compounds.get(getCurrent()) : new ArrayList<>();
    }

    protected abstract List<String> getHelps(CommandSender sender);

}