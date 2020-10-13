package ykskoc.example.snavadogru.FirstScreen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import ykskoc.example.snavadogru.Options;
import com.ykskoc.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class KaydolActivity extends AppCompatActivity {
    EditText edit_sifretekrar,edit_Ad,edit_email,edit_sifre;
    Button btn_kaydol;
    Animation apperance,click;
    TextView txt_girissayfasınagit;
    LinearLayout screen;
    FirebaseAuth yetki ;
    DatabaseReference yol;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kaydol);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.TYPE_STATUS_BAR);
        edit_sifretekrar = findViewById(R.id.edit_sifretekrar);
        screen=findViewById(R.id.Registerscreen);
        edit_Ad = findViewById(R.id.edit_ad);
        edit_email = findViewById(R.id.edit_email);
        edit_sifre = findViewById(R.id.edit_sifre);
        btn_kaydol= findViewById(R.id.btn_kaydol_activity);
        txt_girissayfasınagit = findViewById(R.id.txt_girissayfasına_git);
        apperance= AnimationUtils.loadAnimation(this,R.anim.appearance_slide);
        click=AnimationUtils.loadAnimation(this,R.anim.bounce_button);
        yetki=FirebaseAuth.getInstance();
        screen.startAnimation(apperance);


        txt_girissayfasınagit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_girissayfasınagit.startAnimation(click);
                startActivity(new Intent(KaydolActivity.this,GirisActivity.class));
            }
        });


        btn_kaydol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_kaydol.startAnimation(click);
                pd =new  ProgressDialog(KaydolActivity.this);
                pd.setMessage("Lütfen Bekleyin");
                pd.show();
                String str_sifretekrar = edit_sifretekrar.getText().toString();
                String str_ad = edit_Ad.getText().toString();
                String str_email = edit_email.getText().toString();
                String str_sifre = edit_sifre.getText().toString();
                if(TextUtils.isEmpty(str_ad) || TextUtils.isEmpty(str_sifretekrar) ||
                        TextUtils.isEmpty(str_email) || TextUtils.isEmpty(str_sifre )
                ){
                    Toast.makeText(KaydolActivity.this, "Lütfen gerekli bilgileri giriniz", Toast.LENGTH_SHORT).show();

                    pd.dismiss();
                }
                else if (!str_sifre.equals(str_sifretekrar)){
                    pd.dismiss();
                    Toast.makeText(KaydolActivity.this,"Lütfen şifreyi kontrol edin",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    //Yeni kullanici kaydetme
                    kullaniciKaydet(str_ad,str_email,str_sifre);

                }



            }
        });

    }
    private void kullaniciKaydet(final String ad, String email, String sifre)
    {//Yeni kullanici kaydetme kodları
        yetki.createUserWithEmailAndPassword(email,sifre)
                .addOnCompleteListener(KaydolActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser firebasekullanici= yetki.getCurrentUser();
                            String kullaniciId= firebasekullanici.getUid();
                            yol = FirebaseDatabase.getInstance().getReference().child("Kullanicilar").child(kullaniciId);
                            HashMap<String ,Object > hashMap = new HashMap<>();
                            hashMap.put("id",kullaniciId);
                            hashMap.put("ad",ad.toLowerCase());
                            //hashMap.put("mail",email);
                           // hashMap.put("sifre",sifre);
                           yol.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful())
                                    {
                                        pd.dismiss();
                                        Intent intent = new Intent(KaydolActivity.this, Options.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                    }
                                }
                            });
                        }
                        else {
                            pd.dismiss();
                            //firabasede authentication da etkinlestirmeyi unutma

                            Toast.makeText(KaydolActivity.this, "Bu mail veya şifre ile kayıt başarısız", Toast.LENGTH_LONG).show();
                        }


                    }
                });

    }
}
