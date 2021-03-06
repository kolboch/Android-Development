package dev.kb.jsonparser;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * proceeds to DisplayData activity
     * @param view
     */
    public void proceedToData(View view){
        Intent intent = new Intent(this, DisplayData.class);
        startActivity(intent);
    }
}
