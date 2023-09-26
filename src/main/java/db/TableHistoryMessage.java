package db;

import api.HistoryMessage;
import main.Crypto;

import java.util.List;

public class TableHistoryMessage extends BaseConnection {
    private static TableHistoryMessage tableHistoryMessage;

    public TableHistoryMessage() {
        super();
    }

    public static TableHistoryMessage getInstance() {
        if (tableHistoryMessage == null)
            return tableHistoryMessage = new TableHistoryMessage();
        else
            return tableHistoryMessage;
    }

    /**
     * добавление записи в историю сообщений
     */
    public void addHistory(HistoryMessage message) {
        String sql = String.format(
                "INSERT INTO history VALUES(NULL, \""
                        + Crypto.cryptText(message.getDate())
                        + "\", \"%s\", \"%s\");", Crypto.cryptText(message.getText()), Crypto.cryptText(message.getNumbers()));

        updateSQL(sql);
    }

    /**
     * загрузка истории сообщений
     */
    public String loadHistory() {
        String sql = "SELECT date, message, contacts FROM history;";
        List<List<String>> list = runSQL(sql);

        StringBuilder sb = new StringBuilder();

        String date;
        String text;
        String numbers;

        for (List<String> strings : list) {
            date = Crypto.decryptText(strings.get(0));
            text = Crypto.decryptText(strings.get(1));
            numbers = Crypto.decryptText(strings.get(2));

            sb.append(new HistoryMessage(date, text, numbers));
        }

        return sb.toString();
    }
}
