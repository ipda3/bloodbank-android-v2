package com.reda.yehia.bloodbankv2.utils;

import android.text.format.DateFormat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Yehia on 02/08/2017.
 */

public class GetTimeAgo {

    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;

//
//    public static String getTimeAgo(long time, Context context) {
//        if (time < 1000000000000L) {
//            // if timestamp given in seconds, convert to millis
//            time *= 1000;
//        }
//
//        long now = System.currentTimeMillis();
//        if (time > now || time <= 0) {
//            return null;
//        }
//
//        // TODO: localize
//        final long diff = now - time;
//        if (diff < MINUTE_MILLIS) {
//            return context.getString(R.string.just_now);
//        } else if (diff < 2 * MINUTE_MILLIS) {
//            return context.getString(R.string.a_minute_ago);
//        } else if (diff < 50 * MINUTE_MILLIS) {
//            return context.getString(R.string.from) + " " + diff / MINUTE_MILLIS + " " + context.getString(R.string.minutes_ago);
//        } else if (diff < 90 * MINUTE_MILLIS) {
//            return context.getString(R.string.an_hour_ago);
//        } else if (diff < 24 * HOUR_MILLIS) {
//            return context.getString(R.string.from) + " " + diff / HOUR_MILLIS + " " + context.getString(R.string.hours_ago);
//        } else if (diff < 48 * HOUR_MILLIS) {
//            return context.getString(R.string.yesterday);
//        } else {
//            return context.getString(R.string.from) + " " + diff / DAY_MILLIS + " " + context.getString(R.string.days_ago);
//        }
//    }
//
//    public static boolean differenceBetweenTwoDates(long time, long time2) {
//        if (time < 1000000000000L) {
//            // if timestamp given in seconds, convert to millis
//            time *= 1000;
//        }
//
//        if (time2 < 1000000000000L) {
//            // if timestamp given in seconds, convert to millis
//            time2 *= 1000;
//        }
//
//        final long diff = time - time2;
//        if (diff < MINUTE_MILLIS) {
//            return false;
//        } else if (diff < 2 * MINUTE_MILLIS) {
//            return false;
//        } else if (diff < 50 * MINUTE_MILLIS) {
//            return false;
//        } else if (diff < 90 * MINUTE_MILLIS) {
//            return false;
//        } else if (diff < 24 * HOUR_MILLIS) {
//            return true;
//        } else if (diff < 48 * HOUR_MILLIS) {
//            return true;
//        } else {
//            return true;
//        }
//    }

    public static String getDate(String time, Locale lang) {
        try {

            SimpleDateFormat inFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", lang);
            Date parse2 = inFormat.parse(time);

            String dayOfTheWeek = (String) DateFormat.format("EEE", parse2); // Thursday
            String day = (String) DateFormat.format("dd", parse2); // 20
            String monthString = (String) DateFormat.format("MMM", parse2); // Jun
            String monthNumber = (String) DateFormat.format("MM", parse2); // 06
            String year = (String) DateFormat.format("yyyy", parse2); // 2013
            String hour = (String) DateFormat.format("HH", parse2); // 2013
            String MINUTE = (String) DateFormat.format("mm", parse2); // 2013

            return dayOfTheWeek + " , " + day + "/" + monthNumber + "/" + year;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
