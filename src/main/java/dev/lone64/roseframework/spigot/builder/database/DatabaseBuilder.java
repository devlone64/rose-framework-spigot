package dev.lone64.roseframework.spigot.builder.database;

import dev.lone64.roseframework.spigot.builder.database.handler.ClassHandler;
import dev.lone64.roseframework.spigot.builder.database.interfaces.IDatabaseBuilder;
import dev.lone64.roseframework.spigot.builder.database.sql.SQLDatabase;

import java.util.List;

public class DatabaseBuilder implements IDatabaseBuilder {

    public static DatabaseBuilder getInstance() {
        return new DatabaseBuilder();
    }

    @Override
    public void createTable(SQLDatabase database, String table, String columns) {
        database.createTable(table, columns);
    }

    @Override
    public void deleteTable(SQLDatabase database, String table) {
        database.deleteTable(table);
    }

    @Override
    public void set(SQLDatabase database, String table, String selected, Object[] values, String logic, String column, String data) {
        database.set(table, selected, values, logic, column, data);
    }

    @Override
    public void setIfAbuse(SQLDatabase database, String table, String selected, Object[] values, String logic, String column, String data) {
        database.setIfAbuse(table, selected, values, logic, column, data);
    }

    @Override
    public void remove(SQLDatabase database, String table, String logic, String column, String data) {
        database.remove(table, logic, column, data);
    }

    @Override
    public Object get(SQLDatabase database, String table, String selected, String logic, String column, String data) {
        return database.get(table, selected, logic, column, data);
    }

    @Override
    public String getString(SQLDatabase database, String table, String selected, String logic, String column, String data) {
        return database.getString(table, selected, logic, column, data);
    }

    @Override
    public <T> T get(SQLDatabase database, String table, String selected, String logic, String column, String data, ClassHandler<T> handler) {
        return database.get(table, selected, logic, column, data, handler);
    }

    @Override
    public List<Object> getList(SQLDatabase database, String table, String selected, String logic, String column, String data) {
        return database.getList(table, selected, logic, column, data);
    }

    @Override
    public List<String> getStringList(SQLDatabase database, String table, String selected, String logic, String column, String data) {
        return database.getStringList(table, selected, logic, column, data);
    }

    @Override
    public <T> List<T> getList(SQLDatabase database, String table, String selected, String logic, String column, String data, ClassHandler<T> handler) {
        return database.getList(table, selected, logic, column, data, handler);
    }

    @Override
    public List<String> getKeys(SQLDatabase database, String table, String selected) {
        return database.getKeys(table, selected);
    }

    @Override
    public boolean is(SQLDatabase database, String table, String column, String data) {
        return database.is(table, column, data);
    }

}