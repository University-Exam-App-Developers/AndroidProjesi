package com.example.snavadogru.FirstScreen;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.snavadogru.Options;
import com.example.snavadogru.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.onesignal.OneSignal;

public class MainActivity extends AppCompatActivity {
    Button btn_giris,btn_kaydol;
    FirebaseUser baslangickullanici;
    Animation apperance,upslide,click;
    LinearLayout bottomLayout;
    @Override
    protected void onStart() {
        super.onStart();
        baslangickullanici = FirebaseAuth.getInstance().getCurrentUser();
        //Kullanıcı veri tabanında varsa ana sayfaya gitsin
        if (baslangickullanici != null ){
            startActivity(new Intent(MainActivity.this,Options.class));
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.TYPE_STATUS_BAR);
        bottomLayout=findViewById(R.id.bottomLayout);
        //One signal bildirim göndermek için ilgili kod
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();

        btn_giris= findViewById(R.id.btn_giris);
        btn_kaydol=findViewById(R.id.btn_kaydol);
        apperance= AnimationUtils.loadAnimation(this,R.anim.appearance_slide);
        upslide=AnimationUtils.loadAnimation(this,R.anim.up_slide);
        click=AnimationUtils.loadAnimation(this,R.anim.bounce_button);
        bottomLayout.startAnimation(upslide);
        btn_giris.startAnimation(apperance);
        btn_kaydol.startAnimation(apperance);
        btn_kaydol.setOnClickListener(v -> {
            btn_kaydol.startAnimation(click);
            startActivity(new Intent(MainActivity.this,KaydolActivity.class));
        });
        btn_giris.setOnClickListener(v -> {
            btn_giris.startAnimation(click);
            startActivity(new Intent(MainActivity.this,GirisActivity.class));
        });

    }


}
