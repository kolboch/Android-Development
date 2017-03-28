package bochynski.karol.bmi;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

import bochynski.karol.bmi.exceptions.InvalidHeightException;
import bochynski.karol.bmi.exceptions.InvalidMassException;
import bochynski.karol.bmi.fragments.InputImperialFragment;
import bochynski.karol.bmi.fragments.InputMetricFragment;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.BMI_result) TextView resultBMI;
    @BindView(R.id.BMI_result_description) TextView resultBMIdescription;
    @BindView(R.id.count_bmi_button) Button countBmiBttn;
    private ICountBMI counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        addFragmentIfNotPresent();
        setListeners();
    }

    private void addFragmentIfNotPresent() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.data_input_frame);
        if (fragment == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.data_input_frame, new InputMetricFragment());
            transaction.commit();
        }
    }

    private void setListeners() {
        countBmiBttn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (isFragmentMetric()) {
                        counter = new CountBMIForKgM();
                        float result = counter.countBMI(
                                getNumberOrZero(((EditText) findViewById(R.id.mass_input)).getText().toString()),
                                getNumberOrZero(((EditText) findViewById(R.id.height_input)).getText().toString()));
                        setResultsBMI(result);
                        showResultViews();
                    } else if (isFragmentImperial()) {
                        counter = new CountBMIForLbIn();
                        float result = ((CountBMIForLbIn) counter).countBMI(
                                getNumberOrZero(((EditText) findViewById(R.id.stones_input)).getText().toString()),
                                getNumberOrZero(((EditText) findViewById(R.id.pounds_input)).getText().toString()),
                                getNumberOrZero(((EditText) findViewById(R.id.feet_input)).getText().toString()),
                                getNumberOrZero(((EditText) findViewById(R.id.inches_input)).getText().toString()));
                        setResultsBMI(result);
                        showResultViews();

                    }
                }
                catch (InvalidHeightException e){
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.invalid_height_toast), Toast.LENGTH_SHORT).show();
                }
                catch (InvalidMassException e){
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.invalid_mass_toast), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_item_kg_meters:
                if (!isFragmentMetric()) {
                    replaceInputDataFrame(InputMetricFragment.newInstance());
                    hideResultsViews();
                    Toast.makeText(this, "metric", Toast.LENGTH_SHORT).show();
                }
                return true;
            case R.id.menu_item_lb_inches:
                if (!isFragmentImperial()) {
                    replaceInputDataFrame(InputImperialFragment.newInstance());
                    hideResultsViews();
                    Toast.makeText(this, "imperial", Toast.LENGTH_SHORT).show();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private float getNumberOrZero(String numberString){
        float number;
        if(!TextUtils.isEmpty(numberString)){
            number = Float.parseFloat(numberString);
        }
        else{
            number = 0;
        }
        return number;
    }

    private boolean isFragmentMetric(){
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.data_input_frame);
        return fragment instanceof InputMetricFragment;
    }

    private boolean isFragmentImperial(){
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.data_input_frame);
        return fragment instanceof  InputImperialFragment;
    }

    private void showResultViews() {
        resultBMI.setVisibility(View.VISIBLE);
        resultBMIdescription.setVisibility(View.VISIBLE);
    }

    private void hideResultsViews() {
        resultBMI.setVisibility(View.GONE);
        resultBMIdescription.setVisibility(View.GONE);
    }

    private void setResultsBMI(float result) {
        resultBMI.setText(formatResult(result));
        int BMIdescription = AnalyzerBMI.getDescriptionResource(result);
        int color = AnalyzerBMI.getColorResourceForResult(result);
        resultBMI.setTextColor(ContextCompat.getColor(this, color));
        resultBMIdescription.setText(BMIdescription);
    }

    private String formatResult(float result){
        DecimalFormat df = new DecimalFormat("#.##");
        String formatted = df.format(result);
        return formatted;
    }

    private void replaceInputDataFrame(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.data_input_frame, fragment);
        transaction.commit();
    }
}
