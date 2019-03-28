import viewer.Viewer;

import java.sql.*;

public class Main {
    public static void main(String[] args) {
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

        new Viewer();
    }
}
