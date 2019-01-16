package com.yc.gtv.utils;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import java.security.MessageDigest;

/**
 * Created by edison on 2018/12/28.
 */

public class BitmapFixedWidthTransform extends BitmapTransformation {

    public BitmapFixedWidthTransform() {
    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        int targetWidth = outWidth;

        double aspectRatio = (double) toTransform.getHeight() / (double) toTransform.getWidth();
        int targetHeight = (int) (targetWidth * aspectRatio);
        Bitmap result = Bitmap.createScaledBitmap(toTransform, targetWidth, targetHeight, true);
        if (result != toTransform) {
            // Same bitmap is returned if sizes are the same
//            toTransform.recycle(); // removed this
        }
        return result;
    }

    @Override
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {

    }
}
