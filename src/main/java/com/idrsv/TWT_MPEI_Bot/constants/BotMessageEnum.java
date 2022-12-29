package com.idrsv.TWT_MPEI_Bot.constants;

/**
 * Текстовые сообщения, посылаемые ботом
 **/
public enum BotMessageEnum {
    HELP_MESSAGE("Список таблиц"),
    INF_ABOUT_TABLE("""
            Таблица 1. В состоянии насыщения (по температуре)
            Таблица 2. В состоянии насыщения (по давлению)
            Таблица 3. В зависимости от T и p (скоро)
            Таблица 4. В зависимости от p и h (скоро)
            Таблица 5. В зависимости от p и s (скоро)
            Таблица 6. В зависимости от h и s (скоро)
            Таблица 7. В зависимости от T и h (скоро)
            """),
    INF_ABOUT_PROJECT("""
            Проект создан исключительно в личных целях и интересах.
            По всем интересующим вас вопросам пишите t.me/@idrsv
            """),
    CHOOSE_TABLES("Выберете таблицу для вычисления параметров"),
    NON_COMMAND_MESSAGE("Пожалуйста, воспользуйтесь клавиатурой"),
    EXCEPTION_WHAT_THE_FUCK("Что-то пошло не так"),
    TABLE_1_INFO("В состоянии насыщения (по температуре)"),
    TABLE_2_INFO("В состоянии насыщения (по давлению)"),
    TABLE_3_INFO("В зависимости от T и P"),
    TABLE_1_BUTTON("Table 1 BUTTON"),
    TABLE_2_BUTTON("Table 2 BUTTON"),
    TABLE_1("Таблица 1"),
    TABLE_2("Таблица 2");
    private final String message;

    BotMessageEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
