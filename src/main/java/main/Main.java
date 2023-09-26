package main;

import api.Contacts;
import api.User;
import gui.FormLogin;
import java.util.ArrayList;
import java.util.List;

/**
 * точка входа
 */
public class Main {
    public static Contacts contacts = new Contacts();
    private static List<String> sendList = new ArrayList<>();
    public static User currentUser;

    public static void main(String[] args) {
        new FormLogin().showWindow();
    }

    public static List<String> getSendList() {
        return sendList;
    }
}
