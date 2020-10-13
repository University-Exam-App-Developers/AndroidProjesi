package ykskoc.example.snavadogru.KonuTakip;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ykskoc.R;

public class aytKonuDil extends AppCompatActivity implements takeanote.dialog{
    RecyclerView recyclerView ;
    KonuTakipArrayAdapt arrayAdapter;
    String[] konular={"Kelime Bilgisi" , "Dilbilgisi" , "Cloze Test" , "Cümleyi Tamamlama" , "İNG/TR Cümlenin Karşılığını Bulma" , "Paragraf" , "Anlamca Yakın Cümleyi Bulma" , "Paragrafta Anlam Bütünlüğünü Sağlayacak Cümleyi Bulma" , "Verilen Durumda Söylenecek İfadeli Bulma" , "Diyalog Tamamlama" , "Anlam Bütünlüğünü Bozan Cümleyi Bulma"};
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_YES)
            setTheme(R.style.AppTheme_DarkMode);
        else
            setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konu_takipview);

        recyclerView = findViewById(R.id.konuTakip_recyclerView);
        arrayAdapter = new KonuTakipArrayAdapt(this,konular,getSupportFragmentManager(),13);
        recyclerView.setAdapter(arrayAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void set() {arrayAdapter.set();}
}
