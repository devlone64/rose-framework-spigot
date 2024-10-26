package dev.lone64.roseframework.spigot.command.provider;

import org.bukkit.entity.Player;

public interface SenderProvider {
    default String onConsoleRequest() {
        return "&c해당 명령어는 콘솔에서 사용할 수 없습니다.";
    }

    default String onPermissionRequest(Player player) {
        return "&c당신은 해당 명령어를 사용할 권한이 없습니다.";
    }
}