package dev.lone64.roseframework.spigot.builder.database.sql;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import java.sql.Connection;

@Setter
@Getter
public class SQLConnection {
    private Connection connection;

    @SneakyThrows
    public void disconnect() {
        if (!isConnection()) return;
        this.connection.close();
    }

    @SneakyThrows
    public boolean isConnection() {
        return !(this.connection == null || this.connection.isClosed());
    }
}