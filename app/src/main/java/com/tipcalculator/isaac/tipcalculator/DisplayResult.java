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
import android.widget.TextView;


public class DisplayResult extends ActionBarActivity {

    int billDollars;
    int billCents;
    int tipPercent;
    String currency;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_result);

        SharedPreferences sharedPref =
                this.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        currency=sharedPref.getString(getString(R.string.currency),"$");

        double bill;
        double tip;
        Bundle extras = getIntent().getExtras();

        //retrieve the info from the last page
        Intent intent = getIntent();
         billDollars=(int)extras.get("billDollars");
         billCents=(int)extras.get("billCents");
         tipPercent=(int)extras.get("tipPercent");
        int numberSplit=(int)extras.get("numberSplit");

        //convert the bill amount
        bill = convertBill(billDollars,billCents);

        //calculate the tip amount
        tip = calculateTip(bill,tipPercent);

        //initialize the UI
        initBillAmount(bill);
        initTipAmount(tip);
        initSplitBillAmount(bill, numberSplit);
        initSplitTipAmount(tip,numberSplit);
        initBackButton();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_display_result, menu);
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
    convert the bill strings to a double
     */
    private double convertBill(int dollarVal,int centVal){

        double total = 0;
        double dollars = dollarVal;
        double cents = (double)centVal/100;
        total+=cents+dollars;

        return total;
    }

    /*
    calculate the tip amount
     */
    private double calculateTip(double bill,int tipVal){

        return bill*(double)tipVal/100;
    }

    /*
    initialize the bill text
     */
    private void initBillAmount(double bill){
        TextView billText = (TextView) findViewById(R.id.bill_amount);
        billText.setText(String.format("%.2f",bill)+currency);
    }

    /*
    initialize the tip text
     */
    private void initTipAmount(double tip){
        TextView tipText = (TextView) findViewById(R.id.tip_amount);
        tipText.setText(String.format("%.2f", tip)+currency);
    }

    /*
    initialize the split bill text
     */
    private void initSplitBillAmount(double bill, int split){
        bill=bill/(double)split;
        TextView billText = (TextView) findViewById(R.id.split_bill_amount);
        TextView billTitle = (TextView) findViewById(R.id.split_bill_title);
        billText.setText(String.format("%.2f", bill)+currency);
        if(split==1) {
            billText.setVisibility(View.INVISIBLE);
            billTitle.setVisibility(View.INVISIBLE);
        }
        else {
            billText.setVisibility(View.VISIBLE);
            billTitle.setVisibility(View.VISIBLE);
        }

    }

    /*
    initialize the split tip text
     */
    private void initSplitTipAmount(double tip, int split){
        tip=tip/(double)split;
        TextView tipText = (TextView) findViewById(R.id.split_tip_amount);
        TextView tipTitle = (TextView) findViewById(R.id.split_tip_title);
        tipText.setText(String.format("%.2f",tip)+currency);
        if(split==1) {
            tipText.setVisibility(View.INVISIBLE);
            tipTitle.setVisibility(View.INVISIBLE);
        }
        else {
            tipText.setVisibility(View.VISIBLE);
            tipTitle.setVisibility(View.VISIBLE);
        }
    }

    /*
    initialize back button
     */
    private void initBackButton(){
        Button backbutton=(Button) findViewById(R.id.back_res);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //prepare the intent
                Intent intent = new Intent(getBaseContext(),SplitTip.class); //initialize the intent for the next button
                intent.putExtra("tipPercent",tipPercent);
                intent.putExtra("billDollars",billDollars);
                intent.putExtra("billCents",billCents);
                //start the next page
                DisplayResult.this.startActivity(intent);
            }
        });
    }
}
