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

public class denemeSOZtakip extends AppCompatActivity implements denemeSOZ_TakipPopUpAdding.dialog,denemetakip_deleting.dialogDeleting{
    ArrayList<LineGraphSeries<DataPoint>> values= new ArrayList<>();
    ArrayList<sozDenemesi> Denemeler= new ArrayList<>();
    ArrayList<sozDenemesi> wholeSozDenemeler= new ArrayList<>();
    ArrayList<GraphView> graphs = new ArrayList<>();
    ArrayList<String> namesArray = new ArrayList<>();
    StaticLabelsFormatter staticLabelsFormatter;
    denemetakip_deleting popUp_deleting;
    denemeSOZ_TakipPopUpAdding popUp;
    FloatingActionButton deleteButton,libraryButton,addButton;
    boolean isItFirst=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_denemesoz);
        buildScreen();
    }
    public void buildScreen(){
        graphs.add(findViewById(R.id.deneme_sozGraph));
        graphs.add(findViewById(R.id.deneme_edebiyatGraph));
        graphs.add(findViewById(R.id.deneme_tarih_1Graph));
        graphs.add(findViewById(R.id.deneme_cog1Graph));
        graphs.add(findViewById(R.id.deneme_tarih_2Graph));
        graphs.add(findViewById(R.id.deneme_cog_2Graph));
        graphs.add(findViewById(R.id.deneme_felsefeGraph));
        graphs.add(findViewById(R.id.deneme_dinGraph));

        addButton = findViewById(R.id.floatAdd);
        deleteButton = findViewById(R.id.floatDelete);
        libraryButton=findViewById(R.id.floatLibrary);
        for (int i = 0; i <graphs.size() ; i++)
            values.add(new LineGraphSeries<>());
        loadData();

        addButton.setOnClickListener(view -> {
            popUp = new denemeSOZ_TakipPopUpAdding();
            popUp.setNameIncrement(Denemeler.size()+1);
            popUp.show(getSupportFragmentManager(), "Sinava Dogru");
        });
        deleteButton.setOnClickListener(view -> {
            popUp_deleting = new denemetakip_deleting();
            popUp_deleting.setSozDenemeleri(Denemeler);
            popUp_deleting.show(getSupportFragmentManager(), "Sinava Dogru");
        });
        libraryButton.setOnClickListener(view->{
            Intent i = new Intent(denemeSOZtakip.this,denemeTakipLibrary.class);
            i.putExtra("key","SOZ");
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
            for (int i = 0; i <graphs.size() ; i++){
                values.set(i,new LineGraphSeries<>());
                graphs.get(i).removeAllSeries();}
            for (int i = 0; i < Denemeler.size(); i++) {
                values.get(0).appendData(new DataPoint(i, Denemeler.get(i).totalNet), true, 500);
                values.get(1).appendData(new DataPoint(i, Denemeler.get(i).edebiyat), true, 500);
                values.get(2).appendData(new DataPoint(i, Denemeler.get(i).tarih_1), true, 500);
                values.get(3).appendData(new DataPoint(i, Denemeler.get(i).cog_1), true, 500);
                values.get(4).appendData(new DataPoint(i, Denemeler.get(i).tarih_2), true, 500);
                values.get(5).appendData(new DataPoint(i, Denemeler.get(i).cog_2), true, 500);
                values.get(6).appendData(new DataPoint(i, Denemeler.get(i).felsefe), true, 500);
                values.get(7).appendData(new DataPoint(i, Denemeler.get(i).din), true, 500);} if (Denemeler.size()>0)isItFirst=false;}
        else{
            values.get(0).appendData(new DataPoint(Denemeler.size()-1  ,Denemeler.get(Denemeler.size()-1).totalNet),true,500);
            values.get(1).appendData(new DataPoint(Denemeler.size()-1  ,Denemeler.get(Denemeler.size()-1).edebiyat),true,500);
            values.get(2).appendData(new DataPoint(Denemeler.size()-1  ,Denemeler.get(Denemeler.size()-1).tarih_1),true,500);
            values.get(3).appendData(new DataPoint(Denemeler.size()-1  ,Denemeler.get(Denemeler.size()-1).cog_1),true,500);
            values.get(4).appendData(new DataPoint(Denemeler.size()-1  ,Denemeler.get(Denemeler.size()-1).tarih_2),true,500);
            values.get(5).appendData(new DataPoint(Denemeler.size()-1  ,Denemeler.get(Denemeler.size()-1).cog_2),true,500);
            values.get(6).appendData(new DataPoint(Denemeler.size()-1  ,Denemeler.get(Denemeler.size()-1).felsefe),true,500);
            values.get(7).appendData(new DataPoint(Denemeler.size()-1  ,Denemeler.get(Denemeler.size()-1).din),true,500);}

        for (int c = 0; c <graphs.size() ; c++) {
            staticLabelsFormatter = new StaticLabelsFormatter(graphs.get(c));
            staticLabelsFormatter.setHorizontalLabels(namesArray.toArray(new String[0]));

            if (c==0)
                graphs.get(c).setTitle("SÖZEL NETLERİ");
            else if (c==1)
                graphs.get(c).setTitle("TÜRK DİLİ VE EDEBİYATI NETLERİ");
            else if (c==2)
                graphs.get(c).setTitle("TARİH I NETLERİ");
            else if (c==3)
                graphs.get(c).setTitle("COĞRAFYA I NETLERİ");
            else if (c==4)
                graphs.get(c).setTitle("TARİH II NETLERİ");
            else if (c==5)
                graphs.get(c).setTitle("COĞRAFYA II NETLERİ");
            else if (c==6)
                graphs.get(c).setTitle("FELSEFE NETLERİ");
            else if (c==7)
                graphs.get(c).setTitle("DİN KÜLTÜRÜ VE AHLAK BİLGİSİ NETLERİ");

            graphs.get(c).getViewport().setMinX(0);
            graphs.get(c).getViewport().setMaxX(Denemeler.size()-1);
            graphs.get(c).getViewport().setMaxY(40);
            graphs.get(c).getViewport().setMinY(-10);
            graphs.get(c).getViewport().setXAxisBoundsManual(true);
            graphs.get(c).setHorizontalScrollBarEnabled(true);
            graphs.get(c).getViewport().setScrollable(true);
            graphs.get(c).setTitleTextSize(40);

            values.get(c).setDrawBackground(true);
            values.get(c).setColor(Color.argb(60,21,67,96));
            values.get(c).setBackgroundColor(Color.argb(60,46,134,193));
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
        String jsonWhole = gson.toJson(wholeSozDenemeler);
        editor.putString("sozDenemeler_List", json);
        editor.putString("sozWholeDenemeler_List", jsonWhole);
        editor.apply();
    }

    private void loadData() {
        SharedPreferences sP = getSharedPreferences("denemeTakipSave", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sP.getString("sozDenemeler_List", null);
        String jsonWhole = sP.getString("sozWholeDenemeler_List", null);
        Type type = new TypeToken<ArrayList<sozDenemesi>>() {}.getType();
        Denemeler = gson.fromJson(json, type);
        wholeSozDenemeler= gson.fromJson(jsonWhole,type);

        if (Denemeler == null)
            Denemeler = new ArrayList<>();
        if (wholeSozDenemeler==null)
            wholeSozDenemeler=new ArrayList<>();
    }
    @Override
    public void set() {
        int i;
        sozDenemesi goingToAdd;
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
            wholeSozDenemeler.add(goingToAdd);
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
            wholeSozDenemeler.remove(popUp_deleting.deletableIndex());
        }
        isItFirst=true;
        saveData();
        buildScreen();
        drawLineChart();
    }
}
