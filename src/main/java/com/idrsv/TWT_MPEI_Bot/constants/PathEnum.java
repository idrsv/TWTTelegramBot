package com.idrsv.TWT_MPEI_Bot.constants;

public enum PathEnum {
    PATH_1("http://twt.mpei.ru/mcs/Worksheets/rbtpp/tab1.xmcd"),
    PATH_2("http://twt.mpei.ru/mcs/Worksheets/rbtpp/tab2.xmcd"),
    PATH_3("http://twt.mpei.ru/mcs/Worksheets/rbtpp/tab3.xmcd");

    private final String path;

    PathEnum(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
