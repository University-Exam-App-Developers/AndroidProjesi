package com.example.snavadogru.Kendin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.text.Html;
import android.util.Log;
import android.widget.LinearLayout;
import com.example.snavadogru.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.widget.TextView;
import android.widget.Button;

import java.lang.reflect.Type;

public class kendiniDene extends AppCompatActivity implements addingPopUp.dialog{
    Intent i;
    ViewPager viewPager;
    Button sureTakip,programYapButton;
    LinearLayout sliderDotsPanel;
    int today=1,thisMonth=1;
    ViewPagerAdapter viewPagerAdapter;
    TextView aylikTime,haftalikTime,gunlukTime;
    long [] gunlukGraphValues= new long[7];
    long [] aylikGraphValues = new long[12];
    TextView dots[] = new TextView[2];
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kendinidene);
        loadData();
        viewPager=findViewById(R.id.viewPager);
        sliderDotsPanel=findViewById(R.id.dotLayout);
        aylikTime=findViewById(R.id.aylikKayit);
        haftalikTime=findViewById(R.id.haftalikKayit);
        gunlukTime=findViewById(R.id.gunlukKayit);
        programYapButton=findViewById(R.id.programYap);
        sureTakip=findViewById(R.id.suretakip);

        viewPagerAdapter = new ViewPagerAdapter(this,getSupportFragmentManager(),aylikTime,haftalikTime,gunlukTime);

        viewPager.setAdapter(viewPagerAdapter);
        addDotsIndicator(0);

        viewPager.addOnPageChangeListener(viewListener);

        sureTakip.setOnClickListener(v -> {
            i= new Intent(kendiniDene.this,zamanTakip.class);
            i.putExtra("gunlukArray",gunlukGraphValues);
            i.putExtra("aylikArray",aylikGraphValues);
            i.putExtra("gunlukCount",today);
            i.putExtra("aylikCount",thisMonth);

            Log.d("KENDIKENDINE","g "+today);
            startActivity(i);
        });

        programYapButton.setOnClickListener(v -> {
            i= new Intent(kendiniDene.this,programYap.class);
            startActivity(i);
        });

    }
    public void addDotsIndicator(int position){
        for (int i = 0; i <2 ; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.dotWhite));
            sliderDotsPanel.addView(dots[i]);
        }
            dots[position].setTextColor(getResources().getColor(R.color.White));
    }
    ViewPager.OnPageChangeListener viewListener =new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            Log.d("position","pos"+position);
            if (position==0)
            {
                dots[1].setTextColor(getResources().getColor(R.color.dotWhite));
                dots[0].setTextColor(getResources().getColor(R.color.White));
            }
            else if (position==1)
            {
                dots[0].setTextColor(getResources().getColor(R.color.dotWhite));
                dots[1].setTextColor(getResources().getColor(R.color.White));
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private void loadData() {
        SharedPreferences spValues= getSharedPreferences("viewPagerAdapterValueSave", Context.MODE_PRIVATE);

        Type type = new TypeToken<long[]>() {}.getType();
        Gson gson = new Gson();
        String jsonGun = spValues.getString("gunluk_Array",null);
        String jsonAylik = spValues.getString("aylik_Array",null);

        gunlukGraphValues = gson.fromJson(jsonGun, type);
        aylikGraphValues = gson.fromJson(jsonAylik, type);

        if (gunlukGraphValues==null){
            gunlukGraphValues= new long[7];
            today=1;
        }
        else
            today=7;
        if (aylikGraphValues==null){
            aylikGraphValues= new long[12];
            thisMonth=1;
        }
        else
            thisMonth=12;

        SharedPreferences sP= getSharedPreferences("daySave", Context.MODE_PRIVATE);

        today=sP.getInt("day_Numb",0);
        thisMonth=sP.getInt("month_Numb",0);

        Log.d("loadData in MAIN"," "+today);
    }

    @Override
    public void set() {
        viewPagerAdapter.set();
    }
}
