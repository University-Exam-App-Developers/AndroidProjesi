package ykskoc.example.snavadogru.KonuTakip.TytKonulari;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ykskoc.example.snavadogru.KonuTakip.KonuTakipArrayAdapt;
import ykskoc.example.snavadogru.KonuTakip.takeanote;
import com.ykskoc.R;
public class tytKonuMat extends AppCompatActivity implements takeanote.dialog{
    RecyclerView recyclerView ;
    KonuTakipArrayAdapt arrayAdapter;
    String[] konular={"Sayı Kümeleri ve Çeşitleri" , "Bölme-Bölünebilme" , "Ebob-Ekok" , "Rasyonel Sayılar" , "Bir Bilinmeyenli Eşitsizlikler" , "Mutlak Değer" , "Üslü İfadeler" , "Köklü İfadeler" , "Çarpanlara Ayırma" , "Oran – Orantı" , "Denklem Çözme" , "Sayı – Kesir Problemleri" , "Yaş Problemleri" , "Hareket Problemleri" , "Karışım Problemleri" , "Yüzde-Faiz Problemleri" , "Tablo-Grafik Problemleri" , "Rutin Olmayan Problemler" , "Veri" , "Mantık" , "Kümeler" , "Fonksiyonlar" , "Polinomlar" , "2. Dereceden Denklemler" , "Karmaşık Sayılar" , "Permütasyon" , "Kombinasyon" , "Pascal Üçgeni ve Binom Açılımı" , "Olasılık", "Doğruda Açılar" , "Üçgende Açılar" , "Açı-Kenar Bağıntıları" , "Eşlik" , "Benzerlik" , "Açıortay" , "Kenarortay", "İkizkenar-Eşkenar Üçgen" , "Dik Üçgen" , "Üçgende Alan" , "Çokgenler" , "Dörtgenler" , "Yamuk" , "Paralelkenar" , "Eşkenar Dörtgen" , "Dikdörtgen" , "Kare" , "Deltoid" , "Noktanın ve Doğrunun Analitiği" , "Çemberde Açı ve Uzunluk" , "Dairenin Çevresi ve Alanı" , "Dik Prizma" , "Dik Piramit"};
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_YES)
            setTheme(R.style.AppTheme_DarkMode);
        else
            setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konu_takipview);

        recyclerView = findViewById(R.id.konuTakip_recyclerView);
        arrayAdapter = new KonuTakipArrayAdapt(this,konular,getSupportFragmentManager(),2);
        recyclerView.setAdapter(arrayAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void set() {arrayAdapter.set();}

}
