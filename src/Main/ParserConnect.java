package Main;

import java.util.ArrayList;

public class ParserConnect {
    public String errorMessage;
    public ArrayList<Book> arrayList;

    public ParserConnect(ArrayList<Book> arrayList) {
        this.arrayList = arrayList;
    }

    public ParserConnect(ArrayList<Book> arrayList, String errorMessage) {
        this.arrayList = arrayList;
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public ArrayList<Book> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<Book> arrayList) {
        this.arrayList = arrayList;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
