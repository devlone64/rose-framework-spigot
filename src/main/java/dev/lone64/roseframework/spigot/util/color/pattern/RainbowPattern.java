package dev.lone64.roseframework.spigot.util.color.pattern;

import dev.lone64.roseframework.spigot.util.color.ColorPattern;
import dev.lone64.roseframework.spigot.util.color.ColorUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RainbowPattern implements ColorPattern {

    private final Pattern pattern = Pattern.compile("<RAINBOW([0-9]{1,3})>(.*?)</RAINBOW>");

    public String process(String string) {
        Matcher matcher = pattern.matcher(string);
        while (matcher.find()) {
            String saturation = matcher.group(1);
            String content = matcher.group(2);
            string = string.replace(matcher.group(), ColorUtil.rainbow(content, Float.parseFloat(saturation)));
        }
        return string;
    }

}