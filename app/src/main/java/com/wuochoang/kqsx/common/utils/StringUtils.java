package com.wuochoang.kqsx.common.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Patterns;
import android.widget.EditText;

import com.wuochoang.kqsx.App;
import com.wuochoang.kqsx.base.BaseActivity;

import com.wuochoang.kqsx.R;


//
// BaseAndroid - StringUtils
//
// Created by Vin on 4/17/17.
// Copyright (c) 2017 Ominext. All rights reserved.
//
public class StringUtils {
    /**
     * Checks whether the given string is null or empty.
     *
     * @param string the string to check
     * @return true if {@code string} is null or empty
     */
    public static boolean isNullOrEmpty(@Nullable String string) {
        return string == null || string.isEmpty();
    }

    /**
     * Checks whether the given char sequence is null or empty.
     *
     * @param string the char sequence to check
     * @return true if {@code string} is null or empty
     */
    public static boolean isNullOrEmpty(@Nullable CharSequence string) {
        return string == null || string.length() == 0;
    }

    /**
     * Changes the first letter of the given word to uppercase.
     *
     * @param word the word to capitalize
     * @return a new {@code string} instance with the first letter in uppercase
     */
    @NonNull
    public static String capitalizeWord(@NonNull String word) {
        if (word.isEmpty()) {
            return "";
        } else {
            return word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();
        }
    }

    public static boolean isValidEmail(String email, EditText editText) {
        return isValidEmail(email, editText, true);
    }

    public static boolean isValidEmail(String email, EditText editText, boolean isShowError) {
        BaseActivity activity = App.get().getCurrentActivity();
        if (email == null || editText == null) return false;
        int mail = email.trim().length();
        if (mail == 0) {
            if (isShowError) {
                editText.setError(editText.getContext().getString(R.string.email_empty));
                editText.requestFocus();
            } else {
//                OmiAlertDialog dialog = OmiAlertDialog.newInstanceOne(activity, "Loi email", "Khong de trong email", "Close");
//                dialog.show(activity.getSupportFragmentManager(), null);
                editText.requestFocus();
            }
            return false;
        } else if ((mail > 0 && !Patterns.EMAIL_ADDRESS.matcher(email).matches()) || mail > 255) {
            if (isShowError) {
                editText.setError(editText.getContext().getString(R.string.email_empty));
                editText.requestFocus();
            } else {
//                OmiAlertDialog dialog = OmiAlertDialog.newInstanceOne(activity, "Loi email", "Loi dinh dang email", "Close");
//                dialog.show(activity.getSupportFragmentManager(), null);
                editText.requestFocus();
            }
            return false;
        }
        return true;
    }

    public static boolean isValidPassword(String pass, EditText editText) {
        BaseActivity activity = App.get().getCurrentActivity();
        if (pass.trim().length() == 0) {
//            OmiAlertDialog dialog = OmiAlertDialog.newInstanceOne(activity, "Loi password", "Khong de trong pass", "Close");
//            dialog.show(activity.getSupportFragmentManager(), null);
            editText.requestFocus();
            return false;
        } else if (pass.trim().length() > 0 && pass.length() < 6) {
//            OmiAlertDialog dialog = OmiAlertDialog.newInstanceOne(activity, "Loi password", "It nhat co 6 ki tu", "Close");
//            dialog.show(activity.getSupportFragmentManager(), null);
            editText.requestFocus();
            return false;
        } else if (!pass.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$")) {
//            OmiAlertDialog dialog = OmiAlertDialog.newInstanceOne(activity, "Loi password", "PassWord phai bao gom chu va so", "Close");
//            dialog.show(activity.getSupportFragmentManager(), null);
            editText.requestFocus();
            return false;
        }
        return true;
    }

    /**
     * Set up other color of 1 string in long string
     *
     * @param context
     * @param color
     * @param text
     * @param params
     * @return
     */
    public static SpannableString getFormattedString(Context context, int color, String text, String... params) {
        SpannableString styledString = new SpannableString(text);
        for (String string : params) {
            styledString.setSpan(new ForegroundColorSpan(color), text.indexOf(string), text.indexOf(string) + string.length(), 0);
        }
        return styledString;
    }

    public static SpannableString getFormattedStringPositon(Context context, int color, String text, int position) {
        SpannableString styledString = new SpannableString(text);

        styledString.setSpan(new ForegroundColorSpan(color), position, position + 1, 0);

        return styledString;
    }

    /**
     * @param context
     * @param color
     * @param text
     * @param params
     * @return
     */
    public static SpannableString getFormattedStringBold(Context context, int color, String text, String... params) {
        SpannableString styledString = new SpannableString(text);
        for (String string : params) {
            styledString.setSpan(new ForegroundColorSpan(color), text.indexOf(string), text.indexOf(string) + string.length(), 0);
            styledString.setSpan(new StyleSpan(Typeface.BOLD), text.indexOf(string), text.indexOf(string) + string.length(), 0);
        }
        return styledString;
    }

    /**
     * @param context
     * @param text
     * @param params  Deafault color blue
     * @return
     */
    public static SpannableString getFormattedString(Context context, String text, String... params) {
        return getFormattedString(context, ContextCompat.getColor(context, R.color.blue), text, params);
    }

    /**
     * @param context
     * @param text
     * @param params
     * @return
     */
    public static SpannableString getFormattedStringBold(Context context, String text, String... params) {
        return getFormattedStringBold(context, ContextCompat.getColor(context, R.color.blue), text, params);
    }
}
