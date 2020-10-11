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

public class tytKonuSosyal extends AppCompatActivity implements takeanote.dialog{
    RecyclerView recyclerView ;
    KonuTakipArrayAdapt arrayAdapter;
    String[] konular={"Tarih Bilimine Giriş" , "İlk Çağ Medeniyetleri" , "İslamiyet Öncesi Türk Devletleri" , "İslam Tarihi ve Uygarlığı" , "Türk-İslam Devletleri" , "Türkiye Tarihi" , "Beylikten Devlete" ,"Dünya Gücü Osmanlı Devleti" , "Arayış Yılları" , "Yeniçağ Avrupası" , "18. Yüzyılda Değişim ve Diplomasi" , "Yakınçağ Avrupası" , "En Uzun Yüzyıl" , "Osmanlı Kültür ve Medeniyeti" , "1881’den 1919’a Mustafa Kemal" , "20. Yüzyıl Başlarında Dünya" , "I. Dünya Savaşı" , "I. TBMM Dönemi" , "Kurtuluş Savaşında Cepheler" , "Türk İnkılabı" , "Atatürkçülük ve Atatürk İlkeleri" , "Atatürk Dönemi Türk Dış Politikası","Doğa ve İnsan" , "Dünya’nın Şekil ve Hareketleri" , "Coğrafi Konum" , "Harita Bilgisi" , "Atmosfer ve İklim" , "Sıcaklık" , "Basınç ve Rüzgarlar" , "Nemlilik ve Yağış" , "İklim Tipleri ve Bitki Örtüsü" , "Türkiye’nin İklimi" , "Yerin Şekillenmesi" , "İç Kuvvetler" , "Dış Kuvvetler" , "Su Kaynakları" , "Topraklar" , "Bitkiler" , "Göç" , "Yerleşmeler" , "Bölgeler ve Ülkeler" , "Doğal Afetle","İnanç" , "İbadet" , "Ahlâk ve Değerler" , "Din, Kültür ve Medeniyet" , "Hz. Muhammed (SAV.)" , "Vahiy ve Akıl" , "İslam ve Bilim / Anadolu’da İslam" , "Kur’an’da Bazı Kavramlar" , "İslam Düşüncesinde Tasavvufi Yorumlar" , "Güncel Dini Meseleler" , "Dinler","Felsefeyi Tanıma" , "Felsefe ile Düşünme" , "Varlık Felsefesi" , "Bilim Felsefesi" , "Din Felsefesi" , "Siyaset Felsefesi" , "Sanat Felsefesi"};
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_YES)
            setTheme(R.style.AppTheme_DarkMode);
        else
            setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konu_takipview);

        recyclerView = findViewById(R.id.konuTakip_recyclerView);
        arrayAdapter = new KonuTakipArrayAdapt(this,konular,getSupportFragmentManager(),1);
        recyclerView.setAdapter(arrayAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void set() {arrayAdapter.set();}
}
