package com.tipcalculator.isaac.tipcalculator;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.TextView;


public class SplitTip extends ActionBarActivity {
    NumberPicker peoplePicker;
    RadioButton yesButton;
    RadioButton noButton;
    TextView peopleText;

    int billDollars;
    int billCents;
    int tipPercent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_split_tip);

        Bundle extras = getIntent().getExtras();
        billDollars=(int)extras.get("billDollars");
        billCents=(int)extras.get("billCents");
        tipPercent=(int)extras.get("tipPercent");

        initPeopleText();
        initPeoplePicker();
        initBackButton();
        initNextButton();
        initRadios();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_split_tip, menu);
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
    initialize the number of people text
    the text is invisible initially, wait for a yes on the radios for visible
     */
    private void initPeopleText(){
        peopleText = (TextView) findViewById(R.id.people_text);
        peopleText.setVisibility(TextView.INVISIBLE);
    }

    /*
    initalize a picker for the number of people
    invisible initially, wait for a yes on the radios for visible
     */
    private void initPeoplePicker(){
        peoplePicker = (NumberPicker) findViewById(R.id.people_picker);
        peoplePicker.setVisibility(View.INVISIBLE);

        //create and populate the array of values for the picker
        String[] values = new String[100];
        for(int i=0;i<values.length;i++){
            values[i]=Integer.toString(i+1);
        }

        //set the number of choices in the picker
        peoplePicker.setMinValue(1);
        peoplePicker.setMaxValue(values.length-1);

        //add the array to the picker
        peoplePicker.setDisplayedValues(values);
        peoplePicker.setValue(1);

        //do not wraparound the picker
        peoplePicker.setWrapSelectorWheel(false);
    }

    /*
    initialize next button
     */
    private void initNextButton(){
        Button nextbutton=(Button) findViewById(R.id.next_button_split);
        nextbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //prepare the intent
                Intent intent = new Intent(getBaseContext(),DisplayResult.class); //initialize the intent for the next button
                //add the data from the number pickers
                intent.putExtra("tipPercent",tipPercent);
                intent.putExtra("billDollars",billDollars);
                intent.putExtra("billCents",billCents);
                intent.putExtra("numberSplit", peoplePicker.getValue());
                //start the next page
                SplitTip.this.startActivity(intent);
            }
        });
    }

    /*
    initialize back button
     */
    private void initBackButton(){
        Button backbutton=(Button) findViewById(R.id.back_button_split);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //prepare the intent
                Intent intent = new Intent(getBaseContext(),MainActivity.class); //initialize the intent for the next button
                //start the next page
                SplitTip.this.startActivity(intent);
            }
        });
    }

    private void initRadios(){
        RadioButton yesButton = (RadioButton) findViewById(R.id.radio_yes);
        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                peoplePicker.setVisibility(View.VISIBLE);
                peopleText.setVisibility(View.VISIBLE);
            }
        });
        RadioButton noButton = (RadioButton) findViewById(R.id.radio_no);
        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                peoplePicker.setVisibility(View.INVISIBLE);
                peopleText.setVisibility(View.INVISIBLE);
            }
        });
        noButton.toggle();
    }
}
