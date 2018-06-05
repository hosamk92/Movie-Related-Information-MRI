package com.immortal.volloy;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;

import java.sql.Blob;

/**
 * Created by IMMORTAl on 7/19/2017.
 */

public class Movie implements Parcelable{
    private String title , id , backdrop_path , poster_path , overview , release_date;
    private float vote_average;
    private Bitmap back,poster;

    public Bitmap getBack() {
        return back;
    }

    public void setBack(Bitmap back) {
        this.back = back;
    }

    public Bitmap getPoster() {
        return poster;
    }

    public void setPoster(Bitmap poster) {
        this.poster = poster;
    }

    public  Movie()
    {
        id=title=backdrop_path=poster_path=overview=release_date="";
        vote_average=0;
        back=poster=null;
    }

    public  Movie(String title, String overview, byte[] poster, float vote, byte [] back, String release,String id)
    {
        this.id=id;
        this.title=title;
        this.overview=overview;
        this.poster= BitmapFactory.decodeByteArray(poster,0,poster.length);
        this.vote_average=vote;
        this.back=BitmapFactory.decodeByteArray(back,0,back.length);;
        release_date=release;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public float getVote_average() {
        return vote_average;
    }

    public void setVote_average(float vote_average) {
        this.vote_average = vote_average;
    }

    public Movie(Parcel in){
        String[] data = new String[7];

        in.readStringArray(data);
        // the order needs to be the same as in writeToParcel() method
        this.id=data[0];
        this.backdrop_path=data[1];
        this.overview=data[2];
        this.title=data[3];
        this.poster_path=data[4];
        this.vote_average=Float.parseFloat(data[5]);
        this.release_date=data[6];
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] {this.id,
                this.backdrop_path,this.overview,this.title,this.poster_path,Float.toString(this.vote_average),this.release_date});
    }
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
