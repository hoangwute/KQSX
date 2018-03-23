package com.wuochoang.kqsx.utility;

import android.util.Log;

import com.wuochoang.kqsx.common.Constant;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by admin on 07-Mar-18.
 */

public class DateUtils {

    public static String formatReceivedDateToSentDate(String receivedDate) {
        DateFormat parser = new SimpleDateFormat(Constant.RECEIVED_DATE_FORMAT);
        Date date = null;
        try {
            date = parser.parse(receivedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DateFormat formatter = new SimpleDateFormat(Constant.SENT_DATE_FORMAT);
        if (date == null) return "";
        return formatter.format(date);
    }

    public static String formatSentDateToReceivedDate(String sentDate) {
        DateFormat parser = new SimpleDateFormat(Constant.SENT_DATE_FORMAT);
        Date date = null;
        try {
            date = parser.parse(sentDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DateFormat formatter = new SimpleDateFormat(Constant.RECEIVED_DATE_FORMAT);
        if (date == null) return "";
        return formatter.format(date);
    }

    public static String getNextDate(String previousDateString, int nextDayNumber) {
        DateFormat parser = new SimpleDateFormat(Constant.SENT_DATE_FORMAT);
        Date previousDate = null;
        try {
            previousDate = parser.parse(previousDateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(previousDate);
        calendar.add(Calendar.DAY_OF_YEAR, nextDayNumber);
        SimpleDateFormat sdf = new SimpleDateFormat(Constant.SENT_DATE_FORMAT);
        return sdf.format(calendar.getTime());
    }

    public static boolean isExceedThreshold(String lastSavedDateString, String endRunningDateString) {
        Log.d("date string last", lastSavedDateString);
        Log.d("date string end", endRunningDateString);
        boolean exceededThreshold = false;
        DateFormat parser = new SimpleDateFormat(Constant.SENT_DATE_FORMAT);
        Date lastSaveDate = null;
        Date endRunningDate = null;
        try {
            lastSaveDate = parser.parse(lastSavedDateString);
            endRunningDate = parser.parse(endRunningDateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(endRunningDate.after(lastSaveDate))
            exceededThreshold = true;
        return exceededThreshold;
    }

    public static boolean isEarlierDate(String firstSavedDateString, String chosenDateString) {
        boolean earlier = false;
        DateFormat parser = new SimpleDateFormat(Constant.SENT_DATE_FORMAT);
        Date firstSavedDate = null;
        Date chosenDate = null;
        try {
            firstSavedDate = parser.parse(firstSavedDateString);
            chosenDate = parser.parse(chosenDateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(chosenDate.before(firstSavedDate))
            earlier = true;
        return earlier;
    }

    public static Date convertStringToDate(String dateString) {
        DateFormat parser = new SimpleDateFormat(Constant.SENT_DATE_FORMAT);
        Date date = null;
        try {
            date = parser.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
