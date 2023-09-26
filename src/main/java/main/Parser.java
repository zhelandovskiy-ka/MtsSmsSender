package main;

import api.MessageIds;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Parser {
    public static List<MessageIds> parse(String fileName) {

        DocumentBuilder documentBuilder;
        try {
            documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
        // Создается дерево DOM документа из файла
        Document document;
        try {
            document = documentBuilder.parse(fileName);
        } catch (SAXException | IOException e) {
            throw new RuntimeException(e);
        }

        List<MessageIds> messageIds = new ArrayList<>();
        Node root = document.getDocumentElement();
        Node body = root.getChildNodes().item(0).getChildNodes().item(0).getChildNodes().item(0);

        NodeList mesResults = body.getChildNodes();

        String number = "";
        String id = "";

        for (int i = 0; i < mesResults.getLength(); i++) {
            Node mesIds = mesResults.item(i);

            if (mesIds.getNodeType() == 1) {
                NodeList mesIdsParam = mesIds.getChildNodes();
                number = mesIdsParam.item(0).getChildNodes().item(0).getTextContent();
                id = mesIdsParam.item(1).getChildNodes().item(0).getTextContent();

                if (!number.equals("") && !id.equals("")) {
                    messageIds.add(new MessageIds(number, id));
                    id = "";
                    number = "";
                }
            }
        }

        return messageIds;
    }
}
