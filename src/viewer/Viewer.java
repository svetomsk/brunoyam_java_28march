package viewer;

import parser.NewsItem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
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
    private static JList jListWeb;
    private static JScrollPane jScrollPaneWeb;
//  Это для работы со списком. В данном случае, со списком сайтов
    private static DefaultListModel listModelWeb;

//    Это ненужные заглушки
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
//       Загрузка сайтов с диска
        urlList = loadURL();
//        Распечатка их в окне
        writeWeb();


        jButtonAddWeb.addMouseListener(new MyLisenMouseWebAdd());
        jButtonDelWeb.addMouseListener(new MyLisenMouseWebDel());
        jFrame.add(jPanelNews);
        jFrame.add(jPanelWeb);
        jFrame.setVisible(true);

    }

    public void updateNewsList(List<NewsItem> news) {

    }

    //  Слушатель кнопки Update
    private static class MyLisenMouseUpdate extends MouseAdapter {
        //  Далее всё заглушки
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

//    Слушатель кнопки удаления сайта из списка
    private  static class MyLisenMouseWebDel extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            super.mousePressed(e);
//            Выскакивающая менюшка для подтверждения удаления сайта из списка. ( 0 = да, 1 = нет, 2 = отмена)
            int menu = JOptionPane.showConfirmDialog(null,"Вы точно хотите удалить "+urlList.get(jListWeb.getSelectedIndex()));
            if (menu == 0) {
                urlList.remove(jListWeb.getSelectedIndex());
                listModelWeb.remove(jListWeb.getSelectedIndex());
//                Сохранение изменений на диск
                saveURL(urlList);
            }
        }
    }

//  Слушатель кнопки добавления нового сайта
    private static class MyLisenMouseWebAdd extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent e) {
            super.mousePressed(e);
            String tempStringUrl = JOptionPane.showInputDialog(null, "Введите url сайта");
//            Это первая проверка на существование уже такого сайта в списке.
            if (urlList.contains(tempStringUrl)) {
                JOptionPane.showMessageDialog(null,"Такой адрес уже есть");
            } else {
                URL url = null;
                try {
                    url = new URL(tempStringUrl);
                    urlList.add(tempStringUrl);
                    saveURL(urlList);
                    writeWeb();

                } catch (MalformedURLException e1) {
//                    Вторая проверка, если адресс введен неверно
                    JOptionPane.showMessageDialog(null,"Такой веб не существует");
                }
            }
        }
    }
//    Метод вывода списка сайтов
    private static void writeWeb(){
//        Это если первый вызов метода, то есть инициализация списка
        if ( jScrollPaneWeb == null) {
//            Далее танцы с бубном, присущее JList
            listModelWeb = new DefaultListModel();
            jListWeb = new JList(listModelWeb);
            for (String str:urlList) {
                listModelWeb.addElement(str);
            }
             jScrollPaneWeb = new JScrollPane(jListWeb);
            jScrollPaneWeb.setBounds(20, 120, 150, 300);
            jPanelWeb.add(jScrollPaneWeb);
        } else {
//            Это если просто добавился новый элемент
            listModelWeb.addElement(urlList.get(urlList.size()-1));
            jListWeb.setSelectedIndex( listModelWeb.size() - 1);
            jListWeb.ensureIndexIsVisible( listModelWeb.size() - 1 );
        }
    }
//    Загрузка массива сайтов с диска
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

//        Сохранение массива сайтов на диск
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

//  Это геттер на массив сайтов
    public static List<String> getUrlList() {
        return urlList;
    }
}
