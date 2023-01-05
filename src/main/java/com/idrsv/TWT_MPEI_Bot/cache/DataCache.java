package com.idrsv.TWT_MPEI_Bot.cache;

import com.idrsv.TWT_MPEI_Bot.constants.BotState;

public interface DataCache {

    void setUsersCurrentBotState(long userId, BotState botState);

    BotState getUsersCurrentBotState(long userId);
}
