package com.idrsv.TWT_MPEI_Bot.constants;
/**
 * Текстовые сообщения, посылаемые ботом
 **/
public enum BotMessageEnum {
    HELP_MESSAGE("Какой-то текст"),
    CHOOSE_TABLES("Выберете таблицу для вычисления параметров"),
    NON_COMMAND_MESSAGE("Пожалуйста, воспользуйтесь клавиатурой"),
    EXCEPTION_WHAT_THE_FUCK("Что-то пошло не так"),
    TABLE_1("В состоянии насыщения (по температуре)"),
    TABLE_2("В состоянии насыщения (по давлению)"),
    TABLE_3("В зависимости от T и P");
    private final String message;

    BotMessageEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
