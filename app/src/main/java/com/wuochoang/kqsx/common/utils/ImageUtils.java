package com.wuochoang.kqsx.common.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

/**
 * Created by TuanJio on 8/10/2017.
 */

public class ImageUtils {
    public static void loadImageRoundFrom(Context context, int resID, ImageView imageView) {
        Glide.with(context).load(resID).apply(RequestOptions.circleCropTransform()).into(imageView);
    }

    public static Bitmap getBitmapImageFrom(Resources resources, int resId) {
        Bitmap bitmapOfImg = BitmapFactory.decodeResource(resources, resId);
        return bitmapOfImg;
    }
}
