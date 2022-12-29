package com.idrsv.TWT_MPEI_Bot.telegram.keyboards;

import com.idrsv.TWT_MPEI_Bot.service.TWTService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.io.IOException;


@Component
@AllArgsConstructor
public class CallbackKeyboard {

    private TWTService twtService;

    public SendMessage getCallbackInAStateOfSaturationByTemperature(long chatId) throws IOException {
        int temperatureByTable1 = 270;
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(twtService.inAStateOfSaturationByTemperature(temperatureByTable1).toString());
        return message;
    }

    public SendMessage getCallbackInAStateOfSaturationByPressure(long chatId) throws IOException {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(twtService.inAStateOfSaturationByPressure().toString());
        return message;
    }
}
