package com.example.snavadogru.KonuTakip.TytKonulari;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.snavadogru.KonuTakip.KonuTakipArrayAdapt;
import com.example.snavadogru.KonuTakip.takeanote;
import com.example.snavadogru.R;

public class tytKonuTurkce extends AppCompatActivity implements takeanote.dialog{
    RecyclerView recyclerView ;
    KonuTakipArrayAdapt arrayAdapter;
    String[] konular={"Sözcükte Anlam" , "Cümlede Anlam" , "Paragrafta Anlam" , "Ses Bilgisi" , "Yazım Kuralları" , "Noktalama İşaretleri" , "Sözcükte Yapı" , "İsimler (Adlar)" , "Sıfatlar (Ön Adlar)" , "Zamirler (Adıllar)" , "Zarflar (Belirteç)" , "Edat, Bağlaç, Ünlem" , "Fiiler (Eylemler)" , "Ek Fiil" , "Fiilimsi" , "Fiilde Çatı" , "Cümle Ögeleri" , "Cümle Türleri", "Anlatım Bozuklukları"};
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_YES)
            setTheme(R.style.AppTheme_DarkMode);
        else
            setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konu_takipview);

        recyclerView = findViewById(R.id.konuTakip_recyclerView);
        arrayAdapter = new KonuTakipArrayAdapt(this,konular,getSupportFragmentManager(),0);
        recyclerView.setAdapter(arrayAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void set() {arrayAdapter.set();}

}
