package com.example.snavadogru;

import android.content.Intent;
import android.graphics.Color;
import android.icu.text.CaseMap;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.snavadogru.Camera.NewCamera;
import com.example.snavadogru.DenemeTakip.DenemeTakip;
import com.example.snavadogru.Kendin.kendiniDene;
import com.example.snavadogru.KonuTakip.Konu_takip;
import com.example.snavadogru.SikSorulanSoru.SSS;
import com.example.snavadogru.SoruTakip.SoruTakip;
import com.example.snavadogru.puanHesapla.PUAN;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.hitomi.cmlibrary.CircleMenu;

public class Options extends AppCompatActivity {
    CircleMenu circleMenu;
    String menu_secenekler[] = {
            "Çözülemeyen Soru",
            "Konu Takip",
            "Soru Takip",
            "Deneme Takip",
            "Puan",
            "Rehberlik",
            "Kendini Dene",
            "SSS"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        circleMenu = findViewById(R.id.options_circle_menu);
        circleMenu.setMainMenu(Color.parseColor("#CDCDCD"), R.drawable.ic_ekle, R.drawable.ic_sil)
                .addSubMenu(Color.parseColor("#FFCE00"), R.drawable.photo)
                .addSubMenu(Color.parseColor("#017296"), R.drawable.test2)
                .addSubMenu(Color.parseColor("#3498DB"), R.drawable.notepad)
                .addSubMenu(Color.parseColor("#FF1A56"), R.drawable.line_chart)
                .addSubMenu(Color.parseColor("#FF127F"), R.drawable.mathematics2)
                .addSubMenu(Color.parseColor("#FC435F"), R.drawable.news)
                .addSubMenu(Color.parseColor("#8E44AD"), R.drawable.work)
                .addSubMenu(Color.parseColor("#FFD460"), R.drawable.question)
                .setOnMenuSelectedListener(index -> {

                    Toast.makeText(Options.this, menu_secenekler[index], Toast.LENGTH_SHORT).show();
                    switch (index) {
                            case 0:
                                Intent camera_activity = new Intent(Options.this, NewCamera.class);
                                startActivity(camera_activity);
                                reklamgoster();
                                break;
                            case 1:
                                Intent konu_takip = new Intent(Options.this, Konu_takip.class);
                                startActivity(konu_takip);
                              reklamgoster();
                            break;
                            case 2: //sıkıntı var
                                //vargit
                                Intent soru_takip = new Intent(Options.this, SoruTakip.class);
                                startActivity(soru_takip);
                                reklamgoster();

                            break;
                            case 3: //sıkıntı var
                                Intent deneme_takip = new Intent(Options.this, DenemeTakip.class);
                                startActivity(deneme_takip);
                                reklamgoster();

                            break;
                            case 4:
                                Intent puan = new Intent(Options.this, PUAN.class);
                                startActivity(puan);
                               reklamgoster();

                            break;
                            case 5:
                                Intent Rehberlik = new Intent(Options.this,REHBERLİK.class);
                                startActivity(Rehberlik);
                                reklamgoster();
                            break;
                            case 6:
                                Intent kendini_dene = new Intent(Options.this, kendiniDene.class);
                                startActivity(kendini_dene);
                                reklamgoster();
                            break;
                            case 7:
                                Intent Sss = new Intent(Options.this, SSS.class);
                                startActivity(Sss);
                               reklamgoster();
                            break;

                    }
                });


    }
    public void reklamgoster(){
        InterstitialAd mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712"); //test için olan id değiştirilecek unutma
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
