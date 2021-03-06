package com.nikalexion.milasiskas;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.ads.MobileAds;
import com.unity3d.ads.metadata.MetaData;

public class MainActivity extends AppCompatActivity {


    //To olo "press back again to leave"
    boolean doubleBackToExitPressedOnce = false;

    //pfk = plithos free katigorion
    private int pfk = 6;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Πατήστε ξανά για έξοδο απο την εφαρμογή", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //GDPR gia unity ads
        MetaData gdprMetaData = new MetaData(this);
        gdprMetaData.set("gdpr.consent", true);
        gdprMetaData.commit();

        MobileAds.initialize(getApplicationContext(), "ca-app-pub-5861682469694178~7665455042");


        if(getSharedPreferences("EPILOGES", 0).getBoolean("protiFora",true)) {
            SharedPreferences.Editor defaultAgores = getSharedPreferences("AGORES", 0).edit();
            SharedPreferences.Editor defaultEpiloges = getSharedPreferences("EPILOGES", 0).edit();

            defaultEpiloges.putBoolean("protiFora", false);
            defaultEpiloges.apply();

            String[] katigories = getResources().getStringArray(R.array.categories);
            for (int i = 0; i < pfk; i++) {
                defaultAgores.putBoolean(katigories[i], true);
                defaultAgores.apply();
                defaultEpiloges.putBoolean(katigories[i], true);
                defaultEpiloges.apply();
            }

        }

        Button starter = (Button) findViewById(R.id.starter);
        Button guidelines = (Button) findViewById(R.id.guidelines);
        Button shop = (Button) findViewById(R.id.shop);
        Button promotion = (Button) findViewById(R.id.promotion);


        starter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                startActivity(new Intent(getApplicationContext(), ModePicker.class));
            }
        });

        guidelines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                startActivity(new Intent(getApplicationContext(), Guide.class));
            }
        });

        shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                startActivity(new Intent(getApplicationContext(), Shop.class));
            }
        });

        promotion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                startActivity(new Intent(getApplicationContext(), Promotion.class));
            }
        });

    }

    @Override
    protected void onStart() {
        SharedPreferences agorasmena = getSharedPreferences("AGORES", 0);

        String[] katigories = getResources().getStringArray(R.array.categories);

        Boolean colorFlag = false;
        for (int i = 0; i < katigories.length - pfk; i++) {
            final String kat = katigories[i+pfk];
            if(!(agorasmena.getBoolean(kat,false))) {
                colorFlag = true;
            }
        }


        super.onStart();
    }

}
