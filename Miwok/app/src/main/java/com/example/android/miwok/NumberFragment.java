package com.example.android.miwok;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

public class NumberFragment extends Fragment {

    private ArrayList<Word> numbers;
    private WordAdapter itemsAdapter;

    public NumberFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View rootView = inflater.inflate(R.layout.word_list, container, false);

        numbers = new ArrayList<>();
        setNumbersData();
        itemsAdapter = new WordAdapter(getActivity(), numbers, R.color.category_numbers);
        ListView listView = (ListView) rootView.findViewById(R.id.word_listView);
        listView.setAdapter(itemsAdapter);

        return rootView;
    }

    private void setNumbersData() {
        numbers.add(new Word("one", "lutti", R.drawable.number_one, R.raw.number_one));
        numbers.add(new Word("two", "kutti", R.drawable.number_two, R.raw.number_two));
        numbers.add(new Word("three", "otiko", R.drawable.number_three, R.raw.number_three));
        numbers.add(new Word("four", "toloksou", R.drawable.number_four, R.raw.number_four));
        numbers.add(new Word("five", "oyisa", R.drawable.number_five, R.raw.number_five));
        numbers.add(new Word("six", "massoka", R.drawable.number_six, R.raw.number_six));
        numbers.add(new Word("seven", "temokka", R.drawable.number_seven, R.raw.number_seven));
        numbers.add(new Word("eight", "kawintak", R.drawable.number_eight, R.raw.number_eight));
        numbers.add(new Word("nine", "kenekakuy", R.drawable.number_nine, R.raw.number_nine));
        numbers.add(new Word("ten", "kawinta", R.drawable.number_ten, R.raw.number_ten));
    }

    @Override
    public void onStop() {
        super.onStop();
        itemsAdapter.releaseResources();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(getActivity());
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
