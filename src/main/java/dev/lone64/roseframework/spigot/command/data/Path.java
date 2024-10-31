package dev.lone64.roseframework.spigot.command.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Path {
    NOT_REGISTERED(0, "명령어가 등록되지 않음", "&cThis command is not registered!"),
    NOT_SETUP_HELP(1, "도움말이 설정되지 않음", "&cThe help command is not set for this command!"),
    NOT_FOUND_CMD(2, "명령어를 찾을 수 없음", "&cPlease make sure that the command you entered is correct and enter it!"),
    NOT_FOUND_PAGE(3, "페이지를 찾을 수 없음", "&cThe page could not be found!");

    private final int id;
    private final String name;
    private final String message;
}