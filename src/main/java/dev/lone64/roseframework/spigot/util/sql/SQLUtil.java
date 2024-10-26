package dev.lone64.roseframework.spigot.util.sql;

import dev.lone64.roseframework.spigot.builder.database.sql.SQLConnection;
import lombok.SneakyThrows;

import java.sql.PreparedStatement;

public class SQLUtil {

    @SneakyThrows
    public static PreparedStatement prepareStatement(SQLConnection conn, String sql, Object... objectsToSet) {
        var statement = conn.getConnection().prepareStatement(sql);
        for (int i = 0; i < objectsToSet.length; i++) statement.setObject(i + 1, objectsToSet[i]);
        return statement;
    }

}