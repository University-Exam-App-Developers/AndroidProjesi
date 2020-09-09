package com.example.snavadogru;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.snavadogru.Camera.NewCamera;
import com.example.snavadogru.DenemeTakip.DenemeTakip;
import com.example.snavadogru.Kendin.kendiniDene;
import com.example.snavadogru.KonuTakip.Konu_takip;
import com.example.snavadogru.SoruTakip.SoruTakip;
import com.example.snavadogru.puanHesapla.PUAN;
import com.hitomi.cmlibrary.CircleMenu;
import com.hitomi.cmlibrary.OnMenuSelectedListener;

public class Options extends AppCompatActivity {
    CircleMenu circleMenu;
    String menu_secenekler[] = {
            "Çözülemeyen Soru",
            "Konu Takip",
            "Soru Takip",
            "Deneme Takip",
            "Puan",
            "Rehberlik",
            "Kendini Dene",
            "SSS"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        circleMenu = findViewById(R.id.options_circle_menu);
        circleMenu.setMainMenu(Color.parseColor("#CDCDCD"), R.drawable.ic_ekle, R.drawable.ic_sil)
                .addSubMenu(Color.parseColor("#FFCE00"), R.drawable.photo)
                .addSubMenu(Color.parseColor("#017296"), R.drawable.test2)
                .addSubMenu(Color.parseColor("#3498DB"), R.drawable.notepad)
                .addSubMenu(Color.parseColor("#FF1A56"), R.drawable.line_chart)
                .addSubMenu(Color.parseColor("#FF127F"), R.drawable.mathematics2)
                .addSubMenu(Color.parseColor("#FC435F"), R.drawable.news)
                .addSubMenu(Color.parseColor("#8E44AD"), R.drawable.work)
                .addSubMenu(Color.parseColor("#FFD460"), R.drawable.question)
                .setOnMenuSelectedListener(index -> {

                    Toast.makeText(Options.this, menu_secenekler[index], Toast.LENGTH_SHORT).show();
                    switch (index) {
                            case 0:
                                Intent camera_activity = new Intent(Options.this, NewCamera.class);
                                startActivity(camera_activity);

                            break;
                            case 1:
                                Intent konu_takip = new Intent(Options.this, Konu_takip.class);
                                startActivity(konu_takip);

                            break;
                            case 2: //sıkıntı var
                                Intent soru_takip = new Intent(Options.this, SoruTakip.class);
                                startActivity(soru_takip);

                            break;
                            case 3: //sıkıntı var
                                Intent deneme_takip = new Intent(Options.this, DenemeTakip.class);
                                startActivity(deneme_takip);

                            break;
                            case 4:
                                Intent puan = new Intent(Options.this, PUAN.class);
                                startActivity(puan);

                            break;
                            case 5:
                                Intent Rehberlik = new Intent(Options.this,REHBERLİK.class);
                                startActivity(Rehberlik);
                            break;
                            case 6:
                                Intent kendini_dene = new Intent(Options.this, kendiniDene.class);
                                startActivity(kendini_dene);
                            break;
                            case 7:
                                Intent Sss = new Intent(Options.this,SSS.class);
                                startActivity(Sss);
                            break;

                    }
                });


    }


}
