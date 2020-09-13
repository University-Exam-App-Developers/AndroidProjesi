package com.example.snavadogru.FirstScreen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.snavadogru.Options;
import com.example.snavadogru.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GirisActivity extends AppCompatActivity {
    EditText edit_email_giris,edit_sifre_giris;
    Button btn_giris;
    TextView txt_kayıtsayfasına_git;
    FirebaseAuth girisyetkisi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giris);
        edit_email_giris= findViewById(R.id.edit_email_giris);
        edit_sifre_giris= findViewById(R.id.edit_sifre_giris);
        btn_giris=findViewById(R.id.btn_giris_activity);
        txt_kayıtsayfasına_git=findViewById(R.id.txt_kaydolsayfasına_git);
        girisyetkisi=FirebaseAuth.getInstance();
        txt_kayıtsayfasına_git.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GirisActivity.this,KaydolActivity.class));
            }
        });
        btn_giris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog pdGiris= new ProgressDialog(GirisActivity.this);
                pdGiris.setMessage("Giriş yapılıyor");
                pdGiris.show();
                String str_email_giris= edit_email_giris.getText().toString();
                String str_sifre_giris=edit_sifre_giris.getText().toString();
                if(TextUtils.isEmpty(str_email_giris) || TextUtils.isEmpty(str_sifre_giris)){
                    Toast.makeText(GirisActivity.this, "Bütün alanalrı doldurun", Toast.LENGTH_LONG).show();
                }
                else{
                    //Giriş Kodları
                    girisyetkisi.signInWithEmailAndPassword(str_email_giris,str_sifre_giris)
                            .addOnCompleteListener(GirisActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        DatabaseReference yolgiris= FirebaseDatabase.getInstance().getReference().child("Kullanicilar")
                                                .child(girisyetkisi.getCurrentUser().getUid());

                                        yolgiris.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                pdGiris.dismiss();
                                                Intent intent =new Intent(GirisActivity.this, Options.class);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                startActivity(intent);
                                                finish();
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                                pdGiris.dismiss();

                                            }
                                        });

                                    }
                                    else{
                                        pdGiris.dismiss();
                                        Toast.makeText(GirisActivity.this, "Giriş başarısız", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });

                }
            }
        });
    }
}
