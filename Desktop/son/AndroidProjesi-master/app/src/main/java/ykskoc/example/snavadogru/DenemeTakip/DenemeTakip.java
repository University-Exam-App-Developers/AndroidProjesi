package ykskoc.example.snavadogru.DenemeTakip;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;

import com.ykskoc.R;

public class DenemeTakip extends Activity implements View.OnClickListener{
   CardView tyt,say,ea,dil,soz,info;


    protected void onCreate(Bundle savedInstanceState) {
        if (AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_YES)
            setTheme(R.style.AppTheme_DarkMode);
        else
            setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_denemetakip);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.TYPE_STATUS_BAR);

        tyt = findViewById(R.id.deneme_tyt);
        say = findViewById(R.id.deneme_say);
        ea = findViewById(R.id.deneme_ea);
        dil = findViewById(R.id.deneme_dil);
        soz = findViewById(R.id.deneme_soz);
        info=findViewById(R.id.nasilKullanmaliyim);

        tyt.setOnClickListener(this);
        say.setOnClickListener(this);
        ea.setOnClickListener(this);
        dil.setOnClickListener(this);
        soz.setOnClickListener(this);
        info.setOnClickListener(this);

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
        else if (view.getId()==info.getId())
        {
            showpopup();
        }
    }
    public void showpopup()
    {
        ImageButton button;
        LayoutInflater layout = LayoutInflater.from(this);
        View view = layout.inflate(R.layout.deneme_info,null);
        button=view.findViewById(R.id.confirm);

        Dialog ad = new Dialog(this);
        ad.setContentView(view);
        button.setOnClickListener(v-> ad.dismiss());
        ad.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ad.show();
    }
}
