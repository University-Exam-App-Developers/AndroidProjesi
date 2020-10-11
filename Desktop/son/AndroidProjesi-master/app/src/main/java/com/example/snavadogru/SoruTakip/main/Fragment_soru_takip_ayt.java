package com.example.snavadogru.SoruTakip.main;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.snavadogru.R;
import com.google.gson.Gson;

public class Fragment_soru_takip_ayt extends Fragment{
    private RecyclerView recyclerView;
    Context context;
    int[] aytSorular= new int[11];
    private String[] tv1={"TÜRKÇE","MATEMATİK","GEOMETRİ","FİZİK","KİMYA","BİYOLOJİ","TARİH","COĞRAFYA","FELSEFE","DİN KÜLTÜRÜ","İNGİLİZCE"};
    private MyFragmentSoruTakipTytRecyclerViewAdapter arrayAdapter;

    public Fragment_soru_takip_ayt(Context context){this.context=context;}
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sorutakip_ayt,null);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView=view.findViewById(R.id.soru_takip_ayt_RecyclerView);
        arrayAdapter = new MyFragmentSoruTakipTytRecyclerViewAdapter(this.getContext(),tv1,"ayt");
        recyclerView.setAdapter(arrayAdapter);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        aytSorular=arrayAdapter.getAytSoruSayisi();
      //  Log.d("aytFrag","Destroyed");
        saveData();
    }

    private void saveData() {
        new Runnable() {
            boolean runnable = true;
            @Override
            public void run() {
                if (!runnable)
                    return;
                SharedPreferences sP = context.getSharedPreferences("soruSayilari102", Context.MODE_PRIVATE);
                Gson gson = new Gson();
                String jsonAyt = gson.toJson(aytSorular);
                SharedPreferences.Editor editor = sP.edit();
                editor.putString("aytSoruSayisi",jsonAyt);
                editor.putInt("aytToplam",calculate());
                editor.apply();
                runnable=false;
            }
        }.run();
    }
    private int calculate(){
        int cozulenSorular = 0;
        for (int value : aytSorular) cozulenSorular += value;
        return cozulenSorular;
    }
}
