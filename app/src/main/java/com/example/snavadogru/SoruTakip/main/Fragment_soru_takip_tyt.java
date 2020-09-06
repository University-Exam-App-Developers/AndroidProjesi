package com.example.snavadogru.SoruTakip.main;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.snavadogru.R;
public class Fragment_soru_takip_tyt extends Fragment {
    private String[] tv1={"TÜRKÇE","MATEMATİK","FİZİK","KİMYA","BİYOLOJİ","TARİH","COĞRAFYA","FELSEFE"};
    private RecyclerView recyclerView;
    private boolean changed;
    private MyFragmentSoruTakipTytRecyclerViewAdapter arrayAdapter;
    public Fragment_soru_takip_tyt(boolean isChanged){
        changed=isChanged;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_soru_takip_tyt_list, container, false);
        Log.d("View","onCreateView()");
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView=view.findViewById(R.id.sorutakip_rv);
        arrayAdapter = new MyFragmentSoruTakipTytRecyclerViewAdapter(this.getContext(),tv1,"tyt",changed);
        recyclerView.setAdapter(arrayAdapter);
    }
}