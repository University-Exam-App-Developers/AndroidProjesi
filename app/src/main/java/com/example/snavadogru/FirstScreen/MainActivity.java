package com.example.snavadogru.FirstScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.snavadogru.Options;
import com.example.snavadogru.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.onesignal.OneSignal;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button bEnterace;
    EditText editTextMail;
    String mail;
    FirebaseDatabase firebaseDatabase= FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference("mail");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //One signal bildirim göndermek için ilgili kod
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();

        bEnterace = findViewById(R.id.button);

        bEnterace.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case (R.id.button):

                editTextMail = findViewById(R.id.editText);
                mail = editTextMail.getText().toString();
                databaseReference.setValue(mail);                                  // realtime olrak kullanıcı maili firebase kaydedildi
                Intent i= new Intent(MainActivity.this, Options.class);
                startActivity(i);
                break;

        }
    }
}
