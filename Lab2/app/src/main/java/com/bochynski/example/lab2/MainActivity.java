package com.bochynski.example.lab2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindString;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindString(R.string.seen_symbol)String seenSign;

    private List<Movie> movieList = new ArrayList<>();
    private RecyclerView recyclerView;
    private MoviesAdapter mAdapter;
    private ItemTouchHelper mTouchHelper;
    private float x1,x2;
    static final int MIN_DISTANCE = 150;

    public static final String MOVIE_DETAILS_BUNDLE_KEY = "movie_details_key";
    public static final String MOVIE_TITLE_KEY = "movie_title_key";
    public static final String MOVIE_GENRE_KEY = "movie_genre_key";
    public static final String MOVIE_YEAR_KEY = "movie_year_key";
    public static final String MOVIE_DESCRIPTION_KEY = "movie_description_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setRecyclerView();
        setItemTouchHelperForRecycler();
        prepareMovieData();
    }

    private void setItemTouchHelperForRecycler(){
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(mAdapter);
        mTouchHelper = new ItemTouchHelper(callback);
        mTouchHelper.attachToRecyclerView(this.recyclerView);
    }

    private Bundle createBundleWithMovieData(Movie movie){
        Bundle movieDetails = new Bundle();
        movieDetails.putString(MOVIE_TITLE_KEY, movie.getTitle());
        movieDetails.putString(MOVIE_YEAR_KEY, movie.getYear());
        movieDetails.putString(MOVIE_GENRE_KEY, movie.getGenre());
        movieDetails.putString(MOVIE_DESCRIPTION_KEY, movie.getDescription());
        return movieDetails;
    }

    private void setRecyclerView(){
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mAdapter = new MoviesAdapter(movieList);

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        setRecyclerViewListeners();
    }

    private void setRecyclerViewListeners(){
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent i = new Intent(MainActivity.this, MovieDetailsActivity.class);
                Movie movie = movieList.get(position);
                Bundle movieDetails = createBundleWithMovieData(movie);
                i.putExtra(MOVIE_DETAILS_BUNDLE_KEY, movieDetails);
                startActivity(i);
            }

            @Override
            public void onLongClick(View view, int position) {
                Movie movie = movieList.get(position);
                movie.setSeenByUser(!movie.getSeenByUser());
                mAdapter.notifyDataSetChanged();
            }
        }));
    }

    private void prepareMovieData() {
        Movie movie = new Movie("Mad Max: Fury Road", "Action & Adventure", "2015", true);
        movieList.add(movie);

        movie = new Movie("Inside Out", "Animation, Kids & Family", "2015", true);
        movieList.add(movie);

        movie = new Movie("Star Wars: Episode VII - The Force Awakens", "Action", "2015", true);
        movieList.add(movie);

        movie = new Movie("Shaun the Sheep", "Animation", "2015");
        movieList.add(movie);

        movie = new Movie("The Martian", "Science Fiction & Fantasy", "2015");
        movieList.add(movie);

        movie = new Movie("Mission: Impossible Rogue Nation", "Action", "2015");
        movieList.add(movie);

        movie = new Movie("Up", "Animation", "2009");
        movieList.add(movie);

        movie = new Movie("Star Trek", "Science Fiction", "2009");
        movieList.add(movie);

        movie = new Movie("The LEGO Movie", "Animation", "2014");
        movieList.add(movie);

        movie = new Movie("Iron Man", "Action & Adventure", "2008");
        movieList.add(movie);

        movie = new Movie("Aliens", "Science Fiction", "1986");
        movieList.add(movie);

        movie = new Movie("Chicken Run", "Animation", "2000");
        movieList.add(movie);

        movie = new Movie("Back to the Future", "Science Fiction", "1985");
        movieList.add(movie);

        movie = new Movie("Raiders of the Lost Ark", "Action & Adventure", "1981");
        movieList.add(movie);

        movie = new Movie("Goldfinger", "Action & Adventure", "1965");
        movieList.add(movie);

        movie = new Movie("Guardians of the Galaxy", "Science Fiction & Fantasy", "2014");
        movieList.add(movie);

        mAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        switch(event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                x2 = event.getX();
                float deltaX = x2 - x1;
                if (Math.abs(deltaX) > MIN_DISTANCE)
                {
                    Toast.makeText(this, "left2right swipe", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(this, "something else", Toast.LENGTH_SHORT).show();
                }
                break;
        }
        return super.onTouchEvent(event);
    }
}
