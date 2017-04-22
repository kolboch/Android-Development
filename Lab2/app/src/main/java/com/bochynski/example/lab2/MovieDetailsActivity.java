package com.bochynski.example.lab2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
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
    @BindView(R.id.toolbar)Toolbar toolbar;
    @BindString(R.string.default_movie_title)String defaultTitle;
    @BindString(R.string.default_movie_production_year)String defaultProductionYear;
    @BindString(R.string.default_movie_description)String defaultDescription;

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
        Bundle movieDetails = i.getBundleExtra(MainActivity.MOVIE_DETAILS_BUNDLE_KEY);
        titleView.setText(movieDetails.getString(MainActivity.MOVIE_TITLE_KEY, defaultTitle));
        descriptionView.setText(movieDetails.getString(MainActivity.MOVIE_DESCRIPTION_KEY, defaultDescription));
    }
}
