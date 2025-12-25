package com.gen.service.utility;


import java.text.SimpleDateFormat;
import java.util.Date;

public class StringUti {

    /**
     * Default Date Format
     */
    static SimpleDateFormat dateTimesFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.sss");

    /**
     * Long 类型
     *
     * @param value 源值
     */
    public static Long getLong(Object value) {
        return getLong(value, 0L);
    }

    /**
     * Long 类型
     *
     * @param value        源值
     * @param defaultValue 默认值
     */
    public static Long getLong(Object value, Long defaultValue) {
        if (value != null && !value.toString().isEmpty()) {
            return Long.parseLong(value.toString());
        }
        return defaultValue;
    }

    /**
     * String
     *
     * @param value        Value
     * @param defaultValue Default Value
     * @return String
     */
    public static String getString(Object value, String defaultValue) {
        if (value != null && !value.toString().isEmpty()) {
            return value.toString().trim().replace(">", "&gt;").replace("<", "&lt;").replace("'", "&#39;");
        }
        return defaultValue;
    }

    /***
     * 获取当前系统时间字符串
     * */
    public static String getCurrDateString()
    {
        return dateTimesFormat.format(new Date());
    }

    /**
     * first word to the upper case
     *
     * @param name Table Name
     */
    public static String firstUpperCase(String name) {
        name = name.substring(0, 1).toUpperCase() + name.substring(1);
        return name;
    }
}
