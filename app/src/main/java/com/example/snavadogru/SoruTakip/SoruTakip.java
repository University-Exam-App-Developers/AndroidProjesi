package com.example.snavadogru.SoruTakip;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.snavadogru.R;
import com.example.snavadogru.SoruTakip.main.Fragment_soru_takip_analiz;
import com.example.snavadogru.SoruTakip.main.Fragment_soru_takip_ayt;
import com.example.snavadogru.SoruTakip.main.Fragment_soru_takip_tyt;
import com.example.snavadogru.SoruTakip.main.SectionsPagerAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.Calendar;

public class SoruTakip extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private int dayNumb=0,monthNumb=0;
    private boolean changedDay=false,changedMonth=false;
    private SectionsPagerAdapter adapter;
    Calendar calendar;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_takip_sorutakip);
        tabLayout=findViewById(R.id.tablayout);
        viewPager=findViewById(R.id.vPager);
        laodData();
        calendar = Calendar.getInstance();
        adapter=new SectionsPagerAdapter(getSupportFragmentManager());
        if (dayNumb!=calendar.get(Calendar.DAY_OF_WEEK))
            changedDay=true;
        if (monthNumb!=calendar.get(Calendar.MONTH))
            changedMonth=true;
        dayNumb=calendar.get(Calendar.DAY_OF_WEEK);
        monthNumb=calendar.get(Calendar.MONTH);
        saveData();
        adapter.addFragment(new Fragment_soru_takip_tyt(changedDay),"TYT");
        adapter.addFragment(new Fragment_soru_takip_ayt(changedMonth),"AYT");
        adapter.addFragment(new Fragment_soru_takip_analiz(calendar.get(Calendar.DAY_OF_WEEK)-1,calendar.get(Calendar.MONTH)-1),"ANALİZ");
        Log.d("Message","FRAGMENT OLUŞTURULDU");
//        adapter.addFragment(new deneme(),"Benlik ANALİZ");


        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }
    public void saveData(){
        SharedPreferences sharedPreferences= getSharedPreferences("soruTakipLastTimeSave",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("lastDay",dayNumb);
        editor.putInt("lastMonth",monthNumb);
        editor.apply();
    }
    public void laodData(){
        SharedPreferences sharedPreferences= getSharedPreferences("soruTakipLastTimeSave",MODE_PRIVATE);
        dayNumb=sharedPreferences.getInt("lastDay",0);
        monthNumb=sharedPreferences.getInt("lastMonthı",0);
    }
}
