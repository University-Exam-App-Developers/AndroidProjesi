package com.example.snavadogru.KonuTakip;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.snavadogru.R;

public class aytKonuSoz extends AppCompatActivity{
    Intent intent;
    Button edeb,tarih,cog,felsefe,din;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_takip_konu_soz);
        intent = new Intent(aytKonuSoz.this,aytKonulari.class);

        edeb = findViewById(R.id.aytKonuSoz_edebiyat);
        tarih = findViewById(R.id.aytKonuSoz_tarih);
        cog = findViewById(R.id.aytKonuSoz_cografya);
        felsefe = findViewById(R.id.aytKonuSoz_felsefe);
        din = findViewById(R.id.aytKonuSoz_din);

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
        felsefe.setOnClickListener(view ->{
            intent.putExtra("dersCode",11);
            startActivity(intent);
        });
        din.setOnClickListener(view ->{
            intent.putExtra("dersCode",12);
            startActivity(intent);
        });


    }

}
