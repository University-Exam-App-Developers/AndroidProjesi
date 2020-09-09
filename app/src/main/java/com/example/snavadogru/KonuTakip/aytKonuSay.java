package com.example.snavadogru.KonuTakip;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import com.example.snavadogru.R;

public class aytKonuSay extends AppCompatActivity{
    Intent intent;
    CardView mat,fiz,kim,bio;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_takip_konu_say);
        intent = new Intent(aytKonuSay.this,aytKonulari.class);

        mat = findViewById(R.id.aytKonuSay_matematik);
        fiz = findViewById(R.id.aytKonuSay_fizik);
        kim = findViewById(R.id.aytKonuSay_kimya);
        bio = findViewById(R.id.aytKonuSay_bio);

        mat.setOnClickListener(view ->{
            intent.putExtra("dersCode",4);
            startActivity(intent);
        });
        fiz.setOnClickListener(view ->{
            intent.putExtra("dersCode",5);
            startActivity(intent);
        });
        kim.setOnClickListener(view ->{
            intent.putExtra("dersCode",6);
            startActivity(intent);
        });
        bio.setOnClickListener(view ->{
            intent.putExtra("dersCode",7);
            startActivity(intent);
        });

    }

}
