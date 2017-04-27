package com.bochynski.example.lab2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Karol on 2017-04-22.
 */

public class MovieDetailsActivity extends AppCompatActivity {

    @BindView(R.id.details_movie_title)TextView titleView;
    @BindView(R.id.details_movie_image)ImageView movieImageView;
    @BindView(R.id.details_movie_description)TextView descriptionView;
    @BindView(R.id.details_movie_ratingBar)RatingBar ratingBar;
    @BindView(R.id.toolbar)Toolbar toolbar;
    @BindString(R.string.default_movie_title)String defaultTitle;
    @BindString(R.string.default_movie_production_year)String defaultProductionYear;
    @BindString(R.string.default_movie_description)String defaultDescription;

    private Movie currentMovie;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_details_activity);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        setViewsDetails();
    }

    private void setViewsDetails(){
        Intent i = getIntent();
        this.currentMovie = i.getParcelableExtra(MainActivity.PARCEL_MOVIE_KEY);
        titleView.setText(currentMovie.getTitle());
        descriptionView.setText(currentMovie.getDescription());
        this.ratingBar.setRating(currentMovie.getRating());
        setRatingListener();
    }

    private void setRatingListener(){
        this.ratingBar.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean b) {
                ratingBar.setRating(rating);
                MovieDetailsActivity.this.currentMovie.setRating(rating);
            }
        });
    }
}
