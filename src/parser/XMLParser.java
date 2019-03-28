package parser;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class XMLParser {

    // не знаю, как достать с какого-либо сайта xml файл, поэтому взял с интернета рандомный файл и "спарсил."
    // here example of using library to parse XML:
    // https://www.mkyong.com/java/how-to-read-xml-file-in-java-dom-parser/
    public static List<NewsItem> parseXML(String source) throws IOException, ParserConfigurationException {
        PrintWriter writer = new PrintWriter("example.xml");
        writer.print(source);
        writer.close();
        ArrayList<NewsItem> list = new ArrayList<>();
        try {
            // объект документа
            File file = new File("Example.xml");
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = documentBuilder.parse("Example.xml");
            document.getDocumentElement().normalize();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E, d MMM yyyy HH:mm:ss");
            LocalDateTime time = LocalDateTime.parse("Thu, 15 Aug 2013 17:37:13 +0300", DateTimeFormatter.RFC_1123_DATE_TIME);
            System.out.println(time);



            NodeList nList = document.getElementsByTagName("item");
            for(int i = 0; i < nList.getLength(); i++) {
                Node nNode = nList.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    NewsItem item = new NewsItem();
                    item.setTitle(eElement.getElementsByTagName("title").item(0).getTextContent());
//                    System.out.println("title : " + eElement.getElementsByTagName("title").item(0).getTextContent());
                    item.setLink(eElement.getElementsByTagName("link").item(0).getTextContent());
//                    System.out.println("link : " + eElement.getElementsByTagName("link").item(0).getTextContent());
                    item.setDescription(eElement.getElementsByTagName("description").item(0).getTextContent());
//                    System.out.println("description : " + eElement.getElementsByTagName("description").item(0).getTextContent());
                    item.setTime(LocalDateTime.parse(eElement.getElementsByTagName("pubDate").item(0).getTextContent(), DateTimeFormatter.RFC_1123_DATE_TIME));

                    //                    item.setPubDate(eElement.getElementsByTagName("pubDate").item(0).getTextContent());
                    list.add(item);
                }
            }
        } catch (SAXException e) {
            e.printStackTrace(System.out);
        } catch (NullPointerException exc) {
            exc.printStackTrace(System.out);
        }


        return list;
    }
}
