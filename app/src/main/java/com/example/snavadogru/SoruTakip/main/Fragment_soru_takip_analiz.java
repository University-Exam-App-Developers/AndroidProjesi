package com.example.snavadogru.SoruTakip.main;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.snavadogru.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Fragment_soru_takip_analiz extends Fragment{
    View view;
    GraphView graph;
    GraphView graph2;
    private ArrayList<Double> dailyData = new ArrayList<>();
    private ArrayList<Double> monthlyData = new ArrayList<>();
    private ArrayList<String> xAxisDayArray = new ArrayList<>();
    private ArrayList<String> xAxisMonthArray = new ArrayList<>();
  //  String[] tempDay,tempMonth;
    double todayLastAdding=0;
    private int tytCozulenSorular,aytCozulenSorular;
    LineGraphSeries<DataPoint> seriesDaily,seriesMonthly;

    String[] dayNames = new String[]{"Pazar","Pazartesi","Salı","Çarşamba","Perşembe","Cuma","Cumartesi"};
    String[] monthNames = new String[]{"Ocak","Şubat","Mart","Nisan","Mayıs","Haziran","Temmuz","Ağustos","Eylül","Ekim","Kasım","Aralık"};
    StaticLabelsFormatter staticLabelsFormatter2,staticLabelsFormatter;
    int dateTime,monthTime;
    public Fragment_soru_takip_analiz(int date,int month){ dateTime=date; monthTime=month;}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view=inflater.inflate(R.layout.fragment_sorutakip_analiz,container,false);

        loadData();
        loadCozulenSorular();


        graph = view.findViewById(R.id.soruTakip_DailyGraph);
        graph2 = view.findViewById(R.id.soruTakip_Monthlygraph);

        seriesDaily = new LineGraphSeries<>();
        seriesMonthly = new LineGraphSeries<>();
        Log.d("buildGraph before",xAxisDayArray.get(0)+"-"+xAxisDayArray.size()+"> "+dailyData.size());
        Log.d("monthlyData before",xAxisMonthArray.get(0)+"-"+monthlyData.size()+"> "+monthlyData.size());
        buildGraph();

        return view;
    }

    public void buildGraph(){
        graph.removeAllSeries();
        graph2.removeAllSeries();
        seriesDaily = new LineGraphSeries<>();
        seriesMonthly = new LineGraphSeries<>();

        for (int i = 0; i <dailyData.size() ; i++)
            seriesDaily.appendData(new DataPoint(i, dailyData.get(i)), true, 1000);
        for (int i = 0; i <monthlyData.size() ; i++)
            seriesMonthly.appendData(new DataPoint(i, monthlyData.get(i)), true, 1000);


        staticLabelsFormatter = new StaticLabelsFormatter(graph);
        staticLabelsFormatter.setHorizontalLabels(xAxisDayArray.toArray(new String[0]));
        seriesDaily.setColor(Color.GREEN);
        seriesDaily.setDrawDataPoints(true);
        seriesDaily.setDataPointsRadius(10);
        seriesDaily.setThickness(8);

        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(7);
        graph.getViewport().setMinY(0);
        graph.getViewport().setXAxisBoundsManual(true);

        graph.addSeries(seriesDaily);
        graph.getGridLabelRenderer().setTextSize(30);
        graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);


        seriesMonthly.setColor(Color.GREEN);
        seriesMonthly.setDrawDataPoints(true);
        seriesMonthly.setDataPointsRadius(10);
        seriesMonthly.setThickness(8);

        graph2.getViewport().setMinX(0);
        graph2.getViewport().setMinY(0);
        graph2.getViewport().setMaxX(12);
        graph2.getViewport().setXAxisBoundsManual(true);

        staticLabelsFormatter2 = new StaticLabelsFormatter(graph2);
        staticLabelsFormatter2.setHorizontalLabels(xAxisMonthArray.toArray(new String [0]));

        graph2.getGridLabelRenderer().setTextSize(30);
        graph2.addSeries(seriesMonthly);
        graph2.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter2);
    }

    private void saveData() {

        SharedPreferences sP = getContext().getSharedPreferences("daily_MonthlyDataSave", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sP.edit();

        Gson gson = new Gson();
        String json = gson.toJson(dailyData);
        String json2 = gson.toJson(monthlyData);
        String jsonDaily = gson.toJson(xAxisDayArray);
        String jsonMonthly = gson.toJson(xAxisMonthArray);

        editor.putString("dailyData", json);
        editor.putString("monthlyData", json2);
        editor.putInt("todayAdded",(int)todayLastAdding);
        editor.putString("xAxisDailyData", jsonDaily);
        editor.putString("xAxisMonthlyData", jsonMonthly);

        editor.apply();
    }
    private void loadData() {
        SharedPreferences sP = getContext().getSharedPreferences("daily_MonthlyDataSave", Context.MODE_PRIVATE);
        SharedPreferences sPYks = getContext().getSharedPreferences("yks_Soru_SayilariSave", Context.MODE_PRIVATE);

        tytCozulenSorular= sPYks.getInt("tytSoruSayisi",0);
        aytCozulenSorular= sPYks.getInt("aytSoruSayisi",0);

        Gson gson = new Gson();
        String json = sP.getString("dailyData", null);
        String json2 = sP.getString("monthlyData", null);
        String jsonDaily = sP.getString("xAxisDailyData", null);
        String jsonMonthly = sP.getString("xAxisMonthlyData", null);

        Type type = new TypeToken<ArrayList<Double>>() {}.getType();
        dailyData = gson.fromJson(json, type);
        monthlyData = gson.fromJson(json2, type);

        Type type2 = new TypeToken<ArrayList<String>>() {}.getType();
        xAxisDayArray = gson.fromJson(jsonDaily, type2);
        xAxisMonthArray = gson.fromJson(jsonMonthly, type2);
        todayLastAdding=sP.getInt("todayAdded",0);

        if (dailyData==null){
            dailyData = new ArrayList<>();
            xAxisDayArray= new ArrayList<>();
            dailyData.add((double)tytCozulenSorular+aytCozulenSorular);
            tytCozulenSorular=0; aytCozulenSorular=0;
            xAxisDayArray.add(dayNames[dateTime]);
            xAxisDayArray.add("");
        }
        if(monthlyData==null){
            monthlyData = new ArrayList<>();
            xAxisMonthArray= new ArrayList<>();
            monthlyData.add(dailyData.get(dailyData.size()-1));
            xAxisMonthArray.add(monthNames[monthTime]);
            xAxisMonthArray.add("");
        }
        Log.d("dailyData",dailyData+"");
        Log.d("monthlyData",monthlyData+"");
    }

    private void loadCozulenSorular() {
            SharedPreferences sP2 = getContext().getSharedPreferences("yks_Soru_SayilariSave",Context.MODE_PRIVATE);
            tytCozulenSorular= sP2.getInt("tytSoruSayisi",0);
            aytCozulenSorular= sP2.getInt("aytSoruSayisi",0);
            update();
    }
    public int translateDay(String day){
        Log.d("Day",day+""+day.equals("Salı"));
        if (day.equals("Monday") || day.equals("Pazartesi"))
            return 1;
        else if (day.equals("Tuesday") || day.equals("Salı"))
            return 2;
        else if (day.equals("Wednesday") || day.equals("Çarşamba"))
            return 3;
        else if (day.equals("Thursday") || day.equals("Perşembe"))
            return 4;
        else if (day.equals("Friday") || day.equals("Cuma"))
            return 5;
        else if (day.equals("Saturday") || day.equals("Cumartesi"))
            return 6;
        else if (day.equals("Sunday") || day.equals("Pazar"))
            return 7;
        return -1;
    }

    public int translateMonth(String month){
            if (month.equals("Ocak"))
                return 1;
            else if (month.equals("Şubat"))
                return 2;
            else if (month.equals("Mart"))
                return 3;
            else if (month.equals("Nisan"))
                return 4;
            else if (month.equals("Mayıs"))
                return 5;
            else if (month.equals("Haziran"))
                return 6;
            else if (month.equals("Temmuz"))
                return 7;
            else if (month.equals("Ağustos"))
                return 8;
            else if (month.equals("Eylül"))
                return 9;
            else if (month.equals("Ekim"))
                return 10;
            else if (month.equals("Kasım"))
                return 11;
            else if (month.equals("Aralık"))
                return 12;

            return -1;

    }
    public void update(){
        Log.d("update","dateTime-> "+dateTime+" "+xAxisDayArray.get(xAxisDayArray.size()-1)+"->"+(!xAxisDayArray.get(xAxisDayArray.size()-1).equals(dayNames[dateTime])));
        if (!xAxisDayArray.get(xAxisDayArray.size()-1).equals(dayNames[dateTime]) && !xAxisDayArray.get(xAxisDayArray.size()-1).equals(""))
            fullBlank(0);

        if (!xAxisMonthArray.get(xAxisMonthArray.size()-1).equals(monthNames[monthTime]) && !xAxisMonthArray.get(xAxisMonthArray.size()-1).equals(""))
            fullBlank(1);

        if (dailyData.get(dailyData.size()-1)!=tytCozulenSorular+aytCozulenSorular)
            dailyData.set(dailyData.size()-1,(double)(tytCozulenSorular+aytCozulenSorular));
        if (todayLastAdding!=0){
            monthlyData.set(monthlyData.size()-1,(monthlyData.get(monthlyData.size()-1)-todayLastAdding+dailyData.get(dailyData.size()-1)));
            todayLastAdding=0;
        }
    }
    public void fullBlank(int k){
        int start=0;
        if (k==0)
        {
            start=translateDay(xAxisDayArray.get(xAxisDayArray.size()-1));
            while (start!=dateTime){
                if (dailyData.size()<7){
                        if (dailyData.size() == 1)
                            xAxisDayArray.set(1,dayNames[start-1]);
                        else
                            xAxisDayArray.add(dayNames[start-1]);
                    dailyData.add((double)tytCozulenSorular+aytCozulenSorular);
                    todayLastAdding=(double)tytCozulenSorular+aytCozulenSorular;
                    tytCozulenSorular=0;
                    aytCozulenSorular=0;
                }
                else
                {
                    dailyData.set(start-1,(double)tytCozulenSorular+aytCozulenSorular);
                    todayLastAdding=(double)tytCozulenSorular+aytCozulenSorular;
                    xAxisDayArray.set(start-1,dayNames[start-1]);
                    tytCozulenSorular=0;
                    aytCozulenSorular=0;
                }
                if (start==7)
                    start=1;
                else
                    start++;
            }
        }
        else if (k==1)
        {
            start=translateMonth(xAxisMonthArray.get(xAxisMonthArray.size()-1));
            while (start!=monthTime){
                if (monthlyData.size()<12) {
                    if (monthlyData.size() == 1)
                        xAxisMonthArray.set(1, monthNames[start - 1]);
                    else
                        xAxisMonthArray.add(monthNames[start - 1]);
                    monthlyData.add(dailyData.get(dailyData.size() - 1) + monthlyData.get(monthlyData.size() - 1) - todayLastAdding);
                }
                else{
                    monthlyData.set(start-1,dailyData.get(dailyData.size()-1)+monthlyData.get(monthlyData.size()-1)-todayLastAdding);
                    xAxisMonthArray.set(start-1,monthNames[start-1]);
                }
                if (start==12)
                        start=1;
                    else
                        start++;
            }
        }
        saveData();
    }
}