package com.example.snavadogru.SoruTakip.main;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.snavadogru.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class MyFragmentSoruTakipTytRecyclerViewAdapter extends RecyclerView.Adapter<MyFragmentSoruTakipTytRecyclerViewAdapter.ViewHolder> {
    private String[] textView;
    private int[] soruSayisi;
    private String fragment;
    private Context c;
    private boolean changed;
    private int tytCozulenSorular=0,aytCozulenSorular=0;

    public MyFragmentSoruTakipTytRecyclerViewAdapter(Context context,String[] textNames,String frag,boolean isChanged) {
        Log.d("number",textNames.length+"");
        soruSayisi=new int[textNames.length];
        textView = textNames;
        fragment=frag;
        c = context;
        changed=isChanged;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(c).inflate(R.layout.fragment_sorutakip_adding, parent, false);
        loadData();
        if (changed)
            soruSayisi=new int[textView.length];

        Log.d("Massage","View->"+changed);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.tv.setText(textView[position]);
        holder.editText.setText(soruSayisi[position]+"");
        holder.bPlus.setOnClickListener(view -> {
            soruSayisi[position]+=10;
            holder.editText.setText(soruSayisi[position]+"");
            saveData();
        });
        holder.bMin.setOnClickListener(view -> {
            if (soruSayisi[position]<10)
                Toast.makeText(view.getContext(), "Lütfen 0'dan büyük soru sayısı giriniz", Toast.LENGTH_SHORT).show();
            else{
                soruSayisi[position]-=10;
                holder.editText.setText(soruSayisi[position]+"");
                saveData();
            }
        });

        holder.editText.setOnClickListener(view -> {
            soruSayisi[position]=Integer.parseInt(holder.editText.getText()+"");
            holder.editText.setText(soruSayisi[position]+"");
            saveData();
        });
    }

    @Override
    public int getItemCount() {
        return textView.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private EditText editText;
        private Button bPlus,bMin;
        private TextView tv;

        public ViewHolder(View view) {
            super(view);
            editText =view.findViewById(R.id.editTextL2et1);
            bMin=view.findViewById(R.id.bMinus);
            bPlus=view.findViewById(R.id.bPlus);
            tv=view.findViewById(R.id.textViewL1tv1);
        }
    }

    private void saveData() {
        // if sp is not initialized sP=null;
        calculate();
        SharedPreferences sP=null;
        SharedPreferences sP2 = c.getSharedPreferences("soruSayilariSave",Context.MODE_PRIVATE);
        if (fragment.equals("tyt"))
            sP = c.getSharedPreferences("tyt_Soru_SayilariSave", Context.MODE_PRIVATE);
        else if (fragment.equals("ayt"))
            sP = c.getSharedPreferences("ayt_Soru_SayilariSave", Context.MODE_PRIVATE);

        if (fragment.equals("ayt"))
            Log.d("Save","soruSayilari "+soruSayisi[0]);

        SharedPreferences.Editor editor = sP.edit();
        SharedPreferences.Editor editor2=sP2.edit();

        editor2.putInt("tytSoruSayisi",tytCozulenSorular);
        editor2.putInt("aytSoruSayisi",aytCozulenSorular);
        editor2.apply();

        Gson gson = new Gson();
        String json = gson.toJson(soruSayisi);
        editor.putString("SoruSayisi", json);
        editor.apply();
    }
    private void loadData() {
        SharedPreferences sP=null;
        SharedPreferences sP2 = c.getSharedPreferences("soruSayilariSave",Context.MODE_PRIVATE);

        Log.d("CLASS",c.getClass()+","+Fragment_soru_takip_tyt.class);
        if (fragment.equals("tyt"))
            sP = c.getSharedPreferences("tyt_Soru_SayilariSave", Context.MODE_PRIVATE);
        else if (fragment.equals("ayt"))
            sP = c.getSharedPreferences("ayt_Soru_SayilariSave", Context.MODE_PRIVATE);

        Gson gson = new Gson();
        String json = sP.getString("SoruSayisi", null);
        Type type = new TypeToken<int[]>() {}.getType();
        soruSayisi = gson.fromJson(json, type);


        tytCozulenSorular= sP2.getInt("tytSoruSayisi",0);
        aytCozulenSorular= sP2.getInt("aytSoruSayisi",0);

        if (soruSayisi==null)
            soruSayisi=new int[textView.length];
        Log.d("LOAD","tyt-ayt "+tytCozulenSorular+","+aytCozulenSorular);
    }
    public void calculate(){
        for (int i = 0; i <soruSayisi.length ; i++)
            if (fragment.equals("tyt"))
                tytCozulenSorular+=soruSayisi[i];
            else if (fragment.equals("ayt"))
                aytCozulenSorular+=soruSayisi[i];
    }
}
