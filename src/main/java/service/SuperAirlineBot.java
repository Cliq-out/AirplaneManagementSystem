package service;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


public class SuperAirlineBot extends TelegramLongPollingBot {
    @Override
    public void onUpdateReceived(Update update) {


        String  myMessage = "Thanks for contacting us on Telegram." +
                " Please call our Customer Service Team on +2928475949385 for further assistance";
        long chat_id = update.getMessage().getChatId();

        SendMessage message = new SendMessage()
                .setChatId(chat_id)
                .setText(myMessage);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {

        return "SuperAirlineTeam_bot";
    }

    @Override
    public String getBotToken() {

        return "1048068166:AAG147JOrbWtVuGORkBz-6UtTIobdlQ7-sQ";
    }





/*
    public static void main(String[] args) {
        // Initialize Api Context
        ApiContextInitializer.init();

        // Instantiate Telegram Bots API
        TelegramBotsApi botsApi = new TelegramBotsApi();

        // Register our bot
        try {
            botsApi.registerBot(new SuperAirlineBot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
*/

}
