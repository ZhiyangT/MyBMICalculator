package sg.edu.rp.c346.mybmicalculator;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    EditText etWeight;
    EditText etHeight;
    Button btnCalc;
    Button btnReset;
    TextView tvDate;
    TextView tvBMI;
    TextView tvOutcome;

    float bmi = 0;
    String datetime;
    String outcome;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etWeight = findViewById(R.id.editTextWeight);
        etHeight = findViewById(R.id.editTextHeight);
        btnCalc = findViewById(R.id.buttonCalc);
        btnReset = findViewById(R.id.buttonReset);
        tvDate = findViewById(R.id.textViewDate);
        tvBMI = findViewById(R.id.textViewBMI);
        tvOutcome = findViewById(R.id.textViewOutcome);



        btnCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float weight = Float.parseFloat(etWeight.getText().toString());
                float height = Float.parseFloat(etHeight.getText().toString());
                float nheight = height/100;

                bmi = weight / (nheight*nheight) ;
                String result = String.format("%s %.2f", "Last Calculated BMI: ", bmi);

                if(bmi < 18.5){
                    outcome = "You are underweight";
                }
                else if(bmi >=18.5 && bmi <= 24.9){
                    outcome = "Your BMI is normal";
                }
                else if(bmi >=25 && bmi <= 29.9){
                    outcome = "You are overweight";
                }
                else{
                    outcome = "You are obese";

                }

                Calendar now = Calendar.getInstance();
                datetime = now.get(Calendar.DAY_OF_MONTH) + "/" +
                        (now.get(Calendar.MONTH)+1) + "/" +
                        now.get(Calendar.YEAR) + " " +
                        now.get(Calendar.HOUR_OF_DAY) + ":" +
                        now.get(Calendar.MINUTE);


                tvDate.setText("Last Calculated Date: " + datetime);
                tvBMI.setText(result);
                tvOutcome.setText(outcome);
            }

        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etHeight.setText("");
                etWeight.setText("");
                tvDate.setText("");
                tvBMI.setText("");
                tvOutcome.setText("");
            }
        });


    }
    @Override
    protected void onPause() {
        super.onPause();

        //Step 1a: Obtain an instance of the sharedPreferences
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        //Step 1b: Obtain an instance of the SharedPreference Editor for update later
        SharedPreferences.Editor prefEdit = prefs.edit();

        //Step 1c: Add the key-value pair
        prefEdit.putFloat("bmi",bmi);
        prefEdit.putString("datetime", datetime);
        prefEdit.putString("outcome", outcome);


        //Step 1d: Call commit() to save the changes into sharedPreferences
        prefEdit.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();

        //Step 2a: Obtain an instance of the sharedPreferences
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        //Step 2b: Retrieve the saved data with a key "greetings" from the sharedPreference object
        float bmi = prefs.getFloat("bmi", 0);
        String datetime = prefs.getString("datetime", "");
        String outcome = prefs.getString("outcome", "");


        tvDate.setText("Last Calculated Date: " + datetime);
        tvBMI.setText("Last Calculated BMI: "+ bmi);
        tvOutcome.setText(outcome);


    }
}
