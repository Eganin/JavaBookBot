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

import java.io.IOException;
import java.util.ArrayList;

public class Bot extends TelegramLongPollingBot {
    // наследуемся от API
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
                        Parser parserBook = new Parser();
                        ArrayList<Book> books = parserBook.parser(message.getText());
                        getInfoBooks(books, message);
                    } catch (Exception e) {
                        System.out.println("Произошла ошибка во время парсинга");
                    }
            }
        }
    }

    public void getInfoBooks(ArrayList<Book> books, Message message) {
        for (Book element : books) {
            ArrayList<String> title = element.getNameBook();
            ArrayList<ArrayList<String>> info = element.getInfoBook();
            for (int i = 0; i <= title.size(); i++) {
                sendMsgBook(title.get(i), info.get(i), message);
            }
        }
    }

    public void sendMsgBook(String title, ArrayList<String> infoOther, Message message) {
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

    public void sendMsg(Message message, String text) {
        // метод отвечает за отправку сообщения от бота
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true); // включаем разметку текста
        sendMessage.setChatId(message.getChatId().toString()); // находим chat-id и устанавливаем его
        sendMessage.setReplyToMessageId(message.getMessageId());// определяем id - message
        sendMessage.setText(text); // текст сообщения
        try {
            //setButton(sendMessage); // устанавливаем клавиатуру
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
