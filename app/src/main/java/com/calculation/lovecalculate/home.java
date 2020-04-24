package com.calculation.lovecalculate;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

public class home extends AppCompatActivity {
    private String SHARED_PREF="tinydb";
    private InterstitialAd mInterstitialAd;
    boolean adloaded=false;
    private AdView mAdView;
    TextView Results;
    Integer backpressed = 0;
    ProgressBar pb2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().hide();



        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mAdView = findViewById(R.id.adView2);
        AdRequest adRequest2 = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest2);


        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.full_screen_ads));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());



    }
    public void Submitbt(View view) {
        mInterstitialAd.show();
        backpressed = 0;


        EditText boyname = findViewById(R.id.boyname);
        EditText girlname = findViewById(R.id.girlname);
        String boy = "", girl = "";
        boy = boyname.getText().toString();
        girl = girlname.getText().toString();
        boy = boy.trim();
        girl = girl.trim();
        boy = boy.toLowerCase();
        girl = girl.toLowerCase();

        if (!(boy.isEmpty()) && !(girl.isEmpty())) {

            boy = boy.toLowerCase();

            char boyc, girlc;
            int boyint = 0, girlint = 0;

            for (int i = 0; i < boy.length(); i++) {
                boyc = boy.charAt(i);
                boyint = boyint + boyc;
            }

            for (int i = 0; i < girl.length(); i++) {
                girlc = girl.charAt(i);
                girlint = girlint + girlc;
            }

            Results(boyint, girlint, boy, girl);

        } else {
            Toast empty = Toast.makeText(getApplicationContext(), "Enter Both Names", Toast.LENGTH_SHORT);
            empty.setGravity(Gravity.BOTTOM, 0, 200);
            empty.show();
        }
    }

    public void Results(int boytotal, int girltotal, String boyname, String girlname) {

        try {

            RelativeLayout datalayout = findViewById(R.id.datalayout);
            RelativeLayout resultlayout = findViewById(R.id.resultlayout);
            TextView boynametext = findViewById(R.id.boynametext);
            TextView girlnametext = findViewById(R.id.girlnametext);

            Results = findViewById(R.id.results);
            datalayout.setVisibility(View.INVISIBLE);
            resultlayout.setVisibility(View.VISIBLE);

            boyname = boyname.toUpperCase();
            girlname = girlname.toUpperCase();
            boynametext.setText(boyname);
            girlnametext.setText(girlname);

            Integer sumtotal = boytotal + girltotal, temptotal = 0;
            String result1 = "", result2 = "", results = "";
            for (Integer i = 0; i <= 1; i++) {
                temptotal = sumtotal % 10;
                sumtotal = sumtotal / 10;
                if (i == 0) {
                    result1 = temptotal.toString();
                } else {
                    result2 = temptotal.toString();
                }
            }
            results = result1 + result2;
            Integer afterresults, temp;
            afterresults = Integer.parseInt(results);
            if (afterresults < 40)
                afterresults = afterresults + 35;

            results = afterresults.toString();
            Results.setText(results + "%");

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    closeanim();
                }
            }, 2000);



        } catch (Exception ex) {
            Toast empty = Toast.makeText(getApplicationContext(), "Some Error Occured", Toast.LENGTH_SHORT);
            empty.setGravity(Gravity.BOTTOM, 0, 200);
            empty.show();
        }
    }

    public void Back(View view) {
        onBackPressed();
    }

    public void onBackPressed() {
        if(adloaded){

            adloaded=false;
        }

        RelativeLayout datalayout = findViewById(R.id.datalayout);
        RelativeLayout resultlayout = findViewById(R.id.resultlayout);
        RelativeLayout placementlayout = findViewById(R.id.setplacement);
        EditText boyname = findViewById(R.id.boyname);
        EditText girlname = findViewById(R.id.girlname);
        TextView Results = findViewById(R.id.results);
        ProgressBar pb = findViewById(R.id.pb);
        if ((datalayout.getVisibility() == View.VISIBLE) && backpressed.equals(1)) {
            finish();
            System.exit(0);
        } else if (resultlayout.getVisibility() == View.VISIBLE) {
            resultlayout.setVisibility(View.INVISIBLE);
            datalayout.setVisibility(View.VISIBLE);
            pb.setVisibility(View.VISIBLE);
            Results.setVisibility(View.GONE);
            boyname.setText("");
            girlname.setText("");
        } else {
            backpressed = (datalayout.getVisibility() == View.VISIBLE) ? 1 : 0;
            Toast empty = Toast.makeText(getApplicationContext(), "Press again to close", Toast.LENGTH_SHORT);
            empty.setGravity(Gravity.BOTTOM, 0, 200);
            empty.show();
        }
        if (placementlayout.getVisibility()==View.VISIBLE){
            datalayout.setVisibility(View.VISIBLE);
            placementlayout.setVisibility(View.INVISIBLE);
        }
    }

    public void closeanim() {
        TextView Results = findViewById(R.id.results);
        ProgressBar pb = findViewById(R.id.pb);

        pb.setVisibility(View.GONE);
        Results.setVisibility(View.VISIBLE);
    }

    public void Setplacement(View view){
        RelativeLayout datalayout = findViewById(R.id.datalayout);
        RelativeLayout placementlayout = findViewById(R.id.setplacement);
        datalayout.setVisibility(View.INVISIBLE);
        placementlayout.setVisibility(View.VISIBLE);
    }


    public void Savedata(String data){
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        SharedPreferences localdb=getSharedPreferences(SHARED_PREF,MODE_PRIVATE);
        SharedPreferences.Editor editor=localdb.edit();
        editor.putString("placementid",data);
        editor.apply();
        Toast placementdone=Toast.makeText(getApplicationContext(),"Your id has been Updated", Toast.LENGTH_SHORT);
        placementdone.setGravity(Gravity.BOTTOM,0,200);
        placementdone.show();
        pb2=findViewById(R.id.pb2);
        pb2.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pb2.setVisibility(View.INVISIBLE);
            }
        },2000);

        Toast restartingapp=Toast.makeText(getApplicationContext(),"Restarting application", Toast.LENGTH_SHORT);
        restartingapp.setGravity(Gravity.BOTTOM,0,200);
        restartingapp.show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
                System.exit(0);
            }
        },2000);
    }

    }
