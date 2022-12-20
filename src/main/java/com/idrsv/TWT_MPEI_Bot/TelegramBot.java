package com.idrsv.TWT_MPEI_Bot;

import com.idrsv.TWT_MPEI_Bot.config.BotConfig;
import com.idrsv.TWT_MPEI_Bot.service.TWTService;
import com.vdurmont.emoji.EmojiParser;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@AllArgsConstructor
public class TelegramBot extends TelegramLongPollingBot {

    private static final String TABLE_1_BUTTON = "Table 1 BUTTON";
    private static final String TABLE_2_BUTTON = "Table 2 BUTTON";
    //    private static final String TABLE_3_BUTTON = "Table 3 BUTTON";
//    private static final String TABLE_4_BUTTON = "Table 4 BUTTON";
//    private static final String TABLE_5_BUTTON = "Table 5 BUTTON";
//    private static final String TABLE_6_BUTTON = "Table 6 BUTTON";
//    private static final String TABLE_7_BUTTON = "Table 7 BUTTON";
    @Autowired
    final BotConfig config;
    @Autowired
    private TWTService twtService;


    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            switch (messageText) {
                case "/start":
                    startCommandReceived(chatId, update.getMessage().getChat().getFirstName(), update.getMessage().getChat().getLastName());
                    prepareAndSendMessage(chatId, "Select a command in the menu bar " + EmojiParser.parseToUnicode(" :point_down:"));
                    break;
                case "/tables":
                    sendMessage(chatId, "Test");
                    break;
                case "/info":
                    infoSendMessage(chatId);
                    break;
                default:
                    prepareAndSendMessage(chatId, "Sorry");
            }
            // Обработка выпадающих кнопок
        } else if (update.hasCallbackQuery()) {
            String callbackData = update.getCallbackQuery().getData();
//            long messageId = update.getCallbackQuery().getMessage().getMessageId();
            long chatId = update.getCallbackQuery().getMessage().getChatId();
            switch (callbackData) {
                case TABLE_1_BUTTON:
                    int temperatureByTable1 = 270;
                    SendMessage message1 = new SendMessage();
                    message1.setChatId(String.valueOf(chatId));
                    message1.setText(twtService.inAStateOfSaturationByTemperature(temperatureByTable1).toString());
                    System.out.println("Finished");
                    executeMessage(message1);
                    break;
                case TABLE_2_BUTTON:
                    SendMessage message2 = new SendMessage();
                    message2.setChatId(String.valueOf(chatId));
                    message2.setText(twtService.inAStateOfSaturationByPressure().toString());
                    System.out.println("Finished");
                    executeMessage(message2);
                    break;
                default:
                    sendMessage(chatId, "Sorry");
            }
        }
    }


    private void executeMessage(SendMessage sendMessage) {
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error("Error occurred: " + e.getMessage());
        }
    }


    private void infoSendMessage(long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText("""
                Таблица 1. В состоянии насыщения (по температуре)
                Таблица 2. В состоянии насыщения (по давлению)
                Таблица 3. В зависимости от T и p (скоро)
                Таблица 4. В зависимости от p и h (скоро)
                Таблица 5. В зависимости от p и s (скоро)
                Таблица 6. В зависимости от h и s (скоро)
                Таблица 7. В зависимости от T и h (скоро)
                """);
        executeMessage(message);
    }

    private void startCommandReceived(long chatId, String firstName, String lastName) {
        String answer;
        if (lastName == null) {
            lastName = " ";
            answer = "Hello, " + firstName + lastName + "!" + '\n' + "Thank you for joining our bot!";
        } else {
            answer = "Hello, " + firstName + " " + lastName + "!" + '\n' + "Thank you for joining our bot!";
        }
        log.info("Replied to user " + firstName + " " + lastName + " " + chatId);
        sendMessage(chatId, answer);
    }

    private void sendMessage(long chatId, String textToSend) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText("Выберете таблицу из списка " + EmojiParser.parseToUnicode(" :point_down:") + "\n" + "Среднее время ожидания получения результатов ≈ 15 секунд");

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline1 = new ArrayList<>();
        List<InlineKeyboardButton> rowInline2 = new ArrayList<>();
        List<InlineKeyboardButton> rowInline3 = new ArrayList<>();

        var tableButton1 = new InlineKeyboardButton();
        tableButton1.setText("Таблица 1");
        tableButton1.setCallbackData(TABLE_1_BUTTON);

        var tableButton2 = new InlineKeyboardButton();
        tableButton2.setText("Таблица 2");
        tableButton2.setCallbackData(TABLE_2_BUTTON);


        rowInline1.add(tableButton1);
        rowInline1.add(tableButton2);


        rowsInline.add(rowInline1);
        rowsInline.add(rowInline2);
        rowsInline.add(rowInline3);

        inlineKeyboardMarkup.setKeyboard(rowsInline);

        sendMessage.setReplyMarkup(inlineKeyboardMarkup);

        executeMessage(sendMessage);
    }

    private void prepareAndSendMessage(long chatId, String textToSend) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);
        executeMessage(message);
    }
}