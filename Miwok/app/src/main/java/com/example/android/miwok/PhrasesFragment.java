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

/**
 * A simple {@link Fragment} subclass.
 */
public class PhrasesFragment extends Fragment {


    private ArrayList<Word> phrases;
    private WordAdapter itemsAdapter;

    public PhrasesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.word_list, container, false);

        phrases = new ArrayList<>();
        setPhrasesData();
        itemsAdapter = new WordAdapter(getActivity(), phrases, R.color.category_phrases);
        ListView listView = (ListView) rootView.findViewById(R.id.word_listView);
        listView.setAdapter(itemsAdapter);

        return rootView;
    }

    private void setPhrasesData() {
        phrases.add(Word.createWithAudioNoImage("Where are you goind?", "minto wuksus", R.raw.phrase_where_are_you_going));
        phrases.add(Word.createWithAudioNoImage(" What is your name?", " tinnә oyaase'nә", R.raw.phrase_what_is_your_name));
        phrases.add(Word.createWithAudioNoImage("My name is...", "michәksәs?", R.raw.phrase_my_name_is));
        phrases.add(Word.createWithAudioNoImage("How are you feeling?", "tune", R.raw.phrase_how_are_you_feeling));
        phrases.add(Word.createWithAudioNoImage("I’m feeling good.", "kuchi achit", R.raw.phrase_im_feeling_good));
        phrases.add(Word.createWithAudioNoImage("Are you coming ?", "әәnәs 'aa?", R.raw.phrase_are_you_coming));
        phrases.add(Word.createWithAudioNoImage("Yes, I’m coming.", "hәә’әәnәm", R.raw.phrase_yes_im_coming));
        phrases.add(Word.createWithAudioNoImage("I’m coming.", "әәnәm", R.raw.phrase_im_coming));
        phrases.add(Word.createWithAudioNoImage("Let’s go.", "yoowutis", R.raw.phrase_lets_go));
        phrases.add(Word.createWithAudioNoImage("Come here.", " әnni 'nem", R.raw.phrase_come_here));
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
