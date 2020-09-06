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


public class denemeSAYtakip extends AppCompatActivity implements denemeSAY_TakipPopUpadding.dialog,denemetakip_deleting.dialogDeleting{
    ArrayList<LineGraphSeries<DataPoint>> values= new ArrayList<>();
    ArrayList<sayDenemesi> Denemeler= new ArrayList<>();
    ArrayList<sayDenemesi> wholeSayDenemeler= new ArrayList<>();
    ArrayList<GraphView> graphs = new ArrayList<>();
    ArrayList<String> namesArray = new ArrayList<>();
    StaticLabelsFormatter staticLabelsFormatter;
    denemetakip_deleting popUp_deleting;
    denemeSAY_TakipPopUpadding popUp;
    FloatingActionButton deleteButton,libraryButton,addButton;
    boolean isItFirst=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_denemesay);
        buildScreen();
    }
    public void buildScreen(){
        graphs.add(findViewById(R.id.deneme_sayGraph));
        graphs.add(findViewById(R.id.deneme_matsayGraph));
        graphs.add(findViewById(R.id.deneme_fizikGraph));
        graphs.add(findViewById(R.id.deneme_kimyaGraph));
        graphs.add(findViewById(R.id.deneme_bioGraph));

        addButton = findViewById(R.id.floatAdd);
        deleteButton = findViewById(R.id.floatDelete);
        libraryButton=findViewById(R.id.floatLibrary);
        for (int i = 0; i <5 ; i++)
            values.add(new LineGraphSeries<>());
        loadData();

        addButton.setOnClickListener(view -> {
            popUp = new denemeSAY_TakipPopUpadding();
            popUp.setNameIncrement(Denemeler.size()+1);
            popUp.show(getSupportFragmentManager(), "Sinava Dogru");
        });
        deleteButton.setOnClickListener(view -> {
            popUp_deleting = new denemetakip_deleting();
            popUp_deleting.setSayDenemeleri(Denemeler);
            popUp_deleting.show(getSupportFragmentManager(), "Sinava Dogru");
        });
        libraryButton.setOnClickListener(view->{
            Intent i = new Intent(denemeSAYtakip.this,denemeTakipLibrary.class);
            i.putExtra("key","SAY");
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
                values.get(1).appendData(new DataPoint(i, Denemeler.get(i).Mat), true, 500);
                values.get(2).appendData(new DataPoint(i, Denemeler.get(i).Fizik), true, 500);
                values.get(3).appendData(new DataPoint(i, Denemeler.get(i).Kimya), true, 500);
                values.get(4).appendData(new DataPoint(i, Denemeler.get(i).Bio), true, 500); } if (Denemeler.size()>0)isItFirst=false;}
        else{
            values.get(0).appendData(new DataPoint(Denemeler.size()-1  ,Denemeler.get(Denemeler.size()-1).totalNet),true,500);
            values.get(1).appendData(new DataPoint(Denemeler.size()-1  ,Denemeler.get(Denemeler.size()-1).Mat),true,500);
            values.get(2).appendData(new DataPoint(Denemeler.size()-1  ,Denemeler.get(Denemeler.size()-1).Fizik),true,500);
            values.get(3).appendData(new DataPoint(Denemeler.size()-1  ,Denemeler.get(Denemeler.size()-1).Kimya),true,500);
            values.get(4).appendData(new DataPoint(Denemeler.size()-1  ,Denemeler.get(Denemeler.size()-1).Bio),true,500);}

        for (int c = 0; c <graphs.size() ; c++) {
            staticLabelsFormatter = new StaticLabelsFormatter(graphs.get(c));
            staticLabelsFormatter.setHorizontalLabels(namesArray.toArray(new String[0]));

            graphs.get(c).getViewport().setMinX(0);
            graphs.get(c).getViewport().setMaxX(Denemeler.size()-1);
            graphs.get(c).getViewport().setMaxY(40);
            graphs.get(c).getViewport().setMinY(-10);
            graphs.get(c).getViewport().setXAxisBoundsManual(true);
            graphs.get(c).setHorizontalScrollBarEnabled(true);
            graphs.get(c).getViewport().setScrollable(true);

            values.get(c).setColor(Color.GREEN);
            values.get(c).setDrawDataPoints(true);
            values.get(c).setDataPointsRadius(10);
            values.get(c).setThickness(8);

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
        String jsonWhole = gson.toJson(wholeSayDenemeler);
        editor.putString("sayDenemeler_List", json);
        editor.putString("sayWholeDenemeler_List", jsonWhole);
        editor.apply();
    }

    private void loadData() {
        SharedPreferences sP = getSharedPreferences("denemeTakipSave", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sP.getString("sayDenemeler_List", null);
        String jsonWhole = sP.getString("sayWholeDenemeler_List", null);
        Type type = new TypeToken<ArrayList<sayDenemesi>>() {}.getType();
        Denemeler = gson.fromJson(json, type);
        wholeSayDenemeler= gson.fromJson(jsonWhole,type);
        if (Denemeler == null)
            Denemeler = new ArrayList<>();
        if (wholeSayDenemeler==null)
            wholeSayDenemeler=new ArrayList<>();
    }
    @Override
    public void set() {
        int i;
        sayDenemesi goingToAdd;
        Log.d("SAY DENEME SET",popUp.comfirm+"");
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
            wholeSayDenemeler.add(goingToAdd);
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
            wholeSayDenemeler.remove(popUp_deleting.deletableIndex());
        }
        isItFirst=true;
        saveData();
        buildScreen();
        drawLineChart();
    }

}
