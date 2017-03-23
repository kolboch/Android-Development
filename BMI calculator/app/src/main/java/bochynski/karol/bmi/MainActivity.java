package bochynski.karol.bmi;

import android.os.Bundle;
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

public class MainActivity extends AppCompatActivity {

    private Button countBmiBttn;

    private TextView massLabelMetric;
    private TextView heightLabelMetric;
    private EditText massInputMetric;
    private EditText heightInputMetric;

    private TextView resultBMI;
    private TextView resultBMIdescription;

    private EditText stonesInputImperial;
    private TextView stonesLabelImperial;
    private EditText poundsInputImperial;
    private TextView poundsLabelImperial;

    private EditText feetInputImperial;
    private TextView feetLabelImperial;
    private EditText inchesInputImperial;
    private TextView inchesLabelImperial;

    private CountBMIForKgM counterKgM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        assignMetricViews();

        resultBMI = (TextView)findViewById(R.id.BMI_result);
        resultBMIdescription = (TextView)findViewById(R.id.BMI_result_description);

        counterKgM= new CountBMIForKgM();

        setListeners();
    }

    private void setListeners(){
        countBmiBttn = (Button)findViewById(R.id.count_bmi_button);
        countBmiBttn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                float result = counterKgM.countBMI(Float.parseFloat(massInputMetric.getText().toString()),
                        Float.parseFloat(heightInputMetric.getText().toString()));
                showResultViews();
                setResultsBMI(result);
                Toast.makeText(getApplicationContext(), "countBMI", Toast.LENGTH_SHORT).show();
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
        switch(item.getItemId()){
            case R.id.menu_item_kg_meters:
                Toast.makeText(this, "metric", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu_item_lb_inches:
                Toast.makeText(this, "imperial", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void assignMetricViews(){
        massInputMetric = (EditText)findViewById(R.id.mass_input);
        heightInputMetric = (EditText)findViewById(R.id.height_input);
        massLabelMetric = (TextView)findViewById(R.id.mass_label);
        heightLabelMetric = (TextView)findViewById(R.id.height_label);
    }

    private void showResultViews(){
        resultBMI.setVisibility(View.VISIBLE);
        resultBMIdescription.setVisibility(View.VISIBLE);
    }

    private void hideResultsViews(){
        resultBMI.setVisibility(View.GONE);
        resultBMIdescription.setVisibility(View.GONE);
    }

    private void setResultsBMI(float result){
        resultBMI.setText("" + result);
        //call Analyzer for description and color of result
    }
}
