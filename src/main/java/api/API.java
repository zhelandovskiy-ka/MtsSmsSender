package api;

import main.Logs;
import main.Main;
import main.Parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * класс для работы с API MTS Communicator
 */
public class API {
    private final static String TOKEN = "121231231"; //необходим api ключ
    private final static String NAMING = "NAMING";
    private final static String URL = "https://api.mcommunicator.ru/m2m/m2m_api.asmx";

    /**
     * отправка сообщения
     */
    public static Response sendMessage(String message) {
        try {
            String data =
                    String.format("<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
                            "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
                            "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"\n" +
                            "xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                            "<soap:Body>\n" +
                            "<SendMessages xmlns=\"http://mcommunicator.ru/M2M\">\n" +
                            "<msids>\n" +
                            generateNumberList() +
                            "</msids>\n" +
                            "<message>%s</message>\n" +
                            "<naming>%s</naming>\n" +
                            "</SendMessages>\n" +
                            "</soap:Body>\n" +
                            "</soap:Envelope>", message, NAMING);

            HttpURLConnection http = getHttpURLConnection(data, "http://mcommunicator.ru/M2M/SendMessages");

            byte[] out = data.getBytes(StandardCharsets.UTF_8);

            OutputStream stream = http.getOutputStream();
            stream.write(out);

            StringBuilder responseBuilder = new StringBuilder();
            InputStreamReader is = new InputStreamReader(http.getInputStream(), StandardCharsets.UTF_8);
            Path pathToResponse = Paths.get("response.xml");

            try (BufferedReader br = new BufferedReader(is)) {
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    responseBuilder.append(responseLine.trim());
                }
                Files.write(pathToResponse, responseBuilder.toString().getBytes());
            } catch (Exception e) {
                Logs.writeLog("API.sendMessage 79 " + e.getMessage());
            }

            List<MessageIds> messageIds = Parser.parse("response.xml");

            Files.delete(pathToResponse);

            Response response = new Response(http.getResponseCode(), messageIds);

            http.disconnect();

            getMessageStatus(messageIds);

            return response;
        } catch (IOException e) {
            Logs.writeLog("API.sendMessage 96 " + e.getMessage());
        }

        return new Response(-1);
    }

    /**
     * Получение статуса отправленных сообщений
     */
    public static void getMessageStatus(List<MessageIds> list) {
        try {
            String data =
                    String.format(
                            "<?xml version=\"1.0\" encoding=\"utf-8\"?> " +
                                    "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" " +
                                    "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" " +
                                    "xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"> " +
                                    "<soap:Body> " +
                                    "<GetMessagesStatus xmlns=\"http://mcommunicator.ru/M2M\"> " +
                                    "<messageIDs>" +
                                    generateIdsList(list) +
                                    "</messageIDs> " +
                                    "</GetMessagesStatus> " +
                                    "</soap:Body> " +
                                    "</soap:Envelope>"
                    );

            HttpURLConnection http = getHttpURLConnection(data, "http://mcommunicator.ru/M2M/GetMessagesStatus");

            byte[] out = data.getBytes(StandardCharsets.UTF_8);

            OutputStream stream = http.getOutputStream();
            stream.write(out);


            StringBuilder responseBuilder = new StringBuilder();
            InputStreamReader is = new InputStreamReader(http.getInputStream(), StandardCharsets.UTF_8);
            try (BufferedReader br = new BufferedReader(is)) {
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    responseBuilder.append(responseLine.trim());
                }
//                System.out.println(responseBuilder);
                Files.write(Paths.get("responseMesStatus.xml"), responseBuilder.toString().getBytes());
            } catch (Exception e) {
                Logs.writeLog("API.getMessageStatus 152 " + e.getMessage());
            }
            http.disconnect();
        } catch (IOException e) {
            Logs.writeLog("API.getMessageStatus 156 " + e.getMessage());
        }
    }

    private static HttpURLConnection getHttpURLConnection(String data, String value) throws IOException {
        URL url = new URL(URL);
        HttpURLConnection http = (HttpURLConnection) url.openConnection();
        http.setRequestMethod("POST");
        http.setDoOutput(true);
        http.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
        http.setRequestProperty("Authorization", "Bearer " + TOKEN);
        http.setRequestProperty("Content-Length", String.valueOf(data.length()));
        http.setRequestProperty("SOAPAction", value);
        return http;
    }

    /**
     * генерация списка контактов для отправки
     */
    private static String generateNumberList() {
        StringBuilder builder = new StringBuilder();
        for (String s : Main.getSendList()) {
            builder.append("<string>").append(s).append("</string>");
        }
        builder.append("7911111111");

        return builder.toString();
    }

    /**
     * генерация списка id отправленных сообщений
     */
    private static String generateIdsList(List<MessageIds> list) {
        StringBuilder builder = new StringBuilder();

        for (MessageIds messageIds : list) {
            builder.append("<long>").append(messageIds.getMesId()).append("</long>");
        }

        return builder.toString();
    }
}
