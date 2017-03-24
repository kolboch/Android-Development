package bochynski.karol.bmi.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import bochynski.karol.bmi.R;

/**
 * Created by Karol on 2017-03-24.
 */

public class InputImperialFragment extends Fragment {

    private EditText stonesInputImperial;
    private EditText poundsInputImperial;
    private EditText feetInputImperial;
    private EditText inchesInputImperial;


    public static InputImperialFragment newInstance(){
        return new InputImperialFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_input_imperial, container, false);
    }
}
