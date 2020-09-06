package com.example.snavadogru.DenemeTakip;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.snavadogru.R;

public class DenemeTakip extends Activity implements View.OnClickListener{
   Button tyt,say,ea,dil,soz;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_denemetakip);

        tyt = findViewById(R.id.deneme_tyt);
        say = findViewById(R.id.deneme_say);
        ea = findViewById(R.id.deneme_ea);
        dil = findViewById(R.id.deneme_dil);
        soz = findViewById(R.id.deneme_soz);

        tyt.setOnClickListener(this);
        say.setOnClickListener(this);
        ea.setOnClickListener(this);
        dil.setOnClickListener(this);
        soz.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view.getId()==tyt.getId())
        {
            Intent i = new Intent(DenemeTakip.this, denemeTYTtakip.class);
            startActivity(i);
        }
        else if (view.getId()==say.getId())
        {
            Intent i = new Intent(DenemeTakip.this, denemeSAYtakip.class);
            startActivity(i);
        }
        else if (view.getId()==ea.getId())
        {
            Intent i = new Intent(DenemeTakip.this, denemeEAtakip.class);
            startActivity(i);
        }
        else if (view.getId()==soz.getId())
        {
            Intent i = new Intent(DenemeTakip.this, denemeSOZtakip.class);
            startActivity(i);
        }
        else if (view.getId()==dil.getId())
        {
            Intent i = new Intent(DenemeTakip.this, denemeDiltakip.class);
            startActivity(i);
        }
    }
}
