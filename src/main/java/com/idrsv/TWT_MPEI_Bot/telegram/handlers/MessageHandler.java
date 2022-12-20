package com.idrsv.TWT_MPEI_Bot.telegram.handlers;


import com.idrsv.TWT_MPEI_Bot.constants.BotMessageEnum;
import com.idrsv.TWT_MPEI_Bot.constants.ButtonNameEnum;
import com.idrsv.TWT_MPEI_Bot.telegram.keyboards.ReplyKeyboardMaker;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class MessageHandler {

    ReplyKeyboardMaker replyKeyboardMaker;

    public BotApiMethod<?> answerMessage(Message message) {
        String chatId = message.getChatId().toString();

        String inputText = message.getText();

        if (inputText == null) {
            throw new IllegalArgumentException();
        } else if (inputText.equals("/start")) {
            return getStartMessage(chatId);
        } else if (inputText.equals(ButtonNameEnum.INFORMATION_ABOUT_TABLES.getButtonName())) {
            return getTasksMessage(chatId);
        } else {
            return new SendMessage(chatId, BotMessageEnum.NON_COMMAND_MESSAGE.getMessage());
        }
    }

    private SendMessage getStartMessage(String chatId) {
        SendMessage sendMessage = new SendMessage(chatId, BotMessageEnum.HELP_MESSAGE.getMessage());
        sendMessage.enableMarkdown(true);
        sendMessage.setReplyMarkup(replyKeyboardMaker.getMainMenuKeyboard());
        return sendMessage;
    }

    private SendMessage getTasksMessage(String chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText("""
                Table 1. In a state of saturation (by temperature)
                Table 2. In the saturation state (by pressure)
                Table 3. Depending on T and p
                Table 4. Depending on p and h
                Table 5. Depending on p and s
                Table 6. Depending on h and s
                Table 7. Depending on T and h
                """);
        return sendMessage;
    }

}
