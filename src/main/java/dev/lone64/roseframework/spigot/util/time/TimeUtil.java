package dev.lone64.roseframework.spigot.util.time;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class TimeUtil {

    public static String getSimpleDate(String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(new Date());
    }

    public static long getDays(int seconds) {
        return TimeUnit.SECONDS.toDays(seconds);
    }

    public static long getHours(int seconds) {
        return TimeUnit.SECONDS.toHours(seconds) - getDays(seconds) * 24L;
    }

    public static long getMinutes(int seconds) {
        return TimeUnit.SECONDS.toMinutes(seconds) - TimeUnit.SECONDS.toHours(seconds) * 60L;
    }

    public static long getSeconds(int seconds) {
        return TimeUnit.SECONDS.toSeconds(seconds) - TimeUnit.SECONDS.toMinutes(seconds) * 60L;
    }

    public static String getTimeFormat(int time) {
        long days = getDays(time), hours = getHours(time), minutes = getMinutes(time), seconds = getSeconds(time);
        if (days > 0 && hours == 0 && minutes == 0 && seconds == 0)
            return "%s일".formatted(days);
        else if (days > 0 && hours > 0 && minutes == 0 && seconds == 0)
            return "%s일 %s시간".formatted(days, hours);
        else if (days > 0 && hours == 0 && minutes > 0 && seconds == 0)
            return "%s일 %s분".formatted(days, seconds);
        else if (days > 0 && hours == 0 && minutes == 0 && seconds > 0)
            return "%s일 %s초".formatted(days, seconds);
        else if (days == 0 && hours > 0 && minutes == 0 && seconds == 0)
            return "%s시간".formatted(hours);
        else if (days == 0 && hours > 0 && minutes > 0 && seconds == 0)
            return "%s시간 %s분".formatted(hours, minutes);
        else if (days == 0 && hours > 0 && minutes == 0 && seconds > 0)
            return "%s시간 %s초".formatted(hours, seconds);
        else if (days == 0 && hours == 0 && minutes > 0 && seconds == 0)
            return "%s분".formatted(minutes);
        else if (days == 0 && hours == 0 && minutes > 0 && seconds > 0)
            return "%s분 %s초".formatted(minutes, seconds);
        else if (days == 0 && hours == 0 && minutes == 0 && seconds > 0)
            return "%s초".formatted(seconds);
        else if (days > 0 && hours == 0 && minutes > 0 && seconds > 0)
            return "%s일 %s분 %s초".formatted(days, minutes, seconds);
        else if (days == 0 && hours > 0 && minutes > 0 && seconds > 0)
            return "%s시간 %s분 %s초".formatted(hours, minutes, seconds);
        else if (days > 0 && hours > 0 && minutes == 0 && seconds > 0)
            return "%s일 %s시간 %s초".formatted(days, hours, seconds);
        else if (days > 0 && hours > 0 && minutes > 0 && seconds == 0)
            return "%s일 %s시간 %s분".formatted(days, hours, minutes);
        else if (days > 0 && hours > 0 && minutes > 0 && seconds > 0)
            return "%s일 %s시간 %s분 %s초".formatted(days, hours, minutes, seconds);
        return "0초";
    }

}