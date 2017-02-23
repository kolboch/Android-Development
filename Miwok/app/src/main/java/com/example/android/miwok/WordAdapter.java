package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.AudioManager.OnAudioFocusChangeListener;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Karol on 2017-02-19.
 */

public class WordAdapter extends ArrayAdapter<Word> {

    private int backgroundColor;
    private static MediaPlayer player;
    private static AudioManager.OnAudioFocusChangeListener afChangeListener;
    private static AudioManager audioManager;
    private static OnCompletionListener onCompletionListener;

    {
        afChangeListener = new OnAudioFocusChangeListener() {
            @Override
            public void onAudioFocusChange(int focusChange) {
                switch (focusChange) {
                    case AudioManager.AUDIOFOCUS_GAIN:
                        player.start();
                        break;
                    case AudioManager.AUDIOFOCUS_LOSS:
                        releasePlayer();
                        break;
                    case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                        player.pause();
                        player.seekTo(0);
                        break;
                }
            }
        };

        onCompletionListener = new OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                releasePlayer();
            }
        };
    }

    public WordAdapter(Context context, List objects, int backgroundColor) {
        super(context, 0, objects);
        this.backgroundColor = backgroundColor;
        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }
        Word current = getItem(position);

        TextView defaultTextView = (TextView) convertView.findViewById(R.id.word_textView);
        TextView miwokTextView = (TextView) convertView.findViewById(R.id.word_miwok_textView);
        LinearLayout wordDescription = (LinearLayout) convertView.findViewById(R.id.word_description);

        defaultTextView.setText(current.getDefaultTranslation());
        miwokTextView.setText(current.getMiwokTranslation());
        wordDescription.setBackgroundResource(this.backgroundColor);

        ImageView wordImage = (ImageView) convertView.findViewById(R.id.word_image);
        if (current.hasImage()) {
            wordImage.setImageResource(current.getImageResource());
            wordImage.setVisibility(View.VISIBLE);
        } else {
            wordImage.setVisibility(View.GONE);
        }

        if (current.hasAudio()) {
            // for inner anonymous class variable must be final
            final int audioResource = current.getAudioResource();

            Button playButton = (Button) convertView.findViewById(R.id.play_button);

            playButton.setBackgroundResource(this.backgroundColor);
            playButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    releasePlayer();
                    //requesting audio focus
                    int requestResult = audioManager.requestAudioFocus(afChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                    if(requestResult == AudioManager.AUDIOFOCUS_REQUEST_GRANTED){
                        // media player for current item
                        player = player.create(WordAdapter.this.getContext(), audioResource);
                        player.start();
                    }
                    player.setOnCompletionListener(onCompletionListener);
                }
            });
        } else {
            convertView.findViewById(R.id.play_button).setVisibility(View.GONE);
        }
        return convertView;
    }

    private void releasePlayer() {
        if (player != null) {
            player.release();
            player = null;
            /* done with playing audio */
            audioManager.abandonAudioFocus(afChangeListener);
        }
    }

    public void releaseResources() {
        releasePlayer();
    }
}
