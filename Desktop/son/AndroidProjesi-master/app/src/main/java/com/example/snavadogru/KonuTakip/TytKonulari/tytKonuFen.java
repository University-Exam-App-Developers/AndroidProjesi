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

public class tytKonuFen extends AppCompatActivity implements takeanote.dialog{
    RecyclerView recyclerView ;
    KonuTakipArrayAdapt arrayAdapter;
    String[] konular={"Elektrostatik" , "Mıknatıs" , "Elektrik Akımı" , "İş ve Enerji" , "Kütle Merkezi" , "Dinamik" , "Doğrusal Hareket – Bağıl Hareket" , "Sıvıların Kaldırma İlkesi" , "Basit Makine" ,"Basınç" , "Ses" , "Optik" ,"Isı Ve Sıcaklık" , "Madde Ve Özellikleri" , "Kuvvetler" ,"Fiziğin Doğası","Kimya Bilimi" , "Atom ve Periyodik Sistem" , "Kimyasal Türler Arası Etkileşimler" , "Maddenin Hâlleri" , "Doğa ve Kimya" , "Kimyanın Temel Kanunları" , "Mol Kavramı" , "Kimyasal Tepkime Denklemleri" , "Kimyasal Hesaplamalar" , "Karışımlar" , "Karışımların Ayrılması" , "Asitler, Bazlar, Tuzlar" , "Kimya Her Yerde","Biyoloji Bilimi" , "Temel Bileşenler" , "Hücrenin Yapısı" , "Hücre Zarından Madde Alışverişi" , "Sitoplazma ve Çekirdek" , "Canlıların Sınıfl andırılması" , "Hücre Bölünmeleri" , "Üreme Çeşitleri" , "Kalıtım" , "Ekosistem Ekolojisi"};
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_YES)
            setTheme(R.style.AppTheme_DarkMode);
        else
            setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konu_takipview);

        recyclerView = findViewById(R.id.konuTakip_recyclerView);
        arrayAdapter = new KonuTakipArrayAdapt(this,konular,getSupportFragmentManager(),3);
        recyclerView.setAdapter(arrayAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void set() {arrayAdapter.set();}
}
