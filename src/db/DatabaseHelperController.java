package db;

import parser.NewsItem;

import java.time.LocalDateTime;
import java.util.List;

public class DatabaseHelperController {
    public static void main(String[] args) {
        DatabaseHelper app = new DatabaseHelper();
        app.CreateDB();
        List  re= app.selectAllNews();
        System.out.println(re);
        LocalDateTime ldt = LocalDateTime.now();
        List <NewsItem> test = app.createTestItems("test title", "test link", "test description", ldt);
        app.insertNews(test);
        app.selectAllNews();
        app.deleteNews(test);
        test = app.createTestItems("updated title", "updated link", "updated description", ldt);
        app.updateNews(test);
    }
}
