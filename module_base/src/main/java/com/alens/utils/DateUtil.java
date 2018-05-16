package com.alens.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateUtil {
    private SimpleDateFormat mFormat;
    {
        mFormat = new SimpleDateFormat();
    }

    public static int getCurrentHour() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    public static int getCurrentMinute() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.MINUTE);
    }
}
