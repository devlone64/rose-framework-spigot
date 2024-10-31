package dev.lone64.roseframework.spigot.builder.database.impl.connection;

import dev.lone64.roseframework.spigot.RoseModule;
import dev.lone64.roseframework.spigot.builder.database.sql.SQLConnection;
import lombok.SneakyThrows;

import java.io.File;
import java.sql.DriverManager;

public class SQLiteConnection extends SQLConnection {

    public SQLiteConnection(RoseModule module, String fileName) {
        this(module, null, fileName);
    }

    @SneakyThrows
    public SQLiteConnection(RoseModule module, String dir, String fileName) {
        var logger = module.getLogger();
        if (isConnection()) return;

        var dataFolder = module.getDataFolder();
        if (!dataFolder.exists()) {
            if (!dataFolder.mkdirs()) {
                logger.severe("Cloud not create folder to '%s'.".formatted(dataFolder.getPath().replace("\\", "/")));
            }
        }

        var dirFolder = dataFolder;
        if (dir != null && !dir.isEmpty()) {
            dirFolder = new File(dataFolder, dir);
            if (!dirFolder.exists()) {
                if (!dirFolder.mkdirs()) {
                    logger.severe("Cloud not create folder to '%s'.".formatted(dirFolder.getPath().replace("\\", "/")));
                }
            }
        }

        var dbFile = new File(dirFolder, fileName);
        if (!dbFile.exists()) {
            if (!dbFile.createNewFile()) {
                logger.severe("Cloud not create file to '%s'.".formatted(dbFile.getPath().replace("\\", "/")));
            }
        }

        try {
            Class.forName("org.sqlite.JDBC");
            setConnection(DriverManager.getConnection("jdbc:sqlite:" + dbFile));
        } catch (ClassNotFoundException e) {
            logger.severe("Failed to connect to SQLite server.");
        }
    }

}