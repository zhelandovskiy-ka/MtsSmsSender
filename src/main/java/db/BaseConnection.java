package db;

import main.Logs;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * подключение базы данных
 */
public class BaseConnection {
    protected static Connection connection;

    protected BaseConnection() {
        try {
            DriverManager.registerDriver(new org.sqlite.JDBC());
            String connectionURL = "jdbc:sqlite:";
            String baseName = "base.db";
            connection = DriverManager.getConnection(connectionURL + baseName);
        } catch (SQLException e) {
            e.printStackTrace();
            Logs.writeLog("BaseConnection 15\n" + e.getMessage());
        }
    }

    public List<List<String>> runSQL(String sql) {
        List<List<String>> res = new ArrayList<>();
        List list;

        try {
            ResultSet resultSet = getResultSet(sql);
            String[] columns = getColumns(resultSet.getMetaData());
            while (resultSet.next()) {
                list = new ArrayList();
                for (String column : columns) {
//                        builder.append(String.format("%s:%s | ", column, resultSet.getObject(column)));
                    list.add(resultSet.getObject(column).toString());
                }
                res.add(list);
            }
        } catch (SQLException e) {
            Logs.writeLog("BaseConnection.runSQL 27\n" + e.getMessage());
        }

        return res;
    }

    protected ResultSet getResultSet(String sql) {
        try {
            Statement statement = connection.createStatement();
            return statement.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    protected void updateSQL(String sql) {
        System.out.println(sql);
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
//            Logs.writeLog("Запись добавлена");
        } catch (SQLException e) {
            Logs.writeLog(e.getMessage());
            Logs.writeLog("BaseConnection.updateSQL 63\n" + e.getMessage());
        }
    }

    protected String[] getColumns(ResultSetMetaData metadata) throws SQLException {

        String[] columns = new String[metadata.getColumnCount()];
        for (int i = 0; i < columns.length; i++) {
            columns[i] = metadata.getColumnName(i + 1);
        }

        return columns;

    }
}
