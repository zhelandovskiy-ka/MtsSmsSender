package db;

import api.Contact;
import main.Utilities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TableHistoryActions extends BaseConnection {
    private static TableHistoryActions tableHistoryActions;
    public TableHistoryActions() {
        super();
    }

    public static TableHistoryActions getInstance() {
        if (tableHistoryActions == null)
            return tableHistoryActions = new TableHistoryActions();
        else
            return tableHistoryActions;
    }
    /**
     * добавление истории действия в базу
     */
    public void addAction(int user, Actions action) {
        String sql = String.format(
                "INSERT INTO history_actions VALUES(NULL, " +
                        "\"%s\", %s, \"%s\");"
                , Utilities.getDate("dd.MM.yy HH:mm:ss"), user, action
        );

        updateSQL(sql);
    }

}
