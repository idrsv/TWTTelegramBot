package com.idrsv.TWT_MPEI_Bot;

import com.idrsv.TWT_MPEI_Bot.config.BotConfig;
import com.idrsv.TWT_MPEI_Bot.constants.BotMessageEnum;
import com.idrsv.TWT_MPEI_Bot.service.TWTService;
import com.idrsv.TWT_MPEI_Bot.telegram.keyboards.CallbackKeyboard;
import com.idrsv.TWT_MPEI_Bot.telegram.keyboards.ReplyKeyboardMaker;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static java.lang.Math.toIntExact;

@Slf4j
@Component
@AllArgsConstructor
public class TelegramBot extends TelegramLongPollingBot {

    final BotConfig config;
    private ReplyKeyboardMaker replyKeyboardMaker;
    private CallbackKeyboard callbackKeyboard;

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
                    break;
                case "Таблицы":
                    execute(replyKeyboardMaker.getTableInlineKeyboard(chatId));
                    break;
                case "Информация о таблицах":
                    infoTableMessage(chatId);
                    break;
                case "Информация о проекте":
                    infoProjectMessage(chatId);
                    break;
                default:
                    execute(replyKeyboardMaker.getMainMenuKeyboard(chatId, BotMessageEnum.EXCEPTION_WHAT_THE_FUCK.getMessage()));
            }
        } else if (update.hasCallbackQuery()) {
            String callbackData = update.getCallbackQuery().getData();
//            long messageId = update.getCallbackQuery().getMessage().getMessageId();
            long chatId = update.getCallbackQuery().getMessage().getChatId();
            switch (callbackData) {
                case "Table 1 BUTTON":
//                    String answer = "Вы выбрали 1 таблицу: В состоянии насыщения (по температуре)";
//                    EditMessageText new_message = new EditMessageText();
//                    new_message.setChatId(chatId);
//                    new_message.setMessageId(toIntExact(messageId));
//                    new_message.setText(answer);
                    executeMessage(callbackKeyboard.getCallbackInAStateOfSaturationByTemperature(chatId));
//                    execute(new_message);
                    break;
                case "Table 2 BUTTON":
//                    String answer2 = "Вы выбрали 2 таблицу: В состоянии насыщения (по давлению)";
//                    EditMessageText new_message2 = new EditMessageText();
//                    new_message2.setChatId(chatId);
//                    new_message2.setMessageId(toIntExact(messageId));
//                    new_message2.setText(answer2);
                    executeMessage(callbackKeyboard.getCallbackInAStateOfSaturationByPressure(chatId));
//                    execute(new_message2);
                    break;
            }
        }
    }

    private void infoTableMessage(long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(BotMessageEnum.INF_ABOUT_TABLE.getMessage());
        executeMessage(message);
    }

    private void infoProjectMessage(long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(BotMessageEnum.INF_ABOUT_PROJECT.getMessage());
        executeMessage(message);
    }

    private void startCommandReceived(long chatId, String firstName, String lastName) throws TelegramApiException {
        String answer;
        if (lastName == null) {
            lastName = " ";
            answer = "Hello, " + firstName + lastName + "!" + '\n' + "Thank you for joining our bot!";
        } else {
            answer = "Hello, " + firstName + " " + lastName + "!" + '\n' + "Thank you for joining our bot!";
        }
        log.info("Replied to user " + firstName + " " + lastName + " " + chatId);
        execute(replyKeyboardMaker.getMainMenuKeyboard(chatId, answer));
    }

    private void executeMessage(SendMessage sendMessage) {
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error("Error occurred: " + e.getMessage());
        }
    }
}