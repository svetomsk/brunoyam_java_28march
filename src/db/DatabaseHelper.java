package db;//package db;
import parser.NewsItem;
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
        app.ReadDB();

    }
    private  Connection connect() {
        // SQLite connection string
        String driver = "jdbc:sqlite:";
        String localDir = System.getProperty("user.dir");
        System.out.println(localDir);
        File dbPath = new File(localDir + "\\db\\database.db");
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
                        " x int, " +
                        " y int)");

                System.out.println("result = " + result);

            }


            PreparedStatement prep = null;
            prep = connection.prepareStatement("INSERT INTO news (x, y) VALUES (?, ?);");
            prep.setInt(1, 10);
            prep.setInt(2, 20);
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
    public void ReadDB() {
        try {
            //resultset = statement.executeQuery("SELECT * FROM news");
            this.resultset = this.statement.executeQuery("SELECT * FROM news");
            System.out.println(resultset);
            while(resultset.next())
            {
                //int id = resultset.getInt("id");
                String  x = Integer.toString(resultset.getInt("x"));
                String  y = Integer.toString(resultset.getInt("y"));
//                System.out.println( "x = " + x );
//                System.out.println( "y = " + y );
                System.out.println(x +  "\t" + y + "\t" );
//                System.out.println();
            }

            System.out.println("Таблица выведена");


        } catch (SQLException e) {
            e.printStackTrace();
        }

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
