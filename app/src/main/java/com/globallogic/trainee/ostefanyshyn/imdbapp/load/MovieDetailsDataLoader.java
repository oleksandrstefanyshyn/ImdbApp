package com.globallogic.trainee.ostefanyshyn.imdbapp.load;

import android.content.ComponentCallbacks2;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.globallogic.trainee.ostefanyshyn.imdbapp.cash.LruCashBitmap;
import com.globallogic.trainee.ostefanyshyn.imdbapp.cash.LruCashMovieDetails;
import com.globallogic.trainee.ostefanyshyn.imdbapp.entity.MovieDetailsEntity;
import com.globallogic.trainee.ostefanyshyn.imdbapp.util.ConnectionUtils;
import com.globallogic.trainee.ostefanyshyn.imdbapp.util.ConstantUtil;
import com.globallogic.trainee.ostefanyshyn.imdbapp.util.OpenImdbJsonUtils;

public class MovieDetailsDataLoader implements ComponentCallbacks2{

    private final static String TAG = MovieDetailsDataLoader.class.getSimpleName();

    private LruCashMovieDetails mCashMovieDetails;
    private LruCashBitmap mCashBitmap;

    public MovieDetailsDataLoader() {
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / ConstantUtil.MB);
        final int cacheSize = maxMemory / ConstantUtil.CASH_DIVIDER;
        mCashMovieDetails = new LruCashMovieDetails(cacheSize);
        mCashBitmap = new LruCashBitmap(cacheSize);
    }

    public void display(TextView mTextViewTitle, TextView mTextViewPlot, ImageView mPosterImageView,
                        ProgressBar mProgressBar, String url, Bundle savedInstanceState) {
        MovieDetailsEntity movieDetailsEntityFromCash = mCashMovieDetails.get(url);
        Bitmap mPosterBitmapFromCash = mCashBitmap.get(url);
        if (savedInstanceState != null) {
            mTextViewTitle.setText(savedInstanceState.getString(ConstantUtil.TITLE));
            mTextViewPlot.setText(savedInstanceState.getString(ConstantUtil.PLOT));
            mPosterImageView.setImageBitmap((Bitmap) savedInstanceState.getParcelable(ConstantUtil.POSTER));
        } else if (movieDetailsEntityFromCash != null && mPosterBitmapFromCash != null) {
            mTextViewTitle.setText(movieDetailsEntityFromCash.getTitle());
            mTextViewPlot.setText(movieDetailsEntityFromCash.getPlot());
            mPosterImageView.setImageBitmap(mPosterBitmapFromCash);
        } else {
            new ImdbDataDownloadTask(mTextViewTitle, mTextViewPlot, mPosterImageView, mProgressBar).execute(url);
        }
    }

    @Override
    public void onTrimMemory(int level) {
        if (level >= TRIM_MEMORY_MODERATE) {
            mCashMovieDetails.evictAll();
            mCashBitmap.evictAll();
        }
        else if (level >= TRIM_MEMORY_BACKGROUND) {
            mCashMovieDetails.trimToSize(mCashMovieDetails.size() / 2);
            mCashBitmap.trimToSize(mCashMovieDetails.size() / 2);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
    }

    @Override
    public void onLowMemory() {
    }

    private class ImdbDataDownloadTask extends AsyncTask<String, Void, Integer> {

        private MovieDetailsEntity mMovieDetailsEntity;
        private Bitmap mPosterBitmap;

        private TextView mTextViewTitle;
        private TextView  mTextViewPlot;
        private ImageView mImageViewPoster;
        private ProgressBar mProgressBar;

        public ImdbDataDownloadTask(TextView textViewTitle, TextView textViewPlot, ImageView posterImageView, ProgressBar progressBar) {
            this.mTextViewTitle = textViewTitle;
            this.mTextViewPlot = textViewPlot;
            this.mImageViewPoster = posterImageView;
            this.mProgressBar = progressBar;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Integer doInBackground(String... strings) {
            String param = strings[0];
            String data = ConnectionUtils.getStringResultFromUrl(param);
            Log.d(TAG, param);
            mMovieDetailsEntity = OpenImdbJsonUtils.getImdbDataStringsFromJson(data);
            mPosterBitmap = ConnectionUtils.getBitmapFromUrl(mMovieDetailsEntity.getPosterString());
            mCashBitmap.put(param, mPosterBitmap);
            mCashMovieDetails.put(param, mMovieDetailsEntity);
            Log.d(TAG, mMovieDetailsEntity.getTitle());
            return ConstantUtil.ONE;
        }

        @Override
        protected void onPostExecute(Integer result) {
            mImageViewPoster.setImageBitmap(mPosterBitmap);
            mTextViewTitle.setText(mMovieDetailsEntity.getTitle());
            mTextViewPlot.setText(mMovieDetailsEntity.getPlot());
            mProgressBar.setVisibility(View.INVISIBLE);
        }
    }
}
