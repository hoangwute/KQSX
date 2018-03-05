package com.wuochoang.kqsx.common.utils;

import android.text.InputFilter;
import android.text.Spanned;

/**
 * Created by TuanJio on 8/14/2017.
 */

public class InputUtils {
    public static InputFilter getDisableSpaceEmojiInput() {
        InputFilter filter = new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                for (int i = start; i < end; i++) {
                    if (Character.isWhitespace(source.charAt(i)) || Character.getType(source.charAt(i)) == Character.SURROGATE) {
                        return "";
                    }
                }
                return null;
            }

        };
        return filter;
    }
}
