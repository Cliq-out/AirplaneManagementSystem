package utils;

import java.util.Calendar;
import java.util.Date;

//Brenda

public class UtilApp {
    public static Date sumDaysDate(Date datum, int days){
        if (days==0) return datum;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(datum);
        calendar.add(Calendar.DAY_OF_YEAR, days);
        return calendar.getTime();
    }
    public static Date sumMonthsDate(Date datum, int months){
        if (months==0) return datum;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(datum);
        calendar.add(Calendar.MONTH, months);
        return calendar.getTime();
    }
}
