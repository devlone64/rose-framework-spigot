package dev.lone64.roseframework.spigot.builder.database.interfaces;

import dev.lone64.roseframework.spigot.builder.database.handler.ClassHandler;
import dev.lone64.roseframework.spigot.builder.database.sql.SQLDatabase;

import java.util.List;

public interface IDatabaseBuilder {
    void createTable(SQLDatabase database, String table, String columns);
    void deleteTable(SQLDatabase database, String table);

    void set(SQLDatabase database, String table, String selected, Object[] values, String logic, String column, String data);
    void setIfAbuse(SQLDatabase database, String table, String selected, Object[] values, String logic, String column, String data);
    void remove(SQLDatabase database, String table, String logic, String column, String data);

    Object get(SQLDatabase database, String table, String selected, String logic, String column, String data);
    String getString(SQLDatabase database, String table, String selected, String logic, String column, String data);
    <T> T get(SQLDatabase database, String table, String selected, String logic, String column, String data, ClassHandler<T> handler);

    List<Object> getList(SQLDatabase database, String table, String selected, String logic, String column, String data);
    List<String> getStringList(SQLDatabase database, String table, String selected, String logic, String column, String data);
    <T> List<T> getList(SQLDatabase database, String table, String selected, String logic, String column, String data, ClassHandler<T> handler);

    List<String> getKeys(SQLDatabase database, String table, String selected);

    boolean is(SQLDatabase database, String table, String column, String data);
}