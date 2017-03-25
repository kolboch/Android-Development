package bochynski.karol.bmi;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
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

import bochynski.karol.bmi.fragments.InputImperialFragment;
import bochynski.karol.bmi.fragments.InputMetricFragment;

public class MainActivity extends AppCompatActivity {

    private Button countBmiBttn;

    private TextView resultBMI;
    private TextView resultBMIdescription;

    private ICountBMI counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultBMI = (TextView) findViewById(R.id.BMI_result);
        resultBMIdescription = (TextView) findViewById(R.id.BMI_result_description);
        countBmiBttn = (Button)findViewById(R.id.count_bmi_button);
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
                if(isFragmentMetric()){
                    counter = new CountBMIForKgM();
                    float result = counter.countBMI(
                                    Float.parseFloat(((EditText)findViewById(R.id.mass_input)).getText().toString()),
                                     Float.parseFloat(((EditText)findViewById(R.id.height_input)).getText().toString()));
                    setResultsBMI(result);
                    showResultViews();
                }
                else if(isFragmentImperial()){
                    counter = new CountBMIForLbIn();
                    float result = ((CountBMIForLbIn)counter).countBMI(
                            Float.parseFloat(((EditText)findViewById(R.id.stones_input)).getText().toString()),
                            Float.parseFloat(((EditText)findViewById(R.id.pounds_input)).getText().toString()),
                            Float.parseFloat(((EditText)findViewById(R.id.feet_input)).getText().toString()),
                            Float.parseFloat(((EditText)findViewById(R.id.inches_input)).getText().toString()));
                    setResultsBMI(result);
                    showResultViews();
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
        FragmentTransaction transaction;
        switch (item.getItemId()) {
            case R.id.menu_item_kg_meters:
                if (!isFragmentMetric()) {
                    Fragment metric = InputMetricFragment.newInstance();
                    transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.data_input_frame, metric);
                    transaction.commit();
                    hideResultsViews();
                    Toast.makeText(this, "metric", Toast.LENGTH_SHORT).show();
                }
                return true;
            case R.id.menu_item_lb_inches:
                if (!isFragmentImperial()) {
                    Fragment imperial = InputImperialFragment.newInstance();
                    transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.data_input_frame, imperial);
                    transaction.commit();
                    hideResultsViews();
                    Toast.makeText(this, "imperial", Toast.LENGTH_SHORT).show();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
}
