package gui;

import javax.swing.*;

public class Notifications {
    public static void showError(String text) {
        JOptionPane.showMessageDialog(null, text, "Ошибка", JOptionPane.ERROR_MESSAGE);
    }
    public static void showWarning(String text) {
        JOptionPane.showMessageDialog(null, text, "Информация", JOptionPane.WARNING_MESSAGE);
    }
    public static void showInfo(String text) {
        JOptionPane.showMessageDialog(null, text, "Информация", JOptionPane.INFORMATION_MESSAGE);
    }
}
