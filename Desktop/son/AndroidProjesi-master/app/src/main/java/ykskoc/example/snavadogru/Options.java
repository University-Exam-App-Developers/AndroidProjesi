package ykskoc.example.snavadogru;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.bumptech.glide.Glide;
import ykskoc.example.snavadogru.Camera.CozemediginSorular;
import ykskoc.example.snavadogru.DenemeTakip.DenemeTakip;
import ykskoc.example.snavadogru.Kendin.kendiniDene;
import ykskoc.example.snavadogru.KonuTakip.Konu_takip;

import com.ykskoc.R;

import ykskoc.example.snavadogru.SikSorulanSoru.SSS;
import ykskoc.example.snavadogru.SoruTakip.SoruTakip;
import ykskoc.example.snavadogru.puanHesapla.PUAN;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import java.util.Locale;


public class Options extends AppCompatActivity implements View.OnClickListener{
    Button darkmode,kendinCalis,cozulemeyenSorular,konuTakip,soruTakip,denemeTakip,puan,rehberlik,sss;
    TextView kendinCalisText,cozulemeyenSorularText,konuTakipText,soruTakipText,denemeTakipText,puanText,rehberlikText,sssText;
    boolean darkModeBoolean=false;
    ImageView mainPhoto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        loadDark();
        if (darkModeBoolean){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            setTheme(R.style.AppTheme_DarkMode);}
        else
            setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);

        Locale locale = new Locale("Tr");
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        setContentView(R.layout.main_2);
        darkmode = findViewById(R.id.darkmodeButton);
        kendinCalis=findViewById(R.id.KC);
        soruTakip=findViewById(R.id.soru);
        denemeTakip = findViewById(R.id.deneme);
        konuTakip = findViewById(R.id.konu);
        cozulemeyenSorular = findViewById(R.id.CS);
        puan = findViewById(R.id.puan);
        mainPhoto=findViewById(R.id.imageView);
        rehberlik = findViewById(R.id.rehber);
        sss = findViewById(R.id.SSS);
        Glide.with(this).load(R.mipmap.main12).into(mainPhoto);

        kendinCalisText=findViewById(R.id.kcText);
        soruTakipText=findViewById(R.id.soruTakipText);
        denemeTakipText = findViewById(R.id.denemeTakipText);
        konuTakipText = findViewById(R.id.konuTakipText);
        cozulemeyenSorularText = findViewById(R.id.CS_text);
        puanText = findViewById(R.id.puanText);
        rehberlikText = findViewById(R.id.rehberText);
        sssText = findViewById(R.id.sssText);

        didStartButton(kendinCalis,kendinCalisText);
        didStartButton(soruTakip,soruTakipText);
        didStartButton(denemeTakip,denemeTakipText);
        didStartButton(konuTakip,konuTakipText);
        didStartButton(cozulemeyenSorular,cozulemeyenSorularText);
        didStartButton(puan,puanText);
        didStartButton(rehberlik,rehberlikText);
        didStartButton(sss,sssText);

        kendinCalis.setOnClickListener(this);
        soruTakip.setOnClickListener(this);
        denemeTakip.setOnClickListener(this);
        konuTakip.setOnClickListener(this);
        cozulemeyenSorular.setOnClickListener(this);
        puan.setOnClickListener(this);
        rehberlik.setOnClickListener(this);
        sss.setOnClickListener(this);

        //      Log.d("onCreate", "loadDark BEFORE");

        if (darkModeBoolean)
            darkmode.setBackground(getResources().getDrawable(R.drawable.darkmode_night3));
        else
            darkmode.setBackground(getResources().getDrawable(R.drawable.darkmode_sun2));
        darkmode.setOnClickListener(v -> {
            if (!darkModeBoolean) {
                //        Log.d("darkModeBoolean", "darkmode true");
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                darkModeBoolean = true;
                darkmode.setBackground(getResources().getDrawable(R.drawable.darkmode_night3));
                restartApp();
            } else {
                //     Log.d("darkModeBoolean", "darkmode false");
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                darkModeBoolean = false;
                darkmode.setBackground(getResources().getDrawable(R.drawable.darkmode_sun2));
                restartApp();
            }
            saveDark();
        });
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
    @Override
    public void onClick(View v) {
     /*   new Runnable(){
            long first=0;
            boolean runnable=true;
            @Override
            public void run() {
                if (!runnable)
                    return;
                first=System.currentTimeMillis();
          */          switch (v.getId()){
            case R.id.CS: didTapButton(v,R.id.CS); break;
            case R.id.konu: didTapButton(v,R.id.konu); break;
            case R.id.soru: didTapButton(v,R.id.soru); break;
            case R.id.deneme: didTapButton(v,R.id.deneme); break;
            case R.id.puan: didTapButton(v,R.id.puan); break;
            case R.id.rehber: didTapButton(v,R.id.rehber); break;
            case R.id.KC: didTapButton(v,R.id.KC); break;
            case R.id.SSS: didTapButton(v,R.id.SSS); break;
        }
       /*         runnable=false;
            }
        }.run();*/
/*
        try {
            Thread.sleep(700);
       //     Log.d("Finish", "700: .");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }   */
        switch (v.getId()){
            case R.id.CS:
                Intent camera_activity = new Intent(Options.this, CozemediginSorular.class);
                startActivity(camera_activity);
                break;
            case R.id.konu:
                Intent konu_takip = new Intent(Options.this, Konu_takip.class);
                reklamgoster();
                startActivity(konu_takip);
                break;
            case R.id.soru: //sıkıntı var
                Intent soru_takip = new Intent(Options.this, SoruTakip.class);
                startActivity(soru_takip);

                break;
            case R.id.deneme: //sıkıntı var
                didTapButton(v,R.id.deneme);
                Intent deneme_takip = new Intent(Options.this, DenemeTakip.class);
                startActivity(deneme_takip);

                break;
            case R.id.puan:
                Intent puan = new Intent(Options.this, PUAN.class);
                startActivity(puan);

                break;
            case R.id.rehber:
                Intent Rehberlik = new Intent(Options.this, REHBERLİK.class);
                startActivity(Rehberlik);
                break;
            case R.id.KC:
                Intent kendini_dene = new Intent(Options.this, kendiniDene.class);
                startActivity(kendini_dene);
                break;
            case R.id.SSS:
                Intent Sss = new Intent(Options.this, SSS.class);
                startActivity(Sss);
                reklamgoster();
                break;
        }
    }
    public void saveDark(){
        SharedPreferences sharedPreferences = this.getSharedPreferences("darkMode2",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("darkModeBoolean",darkModeBoolean);
        editor.apply();
    }
    public void loadDark(){
        SharedPreferences sharedPreferences = this.getSharedPreferences("darkMode2",MODE_PRIVATE);
        darkModeBoolean = sharedPreferences.getBoolean("darkModeBoolean", false);
    }
    public void restartApp(){
        //    Log.d("restartApp",""+darkModeBoolean);
        Intent i= new Intent(getApplicationContext(),Options.class);
        startActivity(i);
        finish();
    }
    public void didTapButton(View view, int recourceID) {
        Button button = view.findViewById(recourceID);
        Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce_button);
        button.startAnimation(myAnim);
    }
    public void didStartButton(Button button,TextView textView) {
        Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.appearance_slide);
        button.startAnimation(myAnim);
        textView.startAnimation(myAnim);
    }
}