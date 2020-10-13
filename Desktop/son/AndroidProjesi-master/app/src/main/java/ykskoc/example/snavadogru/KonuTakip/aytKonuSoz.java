package ykskoc.example.snavadogru.KonuTakip;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;import com.ykskoc.R;
public class aytKonuSoz extends AppCompatActivity{
    Intent intent;
    CardView edeb,tarih,cog,felsefe,din;
    Animation upStartAnimation,spawnStartAnimation;
    TextView headText;
    ImageView layoutImage;
    RelativeLayout bcLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_takip_konu_soz);
        intent = new Intent(aytKonuSoz.this,aytKonulari.class);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.TYPE_STATUS_BAR);
        headText=findViewById(R.id.headText);
        layoutImage= findViewById(R.id.layoutImage);
        bcLayout= findViewById(R.id.buttonRelativeLayout);
        edeb = findViewById(R.id.aytKonuSoz_edebiyat);
        tarih = findViewById(R.id.aytKonuSoz_tarih);
        cog = findViewById(R.id.aytKonuSoz_cografya);
        felsefe = findViewById(R.id.aytKonuSoz_felsefe);
        din = findViewById(R.id.aytKonuSoz_din);

        Glide.with(this).load(R.mipmap.working_girl).into(layoutImage);

        upStartAnimation= AnimationUtils.loadAnimation(this,R.anim.up_slide);
        spawnStartAnimation= AnimationUtils.loadAnimation(this,R.anim.start_spawn);
        headText.setAnimation(spawnStartAnimation);
        layoutImage.setAnimation(spawnStartAnimation);
        bcLayout.setAnimation(upStartAnimation);

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
