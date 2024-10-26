package dev.lone64.roseframework.spigot.util.color.pattern;

import dev.lone64.roseframework.spigot.util.color.ColorPattern;
import dev.lone64.roseframework.spigot.util.color.ColorUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SolidPattern implements ColorPattern {

    private final Pattern pattern = Pattern.compile("<SOLID:([0-9A-Fa-f]{6})>|#\\{([0-9A-Fa-f]{6})}");

    public String process(String string) {
        Matcher matcher = pattern.matcher(string);
        while (matcher.find()) {
            String color = matcher.group(1);
            if (color == null) color = matcher.group(2);

            string = string.replace(matcher.group(), ColorUtil.getColor(color) + "");
        }
        return string;
    }

}