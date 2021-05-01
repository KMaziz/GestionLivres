package com.example.alouiomar.gestionlivres;


import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {


    Button livre , navigateur , maps ,  heure ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        livre = (Button) findViewById(R.id.livre);
        navigateur = (Button) findViewById(R.id.nav);
        maps = (Button) findViewById(R.id.map);
        heure = (Button) findViewById(R.id.heure);

        livre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplication() , LivresActivity.class);
                startActivity(i);

            }
        });

        navigateur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri site = Uri.parse("http://www.google.tn/");
                Intent I = new Intent(Intent.ACTION_VIEW, site);
                startActivity(I);

            }
        });

        maps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String lat="35.456";
                String lon="85.4879";
                Uri coordonnées = Uri.parse("geo:" + lat + "," + lon.toString());
                Intent I = new Intent(Intent.ACTION_VIEW, coordonnées);
                startActivity(I);
            }
        });

        heure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent I = new Intent(Settings.ACTION_DATE_SETTINGS);
                startActivity(I);

            }
        });



    }
}