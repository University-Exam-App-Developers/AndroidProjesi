package com.example.snavadogru.puanHesapla;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.snavadogru.R;

import java.text.DecimalFormat;

public class PuanSonuc extends AppCompatActivity {
    double[] values;
    Bundle extras;
    private DecimalFormat df = new DecimalFormat("0.00");
    TextView obp,tytHam,tytYerlestirme,aytSayHam,aytSayYerlestirme,aytEaHam,aytEaYerlestirme,aytSozHam,aytSozYerlestirme,aytDilHam,aytDilYerlestirme;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puan_sonuc);
        obp=findViewById(R.id.obp);
        tytHam=findViewById(R.id.tytHamPuan);
        tytYerlestirme=findViewById(R.id.puan_tyt_yerlestirme);
        aytSayHam=findViewById(R.id.sayHamPuan);
        aytSayYerlestirme=findViewById(R.id.puan_say_yerlestirme);
        aytEaHam=findViewById(R.id.eaHamPuan);
        aytEaYerlestirme=findViewById(R.id.puan_ea_yerlestirme);
        aytSozHam=findViewById(R.id.sozHamPuan);
        aytSozYerlestirme=findViewById(R.id.puan_soz_yerlestirme);
        aytDilHam=findViewById(R.id.dilHamPuan);
        aytDilYerlestirme=findViewById(R.id.puan_dil_yerlestirme);

        extras = getIntent().getExtras();
        if(extras == null)
            values= new double[6];
        else
            values = extras.getDoubleArray("sonuc");

        obp.setText(df.format(values[0])+"");
        tytHam.setText(df.format(values[1])+"");
        tytYerlestirme.setText(df.format(values[1]+values[0])+"");
        aytSayHam.setText(df.format(values[2])+"");
        aytSayYerlestirme.setText(df.format((values[0]+values[2]))+"");
        aytEaHam.setText(df.format(values[3])+"");
        aytEaYerlestirme.setText(df.format((values[3]+values[0]))+"");
        aytSozHam.setText(df.format(values[4])+"");
        aytSozYerlestirme.setText(df.format((values[4]+values[0]))+"");
        aytDilHam.setText(df.format(values[5])+"");
        aytDilYerlestirme.setText(df.format((values[5]+values[0]))+"");
    }
}
