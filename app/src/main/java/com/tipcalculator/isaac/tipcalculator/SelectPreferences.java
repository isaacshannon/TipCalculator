package com.tipcalculator.isaac.tipcalculator;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.RadioButton;


public class SelectPreferences extends ActionBarActivity {
    NumberPicker tipPicker;
    Activity context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_preferences);
        context = this;

        initRadios();
        initTipPicker();
        initBackButton();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_select_preferences, menu);
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

    /*
    initialize back button
     */
    private void initBackButton(){
        Button backbutton=(Button) findViewById(R.id.done_button);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPref =
                        context.getSharedPreferences(getString(R.string.preference_file_key),Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putInt(getString(R.string.tip_val),tipPicker.getValue());
                editor.commit();
                //prepare the intent
                Intent intent = new Intent(getBaseContext(),MainActivity.class); //initialize the intent for the next button
                //start the next page
                SelectPreferences.this.startActivity(intent);
            }
        });
    }

    /*
    initialize the radio buttons
     */
    private void initRadios(){
        RadioButton dollarButton = (RadioButton) findViewById(R.id.radio_dollar);
        dollarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPref =
                        context.getSharedPreferences(getString(R.string.preference_file_key),Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString(getString(R.string.currency),getString(R.string.dollar));
                editor.commit();
            }
        });

        RadioButton euroButton = (RadioButton) findViewById(R.id.radio_euro);
        euroButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPref =
                        context.getSharedPreferences(getString(R.string.preference_file_key),Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString(getString(R.string.currency),getString(R.string.euro));
                editor.commit();
            }
        });

        RadioButton poundButton = (RadioButton) findViewById(R.id.radio_pound);
        poundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPref =
                        context.getSharedPreferences(getString(R.string.preference_file_key),Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString(getString(R.string.currency),getString(R.string.pound));
                editor.commit();
            }
        });
        dollarButton.toggle();
    }

    /*
    initalize the default tip picker
     */
    private void initTipPicker(){
        SharedPreferences sharedPref =
                context.getSharedPreferences(getString(R.string.preference_file_key),Context.MODE_PRIVATE);
        int defaultTip = sharedPref.getInt(getString(R.string.tip_val),15);
        tipPicker = (NumberPicker) findViewById(R.id.default_tip_picker);

        //create and populate the array of values for the picker
        String[] values = new String[100];
        for(int i=0;i<values.length;i++){
            values[i]=Integer.toString(i)+"%";
        }

        //set the number of choices in the picker
        tipPicker.setMinValue(0);
        tipPicker.setMaxValue(values.length-1);

        //add the array to the picker
        tipPicker.setDisplayedValues(values);
        tipPicker.setValue(defaultTip);

        //do not wraparound the picker
        tipPicker.setWrapSelectorWheel(false);
    }
}
