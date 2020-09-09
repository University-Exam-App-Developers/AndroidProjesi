package com.example.snavadogru.DenemeTakip;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;

import com.example.snavadogru.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.lang.reflect.Type;
import java.util.ArrayList;


public class denemeEAtakip extends AppCompatActivity implements denemeEA_TakipPopUpAdding.dialog,denemetakip_deleting.dialogDeleting{
    ArrayList<LineGraphSeries<DataPoint>> values= new ArrayList<>();
    ArrayList<eaDenemesi> Denemeler= new ArrayList<>();
    ArrayList<eaDenemesi> wholeEaDenemeler= new ArrayList<>();
    ArrayList<GraphView> graphs = new ArrayList<>();
    ArrayList<String> namesArray = new ArrayList<>();
    StaticLabelsFormatter staticLabelsFormatter;
    denemetakip_deleting popUp_deleting;
    denemeEA_TakipPopUpAdding popUp;
    FloatingActionButton deleteButton,libraryButton,addButton;
    boolean isItFirst=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_denemeea);
        buildScreen();
    }
    public void buildScreen(){
        graphs.add(findViewById(R.id.deneme_eaGraph));
        graphs.add(findViewById(R.id.deneme_edebGraph));
        graphs.add(findViewById(R.id.deneme_tarihGraph));
        graphs.add(findViewById(R.id.deneme_cogGraph));
        graphs.add(findViewById(R.id.deneme_matGraph));

        addButton = findViewById(R.id.floatAdd);
        deleteButton = findViewById(R.id.floatDelete);
        libraryButton=findViewById(R.id.floatLibrary);
        for (int i = 0; i <5 ; i++)
            values.add(new LineGraphSeries<>());
        loadData();

        addButton.setOnClickListener(view -> {
            popUp = new denemeEA_TakipPopUpAdding();
            popUp.setNameIncrement(Denemeler.size()+1);
            popUp.show(getSupportFragmentManager(), "Sinava Dogru");
        });
        deleteButton.setOnClickListener(view -> {
            popUp_deleting = new denemetakip_deleting();
            popUp_deleting.setEaDenemeleri(Denemeler);
            popUp_deleting.show(getSupportFragmentManager(), "Sinava Dogru");
            Log.d("BUILD NAME ONEMLIIIIII", "TYT buildScreen: "+popUp_deleting);
        });
        libraryButton.setOnClickListener(view->{
            Intent i = new Intent(denemeEAtakip.this,denemeTakipLibrary.class);
            i.putExtra("key","EA");
            startActivity(i);
        });
    }
    private void drawLineChart(){
        loadData();
        if (Denemeler.size()==0){namesArray.add(" "); namesArray.add(" ");}
        else
        { namesArray.clear();
            for (int i = 0; i < Denemeler.size(); i++){
                if (Denemeler.size()<4)
                    if (Denemeler.get(i).Name.length()<10)
                        namesArray.add(Denemeler.get(i).Name);
                    else
                        namesArray.add(Denemeler.get(i).Name.substring(0,10));
                else if (Denemeler.size()<5)
                    if (Denemeler.get(i).Name.length()<9)
                        namesArray.add(Denemeler.get(i).Name);
                    else
                        namesArray.add(Denemeler.get(i).Name.substring(0,9));
                else if (Denemeler.size()<6)
                    if (Denemeler.get(i).Name.length()<6)
                        namesArray.add(Denemeler.get(i).Name);
                    else
                        namesArray.add(Denemeler.get(i).Name.substring(0,6));
                else if (Denemeler.size()<7)
                    if (Denemeler.get(i).Name.length()<5)
                        namesArray.add(Denemeler.get(i).Name);
                    else
                        namesArray.add(Denemeler.get(i).Name.substring(0,5));
                else
                if (Denemeler.get(i).Name.length()<4)
                    namesArray.add(Denemeler.get(i).Name);
                else
                    namesArray.add(Denemeler.get(i).Name.substring(0,4));

                if (Denemeler.size()==1)
                    namesArray.add(" ");
            }
        }

        if (isItFirst) {
            for (int i = 0; i <5 ; i++){
                values.set(i,new LineGraphSeries<>());
                graphs.get(i).removeAllSeries();}
            for (int i = 0; i < Denemeler.size(); i++) {
                values.get(0).appendData(new DataPoint(i, Denemeler.get(i).totalNet), true, 500);
                values.get(1).appendData(new DataPoint(i, Denemeler.get(i).edebiyat), true, 500);
                values.get(2).appendData(new DataPoint(i, Denemeler.get(i).tarih), true, 500);
                values.get(3).appendData(new DataPoint(i, Denemeler.get(i).cografya), true, 500);
                values.get(4).appendData(new DataPoint(i, Denemeler.get(i).Mat), true, 500); } if (Denemeler.size()>0)isItFirst=false;}
        else{
            values.get(0).appendData(new DataPoint(Denemeler.size()-1  ,Denemeler.get(Denemeler.size()-1).totalNet),true,500);
            values.get(1).appendData(new DataPoint(Denemeler.size()-1  ,Denemeler.get(Denemeler.size()-1).edebiyat),true,500);
            values.get(2).appendData(new DataPoint(Denemeler.size()-1  ,Denemeler.get(Denemeler.size()-1).tarih),true,500);
            values.get(3).appendData(new DataPoint(Denemeler.size()-1  ,Denemeler.get(Denemeler.size()-1).cografya),true,500);
            values.get(4).appendData(new DataPoint(Denemeler.size()-1  ,Denemeler.get(Denemeler.size()-1).Mat),true,500);}

        for (int c = 0; c <graphs.size() ; c++) {
            staticLabelsFormatter = new StaticLabelsFormatter(graphs.get(c));
            staticLabelsFormatter.setHorizontalLabels(namesArray.toArray(new String[0]));

            if (c==0)
                graphs.get(c).setTitle("EŞİT AĞIRLIK NETLERİ");
            else if (c==1)
                graphs.get(c).setTitle("MATEMATİK NETLERİ");
            else if (c==2)
                graphs.get(c).setTitle("TÜRK DİLİ VE EDEBİYATI NETLERİ");
            else if (c==3)
                graphs.get(c).setTitle("TARİH NETLERİ");
            else if (c==4)
                graphs.get(c).setTitle("COĞRAFYA NETLERİ");

            graphs.get(c).getViewport().setMinX(0);
            graphs.get(c).getViewport().setMaxX(Denemeler.size()-1);
            graphs.get(c).getViewport().setMaxY(40);
            graphs.get(c).getViewport().setMinY(-10);
            graphs.get(c).getViewport().setXAxisBoundsManual(true);
            graphs.get(c).setHorizontalScrollBarEnabled(true);
            graphs.get(c).getViewport().setScrollable(true);
            graphs.get(c).setTitleTextSize(40);

            values.get(c).setDrawBackground(true);
            values.get(c).setBackgroundColor(Color.argb(60,82,190,128));
            values.get(c).setColor(Color.GREEN);
            values.get(c).setDrawDataPoints(true);
            values.get(c).setDataPointsRadius(15);
            values.get(c).setThickness(10);

            graphs.get(c).addSeries(values.get(c));
            graphs.get(c).getGridLabelRenderer().setTextSize(30);
            graphs.get(c).getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);

        }
        Log.d("DIKKAT"," BURAYA DA GELECEK");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("onResume","onResume");
        isItFirst=true;
        drawLineChart();
    }
    private void saveData() {
        SharedPreferences sP = getSharedPreferences("denemeTakipSave", MODE_PRIVATE);
        SharedPreferences.Editor editor = sP.edit();
        Gson gson = new Gson();
        String json = gson.toJson(Denemeler);
        String jsonWhole = gson.toJson(wholeEaDenemeler);
        editor.putString("eaDenemeler_List", json);
        editor.putString("eaWholeDenemeler_List", jsonWhole);
        editor.apply();
    }

    private void loadData() {
        SharedPreferences sP = getSharedPreferences("denemeTakipSave", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sP.getString("eaDenemeler_List", null);
        String jsonWhole = sP.getString("eaWholeDenemeler_List", null);
        Type type = new TypeToken<ArrayList<eaDenemesi>>() {}.getType();
        Denemeler = gson.fromJson(json, type);
        wholeEaDenemeler= gson.fromJson(jsonWhole,type);

        if (Denemeler == null)
            Denemeler = new ArrayList<>();
        if (wholeEaDenemeler==null)
            wholeEaDenemeler=new ArrayList<>();
        Log.d("wholeEaDenemeler",""+(wholeEaDenemeler==null));
    }
    @Override
    public void set() {
        int i;
        eaDenemesi goingToAdd;
        if (popUp.comfirm){
            goingToAdd=popUp.getDeneme();
            Log.d("main","set "+goingToAdd);
            if (Denemeler.size()<10)
                Denemeler.add(goingToAdd);
            else{
                for (i = 0; i <Denemeler.size()-1 ; i++)
                    Denemeler.set(i,Denemeler.get(i+1));
                Denemeler.set(i,goingToAdd);
            }
            wholeEaDenemeler.add(goingToAdd);
            Log.d("main","Denemeler "+Denemeler.size());
        }
        //    Log.d("SETED", "I SET THAT  - "+Denemeler.get(0).Name);
        saveData();
        drawLineChart();
    }

    @Override
    public void setDeleted() {
        if (popUp_deleting.doComfirm()){
            Denemeler.remove(popUp_deleting.deletableIndex());
            wholeEaDenemeler.remove(popUp_deleting.deletableIndex());
        }
        isItFirst=true;
        saveData();
        buildScreen();
        drawLineChart();
    }
}

