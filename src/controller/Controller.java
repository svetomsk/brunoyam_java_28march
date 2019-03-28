package controller;

import db.DatabaseHelper;
import network.NetworkHelper;
import parser.NewsItem;
import parser.XMLParser;
import viewer.Viewer;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
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
        List<NewsItem> xmlRecords = null;
        updated = false;

        //get the new items from net;
        for(String listURL : urlList) {
            String urlContext = null;
            try {
                urlContext = networkHelper.fetchHTML(listURL);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                xmlRecords = xmlParser.parseXML(urlContext);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            }

            // check the time of the records
            List<NewsItem> newURLNews = new ArrayList<NewsItem>();

            for (NewsItem netItem : xmlRecords) {
                if (databaseTime.isBefore(netItem.getTime())) {
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
