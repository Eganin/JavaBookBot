package Main;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

import java.util.ArrayList;
import java.util.List;

public class Bot extends TelegramLongPollingBot {
    // наследуемся от API
    /*
    Класс предназначеный для работы бота
     */
    public static void main(String[] args) {
        ApiContextInitializer.init(); // инициализируем API
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();// объект бота
        try {
            telegramBotsApi.registerBot(new Bot()); // регистрируем бота

        } catch (TelegramApiRequestException e) {
            System.out.println("Ошибка подключения");

        }
    }

    public void onUpdateReceived(Update update) {
        // Метод предназначенный за прием сообщений
        Message message = update.getMessage();

        if (message != null & message.hasText()) {
            switch (message.getText()) {
                case "/help":
                    sendMsg(message, "Для того чтобы найти книги введи название языка программирования");
                    break;

                case "/start":
                    sendMsg(message, "Я бот который найдет тебе книги по языку прогрвммирования");
                    break;

                default:
                    try {
                        // в ином случае включаем обработку парсера
                        Parser parserBook = new Parser();
                        ArrayList<Book> books = parserBook.parser(message.getText());
                        getInfoBooks(books, message);// отправляем информацию
                    } catch (Exception e) {
                        System.out.println("Произошла ошибка во время парсинга");
                    }
            }
        }
    }

    public void getInfoBooks(ArrayList<Book> books, Message message) {
        for (Book element : books) {
            ArrayList<String> title = element.getNameBook();         // испольюуем getters для получения информации
            ArrayList<ArrayList<String>> info = element.getInfoBook();// от класса Book
            for (int i = 0; i <= title.size(); i++) {
                sendMsgBook(title.get(i), info.get(i), message);// отправляем сообщение полльзователю
            }
        }
    }

    public void sendMsgBook(String title, ArrayList<String> infoOther, Message message) {
        // Метод предназначеный для отправки информации о книге
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true); // включаем разметку текста
        sendMessage.setChatId(message.getChatId().toString()); // находим chat-id и устанавливаем его
        sendMessage.setReplyToMessageId(message.getMessageId());// определяем id - message
        String text = title + "\n"+ infoOther.get(0)+"\n"+infoOther.get(1);
        sendMessage.setText(text); // текст сообщения
        try {
            sendMessage(sendMessage); // отправляем сообщение
        } catch (TelegramApiException e) {
            System.out.println("Ошибка во время отправки сообщения");
        }
    }

    public void setButton(SendMessage sendMessage) {
        // метод отвечает за создание клавиатуры под текстом
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(); // создание клавиатуры
        sendMessage.setReplyMarkup(replyKeyboardMarkup);// устанавливаем разметку
        replyKeyboardMarkup.setSelective(true); // вывод клавы всем пользователям
        replyKeyboardMarkup.setResizeKeyboard(true);// подгон размера клавиатуры
        replyKeyboardMarkup.setOneTimeKeyboard(false); // не скрываем клавиатуру

        List<KeyboardRow> keyboardRowsList = new ArrayList<KeyboardRow>(); // создаем массив кнопок-поле

        KeyboardRow keyboardFirstRow = new KeyboardRow();// создаем кнопку-поле
        keyboardFirstRow.add(new KeyboardButton("/help"));// создаем кнопку с текстом
        keyboardFirstRow.add(new KeyboardButton("/start"));// создаем кнопку с текстом

        keyboardRowsList.add(keyboardFirstRow); // добавляем в list кнопку

        replyKeyboardMarkup.setKeyboard(keyboardRowsList); // и устанавливаем список на клавиатуру

    }

    public void sendMsg(Message message, String text) {
        // метод отвечает за отправку сообщения от бота
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true); // включаем разметку текста
        sendMessage.setChatId(message.getChatId().toString()); // находим chat-id и устанавливаем его
        sendMessage.setReplyToMessageId(message.getMessageId());// определяем id - message
        sendMessage.setText(text); // текст сообщения
        try {
            setButton(sendMessage); // устанавливаем клавиатуру
            sendMessage(sendMessage); // отправляем сообщение
        } catch (TelegramApiException e) {
            System.out.println("Ошибка во время отправки сообщения");
            e.printStackTrace();
        }

    }

    public String getBotUsername() {
        // метод для возвращения имени бота
        String nameBot = new String("JavaBot");
        return nameBot;
    }

    public String getBotToken() {
        // возвращение token бота
        String token = new String("1314965122:AAFhANCEHCuOnej1MWksiSH9IOPN_J8apkU");
        return token;
    }
}
