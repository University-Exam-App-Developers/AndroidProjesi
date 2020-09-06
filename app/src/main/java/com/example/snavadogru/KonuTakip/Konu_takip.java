package com.example.snavadogru.KonuTakip;
import com.example.snavadogru.KonuTakip.TytKonulari.*;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import com.example.snavadogru.R;
import com.google.gson.Gson;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.lang.reflect.Type;
import java.text.DecimalFormat;

public class Konu_takip extends AppCompatActivity implements View.OnClickListener {
    int[] counter= new int[14];
    Button Turkce,Matematik,Fen,Sosyal,Sayisal,Sozel,Esitagirlik,Yabancidil;
    ProgressBar tytBar,sayBar,eaBar,sozBar,dilBar;
    TextView tytProp,sayProp,eaProp,sozProp,dilProp;
    double tytView,sayView,eaView,sozView,dilView;
    private DecimalFormat df = new DecimalFormat("0.00");
    final double    tytKatsayi=170,sayKatsayi=96,sozKatsayi=53,eaKatsayi=77,dilKatsayi=11;
    Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_takip_konu);
        loadData();
        Turkce = findViewById(R.id.Turkce);
        Matematik = findViewById(R.id.Mat);
        Fen = findViewById(R.id.Fen);
        Sosyal = findViewById(R.id.Sosyal);
        Sayisal = findViewById(R.id.Sayisal);
        Sozel = findViewById(R.id.Sozel);
        Esitagirlik = findViewById(R.id.EsitAgirlik);
        Yabancidil = findViewById(R.id.YabanciDil);

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
        if (view.getId()==R.id.Turkce) {
            i = new Intent(this, tytKonuTurkce.class);
            startActivity(i);
        }
        if (view.getId()==R.id.Mat) {
            i = new Intent(this, tytKonuMat.class);
            startActivity(i);
        }
        if (view.getId()==R.id.Sosyal) {
            i = new Intent(this, tytKonuSosyal.class);
            startActivity(i);
        }
        if (view.getId()==R.id.Fen) {
            i = new Intent(this, tytKonuFen.class);
            startActivity(i);
        }
        if (view.getId()==R.id.Sayisal) {
            i = new Intent(this, aytKonuSay.class);
            startActivity(i);
        }
        if (view.getId()==R.id.Sozel) {
            i = new Intent(this, aytKonuSoz.class);
            startActivity(i);
        }
        if (view.getId()==R.id.YabanciDil) {
            i = new Intent(this, aytKonuDil.class);
            startActivity(i);
        }
        if (view.getId()==R.id.EsitAgirlik) {
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
}