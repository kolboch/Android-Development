package com.bochynski.example.lab2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Karol on 2017-05-18.
 */

public class ActorsFragment extends Fragment {

    @BindView(R.id.actors_list_view)
    ListView actorsList;

    private ArrayAdapter<String> actorsListAdapter;
    private static final String[] actors = {"John Doe", "Kate Skate", "Marko Polo",
            "Jessica Parket", "John Travolta", "Gary Oconor", "Indy Harpet", "James Coook", "Grimm Brother"};

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        actorsListAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, actors);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.actors_list_fragment, container, false);
        ButterKnife.bind(this, view);
        actorsList.setAdapter(actorsListAdapter);
        return view;
    }
}
