package db;//package db;
import parser.NewsItem;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.sql.*;

//public class DatabaseHelper {
//    // return all rows
//    public List<NewsItem> selectAllNews() {
//        return new ArrayList<>();
//    }
//
//    // return only last row
//    public List<NewsItem> selectLastNews() {
//        return new ArrayList<>();
//    }
//
//    public boolean updateNews(List<NewsItem> items) {
//        return false;
//    }
//
//    public boolean deleteNews(List<NewsItem> items) {
//        return false;
//    }
//
//    public boolean insertNews(List<NewsItem> items) {
//        return false;
//    }


public class DatabaseHelper {
    public Connection connection;
    public Statement statement;
    public ResultSet resultset;

    public DatabaseHelper(){

    }


    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        System.out.println("new string print");

        DatabaseHelper app = new DatabaseHelper();
        app.CreateDB();
        app.WriteDB();
        List re = app.selectAllNews();
        System.out.println(re);

    }
    private  Connection connect() {
        // SQLite connection string
        String driver = "jdbc:sqlite:";
        String localDir = System.getProperty("user.dir");
        System.out.println(localDir);
        File dbPath = new File(localDir + "\\src\\db\\database.db");
        String url = driver + dbPath;

        try {
            connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public void CreateDB(){
        try {
            this.connection = this.connect();

            Statement statement = null;

            this.statement = connection.createStatement();
            DatabaseMetaData dbm = connection.getMetaData();

            resultset = dbm.getTables(null, null, "news", null);
            if (resultset.next()) {
                // Table exists
                System.out.println("Table exists");
            }
            else {
                // Table does not exist

                int result = this.statement.executeUpdate("CREATE TABLE news ("+
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        " title TEXT, " +
                        " link TEXT, " +
                        " description TEXT, " +
                        " time TEXT)");

                System.out.println("result = " + result);

            }


            PreparedStatement prep = null;
            prep = connection.prepareStatement("INSERT INTO news (title, link, description, time) VALUES (?, ?, ? ,?);");
            prep.setString(1, "title");
            prep.setString(2, "link");
            prep.setString(3, "description");

            prep.setString(4, "2000-02-28 10:01:30");

            prep.executeUpdate();
            System.out.println("result = " + prep.executeUpdate());
//            resultset = statement.executeQuery("SELECT * FROM news");
//            System.out.println(resultset);
//            while(resultset.next())
//            {
//                //int id = resultset.getInt("id");
//                String  x = Integer.toString(resultset.getInt("x"));
//                String  y = Integer.toString(resultset.getInt("y"));
//
////                String  x = resultset.getString("x");
////                String  y = resultset.getString("y");
//                System.out.println( "x = " + x );
//                System.out.println( "y = " + y );
//                System.out.println();
//            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void WriteDB(){
        System.out.println();



    }
    public List<NewsItem> selectAllNews() {
        List<NewsItem> list = new ArrayList<NewsItem>();
        try {
            //resultset = statement.executeQuery("SELECT * FROM news");
            this.resultset = this.statement.executeQuery("SELECT * FROM news");
            System.out.println(resultset);


            while(resultset.next())
            {
//                int id = resultset.getInt("id");
//                private String title;
//                private String link;
//                private String description;
//                private LocalDateTime time;


                String  title = (resultset.getString("title"));
                String  link = (resultset.getString("link"));
                String  description = (resultset.getString("description"));
                String  timedb = (resultset.getString("time"));

//                String str = "2016-03-04 11:30:00";
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

                LocalDateTime time = LocalDateTime.parse(timedb, formatter);
//                System.out.println( "x = " + x );
//                System.out.println( "y = " + y );
                System.out.println(title +  "\t" + link + "\t" + description+ "\t" + time );
//                System.out.println();
                NewsItem ni = new NewsItem(title, link, description, time);
                list.add(ni);
            }

            //System.out.println("Таблица выведена");


        } catch (SQLException e) {
            e.printStackTrace();
        }
    return list;
    }

    public void selectAll(){
        String sql = "SELECT id, name, capacity FROM warehouses";

        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getInt("id") +  "\t" +
                        rs.getString("name") + "\t" +
                        rs.getDouble("capacity"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
