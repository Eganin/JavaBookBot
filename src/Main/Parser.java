package Main;

import org.jsoup.nodes.Document;

import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;


public class Parser {
    final String firstUrl = new String("https://codernet.ru");

    private Document getPage(int pageNumber, String book) throws IOException {
        // метод который получает html-страницу
        String baseUrl = String.format("https://codernet.ru/books/%s/?page=%d", book, pageNumber);
        Document page = Jsoup.parse(new URL(baseUrl), 6000);
        return page;
    }

    private HashMap<String, ArrayList<String>> parsingPage(Document webpage) {
        // метод предназначенный для парсинга данных со страницы
        HashMap<String, ArrayList<String>> dictionaryBook = new HashMap<>();

        Elements tableBook = webpage.select("li[class=media]");

        for (Element book : tableBook) {
            String name = new String(book.select("a").text().trim());
            String href = new String(firstUrl + book.attr("a[href]").trim());
            String infoText = book.select("p").text();

            ArrayList<String> listBook = new ArrayList<String>();
            listBook.add(href);
            listBook.add(infoText);
            dictionaryBook.put(name, listBook);

        }
        return dictionaryBook;


    }

    public Book sendingParsingResult(HashMap<String, ArrayList<String>> result) {
        // преобразование результатов парсера
        ArrayList<String> nameBook = new ArrayList<String>();
        ArrayList<ArrayList<String>> infoBook = new ArrayList<ArrayList<String>>();
        for (HashMap.Entry<String, ArrayList<String>> entry : result.entrySet()) {// обход hashmap
            String name = entry.getKey();// получить ключ
            ArrayList<String> other = entry.getValue();// получить значение
            nameBook.add(name);
            infoBook.add(other);
        }
        Book book = new Book(nameBook, infoBook);

        return book;
    }

    public ArrayList<Book>  parser(String bookName) {
        // логика парсера
        // set состоящий из hashmap
        HashSet<HashMap<String, ArrayList<String>>> setBookInfo = new HashSet<HashMap<String, ArrayList<String>>>();
        ArrayList<Book> bookList = new ArrayList<Book>();
        try {
            for (int i = 1; i < 10; i++) {
                Document page = getPage(i, bookName);// получаем страницу
                HashMap<String, ArrayList<String>> result = parsingPage(page);// получаем нужные данные со страницы
                setBookInfo.add(result);

            }

            for (HashMap<String, ArrayList<String>> element : setBookInfo) {
                Book bookParsing = sendingParsingResult(element);
                bookList.add(bookParsing);
            }


        } catch (IOException e) {
            System.out.println("Error to connect to website");
            //return "Вы неправильно ввели значение попробуйте еще раз";
        }
        return bookList;
    }
}
