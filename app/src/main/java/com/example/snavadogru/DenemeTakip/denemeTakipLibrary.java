package com.example.snavadogru.DenemeTakip;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.example.snavadogru.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class denemeTakipLibrary extends AppCompatActivity {

    private ArrayList<dilDenemesi> dilDenemeleri,wholeDilDenemeler;
    private ArrayList<tytDenemesi> tytDenemeleri,wholeTytDenemeler;
    private ArrayList<eaDenemesi>  eaDenemeleri,wholeEaDenemeler;
    private ArrayList<sayDenemesi> sayDenemeleri,wholeSayDenemeler;
    private ArrayList<sozDenemesi> sozDenemeleri,wholeSozDenemeler;
    Bundle extras;
    libraryRecyclerViewAdapter myAdapter;
    RecyclerView rV;
    boolean comfirm;
    String select="";
    int indexToBeDeleted,indexToBeDeletedOnGraph=0;

    public denemeTakipLibrary(){}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deneme_takip_library);
        rV =  findViewById(R.id.roofs);
        extras = getIntent().getExtras();
        if(extras == null)
            select= "";
        else
            select=extras.getString("key");
        buildRecyclerView();
    }

    public void removeItem(int position){
        indexToBeDeleted=position;
        comfirm=true;
        myAdapter.notifyItemRemoved(position);
    }

    public boolean doComfirm(){return comfirm;}
    public void buildRecyclerView(){
        loadData();
        if (wholeDilDenemeler!=null){myAdapter = new libraryRecyclerViewAdapter(this);
            myAdapter.setDilDenemesiArrayList(wholeDilDenemeler);}
        if (wholeTytDenemeler!=null){myAdapter = new libraryRecyclerViewAdapter(this);
            myAdapter.setTytDenemesiArrayList(wholeTytDenemeler);}
        if (wholeEaDenemeler!=null){ myAdapter = new libraryRecyclerViewAdapter(this);
            myAdapter.setEaDenemesiArrayList(wholeEaDenemeler);}
        if (wholeSayDenemeler!=null){ myAdapter = new libraryRecyclerViewAdapter(this);
            myAdapter.setSayDenemesiArrayList(wholeSayDenemeler);}
        if (wholeSozDenemeler!=null){ myAdapter = new libraryRecyclerViewAdapter(this);
            myAdapter.setSozDenemesiArrayList(wholeSozDenemeler);}

        rV.setLayoutManager(new LinearLayoutManager(this));
        rV.setHasFixedSize(true);
        rV.setAdapter(myAdapter);
        Log.d("ERRRRORRRR", "buildRecyclerView: "+myAdapter);
        myAdapter.setOnItemClickListener(position ->{

            switch (select) {
                case "TYT":
                    if (isContainsTyt(wholeTytDenemeler.get(position), tytDenemeleri))
                        tytDenemeleri.remove(indexToBeDeletedOnGraph);
                    wholeTytDenemeler.remove(position);
                    break;
                case "SAY":
                    if (isContainsSay(wholeSayDenemeler.get(position), sayDenemeleri))
                        sayDenemeleri.remove(indexToBeDeletedOnGraph);
                    wholeSayDenemeler.remove(position);
                    break;
                case "EA":
                    if (isContainsEa(wholeEaDenemeler.get(position), eaDenemeleri))
                        eaDenemeleri.remove(indexToBeDeletedOnGraph);
                    wholeEaDenemeler.remove(position);
                    break;
                case "SOZ":
                    if (isContainsSoz(wholeSozDenemeler.get(position), sozDenemeleri))
                        sozDenemeleri.remove(indexToBeDeletedOnGraph);
                    wholeSozDenemeler.remove(position);
                    break;
                case "DIL":
                    if (isContainsDil(wholeDilDenemeler.get(position), dilDenemeleri))
                        dilDenemeleri.remove(indexToBeDeletedOnGraph);
                    wholeDilDenemeler.remove(position);
                    break;
            }
            saveData();
            removeItem(position);
        });

    }
    public boolean isContainsTyt(tytDenemesi wholeTyt,ArrayList<tytDenemesi> tyt){
        for (int i = 0; i <tyt.size() ; i++)
            if (tyt.get(i).getDenemeNumber()==wholeTyt.getDenemeNumber()){
                indexToBeDeletedOnGraph=i;
                return true;
            }
            return false;
    }
    public boolean isContainsSay(sayDenemesi wholeSay,ArrayList<sayDenemesi> say){
        for (int i = 0; i <say.size() ; i++)
            if (say.get(i).getDenemeNumber()==wholeSay.getDenemeNumber()){
                indexToBeDeletedOnGraph=i;
                return true;
            }
        return false;
    }
    public boolean isContainsEa(eaDenemesi wholeEa,ArrayList<eaDenemesi> ea){
        for (int i = 0; i <ea.size() ; i++)
            if (ea.get(i).getDenemeNumber()==wholeEa.getDenemeNumber()){
                indexToBeDeletedOnGraph=i;
                return true;
            }
        return false;
    }
    public boolean isContainsSoz(sozDenemesi wholeSoz,ArrayList<sozDenemesi> soz){
        for (int i = 0; i <soz.size() ; i++)
            if (soz.get(i).getDenemeNumber()==wholeSoz.getDenemeNumber()){
                indexToBeDeletedOnGraph=i;
                return true;
            }
        return false;
    }
    public boolean isContainsDil(dilDenemesi wholeDil,ArrayList<dilDenemesi> dil){
        for (int i = 0; i <dil.size() ; i++)
            if (dil.get(i).getDenemeNumber()==wholeDil.getDenemeNumber()){
                indexToBeDeletedOnGraph=i;
                return true;
            }
        return false;
    }
    void setDilDenemeleri(ArrayList<dilDenemesi> wholeDil,ArrayList<dilDenemesi> dil) { wholeDilDenemeler = wholeDil; dilDenemeleri=dil;}
    void setTytDenemeleri(ArrayList<tytDenemesi> wholeTyt,ArrayList<tytDenemesi> tyt) { wholeTytDenemeler = wholeTyt; tytDenemeleri=tyt;}
    void setEaDenemeleri(ArrayList<eaDenemesi>   wholeEa,ArrayList<eaDenemesi> ea)    { wholeEaDenemeler = wholeEa;   eaDenemeleri=ea;}
    void setSayDenemeleri(ArrayList<sayDenemesi> wholeSay,ArrayList<sayDenemesi> say) { wholeSayDenemeler = wholeSay; sayDenemeleri=say;}
    void setSozDenemeleri(ArrayList<sozDenemesi> wholeSoz,ArrayList<sozDenemesi> soz) { wholeSozDenemeler = wholeSoz; sozDenemeleri=soz;}

    private void loadData() {
        SharedPreferences sP = getSharedPreferences("denemeTakipSave",Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String jsonTyt = sP.getString("tytDenemeler_List", null);
        String jsonWholeTyt =sP.getString("tytWholeDenemeler_List",null);
        String jsonSay = sP.getString("sayDenemeler_List", null);
        String jsonWholeSay =sP.getString("sayWholeDenemeler_List",null);
        String jsonEa = sP.getString("eaDenemeler_List", null);
        String jsonWholeEa =sP.getString("eaWholeDenemeler_List",null);
        String jsonSoz = sP.getString("sozDenemeler_List", null);
        String jsonWholeSoz =sP.getString("sozWholeDenemeler_List",null);
        String jsonDil = sP.getString("dilDenemeler_List", null);
        String jsonWholeDil =sP.getString("dilWholeDenemeler_List",null);

        Type typeTYT = new TypeToken<ArrayList<tytDenemesi>>() {}.getType();
        Type typeSAY = new TypeToken<ArrayList<sayDenemesi>>() {}.getType();
        Type typeEA  = new TypeToken<ArrayList<eaDenemesi>>() {}.getType();
        Type typeSOZ = new TypeToken<ArrayList<sozDenemesi>>() {}.getType();
        Type typeDIL = new TypeToken<ArrayList<dilDenemesi>>() {}.getType();

        Log.d("selected","-> "+select+"- "+gson.fromJson(jsonWholeEa, typeEA));
        if (select.equals("TYT")){
            setTytDenemeleri( gson.fromJson(jsonWholeTyt, typeTYT),gson.fromJson(jsonTyt, typeTYT));}
        if (select.equals("SAY")){
            setSayDenemeleri( gson.fromJson(jsonWholeSay, typeSAY),gson.fromJson(jsonSay, typeSAY));}
        if (select.equals("EA")){
            setEaDenemeleri( gson.fromJson(jsonWholeEa, typeEA),gson.fromJson(jsonEa, typeEA));}
        if (select.equals("SOZ")){
            setSozDenemeleri( gson.fromJson(jsonWholeSoz, typeSOZ),gson.fromJson(jsonSoz, typeSOZ));}
        if (select.equals("DIL")){
            setDilDenemeleri( gson.fromJson(jsonWholeDil, typeDIL),gson.fromJson(jsonDil, typeDIL));}
    }

    private void saveData() {
        SharedPreferences sP = getSharedPreferences("denemeTakipSave", MODE_PRIVATE);
        SharedPreferences.Editor editor = sP.edit();
        Gson gson = new Gson();
        String jsonTyt,jsonWholeTyt,jsonSay,jsonWholeSay,jsonEa,jsonWholeEa,jsonSoz,jsonWholeSoz,jsonDil,jsonWholeDil;
        if (select.equals("TYT")){
            jsonTyt=gson.toJson(tytDenemeleri);
            jsonWholeTyt = gson.toJson(wholeTytDenemeler);
            editor.putString("tytDenemeler_List", jsonTyt);
            editor.putString("tytWholeDenemeler_List", jsonWholeTyt);
            Log.d("tytDenemeler saveData",tytDenemeleri.size()+"");
        }
        if (select.equals("SAY")){
            jsonSay=gson.toJson(sayDenemeleri);
            jsonWholeSay = gson.toJson(wholeSayDenemeler);
            editor.putString("sayDenemeler_List", jsonSay);
            editor.putString("sayWholeDenemeler_List", jsonWholeSay);
        }
        if (select.equals("EA")){
            jsonEa=gson.toJson(eaDenemeleri);
            jsonWholeEa = gson.toJson(wholeEaDenemeler);
            editor.putString("eaDenemeler_List", jsonEa);
            editor.putString("eaWholeDenemeler_List", jsonWholeEa);
        }
        if (select.equals("SOZ")){
            jsonSoz=gson.toJson(sozDenemeleri);
            jsonWholeSoz = gson.toJson(wholeSozDenemeler);
            editor.putString("sozDenemeler_List", jsonSoz);
            editor.putString("sozWholeDenemeler_List", jsonWholeSoz);
        }
        if (select.equals("DIL")) {
            jsonDil = gson.toJson(dilDenemeleri);
            jsonWholeDil = gson.toJson(wholeDilDenemeler);
            editor.putString("dilDenemeler_List", jsonDil);
            editor.putString("dilWholeDenemeler_List", jsonWholeDil);
        }
        editor.apply();
    }
}
