package com.manqian.mcollect;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    public static Date geStartCurrentDay(){
        Calendar calendar =Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        return calendar.getTime();
    }

    public static Date geEndCurrentDay(){
        Calendar calendar =Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MINUTE, 59);
        return calendar.getTime();
    }


    public static Date yesterdayEndTime(){
        Calendar calendar =Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MINUTE, 59);
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        return calendar.getTime();
    }


    public static Date yesterdayStartTime(){
        Calendar calendar =Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        return calendar.getTime();
    }





    public static String formatDate(Date date){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);

    }

    public static String getCurrentDateStr1(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(new Date());

    }

    public static Date getDateByTimestamp(long timestamp){
        Date date = new Date();
        date.setTime(timestamp);
        return date;
    }
}
