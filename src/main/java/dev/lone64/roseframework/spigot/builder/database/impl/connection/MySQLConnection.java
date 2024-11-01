package dev.lone64.roseframework.spigot.builder.database.impl.connection;

import dev.lone64.roseframework.spigot.RoseModule;
import dev.lone64.roseframework.spigot.builder.database.sql.SQLConnection;
import dev.lone64.roseframework.spigot.spigot.Spigot;
import lombok.SneakyThrows;

import java.sql.DriverManager;

public class MySQLConnection extends SQLConnection {

    @SneakyThrows
    public MySQLConnection(RoseModule module, String host, String port, String name, String username, String password) {
        var logger = module.getLogger();
        if (isConnection()) return;

        try {
            setConnection(DriverManager.getConnection("jdbc:mysql://%s:%s/%s".formatted(host, port, name), username, password));
        } catch (Exception exception) {
            Spigot.disablePlugin(module);
            logger.severe("Failed to connect to MySQL server. are the credentials correct?");
        }
    }

}