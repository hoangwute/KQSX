package com.wuochoang.kqsx.common.utils;

//
// BaseAndroid - DrawableHelper
//
// Created by Vin on 4/26/17.
// Copyright (c) 2017 Ominext. All rights reserved.
//

import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.support.v4.graphics.drawable.DrawableCompat;

public class DrawableHelper {

    public static void changeColor(Drawable drawable, int color){
        if (drawable instanceof ShapeDrawable) {
            ((ShapeDrawable) drawable).getPaint().setColor(color);
        } else if (drawable instanceof GradientDrawable) {
            ((GradientDrawable) drawable).setColor(color);
        } else if (drawable instanceof ColorDrawable) {
            ((ColorDrawable) drawable).setColor(color);
        } else {
            drawable.mutate().setColorFilter(color, PorterDuff.Mode.MULTIPLY);
        }
    }

    public static Drawable getDrawableOverlay(Drawable drawable, int color){
        Drawable wrappedDrawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTint(wrappedDrawable, color);
        return wrappedDrawable;
    }
}
