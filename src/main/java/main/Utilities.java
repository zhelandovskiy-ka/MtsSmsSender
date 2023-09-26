package main;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

public class Utilities {
    /**
    * Получение текущей даты
    */
    public static String getDate(String template) {
        return new SimpleDateFormat(template).format(new Date());
    }

    /**
     * Приостановка выполнения
     */
    public static void sleep(int sec) {
        try {
            Thread.sleep(sec * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Получение MAC адреса
     */
    public static void getMac() {
        InetAddress ip = null;
        try {
            ip = InetAddress.getLocalHost();
            System.out.println("Current IP address : " + ip.getHostAddress());

            NetworkInterface network = NetworkInterface.getByInetAddress(ip);

            byte[] mac = network.getHardwareAddress();

            System.out.print("Current MAC address : ");

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < mac.length; i++) {
                sb.append(String.format("%02X%s", mac[i], ""));
            }
            System.out.println(sb);
        } catch (UnknownHostException | SocketException e) {
            throw new RuntimeException(e);
        }
    }

    public static void scanNumbers() {
        try {
            Stream<String> stream = Files.lines(Paths.get("contacts.txt"));
            List<String> list = new ArrayList<>();
            stream.forEach(list::add);

            for (String s : list) {
                System.out.println("7" + s.substring(1));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void scanNames() {
        try {
            Stream<String> stream = Files.lines(Paths.get("contacts.txt"));
            List<String> list = new ArrayList<>();
            stream.forEach(list::add);

            for (String s : list) {
                int pos1 = s.indexOf("(");
                int pos2 = s.indexOf(")");
                System.out.println(s.substring(pos1 + 1, pos2));
//                System.out.println(s.substring(pos2 + 2));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
