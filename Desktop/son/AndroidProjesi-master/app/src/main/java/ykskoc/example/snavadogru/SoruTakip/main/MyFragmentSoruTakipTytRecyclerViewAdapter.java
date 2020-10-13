package ykskoc.example.snavadogru.SoruTakip.main;

import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ykskoc.R;
import ykskoc.example.snavadogru.SoruTakip.main.SoruTakipSqLite.SoruTakipMyDataBaseHelper;
import ykskoc.example.snavadogru.SoruTakip.main.SoruTakipSqLite.SoruTakipMyDataBaseHelper_Month;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;


public class MyFragmentSoruTakipTytRecyclerViewAdapter extends RecyclerView.Adapter<MyFragmentSoruTakipTytRecyclerViewAdapter.ViewHolder> {
    private String[] textView;
    private int[] soruSayisi;
    private int[] tytSoruSayisi,aytSoruSayisi;
    private String fragment;
    private Context c;

    MyFragmentSoruTakipTytRecyclerViewAdapter(Context context, String[] textNames, String frag ) {
        soruSayisi=new int[textNames.length];
        textView = textNames;
        fragment=frag;
        c = context;
        loadData();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(c).inflate(R.layout.fragment_sorutakip_adding, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.tv.setText(textView[position]);
        holder.editText.setText(soruSayisi[position]+"");

        holder.bPlus.setOnClickListener(view -> {
            soruSayisi[position]+=10;
            if (soruSayisi[position]>10000){
                holder.editText.setError("Lütfen 10000'den küçük GÜNLÜK soru sayısını giriniz");
                soruSayisi[position]-=10;
            }
            holder.editText.setText((soruSayisi[position])+"");
        });

        holder.bMin.setOnClickListener(view -> {
            if (soruSayisi[position]<10)
                Toast.makeText(view.getContext(), "Lütfen 0'dan büyük soru sayısı giriniz", Toast.LENGTH_SHORT).show();
            else{
                soruSayisi[position]-=10;
                holder.editText.setText((soruSayisi[position])+"");
            }
        });

        holder.editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                soruSayisi[position]=Integer.parseInt("0"+s);
                if (soruSayisi[position]>10000){
                    holder.editText.setError("Lütfen 10000'den küçük GÜNLÜK soru sayısını giriniz");
                    holder.editText.setText(0+"");
                    soruSayisi[position]=0;
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override
    public int getItemCount() {
        return textView.length;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private EditText editText;
        private Button bPlus,bMin;
        private TextView tv;

        ViewHolder(View view) {
            super(view);
            editText =view.findViewById(R.id.editTextL2et1);
            bMin=view.findViewById(R.id.bMinus);
            bPlus=view.findViewById(R.id.bPlus);
            tv=view.findViewById(R.id.textViewL1tv1);
        }
    }


    private void loadData() {
        SharedPreferences sP = c.getSharedPreferences("soruSayilari102", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String jsonTyt = sP.getString("tytSoruSayisi", null);
        String jsonAyt = sP.getString("aytSoruSayisi", null);
        boolean reset=sP.getBoolean("reset",true);
        Type type = new TypeToken<int[]>() {}.getType();
        tytSoruSayisi = gson.fromJson(jsonTyt, type);
        aytSoruSayisi = gson.fromJson(jsonAyt, type);

        if (tytSoruSayisi==null || aytSoruSayisi==null || reset){
            tytSoruSayisi=new int[8];
            aytSoruSayisi=new int[11];
            saveData();
        }

        if (fragment.equals("tyt"))
            soruSayisi=tytSoruSayisi;

        else if (fragment.equals("ayt"))
            soruSayisi=aytSoruSayisi;
    }

    private void saveData() {
        SharedPreferences sP = c.getSharedPreferences("soruSayilari102", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sP.edit();
        Gson gson = new Gson();
        String jsonAyt = gson.toJson(aytSoruSayisi);
        editor.putString("aytSoruSayisi",jsonAyt);
        String jsonTyt = gson.toJson(tytSoruSayisi);
        editor.putString("tytSoruSayisi", jsonTyt);
        editor.putBoolean("reset",false);
        editor.apply();
    }

    int[] getTytSoruSayisi() {
        return tytSoruSayisi;
    }

    int[] getAytSoruSayisi() {
        return aytSoruSayisi;
    }
}
