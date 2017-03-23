package bochynski.karol.bmi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button countBmiBttn;
    private TextView massInput;
    private TextView heightInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        massInput = (TextView)findViewById(R.id.mass_input);
        heightInput = (TextView)findViewById(R.id.height_input);

        setListeners();
    }

    private void setListeners(){
        countBmiBttn = (Button)findViewById(R.id.count_bmi_button);
        countBmiBttn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                
            }
        });
    }
}
