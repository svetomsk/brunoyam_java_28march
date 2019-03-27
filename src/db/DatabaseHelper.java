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
    private String dformat = "yyyy-MM-dd HH:mm:ss";

    public DatabaseHelper(){

    }


    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        System.out.println("new string print");

        DatabaseHelper app = new DatabaseHelper();
        app.CreateDB();
        //app.WriteDB();
        List re = app.selectAllNews();
        re = app.selectLastNews();
        System.out.println(re);


        app.insertNews(app.createtestitems());

        //app.deleteNews(test);

    }
    private List<NewsItem> createtestitems(){
        List<NewsItem> list = new ArrayList<NewsItem>();
                String title = (("test title"));
                String link = (("test link"));
                String description = (("test description"));
                LocalDateTime time = LocalDateTime.now();
//                String timedb = ldt.format(formatter);
//                LocalDateTime time = LocalDateTime.parse(timedb, formatter);
                System.out.println(title + "\t" + link + "\t" + description + "\t" + time);
                NewsItem ni = new NewsItem(title, link, description, time);
                list.add(ni);
    return list;
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

            prep.setString(4, "2000-02-28 10:01:301");

            prep.executeUpdate();
            System.out.println("result = " + prep.executeUpdate());

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public List<NewsItem> selectAllNews() {
        return selectFromDB("SELECT * FROM news");
    }
    public List<NewsItem> selectLastNews() {
        return selectFromDB("SELECT * FROM news ORDER BY id DESC LIMIT 1");
    }

    protected List<NewsItem> selectFromDB(String sql) {
        List<NewsItem> list = new ArrayList<NewsItem>();
        try {
            this.resultset = this.statement.executeQuery(sql);
            System.out.println(resultset);

            while(resultset.next())
            {
                String  title = (resultset.getString("title"));
                String  link = (resultset.getString("link"));
                String  description = (resultset.getString("description"));
                String  timedb = (resultset.getString("time"));

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dformat);
                LocalDateTime time = LocalDateTime.parse(timedb, formatter);
                System.out.println(title +  "\t" + link + "\t" + description+ "\t" + time );
                NewsItem ni = new NewsItem(title, link, description, time);
                list.add(ni);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean deleteNews(List<NewsItem> items) {

        for (int i = 0; i < items.size(); i++) {
            LocalDateTime time = items.get(i).getTime();
            System.out.println("deleteNews time=" + time);
            String query = "delete from users where time = ?";
            try {
                this.resultset = this.statement.executeQuery(query);
                PreparedStatement preparedStmt = connection.prepareStatement(query);
                preparedStmt.setString(1, time.toString());
                preparedStmt.execute();

            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        return true;
    }

        public boolean insertNews(List<NewsItem> items) {
            for (int i = 0; i < items.size(); i++) {

                String  title = items.get(i).getTitle();
                String  link = items.get(i).getLink();
                String  description = items.get(i).getDescription();
                LocalDateTime timedb = items.get(i).getTime();

                LocalDateTime time = items.get(i).getTime();
                System.out.println("Insert news");

               // String query = "UPDATE news SET title = ?, link = ?, description = ?  WHERE id = ? AND seq_num = ?";

                try {
                    statement.executeUpdate("INSERT INTO news " + "VALUES ("
                            + title +","
                            + link +","
                            + description +","
                            + timedb.toString() + ")");


//                    this.resultset = this.statement.executeQuery(query);
//                    PreparedStatement preparedStmt = connection.prepareStatement(query);
//                    preparedStmt.setString(1, time.toString());
//                    preparedStmt.execute();

                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
            return true;
    }

    public void WriteDB(){
        System.out.println();
    }
}
