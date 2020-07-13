package Main;

import java.util.ArrayList;

public class Book {
    /*
    Класс предназначенный для хранения результата парсинга
     */
    public ArrayList<String> nameBook;// имя книги
    public ArrayList<ArrayList<String>> infoBook; // остальная информация о книге

    public Book(ArrayList<String> nameBook, ArrayList<ArrayList<String>> infoBook) {
        // конcтруктор
        this.nameBook = nameBook;
        this.infoBook = infoBook;
    }

    public ArrayList<String> getNameBook() {
        return nameBook;
    }

    public ArrayList<ArrayList<String>> getInfoBook() {
        return infoBook;
    }

    public void setInfoBook(ArrayList<ArrayList<String>> infoBook) {
        this.infoBook = infoBook;
    }

    public void setNameBook(ArrayList<String> nameBook) {
        this.nameBook = nameBook;
    }
}
