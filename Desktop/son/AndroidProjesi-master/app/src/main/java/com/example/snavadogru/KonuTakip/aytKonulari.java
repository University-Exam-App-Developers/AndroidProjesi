package com.example.snavadogru.KonuTakip;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.snavadogru.R;

public class aytKonulari extends AppCompatActivity implements takeanote.dialog {
    int ID;
    Bundle extras;
    RecyclerView recyclerView;
    KonuTakipArrayAdapt arrayAdapter;
    String[] konularMat = {"Fonksiyonlar" , "Polinomlar" , "2. Dereceden Denklemler" , "Karmaşık sayı" , "Parabol" , "Eşitsizlikler" , "Permütasyon – Kombinasyon" , "Binom ve Olasılık" , "Logaritma" , "Diziler" , "Trigonometri" , "Limit ve Süreklilik" , "Türev Alma Kuralları" , "Türev Uygulamaları" , "Belirsiz İntegral" , "Belirli İntegral" , "İntegralle Alan Hesabı","Doğruda Açılar" , "Üçgende Açılar" , "Açı-Kenar Bağıntıları" , "Eşlik – Benzerlik" , "Açıortay – Kenarortay" , "İkizkenar-Eşkenar Üçgen" , "Dik Üçgen" , "Üçgende Alan" , "Çokgenler" , "Yamuk" , "Paralelkenar" , "Eşkenar Dörtgen" , "Dikdörtgen", "Kare – Deltoid" , "Noktanın Analitiği ve Doğrunun Analitiği" , "Dönüşüm Geometrisi" , "Çemberde Açı ve Uzunluk" , "Dairenin Çevresi ve Alanı" , "Dik Prizma" , "Dik Piramit" , "Silindir-Koni-Küre" , "Çemberin Analitiği"};
    String[] konularFiz = {"Kuantum Sayıları\n" , "Elektron Dizilimi\n" , "Periyodik Özellik\n" , "Gazlar\n" , "Çözeltilerde Derişim\n" , "Koligatif Özellikler\n" , "Çözünürlük\n" , "Kimyasal Tepkimelerde Enerji\n" , "Kimyasal Tepkimelerde Hız\n" , "Kimyasal Denge\n" , "Asit-Baz Dengesi\n" , "Çözünürlük Dengesi (KÇÇ)\n" , "Redoks\n" , "Piller\n" , "Elektroliz\n" , "Karbon Kimyasına Giriş\n" , "Hidrokarbonlar\n", "Alkoller ve Eterler\n" , "Karbonil Bileşikleri\n" , "Karboksilik Asitler ve Esterler\n" , "Enerji Kaynakları ve Bilimsel Gelişmeler"};
    String[] konularKim = {"Kuantum Sayıları\n" , "Elektron Dizilimi\n" , "Periyodik Özellik\n" , "Gazlar\n" , "Çözeltilerde Derişim\n" , "Koligatif Özellikler\n" , "Çözünürlük\n" , "Kimyasal Tepkimelerde Enerji\n" , "Kimyasal Tepkimelerde Hız\n" , "Kimyasal Denge\n" , "Asit-Baz Dengesi\n" , "Çözünürlük Dengesi (KÇÇ)\n" , "Redoks\n" , "Piller\n" , "Elektroliz\n" , "Karbon Kimyasına Giriş\n" , "Hidrokarbonlar\n" , "Alkoller ve Eterler\n" , "Karbonil Bileşikleri\n" , "Karboksilik Asitler ve Esterler\n" , "Enerji Kaynakları ve Bilimsel Gelişmeler"};
    String[] konularBio = {"Sinir Sistemi\n" , "Endokrin Sistem\n" , "Duyu Organları\n" , "Destek ve Hareket Sistemi\n" , "Sindirim Sistemi\n" , "Dolaşım ve Bağışıklık Sistemi\n" , "Solunum Sistemi\n" , "Boşaltım Sistemi\n" , "Üreme Sistemi ve Embriyonik Gelişim\n" , "Komünite ve Popülasyon Ekolojisi\n" , "Genetik Şifre-Protein Sentezi\n" , "Fotosentez – Kemosentez\n" , "Hücresel Solunum\n" , "Bitki Biyolojisi\n" , "Canlılar ve Çevre"};
    String[] konularEdeb = {"Anlam Bilgisi (Sözcükte, Cümlede ve Paragrafta Anlam)\n" , "Edebî Bilgiler\n" , "Destan Dönemi (İslamiyet Öncesi) Türk Edebiyatı\n" , "İslami Devir Türk Edebiyatı (İlk Ürünler)\n" , "Divan Edebiyatı\n" , "Halk Edebiyatı\n" , "Edebî Akımlar\n" , "Tanzimat Edebiyatı\n" , "Servetifünun ve Fecriati Edebiyatı\n" , "Millî Edebiyat\n" , "Cumhuriyet Şiiri\n" , "Cumhuriyet Romanı\n" , "Cumhuriyet Dönemi\n" , "Dünya Edebiyatı\n" , "Çağdaş Türk Edebiyatı\n" , "Eser Özetleri"};
    String[] konularCog = {"Şehirlerin Fonksiyonları\n" , "Türkiye’de Nüfus ve Yerleşme\n" , "Ekonomik Faaliyetler\n" , "Türkiye’de Ekonomik Faaliyetler\n" , "Nüfus\n" , "Uluslararası Ulaşım Hatları\n" , "Bölgeler ve Ülkeler\n" , "Çevre ve İnsan\n" , "Doğal Afetler"};
    String[] konularTar = {"Atatürk’ün Ölümü" , "20. yy Başlarında Dünya" , "İkinci Dünya Savaşı" , "Soğuk Savaş Dönemi" , "Yumuşama Dönemi ve Sonrası" , "Küreselleşen Dünya" , "Türklerde Devlet Teşkilatı" , "Türklerde Toplum Yapısı" , "Türklerde Hukuk" , "Türklerde Ekonomi" , "Türklerde Eğitim – Bilim" , "Türklerde Sanat" , "Türklerde Spor"};
    String[] konularFel = {"Felsefe’nin Alanı\n" , "Bilgi Felsefesi\n" , "Bilim Felsefesi\n" , "Varlık Felsefesi\n" , "Ahlak Felsefesi\n" , "Siyaset Felsefesi\n" , "Sanat Felsefesi\n" , "Din Felsefesi"};
    String[] konularDin = {"Kuran ve Yorumu" , "Hz. Muhammed’in Hayatı" , "İslam Düşüncesinde Yorumlar" , "İslam Dinine Göre Kötü Alışkanlıklar" , "Hazreti Muhammed" , "İslam Düşüncesinde Tasavvuf" , "Vahiy ve Akıl Kur’an Yorumları"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_YES)
            setTheme(R.style.AppTheme_DarkMode);
        else
            setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konu_takipview);
        extras = getIntent().getExtras();
        ID = extras.getInt("dersCode");

        recyclerView = findViewById(R.id.konuTakip_recyclerView);
        switch (ID) {
            case 4:
                arrayAdapter = new KonuTakipArrayAdapt(this, konularMat, getSupportFragmentManager(), ID); break;
            case 5:
                arrayAdapter = new KonuTakipArrayAdapt(this, konularFiz, getSupportFragmentManager(), ID); break;
            case 6:
                arrayAdapter = new KonuTakipArrayAdapt(this, konularKim, getSupportFragmentManager(), ID); break;
            case 7:
                arrayAdapter = new KonuTakipArrayAdapt(this, konularBio, getSupportFragmentManager(), ID); break;
            case 8:
                arrayAdapter = new KonuTakipArrayAdapt(this, konularEdeb, getSupportFragmentManager(), ID); break;
            case 9:
                arrayAdapter = new KonuTakipArrayAdapt(this, konularCog, getSupportFragmentManager(), ID); break;
            case 10:
                arrayAdapter = new KonuTakipArrayAdapt(this, konularTar, getSupportFragmentManager(), ID); break;
            case 11:
                arrayAdapter = new KonuTakipArrayAdapt(this, konularFel, getSupportFragmentManager(), ID); break;
            case 12:
                arrayAdapter = new KonuTakipArrayAdapt(this, konularDin, getSupportFragmentManager(), ID); break;
        }
        recyclerView.setAdapter(arrayAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void set() {
        arrayAdapter.set();
    }
}