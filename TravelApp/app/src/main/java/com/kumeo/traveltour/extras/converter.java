package com.kumeo.traveltour.extras;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class converter {
    public static String createDate(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date(timestamp);
        return sdf.format(date);
    }

    public static long milisecondDate(String myDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date date = sdf.parse(myDate);
        long millis = date.getTime();
        return millis;
    }
}


