package edu.dipae.sportify.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils
{
    public static String dateToString(Date date)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YYYY");
        return sdf.format(date);
    }

    public static String timeToString(Date date)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        return sdf.format(date);
    }
}
