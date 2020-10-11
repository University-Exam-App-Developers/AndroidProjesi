package com.example.snavadogru.KonuTakip;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;

import com.example.snavadogru.KonuTakip.TytKonulari.tytKonuFen;
import com.example.snavadogru.KonuTakip.TytKonulari.tytKonuMat;
import com.example.snavadogru.KonuTakip.TytKonulari.tytKonuSosyal;
import com.example.snavadogru.KonuTakip.TytKonulari.tytKonuTurkce;
import com.example.snavadogru.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.text.DecimalFormat;

public class Konu_takip extends AppCompatActivity implements View.OnClickListener {
    int[] counter= new int[14];
   // private AdView mAdView;
    CardView Turkce,Matematik,Fen,Sosyal,Sayisal,Sozel,Esitagirlik,Yabancidil;
    ProgressBar tytBar,sayBar,eaBar,sozBar,dilBar;
    TextView tytProp,sayProp,eaProp,sozProp,dilProp;
    double tytView,sayView,eaView,sozView,dilView;
    private DecimalFormat df = new DecimalFormat("0.00");
    final double tytKatsayi=170,sayKatsayi=96,sozKatsayi=53,eaKatsayi=77,dilKatsayi=11;
    Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_YES)
            setTheme(R.style.AppTheme_DarkMode);
        else
            setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_takip_konu);
        loadData();

       /* mAdView = findViewById(R.id.konu_takip_adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);*/
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.TYPE_STATUS_BAR);
        reklamgoster();
        Turkce = findViewById(R.id.Turkce);
        Matematik = findViewById(R.id.Mat);
        Fen = findViewById(R.id.Fen);
        Sosyal = findViewById(R.id.Sosyal);
        Sayisal = findViewById(R.id.sayisalButton);
        Sozel = findViewById(R.id.sozelButton);
        Esitagirlik = findViewById(R.id.esitAgirlikButton);
        Yabancidil = findViewById(R.id.dilButton);

        tytBar=findViewById(R.id.tytDoluluk);
        sayBar=findViewById(R.id.sayDoluluk);
        eaBar=findViewById(R.id.eaDoluluk);
        sozBar=findViewById(R.id.sozDoluluk);
        dilBar=findViewById(R.id.dilDoluluk);

        tytProp=findViewById(R.id.tytOran);
        sayProp=findViewById(R.id.sayOran);
        eaProp=findViewById(R.id.eaOran);
        sozProp=findViewById(R.id.sozOran);
        dilProp=findViewById(R.id.dilOran);

        Turkce.setOnClickListener(this);
        Matematik.setOnClickListener(this);
        Fen.setOnClickListener(this);
        Sosyal.setOnClickListener(this);
        Sayisal.setOnClickListener(this);
        Sozel.setOnClickListener(this);
        Esitagirlik.setOnClickListener(this);
        Yabancidil.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
        updateValues();

        tytProp.setText("%"+df.format(tytView));
        tytBar.setProgress((int) tytView);
        sayProp.setText("%"+df.format(sayView));
        sayBar.setProgress((int) sayView);
        eaProp.setText("%"+df.format(eaView));
        eaBar.setProgress((int) eaView);
        sozProp.setText("%"+df.format(sozView));
        sozBar.setProgress((int) sozView);
        dilProp.setText("%"+df.format(dilView));
        dilBar.setProgress((int) dilView);
    }
    public void updateValues(){
        tytView=((counter[0]+counter[1]+counter[2]+counter[3])/tytKatsayi)*100;
        sayView=((counter[4]+counter[5]+counter[6]+counter[7])/sayKatsayi)*100;
        eaView=((counter[4]+counter[8]+counter[9]+counter[10])/eaKatsayi)*100;
        sozView=((counter[8]+counter[9]+counter[10]+counter[11]+counter[12])/sozKatsayi)*100;
        dilView=(counter[13]/dilKatsayi)*100;
    }
    @Override
    public void onClick(View view) {
        if (view.getId()== R.id.Turkce) {
            i = new Intent(this, tytKonuTurkce.class);
            startActivity(i);
        }
        if (view.getId()== R.id.Mat) {
            i = new Intent(this, tytKonuMat.class);
            startActivity(i);
        }
        if (view.getId()== R.id.Sosyal) {
            i = new Intent(this, tytKonuSosyal.class);
            startActivity(i);
        }
        if (view.getId()== R.id.Fen) {
            i = new Intent(this, tytKonuFen.class);
            startActivity(i);
        }
        if (view.getId()== R.id.sayisalButton) {
            i = new Intent(this, aytKonuSay.class);
            startActivity(i);
        }
        if (view.getId()== R.id.sozelButton) {
            i = new Intent(this, aytKonuSoz.class);
            startActivity(i);
        }
        if (view.getId()== R.id.dilButton) {
            i = new Intent(this, aytKonuDil.class);
            startActivity(i);
        }
        if (view.getId()== R.id.esitAgirlikButton) {
            i = new Intent(this, aytKonuEa.class);
            startActivity(i);
        }
    }
    private void loadData() {
        SharedPreferences sPCounter= getSharedPreferences("progressProportionSave", Context.MODE_PRIVATE);

        Gson gson= new Gson();
        String count= sPCounter.getString("prop",null);
        counter=gson.fromJson(count, (Type) int[].class);
        if (counter==null)
            counter= new int[14];
    }
    public void reklamgoster(){
        InterstitialAd mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-7739380766735309/1906072524"); //test için olan id değiştirilecek unutma
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener(){
            @Override
            public void onAdLoaded() {
                mInterstitialAd.show();
                super.onAdLoaded();
            }
        });
    }
}