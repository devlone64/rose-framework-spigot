package dev.lone64.roseframework.spigot.builder.database.sql;

import dev.lone64.roseframework.spigot.builder.database.handler.ClassHandler;

import java.util.List;

public interface SQLDatabase {
    void createTable(String table, String columns);
    void deleteTable(String table);

    void set(String table, String selected, Object[] values, String logic, String column, String data);
    void setIfAbuse(String table, String selected, Object[] values, String logic, String column, String data);
    void remove(String table, String logic, String column, String data);

    Object get(String table, String selected, String logic, String column, String data);
    String getString(String table, String selected, String logic, String column, String data);
    <T> T get(String table, String selected, String logic, String column, String data, ClassHandler<T> handler);

    List<Object> getList(String table, String selected, String logic, String column, String data);
    List<String> getStringList(String table, String selected, String logic, String column, String data);
    <T> List<T> getList(String table, String selected, String logic, String column, String data, ClassHandler<T> handler);

    List<String> getKeys(String table, String selected);

    boolean is(String table, String column, String data);
}