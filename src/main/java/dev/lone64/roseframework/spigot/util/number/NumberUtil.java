package dev.lone64.roseframework.spigot.util.number;

import java.text.DecimalFormat;

public class NumberUtil {

    public static String getDecimalInteger(int num) {
        DecimalFormat df = new DecimalFormat("#,##0");
        return df.format(num);
    }

    public static String getDecimalDouble(double num) {
        DecimalFormat df = new DecimalFormat("#,##0.0");
        return df.format(num);
    }

    public static String getDecimalFloat(float num) {
        DecimalFormat df = new DecimalFormat("#,##0.0");
        return df.format(num);
    }

    public static String getDecimalLong(long num) {
        DecimalFormat df = new DecimalFormat("#,##0");
        return df.format(num);
    }

    public static Integer getIntegerOrNull(Object raw) {
        try {
            return Integer.valueOf(raw.toString().trim());
        } catch (Throwable ignored) {
            return null;
        }
    }

    public static int getIntegerOrElse(Object raw, int def) {
        Integer value = getIntegerOrNull(raw);
        return value != null ? value : def;
    }

    public static Double getDoubleOrNull(Object raw) {
        try {
            return Double.valueOf(raw.toString().trim());
        } catch (Throwable ignored) {
            return null;
        }
    }

    public static double getDoubleOrElse(Object raw, double def) {
        Double value = getDoubleOrNull(raw);
        return value != null ? value : def;
    }

    public static Float getFloatOrNull(Object raw) {
        try {
            return Float.valueOf(raw.toString().trim());
        } catch (Throwable ignored) {
            return null;
        }
    }

    public static float getFloatOrElse(Object raw, float def) {
        Float value = getFloatOrNull(raw);
        return value != null ? value : def;
    }

    public static double progress(int min, int max) {
        return (double) min / max;
    }

    public static int randomInt(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public static double randomDouble(double min, double max) {
        return (Math.random() * (max - min)) + min;
    }

    public static double randomFloat(float min, float max) {
        return (Math.random() * (max - min)) + min;
    }

    public static boolean isInt(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isDouble(String s) {
        try {
            Double.parseDouble(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isFloat(String s) {
        try {
            Float.parseFloat(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isLong(String s) {
        try {
            Long.parseLong(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}