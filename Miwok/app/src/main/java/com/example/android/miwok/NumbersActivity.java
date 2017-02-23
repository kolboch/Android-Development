package com.example.android.miwok;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

public class NumbersActivity extends AppCompatActivity {

    private ArrayList<Word> numbers;
    private WordAdapter itemsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);
        numbers = new ArrayList<>();
        setNumbersData();
        itemsAdapter = new WordAdapter(this, numbers, R.color.category_numbers);
        ListView listView = (ListView) findViewById(R.id.word_listView);
        listView.setAdapter(itemsAdapter);
    }

    private void setNumbersData() {
        numbers.add(new Word("one","lutti", R.drawable.number_one, R.raw.number_one));
        numbers.add(new Word("two","kutti", R.drawable.number_two, R.raw.number_two));
        numbers.add(new Word("three","otiko", R.drawable.number_three, R.raw.number_three));
        numbers.add(new Word("four","toloksou", R.drawable.number_four, R.raw.number_four));
        numbers.add(new Word("five","oyisa", R.drawable.number_five, R.raw.number_five));
        numbers.add(new Word("six","massoka", R.drawable.number_six, R.raw.number_six));
        numbers.add(new Word("seven","temokka", R.drawable.number_seven, R.raw.number_seven));
        numbers.add(new Word("eight","kawintak", R.drawable.number_eight, R.raw.number_eight));
        numbers.add(new Word("nine","kenekakuy", R.drawable.number_nine, R.raw.number_nine));
        numbers.add(new Word("ten","kawinta", R.drawable.number_ten, R.raw.number_ten));
    }

    @Override
    protected void onStop() {
        super.onStop();
        itemsAdapter.releaseResources();
    }
}
