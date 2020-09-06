package com.example.snavadogru.Kendin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import com.example.snavadogru.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Locale;


public class zamanTakip extends AppCompatActivity {
    Bundle extras;
    int today,thisMonth;
    LineGraphSeries gunlukValue,aylikValue;
    private long[] gunlukGraphValues= new long[7];
    private long[] aylikGraphValues= new long[12];

    ArrayList<String> dayArray = new ArrayList<>();
    ArrayList<String> monthArray = new ArrayList<>();
    ArrayList<String> dayValueArray = new ArrayList<>();
    ArrayList<String> monthValueArray = new ArrayList<>();

    String[] dayNames = new String[]{"Pazartesi","Salı","Çarşamba","Perşembe","Cuma","Cumartesi","Pazar"};
    String[] monthNames = new String[]{"Ocak","Şubat","Mart","Nisan","Mayıs","Haziran","Temmuz","Ağustos","Eylül","Ekim","Kasım","Aralık"};
    GraphView gunlukGraph,aylikGraph;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zaman_takip);
        buildScreen();
        drawLineChart();
    }
    public void buildScreen(){
        gunlukGraph=findViewById(R.id.gunlukGraph);
        aylikGraph=findViewById(R.id.aylikGraph);

        gunlukValue = new LineGraphSeries();
        aylikValue = new LineGraphSeries();
        extras = getIntent().getExtras();
        gunlukGraphValues = extras.getLongArray("gunlukArray");
        aylikGraphValues = extras.getLongArray("aylikArray");
        today = extras.getInt("gunlukCount");
        thisMonth = extras.getInt("aylikCount");
        loadData();

    }
    private void drawLineChart(){

            Log.d("aylikCount",today+"-"+thisMonth);

            Log.d("gunlukGraphValues",dayValueArray.size()+"");
            Log.d("aylikGraphValues",monthValueArray.size()+"");
        for (int i = 0; i < 7;i++) {
                gunlukValue.appendData(new DataPoint(i, i), true, 500);
                if (gunlukGraphValues[today-1]==-1) break;
                if (i<2){
                dayArray.set(i,dayNames[today-1]);
                dayValueArray.set(i,updateCountDownText(gunlukGraphValues[today-1]));
                }
                else {
                    dayArray.add(dayNames[today-1]);
                    dayValueArray.add(i,updateCountDownText(gunlukGraphValues[today-1]));
                }
                Log.d("dayFor",""+dayArray.get(i));
                if (today==7)
                    today=1;
                else
                    today++;
            }
            for (int i = 0; i < 12; i++){
                    aylikValue.appendData(new DataPoint(i, i), true, 500);
                if (aylikGraphValues[thisMonth-1]==-1) break;
                    if (i<2){
                        monthArray.set(i,monthNames[thisMonth-1]);
                        monthValueArray.set(i,updateCountDownText(aylikGraphValues[thisMonth-1]));
                    }
                    else {
                        monthArray.add(monthNames[thisMonth-1]);
                        monthValueArray.add(updateCountDownText(aylikGraphValues[thisMonth-1]));
                    }
                Log.d("monthFor",""+monthValueArray.get(i));
                if (thisMonth==12)
                    thisMonth=1;
                else
                    thisMonth++;
            }


        StaticLabelsFormatter staticLabelsFormatter1 = new StaticLabelsFormatter(gunlukGraph);
        StaticLabelsFormatter staticLabelsFormatter2 = new StaticLabelsFormatter(aylikGraph);

        staticLabelsFormatter1.setHorizontalLabels(dayArray.toArray(new String[0]));
        staticLabelsFormatter1.setVerticalLabels(dayValueArray.toArray(new String[0]));
        staticLabelsFormatter2.setHorizontalLabels(monthArray.toArray(new String[0]));
        staticLabelsFormatter2.setVerticalLabels(monthValueArray.toArray(new String[0]));

        gunlukGraph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter1);
        gunlukGraph.getViewport().setMinX(0);
        gunlukGraph.getViewport().setMaxX(dayArray.size());
        gunlukGraph.getViewport().setMinY(0);
        gunlukGraph.getViewport().setXAxisBoundsManual(true);
        gunlukGraph.setHorizontalScrollBarEnabled(true);
        gunlukGraph.getViewport().setScrollable(true);

        gunlukValue.setColor(Color.GREEN);
        gunlukValue.setDrawDataPoints(true);
        gunlukValue.setDataPointsRadius(10);
        gunlukValue.setThickness(8);

        gunlukGraph.addSeries(gunlukValue);
        gunlukGraph.getGridLabelRenderer().setTextSize(30);
        gunlukGraph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter1);


        aylikGraph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter2);
        aylikGraph.getViewport().setMinX(0);
        aylikGraph.getViewport().setMaxX(monthArray.size());
        aylikGraph.getViewport().setMinY(0);
        aylikGraph.getViewport().setXAxisBoundsManual(true);
        aylikGraph.setHorizontalScrollBarEnabled(true);
        aylikGraph.getViewport().setScrollable(true);

        aylikValue.setColor(Color.GREEN);
        aylikValue.setDrawDataPoints(true);
        aylikValue.setDataPointsRadius(10);
        aylikValue.setThickness(8);

        aylikGraph.addSeries(aylikValue);
        aylikGraph.getGridLabelRenderer().setTextSize(30);
        aylikGraph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter2);
    saveData();
    }

    public String updateCountDownText(long numb){
        Log.d("updateCountDownText","numb "+numb);

        int hour,minute,second;
            hour=(int) (numb/1000)/3600;
            minute=(int) (numb/1000)%3600/60;
            second=(int) (numb/1000)%3600%60;
        return  String.format(Locale.getDefault(),"%02d:%02d:%02d",hour,minute,second);
    }

    private void saveData() {
        SharedPreferences sP= getSharedPreferences("zamanTakipgraphSave", Context.MODE_PRIVATE);

        try {
            SharedPreferences.Editor editor = sP.edit();

            Gson gson = new Gson();
            String jsonGunValue = gson.toJson(dayValueArray);
            String jsonAylikValue = gson.toJson(monthValueArray);
            String jsonGun = gson.toJson(dayArray);
            String jsonAylik = gson.toJson(monthArray);

            editor.putString("day_ValueArray",jsonGunValue);
            editor.putString("month_ValueArray",jsonAylikValue);
            editor.putString("day_Array",jsonGun);
            editor.putString("month_Array",jsonAylik);
            editor.apply();
        }catch (Exception e){
            e.getMessage();
        }
    }
    private void loadData() {
        SharedPreferences sP= getSharedPreferences("zamanTakipgraphSave", Context.MODE_PRIVATE);

        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        Gson gson = new Gson();

        String jsonGunValue = sP.getString("day_ValueArray",null);
        String jsonAylikValue = sP.getString("month_ValueArray",null);
        String jsonGun = sP.getString("day_Array",null);
        String jsonAylik = sP.getString("month_Array",null);

        dayValueArray = gson.fromJson(jsonGunValue, type);
        monthValueArray = gson.fromJson(jsonAylikValue, type);
        dayArray = gson.fromJson(jsonGun, type);
        monthArray = gson.fromJson(jsonAylik, type);

        if(dayArray==null){
            gunlukGraphValues= new long[7];
            dayArray= new ArrayList<>();
            dayValueArray=new ArrayList<>();
            dayArray.add("0"); dayArray.add("");
            dayValueArray.add(""); dayValueArray.add("");
        }
        if(monthArray==null){
            aylikGraphValues= new long[12];
            monthArray= new ArrayList<>();
            monthValueArray=new ArrayList<>();
            monthArray.add("0"); monthArray.add("");
            monthValueArray.add("");  monthValueArray.add("");
        }
        if (aylikGraph!=null)
            aylikGraph.removeAllSeries();
        if (gunlukGraph!=null)
            gunlukGraph.removeAllSeries();
    }
}
