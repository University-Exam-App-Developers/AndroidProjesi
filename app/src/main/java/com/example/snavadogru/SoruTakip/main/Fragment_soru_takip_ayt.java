package com.example.snavadogru.SoruTakip.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.snavadogru.R;

public class Fragment_soru_takip_ayt extends Fragment{
    private RecyclerView recyclerView;
    private boolean changed;
    private String[] tv1={"TÜRKÇE","MATEMATİK","GEOMETRİ","FİZİK","KİMYA","BİYOLOJİ","TARİH","COĞRAFYA","FELSEFE","DİN KÜLTÜRÜ","İNGİLİZCE"};
    private MyFragmentSoruTakipTytRecyclerViewAdapter arrayAdapter;
    public Fragment_soru_takip_ayt(boolean isChanged){
        changed=isChanged;
    }
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
        arrayAdapter = new MyFragmentSoruTakipTytRecyclerViewAdapter(this.getContext(),tv1,"ayt",changed);
        recyclerView.setAdapter(arrayAdapter);
    }
}
