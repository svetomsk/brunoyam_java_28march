package db;

import parser.NewsItem;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper {

    // init connection here
    public DatabaseHelper() {

    }

    // return all rows
    public List<NewsItem> selectAllNews() {
        return new ArrayList<>();
    }

    // return only last row
    public List<NewsItem> selectLastNews() {
        return new ArrayList<>();
    }

    public boolean updateNews(List<NewsItem> items) {
        return false;
    }

    public boolean deleteNews(List<NewsItem> items) {
        return false;
    }

    public boolean insertNews(List<NewsItem> items) {
        return false;
    }
}
