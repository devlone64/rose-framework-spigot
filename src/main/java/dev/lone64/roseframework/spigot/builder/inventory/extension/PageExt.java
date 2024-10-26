package dev.lone64.roseframework.spigot.builder.inventory.extension;

import dev.lone64.roseframework.spigot.util.array.ArrayUtil;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface PageExt<T> {
    void updatePage(Player player, int page);

    int getCurrent();
    int getItemCount();

    List<T> getItemList();

    /*
    * 아래 부분은 디폴트 값이 정해진 함수입니다.
    */
    default int getLast() {
        final List<List<T>> compounds = new ArrayUtil<T>().getChunkedList(getItems(), getItemCount());
        return compounds.isEmpty() ? 1 : compounds.size();
    }

    default List<T> getItems() {
        final Map<Integer, List<T>> compounds = new HashMap<>();
        final List<List<T>> paginates = new ArrayUtil<T>().getChunkedList(getItems(), getItemCount());
        for (int i = 0; i < paginates.size(); i++) {
            compounds.put(i + 1, paginates.get(i));
        }
        return compounds.get(getCurrent()) != null ? compounds.get(getCurrent()) : new ArrayList<>();
    }
}