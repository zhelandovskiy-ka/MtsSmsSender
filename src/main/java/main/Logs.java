package main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logs {
    public static void writeLog(String text) {
        boolean show = true;
//        boolean show = false;
        if (show) {
            String date = new SimpleDateFormat("dd.MM HH:mm:ss").format(new Date());
            text = date + " " + text + "\n";
            text = "-----------------------\n" + text + "-----------------------\n\n";
            try {
                Files.write(Paths.get("logs.txt"), text.getBytes(), StandardOpenOption.APPEND);
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
    }
}
