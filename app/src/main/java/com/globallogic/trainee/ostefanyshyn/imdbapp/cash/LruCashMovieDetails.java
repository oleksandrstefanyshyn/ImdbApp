package com.globallogic.trainee.ostefanyshyn.imdbapp.cash;

import android.support.annotation.NonNull;
import android.support.v4.util.LruCache;

import com.globallogic.trainee.ostefanyshyn.imdbapp.entity.MovieDetailsEntity;
import com.globallogic.trainee.ostefanyshyn.imdbapp.util.ConstantUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class LruCashMovieDetails extends LruCache<String, MovieDetailsEntity> {

    public LruCashMovieDetails(int maxSize) {
        super(maxSize);
    }

    @Override
    protected int sizeOf(@NonNull String key, @NonNull MovieDetailsEntity movieDetailsEntity) {
        return bytesCount(movieDetailsEntity) / ConstantUtil.MB;
    }

    private static int bytesCount(Object obj) {
        ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream;
        try {
            objectOutputStream = new ObjectOutputStream(byteOutputStream);
            objectOutputStream.writeObject(obj);
            objectOutputStream.flush();
            objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return byteOutputStream.toByteArray().length;
    }
}
