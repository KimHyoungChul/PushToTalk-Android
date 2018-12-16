package com.unearth.pushtotalk.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Matthew Roberts on 12/16/2018.
 */

public class DateTimeUtil {
    public static String millisecondsToDatePrettyPrint(long time) {
        DateFormat FORMAT = new SimpleDateFormat("HH_mm_ss.SSS_MM_dd_yyyy");
        Calendar calendar = new GregorianCalendar();
        calendar.setTimeInMillis(time);
        FORMAT.setCalendar(calendar);
        return FORMAT.format(new Date(time));
    }
}
