package com.vtgo.vn.admin.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateTimeUtils {
    private static DateTimeUtils instance = new DateTimeUtils();

    public static synchronized DateTimeUtils getInstance(){
        if(instance == null){
            instance = new DateTimeUtils();
        }
        return instance;
    }

    public long getDateFromStringPattern(String date,String pattern) throws ParseException {
        DateFormat df = new SimpleDateFormat(pattern);
        return df.parse(date).getTime();
    }
    public boolean isSameDay(long time1,long time2){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time1);
        long day1 = calendar.get(Calendar.DAY_OF_YEAR);
        long year1 = calendar.get(Calendar.YEAR);
        calendar.setTimeInMillis(time2);
        long day2 = calendar.get(Calendar.DAY_OF_YEAR);
        long year2 = calendar.get(Calendar.YEAR);
        return (day1 == day2) && (year1 == year2);
    }

    public boolean isSameMonth(long time1,long time2){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time1);
        long month1 = calendar.get(Calendar.MONTH);
        long year1 = calendar.get(Calendar.YEAR);
        calendar.setTimeInMillis(time2);
        long month2 = calendar.get(Calendar.MONTH);
        long year2 = calendar.get(Calendar.YEAR);
        return (month1 == month2) && (year1 == year2);
    }

    public String getStringMonthYear(long time1){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time1);
        long month = calendar.get(Calendar.MONTH);
        long year1 = calendar.get(Calendar.YEAR);
        return year1+"_"+month;

    }
}
