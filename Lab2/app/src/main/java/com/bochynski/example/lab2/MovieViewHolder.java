package com.bochynski.example.lab2;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Karol on 2017-04-14.
 */

public class MovieViewHolder extends RecyclerView.ViewHolder {

    public TextView yearTextView;
    public TextView titleTextView;
    public TextView genreTextView;

    public MovieViewHolder(View view){
        super(view);
        titleTextView = (TextView) view.findViewById(R.id.title);
        genreTextView = (TextView) view.findViewById(R.id.genre);
        yearTextView = (TextView) view.findViewById(R.id.year);
    }
}
