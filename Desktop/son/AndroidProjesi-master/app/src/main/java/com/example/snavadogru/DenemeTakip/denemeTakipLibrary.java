package com.example.snavadogru.DenemeTakip;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.snavadogru.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class denemeTakipLibrary extends AppCompatActivity {

    private ArrayList<dilDenemesi> dilDenemeleri;
    private ArrayList<tytDenemesi> tytDenemeleri;
    private ArrayList<eaDenemesi>  eaDenemeleri;
    private ArrayList<sayDenemesi> sayDenemeleri;
    private ArrayList<sozDenemesi> sozDenemeleri;
    RelativeLayout relativeLayout;
    libraryRecyclerViewAdapter myAdapter;
    String select="";
    RecyclerView rV;
    Bundle extras;

    public denemeTakipLibrary(){}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_YES)
            setTheme(R.style.AppTheme_DarkMode);
        else
            setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deneme_takip_library);
        rV =  findViewById(R.id.roofs);
        relativeLayout= findViewById(R.id.notFound);
        extras = getIntent().getExtras();
        if(extras == null)
            select= "";
        else
            select=extras.getString("key");
        buildRecyclerView();
    }

    public void removeItem(int position){
        myAdapter.notifyItemRemoved(position);
    }

    public void buildRecyclerView(){
        loadData();
        if (select.equals("DIL")){myAdapter = new libraryRecyclerViewAdapter(this);
            myAdapter.setDilDenemesiArrayList(dilDenemeleri);
            if (dilDenemeleri.size()==0) relativeLayout.setVisibility(View.VISIBLE);}
        if (select.equals("TYT")){myAdapter = new libraryRecyclerViewAdapter(this);
            myAdapter.setTytDenemesiArrayList(tytDenemeleri);
            if (tytDenemeleri.size()==0) relativeLayout.setVisibility(View.VISIBLE);}
        if (select.equals("EA")){ myAdapter = new libraryRecyclerViewAdapter(this);
            myAdapter.setEaDenemesiArrayList(eaDenemeleri);
            if (eaDenemeleri.size()==0) relativeLayout.setVisibility(View.VISIBLE);}
        if (select.equals("SAY")){ myAdapter = new libraryRecyclerViewAdapter(this);
            myAdapter.setSayDenemesiArrayList(sayDenemeleri);
            if (sayDenemeleri.size()==0) relativeLayout.setVisibility(View.VISIBLE);}
        if (select.equals("SOZ")){ myAdapter = new libraryRecyclerViewAdapter(this);
            myAdapter.setSozDenemesiArrayList(sozDenemeleri);
            if (sozDenemeleri.size()==0) relativeLayout.setVisibility(View.VISIBLE);}
        rV.setLayoutManager(new LinearLayoutManager(this));
        rV.setHasFixedSize(true);
        rV.setAdapter(myAdapter);
       // Log.d("ERRRRORRRR", "buildRecyclerView: "+myAdapter);
        myAdapter.setOnItemClickListener(position ->{

            switch (select) {
                case "TYT": tytDenemeleri.remove(position); break;
                case "SAY": sayDenemeleri.remove(position); break;
                case "EA": eaDenemeleri.remove(position); break;
                case "SOZ": sozDenemeleri.remove(position); break;
                case "DIL": dilDenemeleri.remove(position); break;
            }
            saveData();
            removeItem(position);
        });

    }
    void setDilDenemeleri(ArrayList<dilDenemesi> dil) { if (dil==null) dilDenemeleri=new ArrayList<>(); else dilDenemeleri=dil;}
    void setTytDenemeleri(ArrayList<tytDenemesi> tyt) { if (tyt==null) tytDenemeleri=new ArrayList<>(); else tytDenemeleri=tyt;}
    void setEaDenemeleri(ArrayList<eaDenemesi> ea)    {if (ea==null) eaDenemeleri=new ArrayList<>(); else eaDenemeleri=ea;}
    void setSayDenemeleri(ArrayList<sayDenemesi> say) { if (say==null) sayDenemeleri=new ArrayList<>(); else sayDenemeleri=say;}
    void setSozDenemeleri(ArrayList<sozDenemesi> soz) { if (soz==null) sozDenemeleri=new ArrayList<>(); else sozDenemeleri=soz;}

    private void loadData() {
        SharedPreferences sP = getSharedPreferences("denemeTakipSave",Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String jsonTyt = sP.getString("tytDenemeler_List", null);
        String jsonSay = sP.getString("sayDenemeler_List", null);
        String jsonEa = sP.getString("eaDenemeler_List", null);
        String jsonSoz = sP.getString("sozDenemeler_List", null);
        String jsonDil = sP.getString("dilDenemeler_List", null);

        Type typeTYT = new TypeToken<ArrayList<tytDenemesi>>() {}.getType();
        Type typeSAY = new TypeToken<ArrayList<sayDenemesi>>() {}.getType();
        Type typeEA  = new TypeToken<ArrayList<eaDenemesi>>() {}.getType();
        Type typeSOZ = new TypeToken<ArrayList<sozDenemesi>>() {}.getType();
        Type typeDIL = new TypeToken<ArrayList<dilDenemesi>>() {}.getType();

        if (select.equals("TYT")){
            setTytDenemeleri(gson.fromJson(jsonTyt, typeTYT));}
        if (select.equals("SAY")){
            setSayDenemeleri(gson.fromJson(jsonSay, typeSAY));}
        if (select.equals("EA")){
            setEaDenemeleri(gson.fromJson(jsonEa, typeEA));}
        if (select.equals("SOZ")){
            setSozDenemeleri(gson.fromJson(jsonSoz, typeSOZ));}
        if (select.equals("DIL")){
            setDilDenemeleri(gson.fromJson(jsonDil, typeDIL));}
    }

    private void saveData() {
        SharedPreferences sP = getSharedPreferences("denemeTakipSave", MODE_PRIVATE);
        SharedPreferences.Editor editor = sP.edit();
        Gson gson = new Gson();
        String jsonTyt,jsonSay,jsonEa,jsonSoz,jsonDil;
        if (select.equals("TYT")){
            jsonTyt=gson.toJson(tytDenemeleri);
            editor.putString("tytDenemeler_List", jsonTyt);
        }
        if (select.equals("SAY")){
            jsonSay=gson.toJson(sayDenemeleri);
            editor.putString("sayDenemeler_List", jsonSay);
        }
        if (select.equals("EA")){
            jsonEa=gson.toJson(eaDenemeleri);
            editor.putString("eaDenemeler_List", jsonEa);
        }
        if (select.equals("SOZ")){
            jsonSoz=gson.toJson(sozDenemeleri);
            editor.putString("sozDenemeler_List", jsonSoz);
        }
        if (select.equals("DIL")) {
            jsonDil = gson.toJson(dilDenemeleri);
            editor.putString("dilDenemeler_List", jsonDil);
        }
        editor.apply();
    }
}
