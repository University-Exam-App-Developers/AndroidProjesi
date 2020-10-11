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

public class Fragment_soru_takip_tyt extends Fragment {
    private String[] tv1={"TÜRKÇE","MATEMATİK","FİZİK","KİMYA","BİYOLOJİ","TARİH","COĞRAFYA","FELSEFE"};
    private RecyclerView recyclerView;
    int[] tytSorular= new int[8];
    Context context;
    private MyFragmentSoruTakipTytRecyclerViewAdapter arrayAdapter;
    public Fragment_soru_takip_tyt(Context context){this.context=context;}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_soru_takip_tyt_list, container, false);
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView=view.findViewById(R.id.sorutakip_rv);
        arrayAdapter = new MyFragmentSoruTakipTytRecyclerViewAdapter(this.getContext(),tv1,"tyt");
        recyclerView.setAdapter(arrayAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        tytSorular=arrayAdapter.getTytSoruSayisi();
    //    Log.d("onDestroy", "onDestroy: "+tytSorular[0]);
  //      Log.d("tytFrag","Destroyed");
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
                String jsonTyt = gson.toJson(tytSorular);
                SharedPreferences.Editor editor = sP.edit();
                editor.putString("tytSoruSayisi", jsonTyt);
                editor.putInt("tytToplam", calculate());
                editor.apply();
            runnable=false;
            }
        }.run();
    }
    private int calculate(){
        int cozulenSorular = 0;
        for (int value : tytSorular) cozulenSorular += value;
        return cozulenSorular;
    }
}