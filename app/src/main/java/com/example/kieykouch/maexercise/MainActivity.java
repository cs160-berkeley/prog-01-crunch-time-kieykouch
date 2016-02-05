package com.example.kieykouch.maexercise;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private Spinner spinner;
    private Spinner spinner2;
    private TextView mSwitcher;
    private EditText count;
    private TextView convert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

        spinner = (Spinner) findViewById(R.id.spinner);
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        mSwitcher = (TextView) findViewById(R.id.burned);
        count = (EditText) findViewById(R.id.rep_minutes);
        Button nextButton = (Button) findViewById(R.id.button);
        convert = (TextView) findViewById(R.id.converted);

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                String myinput = count.getText().toString();
                int input_length = myinput.length();
                if (input_length > 1 && input_length < 16){
                    String myexercise = spinner.getSelectedItem().toString();
                    String different_exercise = spinner2.getSelectedItem().toString();
                    double duration = Double.parseDouble(count.getText().toString());
                    convert.setText(calculation_equal(duration, different_exercise, myexercise));
                }
                else{
                    convert.setText("");
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {}
        });

        nextButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                double duration;
                int input_length;

                try {
                    duration = Double.parseDouble(count.getText().toString());
                    input_length = count.getText().toString().length();
                } catch (NumberFormatException e) {
                    Log.i("Double", e.getMessage().toString());
                    mSwitcher.setText("");
                    convert.setText("");
                    return;
                }

                if (duration <= 0 || input_length > 16) {
                    mSwitcher.setText("");
                    convert.setText("");
                    return;
                }
                String myexercise = spinner.getSelectedItem().toString();
                String different_exercise = spinner2.getSelectedItem().toString();

                mSwitcher.setText(round(calculation(duration, myexercise)));
                convert.setText(calculation_equal(duration, different_exercise, myexercise));
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private double calculation(double time, String type){
        ArrayList<Object> item = string_comp(type);
        double a = (double) item.get(0);
        return time/a*100;
    }

    private String calculation_equal(double time, String str, String old){
        ArrayList<Object> item = string_comp(old);
        double a = (double) item.get(0);
        ArrayList<Object> item2 = string_comp(str);
        double b = (double) item2.get(0);
        String c = (String) item2.get(1);

        double d = time*b/a;
        return round(d)+ " " + c;
    }

    private ArrayList<Object> string_comp(String str){
        ArrayList<Object> rv = new ArrayList<Object>();

        double a = 0;
        String b = "";

        switch (str){
            case "Pushup":
                a = 350;
                b = "Reps";
                break;
            case "Situp":
                a = 200;
                b = "Reps";
                break;
            case "Squats":
                a = 225;
                b = "Reps";
                break;
            case "Leg-lift":
            case "Plank":
                a = 25;
                b = "Minutes";
                break;
            case "Jumping Jacks":
                a = 10;
                b = "Minutes";
                break;
            case "Pullup":
                a = 100;
                b = "Reps";
                break;
            case "Cycling":
            case "Jogging":
                a = 12;
                b = "Minutes";
                break;
            case "Walking":
                a = 20;
                b = "Minutes";
                break;
            case "Swimming":
                a = 13;
                b = "Minutes";
                break;
            case "Stair-Climbing":
                a = 15;
                b = "Minutes";
                break;

            default:
                throw new IllegalArgumentException("Invalid Selection: " + str);
        }
        rv.add(a);
        rv.add(b);
        return rv;
    }


    private String round(double n){
        DecimalFormat df = new DecimalFormat("#.#");
        df.setRoundingMode(RoundingMode.CEILING);
        return df.format(n).toString();
    }
}
