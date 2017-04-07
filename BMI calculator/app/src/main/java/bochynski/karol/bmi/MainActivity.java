package bochynski.karol.bmi;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

import bochynski.karol.bmi.exceptions.InvalidHeightException;
import bochynski.karol.bmi.exceptions.InvalidMassException;
import bochynski.karol.bmi.fragments.InputImperialFragment;
import bochynski.karol.bmi.fragments.InputMetricFragment;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    //TODO hide show save/share when no result yet
    @BindView(R.id.BMI_result)
    TextView resultBMI;
    @BindView(R.id.BMI_result_description)
    TextView resultBMIdescription;
    @BindView(R.id.count_bmi_button)
    Button countBmiBttn;
    @BindString(R.string.dir_screenshots_folder)
    String screenshotsDirFolder;
    @BindString(R.string.screenshot_file_default_name)
    String fileDefaultName;
    @BindString(R.string.saved_toast)
    String savedToast;
    @BindString(R.string.share_BMI_result_text_title)
    String shareBMIResultTextTitle;
    @BindString(R.string.share_using)
    String shareWith;
    private ICountBMI counter;
    private String BMI_RESULT_SAVED = "BMI_RESULT";
    private String BMI_RESULT_DESCRIPTION_SAVED = "BMI_RESULT_DESCRIPTION";
    private static final String PREFERENCES_NAME = "BMI_preferences";
    private static final String MAIN_LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        addFragmentIfNotPresent();
        setListeners();
        setResultIfSaved();
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
                } catch (InvalidHeightException e) {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.invalid_height_toast), Toast.LENGTH_SHORT).show();
                } catch (InvalidMassException e) {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.invalid_mass_toast), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (resultBMI != null && !TextUtils.isEmpty(resultBMI.getText())) {
            outState.putCharSequence(BMI_RESULT_SAVED, resultBMI.getText());
        }
        if (resultBMIdescription != null && !TextUtils.isEmpty(resultBMIdescription.getText())) {
            outState.putCharSequence(BMI_RESULT_DESCRIPTION_SAVED, resultBMIdescription.getText());
        }
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
                    resetResultsViews();
                    hideResultsViews();
                    makeShortToast(R.string.menu_units_kg_m);
                }
                return true;
            case R.id.menu_item_lb_inches:
                if (!isFragmentImperial()) {
                    replaceInputDataFrame(InputImperialFragment.newInstance());
                    resetResultsViews();
                    hideResultsViews();
                    makeShortToast(R.string.menu_units_lb_in);
                }
                return true;
            case R.id.menu_item_share:
                showShareDialog();
                return true;
            case R.id.menu_item_save:
                showSaveDialog();
                return true;
            case R.id.menu_item_about_author:
                Intent i = new Intent(this, AuthorInfoActivity.class);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void saveToSharedPreferences() {
        SharedPreferences preferences = getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        boolean saved = false;
        if (resultBMI != null && !TextUtils.isEmpty(resultBMI.getText())) {
            editor.putString(BMI_RESULT_SAVED, (String) resultBMI.getText());
            editor.commit();
            if (resultBMIdescription != null && !TextUtils.isEmpty(resultBMIdescription.getText())) {
                editor.putString(BMI_RESULT_DESCRIPTION_SAVED, (String) resultBMIdescription.getText());
                editor.commit();
            }
            saved = true;
        }
        if (saved) {
            makeShortToast(R.string.saved_BMI_results);
        }
    }

    private void setResultIfSaved() {
        SharedPreferences preferences = getApplicationContext().getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        String result, description;
        result = preferences.getString(BMI_RESULT_SAVED, "");
        description = preferences.getString(BMI_RESULT_DESCRIPTION_SAVED, "");
        boolean restored = false;
        if (!TextUtils.isEmpty(result)) {
            resultBMI.setText(result);
            setResultBMIColor(result);
            restored = true;
        }
        if (!TextUtils.isEmpty(description)) {
            resultBMIdescription.setText(description);
        }
        if(restored){
            showResultViews();
        }
    }

    private float getNumberOrZero(String numberString) {
        float number;
        if (!TextUtils.isEmpty(numberString)) {
            number = Float.parseFloat(numberString);
        } else {
            number = 0;
        }
        return number;
    }

    private boolean isFragmentMetric() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.data_input_frame);
        return fragment instanceof InputMetricFragment;
    }

    private boolean isFragmentImperial() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.data_input_frame);
        return fragment instanceof InputImperialFragment;
    }

    private void showResultViews() {
        resultBMI.setVisibility(View.VISIBLE);
        resultBMIdescription.setVisibility(View.VISIBLE);
    }

    private void resetResultsViews(){
        if(resultBMI != null){
            resultBMI.setText("");
        }
        if(resultBMIdescription != null){
            resultBMIdescription.setText("");
        }
    }

    private void hideResultsViews() {
        resultBMI.setVisibility(View.GONE);
        resultBMIdescription.setVisibility(View.GONE);
    }

    private void setResultsBMI(float result) {
        resultBMI.setText(formatResult(result));
        int BMIdescription = AnalyzerBMI.getDescriptionResource(result);
        setResultBMIColor(result);
        resultBMIdescription.setText(BMIdescription);
    }

    private void setResultBMIColor(String resultString) {
        resultString = resultString.replace(',', '.');
        float result = Float.parseFloat(resultString);
        setResultBMIColor(result);
    }

    private void setResultBMIColor(float result) {
        int color = AnalyzerBMI.getColorResourceForResult(result);
        resultBMI.setTextColor(ContextCompat.getColor(this, color));
    }

    private String formatResult(float result) {
        DecimalFormat df = new DecimalFormat("#.##");
        String formatted = df.format(result);
        return formatted;
    }

    private void replaceInputDataFrame(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.data_input_frame, fragment);
        transaction.commit();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        showResultViews();
        if (savedInstanceState.containsKey(BMI_RESULT_SAVED)) {
            CharSequence savedBmiResult = savedInstanceState.getCharSequence(BMI_RESULT_SAVED);
            if (resultBMI != null) {
                resultBMI.setText(savedBmiResult);
                setResultBMIColor((String) savedBmiResult);
            }
        }
        if (savedInstanceState.containsKey(BMI_RESULT_DESCRIPTION_SAVED)) {
            CharSequence savedBmiDescription = savedInstanceState.getCharSequence(BMI_RESULT_DESCRIPTION_SAVED);
            if (resultBMIdescription != null) {
                resultBMIdescription.setText(savedBmiDescription);
            }
        }
    }

    private void makeShortToast(int messageResource) {
        Toast.makeText(this, messageResource, Toast.LENGTH_SHORT).show();
    }

    private File saveScreenshot() {
        if(resultBMI == null || TextUtils.isEmpty(resultBMI.getText())){
            makeShortToast(R.string.no_result_available);
            return null;
        }
        Bitmap bm = ImageShareUtils.getScreenshot(findViewById(R.id.activity_main));
        File saved;
        try {
            saved = ImageShareUtils.storeBitmapToFile(bm, screenshotsDirFolder, fileDefaultName + ImageShareUtils.generateFileName());
        } catch (IOException e) {
            makeShortToast(R.string.exception_failed_to_save_screenshot);
            saved = null; // to be sure that successful saving message is not displayed
            Log.e(MAIN_LOG_TAG, e.getMessage());
        }
        if (saved != null) {
            Toast.makeText(this, savedToast + " " + saved.getPath(), Toast.LENGTH_SHORT).show();
        }
        return saved;
    }

    private void shareScreenshot() {
        File saved = saveScreenshot();
        if (saved != null) {
            shareImage(saved);
        }
    }

    private void shareText(String text) {
        if(TextUtils.isEmpty(text)){
            makeShortToast(R.string.no_result_available);
        }
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_SUBJECT, shareBMIResultTextTitle);
        intent.putExtra(Intent.EXTRA_TEXT, text);
        intent.setType("text/plain");
        startActivity(Intent.createChooser(intent, shareWith));
    }

    private void shareImage(File f) {
        Uri uri = Uri.fromFile(f);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("image/*"); // everything that handles images ( of any extension )
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        try {
            startActivity(Intent.createChooser(intent, getResources().getString(R.string.share_apps_chooser_title)));
        } catch (ActivityNotFoundException e) {
            makeShortToast(R.string.exception_no_app_to_handle_share);
        }
    }

    private String getResultBMI(){
        String result = "";
        if(resultBMI != null){
            result = (String)resultBMI.getText();
        }
        return result;
    }

    private void showSaveDialog(){
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setMessage(R.string.save_alert_dialog_title);
        alertBuilder.setPositiveButton(R.string.save_alert_dialog_image, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                saveScreenshot();
            }
        });
        alertBuilder.setNeutralButton(R.string.save_alert_dialog_last_result, new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                saveToSharedPreferences();
            }
        });
        alertBuilder.setNegativeButton(R.string.alert_dialog_cancel, new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if(dialog != null){
                    dialog.dismiss();
                }
            }
        });
        AlertDialog dialog = alertBuilder.create();
        dialog.show();
    }

    private void showShareDialog(){
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setMessage(R.string.save_alert_dialog_title);
        alertBuilder.setPositiveButton(R.string.save_alert_dialog_image, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                shareScreenshot();
            }
        });
        alertBuilder.setNeutralButton(R.string.save_alert_dialog_last_result, new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String result = getResultBMI();
                shareText(result);
            }
        });
        alertBuilder.setNegativeButton(R.string.alert_dialog_cancel, new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if(dialog != null){
                    dialog.dismiss();
                }
            }
        });
        AlertDialog dialog = alertBuilder.create();
        dialog.show();
    }
}
