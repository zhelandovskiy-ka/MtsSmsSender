package db;

import main.Crypto;
import main.Utilities;

import javax.swing.*;
import java.util.List;

public class TableUsers extends BaseConnection {

    private static TableUsers tableUsers;

    public TableUsers() {
        super();
    }

    public static TableUsers getInstance() {
        if (tableUsers == null)
            return tableUsers = new TableUsers();
        else
            return tableUsers;
    }

    /**
     * Проверка учетных данных пользователя
     */
    public int checkUser(String login, char[] password) {
        if (checkLogin(login)) {
            int res = checkPassword(login, password);
            if (res != -1) {
                return res;
            } else
                JOptionPane.showMessageDialog(null, "Неверный пароль", "Ошибка", JOptionPane.ERROR_MESSAGE);
        } else
            JOptionPane.showMessageDialog(null, "Пользователь не найден", "Ошибка", JOptionPane.ERROR_MESSAGE);

        return -1;
    }

    /**
     * Проверка существования пользователя
     */
    private Boolean checkLogin(String login) {
        String sql = "SELECT login FROM users;";
        List<List<String>> userList = runSQL(sql);
        for (List<String> list : userList) {
            if (list.get(0).equalsIgnoreCase(login))
                return true;
        }

        return false;
    }

    /**
     * Проверка пароля
     */
    private int checkPassword(String login, char[] password) {
        int result = -1;
        String sql = "SELECT id, password FROM users WHERE login like \"" + login + "\";";
        List<List<String>> passList = runSQL(sql);

        String cryptPass = Crypto.decryptText(passList.get(0).get(1));

        if (cryptPass.equals(String.valueOf(password)))
            result = Integer.parseInt(passList.get(0).get(0));

        return result;
    }

    /**
     * Обновление даты последнего логина
     */
    public void updateLoginDate(String login) {
        String date = Utilities.getDate("dd.MM.yyyy HH:mm");
        String sql = "UPDATE users SET last_login=\"" + date + "\" WHERE login LIKE \"" + login + "\";";
        updateSQL(sql);
    }

}
