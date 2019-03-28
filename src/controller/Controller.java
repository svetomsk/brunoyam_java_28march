package controller;

import db.DatabaseHelper;
import network.NetworkHelper;
import parser.NewsItem;
import parser.XMLParser;
import viewer.Viewer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Controller {


    private DatabaseHelper databaseHelper;
    private NetworkHelper networkHelper;
    private XMLParser xmlParser;
    private NewsItem newsItem;
    private Viewer viewer;
    private boolean updated;

    public Controller(){
        databaseHelper = new DatabaseHelper();
        viewer = new Viewer(this);
        networkHelper = new NetworkHelper();
        xmlParser = new XMLParser();
    }

    public void update() {

        LocalDateTime actualTime = LocalDateTime.now();
        LocalDateTime databaseTime = LocalDateTime.MIN;

        // get the base context
        databaseHelper.CreateDB();
        List<NewsItem> re = databaseHelper.selectAllNews();
        if (re.size() !=0 ) {

            databaseTime = re.get(0).getTime();

            // try to find max time in the records
            for (NewsItem item : re) {
                if (databaseTime.isBefore(item.getTime())) databaseTime = item.getTime();
            }
        }

        // get the URLs
        List<String> urlList = viewer.getUrlList();
        List<NewsItem> xmlRecords;
        updated = false;

        //get the new items from net;
        for(String listURL : urlList) {
            String urlContext = networkHelper.fetchHTML(listURL);
            xmlRecords = xmlParser.parseXML(urlContext);

            // check the time of the records
            List<NewsItem> newURLNews = new ArrayList<NewsItem>();

            for (NewsItem netItem : xmlRecords) {
                if (databaseTime.isAfter(netItem.getTime())) {
                    newURLNews.add(netItem);
                }
            }

            updated = (newURLNews.size() !=0);

            if (updated) {
                databaseHelper.insertNews(newURLNews);
            }

        }

        if (updated){

            re = databaseHelper.selectAllNews();
            viewer.updateNewsList(re);
        }

    }
}
