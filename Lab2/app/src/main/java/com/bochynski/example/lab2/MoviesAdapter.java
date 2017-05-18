package com.bochynski.example.lab2;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MovieViewHolder> implements ItemTouchHelperAdapter{

    private List<Movie> moviesList;

    public MoviesAdapter(List<Movie> moviesList) {
        this.moviesList = moviesList;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        if (viewType == 0) {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.movie_list_row, parent, false);
        } else {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.movie_list_row_inverted, parent, false);
        }
        return new MovieViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        Movie movie = moviesList.get(position);
        holder.titleTextView.setText(movie.getTitle());
        holder.genreTextView.setText(movie.getGenre());
        holder.yearTextView.setText(movie.getYear());
        if (movie.getSeenByUser()) {
            holder.seenTextView.setVisibility(View.VISIBLE);
        } else {
            holder.seenTextView.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }


    @Override
    public int getItemViewType(int position) {
        return position % 2;
    }

    @Override
    public void onItemDismiss(int position) {
        moviesList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, moviesList.size());
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        //do nothing for now
    }
}
