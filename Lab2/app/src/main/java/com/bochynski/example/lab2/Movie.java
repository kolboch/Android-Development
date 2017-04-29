package com.bochynski.example.lab2;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {
    private String title, genre, year;
    private boolean seenByUser;
    private float rating;
    private static final float MAX_RATING_SCORE = 5;
    private static final float MIN_RATING_SCORE = 0;

    public Movie() {
        this("", "", "", false);
    }

    public Movie(String title, String genre, String year) {
        this(title, genre, year, false);
    }

    public Movie(String title, String genre, String year, boolean seen) {
        this(title, genre, year, seen, MAX_RATING_SCORE);
    }

    public Movie(String title, String genre, String year, boolean seen, float rating){
        this.title = title;
        this.genre = genre;
        this.year = year;
        this.seenByUser = seen;
        setRating(rating);
    }

    public float getRating() {
        return this.rating;
    }

    public void setRating(float rating) {
        if (rating > this.MAX_RATING_SCORE) {
            this.rating = MAX_RATING_SCORE;
        } else if (rating < this.MIN_RATING_SCORE) {
            this.rating = MIN_RATING_SCORE;
        } else {
            this.rating = rating;
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDescription() {
        String description = "Beautiful movie " + title +
                "\ncreated by unknown director.\n" +
                "One of a kind genre: " + genre +
                "\n Definitely a must watch of " + year;
        return description;
    }

    public void setSeenByUser(boolean wasSeen) {
        this.seenByUser = wasSeen;
    }

    public boolean getSeenByUser() {
        return this.seenByUser;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(genre);
        parcel.writeString(year);
        parcel.writeBooleanArray(new boolean[]{seenByUser});
        parcel.writeFloat(rating);
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>(){

        @Override
        public Movie createFromParcel(Parcel parcel) {
            return new Movie(parcel);
        }

        @Override
        public Movie[] newArray(int i) {
            return new Movie[i];
        }
    };

    private Movie(Parcel parcel){
        this.title = parcel.readString();
        this.genre = parcel.readString();
        this.year = parcel.readString();
        boolean[]results = new boolean[1];
        parcel.readBooleanArray(results);
        this.seenByUser = results[0];
        this.rating = parcel.readFloat();
    }
}
