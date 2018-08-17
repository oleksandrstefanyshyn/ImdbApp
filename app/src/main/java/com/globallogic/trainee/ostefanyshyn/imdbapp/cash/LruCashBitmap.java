package com.globallogic.trainee.ostefanyshyn.imdbapp.cash;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v4.util.LruCache;

import com.globallogic.trainee.ostefanyshyn.imdbapp.util.ConstantUtil;

public class LruCashBitmap extends LruCache<String, Bitmap> {

    public LruCashBitmap(int maxSize) {
        super(maxSize);
    }

    @Override
    protected int sizeOf(@NonNull String key, @NonNull Bitmap value) {
        return value.getByteCount() / ConstantUtil.MB;
    }
}
