package com.tipcalculator.isaac.tipcalculator;

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


public class MainActivity extends ActionBarActivity {
    NumberPicker billPicker;
    NumberPicker tipPicker;
    NumberPicker centPicker;
    String currency;
    int tip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize the ui elements
        initPrefs();
        initBillPicker();
        initTipPicker();
        initCentPicker();
        initNextButton();
        initSubButton();
        initAddButton();
        initPrefButton();
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

    /*
    initialize the bill cost picker
     */
    private void initBillPicker(){
        billPicker = (NumberPicker) findViewById(R.id.billNumberPicker);

        //create and populate the array of values for the picker
        String[] values = new String[1000];
        for(int i=0;i<values.length;i++){
            values[i]=Integer.toString(i)+currency;
        }

        //set the number of choices in the picker
        billPicker.setMinValue(0);
        billPicker.setMaxValue(values.length-1);

        //add the array to the picker
        billPicker.setDisplayedValues(values);
        billPicker.setValue(20);

        //do not wraparound the picker
        billPicker.setWrapSelectorWheel(false);
    }

    /*
    initialize the tip picker
     */
    private void initTipPicker(){
        tipPicker = (NumberPicker) findViewById(R.id.tipNumberPicker);

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
        tipPicker.setValue(tip);

        //do not wraparound the picker
        tipPicker.setWrapSelectorWheel(false);
    }

    /*
    initialize the cent picker
     */
    private void initCentPicker(){
        centPicker = (NumberPicker) findViewById(R.id.centNumberPicker);

        //create and populate the array of values for the picker
        String[] values = new String[10];
        for(int i=0;i<values.length;i++){
                values[i]="."+Integer.toString(i)+"0";
        }

        //set the number of choices in the picker
        centPicker.setMinValue(0);
        centPicker.setMaxValue(values.length-1);

        //add the array to the picker
        centPicker.setDisplayedValues(values);
        centPicker.setValue(0);

        //do not wraparound the picker
        centPicker.setWrapSelectorWheel(false);
    }

    /*
    initialize add button
     */
    private void initAddButton(){
        Button addbutton=(Button) findViewById(R.id.addbutton);
        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //add 25 to the bill value
               billPicker.setValue(billPicker.getValue()+25);
            }
        });
        addbutton.setText("+25"+currency);
    }

    /*
    initialize sub button
     */
    private void initSubButton(){
       Button subbutton=(Button) findViewById(R.id.subbutton);
        subbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //subtract 25 to the bill value
                billPicker.setValue(billPicker.getValue() - 25);
            }
        });
        subbutton.setText("-25"+currency);
    }

    /*
    initialize next button
     */
    private void initNextButton(){
        Button nextbutton=(Button) findViewById(R.id.next_button_main);
        nextbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //prepare the intent
                Intent intent = new Intent(getBaseContext(),SplitTip.class); //initialize the intent for the next button
                //add the data from the number pickers
                //System.out.println("intentCreated");
                intent.putExtra("tipPercent", tipPicker.getValue());
                //System.out.println("dollars@main: "+billPicker.getValue());
                intent.putExtra("billDollars",billPicker.getValue());
                intent.putExtra("billCents",centPicker.getValue());
                //start the next page
                MainActivity.this.startActivity(intent);
            }
        });
    }

    /*
    initialize preferences button
     */
    private void initPrefButton(){
        Button prefbutton=(Button) findViewById(R.id.pref_button);
        prefbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //prepare the intent
                Intent intent = new Intent(getBaseContext(),SelectPreferences.class);
                //start the next page
                MainActivity.this.startActivity(intent);
            }
        });
    }

    /*
    initalize the default tip and currency
     */
    private void initPrefs(){
        SharedPreferences sharedPref =
                this.getSharedPreferences(getString(R.string.preference_file_key),Context.MODE_PRIVATE);
        currency=sharedPref.getString(getString(R.string.currency),"$");
        tip=sharedPref.getInt(getString(R.string.tip_val),15);
    }


}
