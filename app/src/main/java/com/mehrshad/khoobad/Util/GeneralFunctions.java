package com.mehrshad.khoobad.Util;

public class GeneralFunctions {

    public static String persian_to_english(String text)
    {

        return text.replace("۰" , "0")
                .replace("۱" , "1")
                .replace("۲" , "2")
                .replace("۳" , "3")
                .replace("۴" , "4")
                .replace("۵" , "5")
                .replace("۶" , "6")
                .replace("۷" , "7")
                .replace("۸" , "8")
                .replace("۹" , "9");
    }

    public static String english_to_persian(String text)
    {
        return text.replace("0" , "۰")
                .replace("1" , "۱")
                .replace("2" , "۲")
                .replace("3" , "۳")
                .replace("4" , "۴")
                .replace("5" , "۵")
                .replace("6" , "۶")
                .replace("7" , "۷")
                .replace("8" , "۸")
                .replace("9" , "۹");
    }
}
