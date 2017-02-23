package com.example.android.miwok;

/**
 * Created by Karol on 2017-02-19.
 */

public class Word {
    private String miwokTranslation;
    private String defaultTranslation;
    private int imageResource;
    private int audioResource = NO_AUDIO;
    private static final int NO_IMAGE = -1;
    private static final int NO_AUDIO = -1;

    public Word(String defaultTranslation, String miwokTranslation) {
        this(defaultTranslation, miwokTranslation, NO_IMAGE);
    }

    public Word(String defaultTranslation, String miwokTranslation, int imageResource) {
        this.defaultTranslation = defaultTranslation;
        this.miwokTranslation = miwokTranslation;
        this.imageResource = imageResource;
    }

    public Word(String defaultTranslation, String miwokTranslation, int imageResource, int audioResource){
        this(defaultTranslation, miwokTranslation, imageResource);
        this.audioResource = audioResource;
    }

    public String getDefaultTranslation() {
        return defaultTranslation;
    }

    public String getMiwokTranslation() {
        return miwokTranslation;
    }

    public int getImageResource() {
        return imageResource;
    }

    /**
     * return audio resource for given file, possible there is no resource
     */
    public int getAudioResource(){
        return audioResource;
    }

    public boolean hasImage(){
        return imageResource != NO_IMAGE;
    }

    public boolean hasAudio(){
        return audioResource != NO_AUDIO;
    }

    /**
     * set audio resource for this word
     */
    public void setAudioResource(int audioResource){
        this.audioResource = audioResource;
    }

    public static Word createWithAudioNoImage(String defaultTranslation, String miwokTranslation, int audioResource){
        Word w = new Word(defaultTranslation, miwokTranslation, Word.NO_IMAGE, audioResource);
        return w;
    }
}
