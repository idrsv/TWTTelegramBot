package com.idrsv.TWT_MPEI_Bot.handlers;

import com.idrsv.TWT_MPEI_Bot.cache.UserDataCache;
import com.idrsv.TWT_MPEI_Bot.constants.BotState;
import com.idrsv.TWT_MPEI_Bot.service.TWTService;
import com.idrsv.TWT_MPEI_Bot.telegram.keyboards.CallbackKeyboard;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.IOException;

@Component
public class StateOfSaturationByTemperatureHandler implements InputMessageHandler{

    private UserDataCache userDataCache;
    private TWTService twtService;

    public StateOfSaturationByTemperatureHandler(UserDataCache userDataCache) {
        this.userDataCache = userDataCache;
    }

    @Override
    public SendMessage handle(Message message) {
        if (userDataCache.getUsersCurrentBotState(message.getFrom().getId()).equals(BotState.ASK_TABLE)) {
            userDataCache.setUsersCurrentBotState(message.getFrom().getId(), BotState.ASK_PARAM);
        }
        return processUsersInput(message);
    }

    @Override
    public BotState getHandlerName() {
        return BotState.ASK_TABLE;
    }

    private SendMessage processUsersInput(Message inputMsg) {
        String usersAnswer = inputMsg.getText();
        long userId = inputMsg.getFrom().getId();

        BotState botState = userDataCache.getUsersCurrentBotState(userId);

        SendMessage replyToUser = null;

        if (botState.equals(BotState.ASK_PARAM)) {
            try {
                twtService.inAStateOfSaturationByTemperature(Integer.parseInt(usersAnswer));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            userDataCache.setUsersCurrentBotState(userId, BotState.MAIN_MENU);
        }
        return replyToUser;
    }
}
