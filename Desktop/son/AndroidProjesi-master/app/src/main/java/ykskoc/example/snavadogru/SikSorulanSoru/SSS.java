package ykskoc.example.snavadogru.SikSorulanSoru;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ykskoc.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class SSS extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<Sik_Soru> sorular;
    CustomAdapter adapter;
    Context  context=this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_YES)
            setTheme(R.style.AppTheme_DarkMode);
        else
            setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sss);

        sorular = new ArrayList<>();
        String cikmissorular = "";
        cikmissorular = dosyaoku("sik_sorulan_sorular");
        sorularıBul(cikmissorular,78);
        recyclerView= findViewById(R.id.sss_recyclerview);
      LinearLayoutManager manager = new LinearLayoutManager(context);
      manager.setOrientation(LinearLayoutManager.VERTICAL);
      manager.scrollToPosition(0);
      recyclerView.setLayoutManager(manager);
      recyclerView.setHasFixedSize(true);
      adapter = new CustomAdapter(sorular,context);
      recyclerView.setAdapter(adapter);
    }
    public String dosyaoku(String filename){
        String cikmissorular = "";

        try {
            InputStream is = getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            cikmissorular = new String(buffer);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return cikmissorular;
    }
    private void sorularıBul(String cikmissorular,int soruSayısı){
        int i;
        sorular.clear();
        for (i=1;i<soruSayısı+1;i++){
            int k= i+1;
            Sik_Soru soru = new  Sik_Soru(cikmissorular.substring(cikmissorular.indexOf(i+"."),cikmissorular.indexOf(k+".")));
           sorular.add(soru);
        }
    }
}
