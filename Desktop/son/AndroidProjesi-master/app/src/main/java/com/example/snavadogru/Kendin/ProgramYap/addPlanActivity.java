package com.example.snavadogru.Kendin.ProgramYap;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.snavadogru.Kendin.SqLite.MyDataBaseHelper;
import com.example.snavadogru.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class addPlanActivity extends AppCompatActivity {
    EditText getGorev,column2SoruSayisi;
    FloatingActionButton addGorev,showDayFromAdding;
    TextView dayInfo;
    String column1String="",column2String="",column3String="";
    Button konuCalismasi,soruCozulecek,denemeCozulecek;
    Spinner column1Spinner,column2Spinner,column3Spinner;
    MyDataBaseHelper dataBaseHelper;
    Calendar day;
    Bundle extras;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_YES)
            setTheme(R.style.AppTheme_DarkMode);
        else
            setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.makeplan_today);
        getGorev=findViewById(R.id.GörevEditText);
        addGorev=findViewById(R.id.addPlan);
        column2SoruSayisi=findViewById(R.id.soruSayisi);
        column1Spinner=findViewById(R.id.items);
        column2Spinner=findViewById(R.id.items2);
        column3Spinner=findViewById(R.id.items3);
        konuCalismasi=findViewById(R.id.konuCalisilacak);
        soruCozulecek=findViewById(R.id.soruCozulecek);
        denemeCozulecek=findViewById(R.id.denemeCozulecek);
        showDayFromAdding=findViewById(R.id.showDayFromAdding);
        dayInfo=findViewById(R.id.day_info);
        dataBaseHelper= new MyDataBaseHelper(addPlanActivity.this);
        day=Calendar.getInstance();
        extras=getIntent().getExtras();

    //    loadTemp();
        assert extras != null;
        day.setTimeInMillis(extras.getLong("today"));
        dayInfo.setText(day.get(Calendar.DAY_OF_MONTH)+"."+day.get(Calendar.MONTH)+"."+day.get(Calendar.YEAR));
        Log.d("DAY  in add","DAY_OF_WEEK "+day.get(Calendar.DAY_OF_WEEK));
        String[] konuCalis={"TYT Türkçe","TYT Matematik","TYT Fizik","TYT Kimya","TYT Biyoloji","TYT Tarih","TYT Coğrafya","TYT Felsefe",
        "AYT Matematik","AYT Fizik","AYT Kimya","AYT Biyoloji","AYT Edebiyat","AYT Fizik","AYT Coğrafya","AYT Felsefe","AYT Din Kültürü","AYT Yabancı Dil"};

        ArrayList<String> konuArray = new ArrayList<>(Arrays.asList(konuCalis));
        ArrayAdapter<String> adapterKonu = new ArrayAdapter<>(this,R.layout.spinner_item,konuArray);
        column1Spinner.setAdapter(adapterKonu);
        column2Spinner.setAdapter(adapterKonu);

        String[] denemeCoz={"TYT","AYT","TYT Matematik Alan","TYT Fen Bilimleri Alan","TYT Sosyal Bilimler Alan"
                ,"AYT Matematik Alan","AYT Edebiyat Alan","AYT Tarih Alan","AYT Coğrafya Alan","AYT Felsefe Alan","AYT Yabancı Dil Alan"};

        ArrayList<String> denemeArray = new ArrayList<>(Arrays.asList(denemeCoz));
        ArrayAdapter<String> adapterDeneme = new ArrayAdapter<>(this,R.layout.spinner_item,denemeArray);
        column3Spinner.setAdapter(adapterDeneme);

        column1Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                column1String=konuCalis[position]+"";
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        column2Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                column2String=konuCalis[position]+" ";
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        column3Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                column3String=denemeCoz[position]+"";
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        konuCalismasi.setOnClickListener(v-> getGorev.setText(column1String+" Konu Çalışılacak"));
        soruCozulecek.setOnClickListener(v-> getGorev.setText(column2String+column2SoruSayisi.getText()+" Soru Çözülecek"));
        denemeCozulecek.setOnClickListener(v-> getGorev.setText(column3String+" Deneme Çözülecek"));
        addGorev.setOnClickListener(v-> {
            int numbOfDayGorevler= getNumbOfDayGorevler();
            if (getGorev.getText().toString().equals(""))
                getGorev.setError("Lütfen Görev Metni Giriniz");
            else if (numbOfDayGorevler>10)
                Toast.makeText(this,"Günlük Görev Sayısı 10'u geçemez",Toast.LENGTH_SHORT).show();
            else{
                dataBaseHelper.addGorev(day.get(Calendar.DAY_OF_WEEK),day.get(Calendar.WEEK_OF_YEAR),numbOfDayGorevler,getGorev.getText().toString().trim(),0);
                Toast.makeText(this,"Görev Eklendi",Toast.LENGTH_SHORT).show();
                getGorev.setText("");
            }
        Log.d("numbOfDay",""+numbOfDayGorevler);
            if (numbOfDayGorevler == 2 || (numbOfDayGorevler+1)%6==0)
            reklamgoster();
        });
        showDayFromAdding.setOnClickListener(v->{
            Intent i = new Intent(addPlanActivity.this,showDay.class);
            i.putExtra("today",day.getTimeInMillis());
            startActivity(i);
        });
    }
    public int getNumbOfDayGorevler(){
        Cursor cursor = dataBaseHelper.readDayData(day.get(Calendar.DAY_OF_WEEK),day.get(Calendar.WEEK_OF_YEAR));
        if (cursor==null)
            return 0;
        return dataBaseHelper.readDayData(day.get(Calendar.DAY_OF_WEEK),day.get(Calendar.WEEK_OF_YEAR)).getCount();
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
