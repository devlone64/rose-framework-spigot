package dev.lone64.roseframework.spigot.command.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Path {
    CONSOLE("&cThis command cannot be executed to console!"),
    PERMISSION("&cYou do not have permission to execute this command!"),

    NOT_FOUND_CMD("&cUnknown command!"),
    NOT_FOUND_PAGE("&cThe page could not be found!"),
    NOT_REGISTERED("&cThis command is not registered!"),
    NOT_SETUP_HELP("&cThe help command is not set for this command!"),
    ARGUMENT_TYPE_MISMATCH("&cInvalid type of argument!");

    private final String message;
}