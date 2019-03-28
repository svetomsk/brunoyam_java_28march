import controller.Controller;
import network.NetworkHelper;
import parser.NewsItem;
import parser.XMLParser;
import viewer.Viewer;

import javax.xml.parsers.ParserConfigurationException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException, ParserConfigurationException {
//        System.out.println("new string print");
//
//        Connection connection;
//        String driver = "jdbc:sqlite:";
//        String dbPath = "Z:\\Общая преподавателей\\Милых Светозар\\rss_reader\\src\\db\\database.db";
//        String url = driver + dbPath;
//        connection = DriverManager.getConnection(url);
//
//        Statement statement = connection.createStatement();
//        boolean result = statement.execute("CREATE TABLE news (x int, y int);");
//        System.out.println("result = " + result);
//
//        ResultSet resultSet = statement.executeQuery("select * from news");
//        resultSet.first();

//        while(resultSet.next()) {
//            System.out.println(resultSet.);
//        }

//        new Viewer();
        String data = NetworkHelper.fetchHTML("https://www.sports.ru/tribuna/blogs/utkin/rss.xml");
        List<NewsItem> items = XMLParser.parseXML(data);
        Controller cont = new Controller();


    }
}
