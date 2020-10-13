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
public class aytKonuSay extends AppCompatActivity{
    Intent intent;
    CardView mat,fiz,kim,bio;
    Animation upStartAnimation,spawnStartAnimation;
    TextView headText;
    ImageView layoutImage;
    RelativeLayout bcLayout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_takip_konu_say);
        intent = new Intent(aytKonuSay.this,aytKonulari.class);
        headText=findViewById(R.id.headText);
        layoutImage= findViewById(R.id.layoutImage);
        bcLayout= findViewById(R.id.buttonRelativeLayout);
        mat = findViewById(R.id.aytKonuSay_matematik);
        fiz = findViewById(R.id.aytKonuSay_fizik);
        kim = findViewById(R.id.aytKonuSay_kimya);
        bio = findViewById(R.id.aytKonuSay_bio);
        Glide.with(this).load(R.mipmap.working_girl).into(layoutImage);

        upStartAnimation= AnimationUtils.loadAnimation(this,R.anim.up_slide);
        spawnStartAnimation= AnimationUtils.loadAnimation(this,R.anim.start_spawn);
        headText.setAnimation(spawnStartAnimation);
        layoutImage.setAnimation(spawnStartAnimation);
        bcLayout.setAnimation(upStartAnimation);

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
