package bochynski.karol.bmi.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import bochynski.karol.bmi.R;

/**
 * Created by Karol on 2017-03-24.
 */

public class InputMetricFragment extends Fragment {

    public static InputMetricFragment newInstance(){
        return new InputMetricFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_input_metric, container, false);
    }
}
