package com.idrsv.TWT_MPEI_Bot.telegram.keyboards;

import com.idrsv.TWT_MPEI_Bot.constants.BotMessageEnum;
import com.idrsv.TWT_MPEI_Bot.constants.MenuButtonNameEnum;
import com.vdurmont.emoji.EmojiParser;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Component
public class ReplyKeyboardMaker {

    public SendMessage getMainMenuKeyboard(long chatId, String textToSend) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();

        List<KeyboardRow> keyboardRows = new ArrayList<>();

        KeyboardRow row = new KeyboardRow();
        row.add(MenuButtonNameEnum.TABLES_BUTTON.getButtonName());
        keyboardRows.add(row);

        row = new KeyboardRow();
        row.add(MenuButtonNameEnum.INFORMATION_ABOUT_TABLES.getButtonName());
        keyboardRows.add(row);

        row = new KeyboardRow();
        row.add(MenuButtonNameEnum.INFORMATION_ABOUT_PROJECT.getButtonName());
        keyboardRows.add(row);

        replyKeyboardMarkup.setKeyboard(keyboardRows);

        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        message.setReplyMarkup(replyKeyboardMarkup);

        return message;
    }

    public SendMessage getTableInlineKeyboard(long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText("Выберете таблицу из списка " + EmojiParser.parseToUnicode(" :point_down:") + "\n" + "Среднее время ожидания получения результатов ≈ 15 секунд");

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline1 = new ArrayList<>();
        List<InlineKeyboardButton> rowInline2 = new ArrayList<>();
        List<InlineKeyboardButton> rowInline3 = new ArrayList<>();

        var tableButton1 = new InlineKeyboardButton();
        tableButton1.setText(BotMessageEnum.TABLE_1.getMessage());
        tableButton1.setCallbackData(BotMessageEnum.TABLE_1_BUTTON.getMessage());

        var tableButton2 = new InlineKeyboardButton();
        tableButton2.setText(BotMessageEnum.TABLE_2.getMessage());
        tableButton2.setCallbackData(BotMessageEnum.TABLE_2_BUTTON.getMessage());


        rowInline1.add(tableButton1);
        rowInline1.add(tableButton2);


        rowsInline.add(rowInline1);
        rowsInline.add(rowInline2);
        rowsInline.add(rowInline3);

        inlineKeyboardMarkup.setKeyboard(rowsInline);

        message.setReplyMarkup(inlineKeyboardMarkup);

        return message;
    }
}
