package com.globallogic.trainee.ostefanyshyn.imdbapp.entity;

import java.io.Serializable;

public class MovieDetailsEntity implements Serializable{

    private String mTitle;
    private String mPlot;
    private String mPosterString;

    public MovieDetailsEntity() {
    }

    public MovieDetailsEntity(String title, String plot, String posterString) {
        this.mTitle = title;
        this.mPlot = plot;
        this.mPosterString = posterString;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getPlot() {
        return mPlot;
    }

    public void setPlot(String mPlot) {
        this.mPlot = mPlot;
    }

    public String getPosterString() {
        return mPosterString;
    }

    public void setPosterString(String posterString) {
        this.mPosterString = posterString;
    }

    @Override
    public String toString() {
        return "MovieDetailsEntity{" +
                "Title='" + mTitle + '\'' +
                ", Plot='" + mPlot + '\'' +
                ", PosterString='" + mPosterString + '\'' +
                '}';
    }
}
