package com.wuochoang.kqsx.utility;

import android.util.Log;

import com.olddog.common.ToastUtils;
import com.wuochoang.kqsx.common.utils.Utils;
import com.wuochoang.kqsx.manager.database.RealmHelper;
import com.wuochoang.kqsx.model.InputInfoEntry;
import com.wuochoang.kqsx.model.LotteryResult;

import org.joda.time.DateTime;
import org.joda.time.Days;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by admin on 07-Mar-18.
 */

public class NumberUtils {

    public static String convertCodeToNumber(String date, String code) {
        String formattedDate = DateUtils.formatSentDateToReceivedDate(date);
        String fullNumber = "";
        LotteryResult result = RealmHelper.findFirst(LotteryResult.class,
                query -> query.equalTo("date", formattedDate));
        if(code.startsWith("DB") || code.startsWith("G11")) {
            if(result != null) {
                fullNumber = result.getG0();
            }
        }
        if(code.startsWith("G2") || code.startsWith("G3") || code.startsWith("G4") || code.startsWith("G5") || code.startsWith("G6") || code.startsWith("G7") || code.startsWith("T1") || code.startsWith("T2")) {
            String fullNumberList = "";
            switch (code.substring(0,2)) {
                case "G2":
                    if(result != null)
                        fullNumberList = result.getG2();
                    break;
                case "G3":
                    if(result != null)
                        fullNumberList = result.getG3();
                    break;
                case "G4":
                    if(result != null)
                        fullNumberList = result.getG4();
                    break;
                case "G5":
                    if(result != null)
                        fullNumberList = result.getG5();
                    break;
                case "G6":
                    if(result != null)
                        fullNumberList = result.getG6();
                    break;
                case "G7":
                    if(result != null)
                        fullNumberList = result.getG7();
                    break;
                case "T1":
                    if(result != null)
                        fullNumber = String.valueOf(Integer.parseInt(result.getG0().substring(0, 1)) + Integer.parseInt(result.getG0().substring(1, 2)));
                    break;
                case "T2":
                    if(result != null)
                        fullNumber = String.valueOf(Integer.parseInt(result.getG0().substring(3, 4)) + Integer.parseInt(result.getG0().substring(4, 5)));
                    break;
            }

            if (code.length() > 2) {
                String[] fullNumberArray = fullNumberList.split("-");
                for (int i = 0; i < fullNumberArray.length; i++) {
                    if (Integer.parseInt(String.valueOf(code.charAt(2))) - 1 == i) {
                        fullNumber = fullNumberArray[i];
                    }
                }
            }
        }
        return fullNumber;
    }

    public static String getDigitFromFullNumber(String code, String fullNumber) {
        String digit = null;
        if(code.length() > 2) {
            char[] charArray = fullNumber.toCharArray();
            String[] stringArray = new String[charArray.length];
            for (int i = 0; i < charArray.length; i++) {
                stringArray[i] = String.valueOf(charArray[i]);
            }
            for (int i = 0; i < stringArray.length; i++) {
                if (code.startsWith("DB")) {
                    if (String.valueOf(code.charAt(2)).equals(String.valueOf(i + 1))) {
                        digit = String.valueOf(i);
                    }
                }
                else if (code.length() == 4 && String.valueOf(code.charAt(3)).equals(String.valueOf(i + 1))) {
                    digit = String.valueOf(i);
                }
            }
        }
        return digit;
    }

    public static int getMaximumRunnableTimes(String comparedDateString, String lastFetchedDateString) {
        Date comparedDate = DateUtils.convertStringToDate(comparedDateString);
        Date lastFetchedDate = DateUtils.convertStringToDate(lastFetchedDateString);
        if(comparedDate == null) return 0;
        if(lastFetchedDate == null) return 0;
        DateTime comparedDateTime = new DateTime(comparedDate.getTime());
        DateTime lastFetchedDateTime = new DateTime(lastFetchedDate.getTime());
        Days d = Days.daysBetween(comparedDateTime, lastFetchedDateTime);
        int days = d.getDays();
        long diff = lastFetchedDate.getTime() - comparedDate.getTime();
        return days;
    }

}
