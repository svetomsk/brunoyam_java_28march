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
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

public class XMLParser {

    // не знаю, как достать с какого-либо сайта xml файл, поэтому взял с интернета рандомный файл и "спарсил."
    // here example of using library to parse XML:
    // https://www.mkyong.com/java/how-to-read-xml-file-in-java-dom-parser/
    public static List<NewsItem> parseXML(String source) {
        // не совсем понимаю, что здесь надо сделать
        return new ArrayList<>();
        
    }

    public static void main(String[] args) throws IOException, ParserConfigurationException {
        try {
            // объект документа
            File file = new File("Example.xml");
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = documentBuilder.parse("Example.xml");
            document.getDocumentElement().normalize();

            NodeList nList = document.getElementsByTagName("item");
            for(int i = 0; i < nList.getLength(); i++) {
                Node nNode = nList.item(i);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    System.out.println("title : " + eElement.getElementsByTagName("title").item(0).getTextContent());
                    System.out.println("link : " + eElement.getElementsByTagName("link").item(0).getTextContent());
                    System.out.println("description : " + eElement.getElementsByTagName("description").item(0).getTextContent());
                    System.out.println();
                }
            }
        } catch (SAXException e) {
            e.printStackTrace(System.out);
        }
    }
}
