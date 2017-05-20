package com.bochynski.example.lab2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Karol on 2017-05-18.
 */

public class MovieDetailsFragment extends Fragment {

    @BindView(R.id.details_movie_title)TextView titleView;
    @BindView(R.id.details_movie_image)ImageView movieImageView;
    @BindView(R.id.details_movie_description)TextView descriptionView;
    @BindView(R.id.details_movie_ratingBar)RatingBar ratingBar;
    @BindView(R.id.cast_details)Button castDetailsButton;
    @BindString(R.string.default_movie_title)String defaultTitle;
    @BindString(R.string.default_movie_production_year)String defaultProductionYear;
    @BindString(R.string.default_movie_description)String defaultDescription;

    private Movie currentMovie;
    private int movieIndex;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.movie_details, container, false);
        ButterKnife.bind(this, view);
        setViewsDetails();
        return view;
    }

    private void setViewsDetails(){
        Intent i = getActivity().getIntent();
        this.currentMovie = i.getParcelableExtra(MainActivity.PARCEL_MOVIE_KEY);
        this.movieIndex = i.getIntExtra(MainActivity.EXTRA_MOVIE_POSITION_KEY, -1);
        titleView.setText(currentMovie.getTitle());
        descriptionView.setText(currentMovie.getDescription());
        this.ratingBar.setRating(currentMovie.getRating());
        setListeners();
    }

    private void setListeners(){
        setRatingListener();
        setCastDetailsButtonListener();
    }

    private void setRatingListener(){
        this.ratingBar.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean b) {
                ratingBar.setRating(rating);
                MovieDetailsFragment.this.currentMovie.setRating(rating);
                MainActivity.modifyMovieRating(movieIndex, rating);
            }
        });
    }

    private void setCastDetailsButtonListener(){
        this.castDetailsButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MovieDetailsFragment.this.getActivity(), CastDetailsActivity.class);
                startActivity(intent);
            }
        });
    }
}
