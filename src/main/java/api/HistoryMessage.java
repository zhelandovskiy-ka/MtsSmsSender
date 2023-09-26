package api;

import main.Main;
import main.Utilities;

public class HistoryMessage {
    private String date;
    private String text;
    private String numbers;

    public HistoryMessage(String text) {
        this.date = Utilities.getDate("dd.MM.yy HH:mm:ss");
        this.text = text;

        StringBuilder numbers = new StringBuilder();
        for (String s : Main.getSendList()) {
            numbers.append(s).append(";");
        }

        this.numbers = numbers.toString();
    }

    public HistoryMessage(String date, String text, String numbers) {
        this.date = date;
        this.text = text;
        this.numbers = numbers;
    }

    @Override
    public String toString() {
        return
                date
                + "\n"
                + "Кому: " + getNamesList()
                + "\n\n"
                + text
                + "\n\n"
                + "——————————————————————————"
                + "\n\n"
                ;
    }

    private String getNamesList() {
        StringBuilder names = new StringBuilder();

        String[] arNumbers = numbers.split(";");

        for (int i = 0; i < arNumbers.length; i++) {
            names.append(Main.contacts.getNameByNumber(arNumbers[i])).append("; ");
        }

        return names.toString();
    }

    public String getDate() {
        return date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getNumbers() {
        return numbers;
    }
}
