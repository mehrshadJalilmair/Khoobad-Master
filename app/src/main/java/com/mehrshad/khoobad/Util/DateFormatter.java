package com.mehrshad.khoobad.Util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateFormatter {

    public static String currentDate()
    {
        Calendar cal = Calendar.getInstance();
        Date currentLocalTime = cal.getTime();
        DateFormat date = new SimpleDateFormat("yyyyMMdd" , Locale.getDefault());
        return date.format(currentLocalTime);
    }
}
