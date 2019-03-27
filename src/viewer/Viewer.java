package viewer;

import parser.NewsItem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Viewer extends JFrame {

//    СВЕТОЗАР!!!!ВОТ ВАЖНЫЙ МОМЕНТ
//    Далее идёт массив url с которых тянуть новости, я этого списка нигде не нашел
//    Так что создал сам. + его хранение на диске
    private static ArrayList<String> urlList;

    private static JFrame jFrame;
    private static JPanel jPanelNews;
    private static JPanel jPanelWeb;
    private static JButton jButtonUpdate;
    private static JButton jButtonAddWeb;
    private static JButton jButtonDelWeb;
    private static JTextPane jTextPaneNews;
    private static JTextPane jTextPaneWeb;

    private static int temp=0;
    private static String news="";

    public Viewer(){

        jFrame = new JFrame("RSS Reader");
        jFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        jFrame.setBounds(50,50,750,550);
        jFrame.setLayout(null);
        jFrame.setResizable(false);

//        Это часть первая - относящаяся к левой части. Где поле вывода новостей
        jPanelNews = new JPanel();
        jPanelNews.setLayout(null);
        jButtonUpdate = new JButton("Update");
        jPanelNews.add(jButtonUpdate);
        jButtonUpdate.setBounds(30,10,100,30);

        jTextPaneNews = new JTextPane();
        jPanelNews.add(jTextPaneNews);
        jPanelNews.setBounds(10,10,500,500);
        JScrollPane jScrollPaneNews = new JScrollPane(jTextPaneNews);
        jScrollPaneNews.setBounds(20,50,450,400);;
        jPanelNews.add(jScrollPaneNews);


        Font font = new Font("TimesRoman", Font.PLAIN, 16);
        jTextPaneNews.setFont(font);
        jTextPaneNews.setText("На кнопки можно смело понажимать, особенно много раз на кнопку Update. \n");
        jButtonUpdate.addMouseListener(new MyLisenMouseUpdate());

//        Это часть вторая - относящаяся к правой части. Где поле адресов сайтов
        jPanelWeb = new JPanel();
        jPanelWeb.setLayout(null);
        jButtonAddWeb = new JButton("Add Web");
        jButtonAddWeb.setBounds(50,30,100,30);
        jButtonDelWeb = new JButton("Del Web");
        jButtonDelWeb.setBounds(50,70,100,30);
        
        jPanelWeb.setBounds(510,10,680,500);
        jPanelWeb.add(jButtonAddWeb);
        jPanelWeb.add(jButtonDelWeb);
        
        jTextPaneWeb = new JTextPane();
        JScrollPane jScrollPaneWeb = new JScrollPane(jTextPaneWeb);
        jScrollPaneWeb.setBounds(20,120,150,300);
        jPanelWeb.add(jScrollPaneWeb);

        urlList = loadURL();
        writeWeb();

        jButtonAddWeb.addMouseListener(new MyLisenMouseWebAdd());


        jFrame.add(jPanelNews);
        jFrame.add(jPanelWeb);
        jFrame.setVisible(true);

    }
    
    private static void writeWeb(){
        String urlString="";
        for (String str:urlList ) {
            urlString = urlString + str + "\n";
        }
        jTextPaneWeb.setText(urlString);
    }
    
    
    private static class MyLisenMouseWebAdd extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent e) {
            super.mousePressed(e);
            urlList.add(JOptionPane.showInputDialog(null,"Введите url сайта"));
            saveURL(urlList);
            writeWeb();
            
        }
    }
    

    private static ArrayList<String> loadURL(){
        ArrayList<String> arrayURL = new ArrayList<>();
        File file = new File("urlWeb.ser");
        try {
            if (!file.createNewFile()) {
                FileInputStream fileInputStream = new FileInputStream(file);
                ObjectInputStream input = new ObjectInputStream(fileInputStream);
    //            arrayKey1ToKey2 = (HashMap<Long, HashMap<Long, int[]>>) input.readObject();
                try {
                    arrayURL = (ArrayList<String>) input.readObject();
                } catch (ClassNotFoundException e) {
                    System.out.println("Не удалось сериализовать адреса сайтов");
                }
                fileInputStream.close();
                input.close();
        }
        } catch (IOException e) {
            System.out.println("Что то не получилось с файлом хранящем адреса сайтов");
        }
        return arrayURL;
        }

        private static void saveURL(ArrayList arraySaveUrl){

            FileOutputStream fileOutputStream = null;
            try {
                fileOutputStream = new FileOutputStream( new File("urlWeb.ser"));
            } catch (FileNotFoundException e) {
                System.out.println("Файл с адресами сайтов не найден");
            }
            ObjectOutputStream output = null;
            try {
                output = new ObjectOutputStream(fileOutputStream);
                output.writeObject(arraySaveUrl);
                fileOutputStream.close();
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }



//  Слушатель кнопки Update
    private static class MyLisenMouseUpdate extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent e) {
            super.mousePressed(e);
            String tempStr;
            temp++;
            tempStr = "News Title "+temp + "\n" +"Link "+temp + "\n" +"discription "+"\n\n";
            news = tempStr + news;

            jTextPaneNews.setText(news);
            jTextPaneNews.setCaretPosition(0);
        }
    }


    public void updateNewsList(List<NewsItem> news) {

    }

    public static List<String> getUrlList() {
        return urlList;
    }
}
