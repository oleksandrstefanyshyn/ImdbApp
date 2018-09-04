package com.globallogic.trainee.ostefanyshyn.imdbapp.fragment;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.globallogic.trainee.ostefanyshyn.imdbapp.R;
import com.globallogic.trainee.ostefanyshyn.imdbapp.load.MovieDetailsDataLoader;
import com.globallogic.trainee.ostefanyshyn.imdbapp.util.ConstantUtil;

public class MovieDetailsFragment extends Fragment{

    public static String URL = "http://www.omdbapi.com/?i=tt3896198&apikey=9a3bfbe4";

    private TextView mTitleTextView;
    private TextView mPlotTextView;
    private ImageView mPosterImageView;
    private ProgressBar mProgressBar;
    private View mView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.movie_details_fragment, container, false);
        initViews(mView);
        MovieDetailsDataLoader movieDetailsDataLoader = new MovieDetailsDataLoader();
        movieDetailsDataLoader.display(mTitleTextView, mPlotTextView,
                mPosterImageView, mProgressBar,URL, savedInstanceState);
        return mView;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(ConstantUtil.POSTER, ((BitmapDrawable) mPosterImageView.getDrawable()).getBitmap());
        outState.putString(ConstantUtil.TITLE, mTitleTextView.getText().toString());
        outState.putString(ConstantUtil.PLOT, mPlotTextView.getText().toString());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            mTitleTextView.setText(savedInstanceState.getString(ConstantUtil.TITLE));
            mPlotTextView.setText(savedInstanceState.getString(ConstantUtil.PLOT));
            mPosterImageView.setImageBitmap((Bitmap) savedInstanceState.getParcelable(ConstantUtil.POSTER));
        }
    }

    private void initViews(View view) {
        mTitleTextView =  view.findViewById(R.id.tv_title);
        mPlotTextView = view.findViewById(R.id.tv_plot);
        mPosterImageView = view.findViewById(R.id.iv_poster);
        mProgressBar = view.findViewById(R.id.pb_movie_details);
    }
}
