package com.globallogic.trainee.ostefanyshyn.imdbapp;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.globallogic.trainee.ostefanyshyn.imdbapp.fragment.MovieDetailsFragment;

public class MainActivity extends AppCompatActivity{

    private static final String URL = "http://www.omdbapi.com/?i=tt3896198&apikey=9a3bfbe4";
    private static final String TAG = "tag";
    private static final String FRAGMENT_NAME = "myFragmentName";

    private MovieDetailsFragment mMovieDetailsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            mMovieDetailsFragment = (MovieDetailsFragment) getSupportFragmentManager().getFragment(savedInstanceState, FRAGMENT_NAME);
        } else {
            mMovieDetailsFragment = new MovieDetailsFragment();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_activity_layout, mMovieDetailsFragment).commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getSupportFragmentManager().putFragment(outState, FRAGMENT_NAME, mMovieDetailsFragment);
    }
}
