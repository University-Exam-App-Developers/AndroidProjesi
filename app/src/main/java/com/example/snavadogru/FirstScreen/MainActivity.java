package com.example.snavadogru.FirstScreen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.snavadogru.Options;
import com.example.snavadogru.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.onesignal.OneSignal;

public class MainActivity extends AppCompatActivity {
    Button btn_giris,btn_kaydol;
    FirebaseUser baslangickullanici;
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

        //One signal bildirim göndermek için ilgili kod
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();

        btn_giris= findViewById(R.id.btn_giris);
        btn_kaydol=findViewById(R.id.btn_kaydol);
        btn_kaydol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,KaydolActivity.class));
            }
        });
        btn_giris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,GirisActivity.class));
            }
        });

    }


}
