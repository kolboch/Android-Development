package com.example.android.miwok;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Karol on 2017-02-25.
 */

public class WordsFragmentPagerAdapter extends FragmentPagerAdapter {

    private Context context;

    public WordsFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new NumberFragment();
        } else if (position == 1) {
            return new FamilyFragment();
        } else if (position == 2) {
            return new ColorsFragment();
        } else {
            return new PhrasesFragment();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return this.context.getString(R.string.category_numbers);
        } else if (position == 1) {
            return this.context.getString(R.string.category_family);
        } else if (position == 2) {
            return this.context.getString(R.string.category_colors);
        } else {
            return this.context.getString(R.string.category_phrases);
        }
    }

}
