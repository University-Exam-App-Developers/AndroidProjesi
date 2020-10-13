package ykskoc.example.snavadogru.KonuTakip;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.ykskoc.R;

public class aytKonuEa extends AppCompatActivity {
    Intent intent;
    CardView edeb,tarih,cog,mat;
    Animation upStartAnimation,spawnStartAnimation;
    TextView headText;
    ImageView layoutImage;
    RelativeLayout bcLayout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_takip_konu_ea);
        intent = new Intent(aytKonuEa.this,aytKonulari.class);
        headText=findViewById(R.id.headText);
        layoutImage= findViewById(R.id.layoutImage);
        bcLayout= findViewById(R.id.buttonRelativeLayout);
        edeb = findViewById(R.id.aytKonuEa_edebiyat);
        tarih = findViewById(R.id.aytKonuEa_tarih);
        cog = findViewById(R.id.aytKonuEa_cografya);
        mat = findViewById(R.id.aytKonuEa_matematik);

        Glide.with(this).load(R.drawable.illustration).into(layoutImage);
        upStartAnimation= AnimationUtils.loadAnimation(this,R.anim.up_slide);
        spawnStartAnimation= AnimationUtils.loadAnimation(this,R.anim.start_spawn);
        headText.setAnimation(spawnStartAnimation);
        layoutImage.setAnimation(spawnStartAnimation);
        bcLayout.setAnimation(upStartAnimation);

              /*  headText.setAnimation(downStartAnimation);
        layoutImage.setAnimation(downStartAnimation);
        bcLayout.setAnimation(upStartAnimation);*/

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
