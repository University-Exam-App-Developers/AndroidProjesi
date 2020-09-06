package com.example.snavadogru.KonuTakip;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;

import com.example.snavadogru.R;

public class aytKonuEa extends AppCompatActivity {
    Intent intent;
    Button edeb,tarih,cog,mat;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_takip_konu_ea);
        intent = new Intent(aytKonuEa.this,aytKonulari.class);

        edeb = findViewById(R.id.aytKonuEa_edebiyat);
        tarih = findViewById(R.id.aytKonuEa_tarih);
        cog = findViewById(R.id.aytKonuEa_cografya);
        mat = findViewById(R.id.aytKonuEa_matematik);

        edeb.setOnClickListener(view ->{
            intent.putExtra("dersCode",8);
            startActivity(intent);
        });
        tarih.setOnClickListener(view ->{
            intent.putExtra("dersCode",9);
            startActivity(intent);
        });
        cog.setOnClickListener(view ->{
            intent.putExtra("dersCode",10);
            startActivity(intent);
        });
        mat.setOnClickListener(view ->{
            intent.putExtra("dersCode",4);
            startActivity(intent);
        });


    }

}
