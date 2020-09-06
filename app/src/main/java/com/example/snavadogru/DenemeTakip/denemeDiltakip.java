package com.example.snavadogru.DenemeTakip;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
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


public class denemeDiltakip extends AppCompatActivity implements denemeDIL_TakipPopUpAdding.dialog,denemetakip_deleting.dialogDeleting{
    FloatingActionButton addButton, deleteButton,libraryButton;
    ArrayList<dilDenemesi> Denemeler= new ArrayList<>();
    ArrayList<dilDenemesi> wholeDilDenemeler= new ArrayList<>();
    LineGraphSeries<DataPoint> values = new LineGraphSeries<>();
    StaticLabelsFormatter staticLabelsFormatter;
    ArrayList<String> namesArray= new ArrayList<>();
    denemetakip_deleting popUpDeleting;
    denemeDIL_TakipPopUpAdding popUp;
    GraphView dilTakipChart;
    boolean isItFirst=true;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_denemeing);
        buildScreen();
    }
    public void buildScreen(){
        dilTakipChart = findViewById(R.id.ydLineChart);

        addButton = findViewById(R.id.floatAdd);
        deleteButton = findViewById(R.id.floatDelete);
        libraryButton=findViewById(R.id.floatLibrary);

        loadData();
        addButton.setOnClickListener(view -> {
            popUp = new denemeDIL_TakipPopUpAdding();
            popUp.setIncrement(Denemeler.size()+1);
            popUp.show(getSupportFragmentManager(), "Sinava Dogru");

        });
        deleteButton.setOnClickListener(view -> {
            popUpDeleting = new denemetakip_deleting();
            popUpDeleting.setDilDenemeleri(Denemeler);
            popUpDeleting.show(getSupportFragmentManager(), "Sinava Dogru");
            Log.d("BUILD NAME ONEMLIIIIII", "buildScreen: "+popUpDeleting);
        });
        libraryButton.setOnClickListener(view->{
            Intent i = new Intent(denemeDiltakip.this,denemeTakipLibrary.class);
            i.putExtra("key","DIL");
            startActivity(i);
        });
    }
    public void drawLineChart() {
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
            Log.d("namesArray",namesArray.size()+"");
        }
            if (isItFirst){ dilTakipChart.removeAllSeries(); values= new LineGraphSeries<>();
                for (int i = 0; i <Denemeler.size() ; i++)
                        values.appendData(new DataPoint(i,Denemeler.get(i).totalNet),true,500);
                if (Denemeler.size()>0)isItFirst=false;
            }
            else
                values.appendData(new DataPoint(Denemeler.size()-1  ,Denemeler.get(Denemeler.size()-1).totalNet),true,500);


        staticLabelsFormatter = new StaticLabelsFormatter(dilTakipChart);
        staticLabelsFormatter.setHorizontalLabels(namesArray.toArray(new String[0]));
        values.setColor(Color.GREEN);
        values.setDrawDataPoints(true);
        values.setDataPointsRadius(10);
        values.setThickness(8);

        dilTakipChart.getViewport().setMinX(0);
        dilTakipChart.getViewport().setMaxY(80);
        dilTakipChart.getViewport().setMinY(-20);
        dilTakipChart.getViewport().setMaxX(Denemeler.size()-1);
        dilTakipChart.getViewport().setXAxisBoundsManual(true);
        dilTakipChart.getGridLabelRenderer().setTextSize(30);
    //    dilTakipChart.getGridLabelRenderer().setNumHorizontalLabels(6);

        dilTakipChart.addSeries(values);
        dilTakipChart.getGridLabelRenderer().setTextSize(30);//
        dilTakipChart.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);


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
        String jsonWhole = gson.toJson(wholeDilDenemeler);
        editor.putString("dilDenemeler_List", json);
        editor.putString("dilWholeDenemeler_List", jsonWhole);
        editor.apply();
    }

    private void loadData() {
        SharedPreferences sP = getSharedPreferences("denemeTakipSave", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sP.getString("dilDenemeler_List", null);
        String jsonWhole = sP.getString("dilWholeDenemeler_List", null);
        Type type = new TypeToken<ArrayList<dilDenemesi>>() {}.getType();
        Denemeler = gson.fromJson(json, type);
        wholeDilDenemeler= gson.fromJson(jsonWhole,type);
        Log.d("Denemeler",""+Denemeler);
        if (Denemeler == null)
            Denemeler = new ArrayList<>();
        if (wholeDilDenemeler==null)
            wholeDilDenemeler=new ArrayList<>();
    }
    @Override
    public void set() {
        int i;
        dilDenemesi goingToAdd;
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
            wholeDilDenemeler.add(goingToAdd);
            Log.d("main","Denemeler "+Denemeler.size());
        }
        //    Log.d("SETED", "I SET THAT  - "+Denemeler.get(0).Name);
        saveData();
        drawLineChart();
    }

    @Override
    public void setDeleted() {
        if (popUpDeleting.doComfirm()){
            Denemeler.remove(popUpDeleting.deletableIndex());
            wholeDilDenemeler.remove(popUpDeleting.deletableIndex());
        }
        isItFirst=true;
        saveData();
        buildScreen();
        drawLineChart();
    }
}