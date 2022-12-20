package com.idrsv.TWT_MPEI_Bot.constants;
/**
 * Названия кнопок основной клавиатуры
 **/
public enum ButtonNameEnum {
    TABLES_BUTTON("Таблицы"),
    INFORMATION_ABOUT_TABLES("Информация о таблицах"),
    INFORMATION_ABOUT_PROJECT("Информация о проекте");

    private final String buttonName;

    ButtonNameEnum(String buttonName) {
        this.buttonName = buttonName;
    }

    public String getButtonName() {
        return buttonName;
    }
}
