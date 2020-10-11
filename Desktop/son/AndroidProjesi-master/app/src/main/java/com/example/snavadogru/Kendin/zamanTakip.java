package com.example.snavadogru.Kendin;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.WindowManager;

import com.example.snavadogru.Kendin.zamanTakipSqlite.zamanTakipMyDataBaseHelper;
import com.example.snavadogru.Kendin.zamanTakipSqlite.zamanTakipMyDataBaseHelper_Month;
import com.example.snavadogru.R;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.Series;

import java.util.ArrayList;
import java.util.Calendar;

public class zamanTakip extends AppCompatActivity {
    LineGraphSeries gunlukValue,aylikValue;
   // private DecimalFormat df = new DecimalFormat("0.00");
    private ArrayList<String> dayArray = new ArrayList<>();
    private ArrayList<String> monthArray = new ArrayList<>();
    zamanTakipMyDataBaseHelper_Month monthly_dataBaseHelper;
    zamanTakipMyDataBaseHelper daily_dataBaseHelper;
    private double[] gunlukGraphValues,aylikGraphValues;
    private int gunStart=0;
    private Calendar calendar;
    private String[] dayNames = new String[]{"Cumartesi","Pazar","Pazartesi","Salı","Çarşamba","Perşembe","Cuma"};
    private String[] monthNames = new String[]{"O","Ş","M","N","M","H","T","A","E","E","K","A"};
    GraphView gunlukGraph,aylikGraph;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zaman_takip);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.TYPE_STATUS_BAR);
        calendar=Calendar.getInstance();
        gunStart=calendar.get(Calendar.DAY_OF_YEAR);

        daily_dataBaseHelper= new zamanTakipMyDataBaseHelper(this);
        monthly_dataBaseHelper= new zamanTakipMyDataBaseHelper_Month(this);
        buildScreen();
        drawLineChart();
    }
    public void buildScreen(){
        gunlukGraph=findViewById(R.id.gunlukGraph);
        aylikGraph=findViewById(R.id.aylikGraph);

        gunlukValue = new LineGraphSeries();
        aylikValue = new LineGraphSeries();
    }
    private void drawLineChart(){
        gunlukValue= new LineGraphSeries<>(); gunlukGraph.removeAllSeries();
        Series lineSeries = new LineGraphSeries<>();
        aylikValue= new LineGraphSeries<>(); aylikGraph.removeAllSeries();
        getDbValues();

        for (int i = 0; i < gunlukGraphValues.length;i++){
                gunlukValue.appendData(new DataPoint(i, (gunlukGraphValues[i])), true, 500);
        //        dayValueArray.add(df.format((gunlukGraphValues[i])));
        }
         for (int i = 0; i < aylikGraphValues.length; i++){
                aylikValue.appendData(new DataPoint(i, (aylikGraphValues[i])), true, 500);
         //       monthValueArray.add(df.format(aylikGraphValues[i])+"");
         }

        if (gunlukGraphValues.length<2){
            if (dayArray.size()==0)
            {// dayValueArray.add("0");
                dayArray.add(dayNames[calendar.get(Calendar.DAY_OF_WEEK)-1]);}
         //   dayValueArray.add("");
            dayArray.add("");
        }
        if (aylikGraphValues.length<2){
            if (monthArray.size()==0)
            { //monthValueArray.add("0");
                monthArray.add(monthNames[calendar.get(Calendar.MONTH)]);}
         //   monthValueArray.add("");
            monthArray.add("");
        }

        StaticLabelsFormatter staticLabelsFormatter1 = new StaticLabelsFormatter(gunlukGraph);
        StaticLabelsFormatter staticLabelsFormatter2 = new StaticLabelsFormatter(aylikGraph);

        staticLabelsFormatter1.setHorizontalLabels(dayArray.toArray(new String[0]));
    //    staticLabelsFormatter1.setVerticalLabels(dayValueArray.toArray(new String[0]));
        staticLabelsFormatter2.setHorizontalLabels(monthArray.toArray(new String[0]));
    //    staticLabelsFormatter2.setVerticalLabels(monthValueArray.toArray(new String[0]));
   //     staticLabelsFormatter1.setVerticalLabels(dayVertical);
    //    staticLabelsFormatter2.setVerticalLabels(monthVertical);
        gunlukValue.setDrawBackground(true);
        gunlukValue.setDrawDataPoints(true);
        gunlukValue.setBackgroundColor(R.color.kendiniDeneGraph);
        gunlukValue.setDataPointsRadius(10);
        gunlukValue.setThickness(7);
        gunlukValue.setColor(Color.WHITE);

        gunlukGraph.getViewport().setMinX(0);
        gunlukGraph.getViewport().setMaxX(dayArray.size()-1);
        gunlukGraph.getViewport().setMinY(0);
        gunlukGraph.getViewport().setXAxisBoundsManual(true);
        gunlukGraph.setTitleTextSize(40);
        gunlukGraph.setTitleColor(Color.BLACK);
        gunlukGraph.setTitle("GÜNLÜK ZAMAN TAKİBİ");
        gunlukGraph.addSeries(gunlukValue);
        gunlukGraph.getGridLabelRenderer().setVerticalLabelsColor(Color.BLACK);
        gunlukGraph.getGridLabelRenderer().setGridColor(Color.WHITE);
        gunlukGraph.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.HORIZONTAL);
        gunlukGraph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter1);
        gunlukGraph.getGridLabelRenderer().setTextSize(30);


        aylikValue.setDrawBackground(true);
        aylikValue.setDrawDataPoints(true);
        aylikValue.setBackgroundColor(R.color.kendiniDeneGraph);
        aylikValue.setDataPointsRadius(10);
        aylikValue.setThickness(7);
        aylikValue.setColor(Color.WHITE);

        aylikGraph.getViewport().setMinX(0);
        aylikGraph.getViewport().setMaxX(monthArray.size()-1);
        aylikGraph.getViewport().setMinY(0);
        aylikGraph.getViewport().setDrawBorder(true);
        aylikGraph.getViewport().setXAxisBoundsManual(true);
        aylikGraph.setTitleTextSize(40);
        aylikGraph.setTitleColor(Color.BLACK);
        aylikGraph.setTitle("AYLIK ZAMAN TAKİBİ");
        aylikGraph.addSeries(aylikValue);
        aylikGraph.getGridLabelRenderer().setTextSize(30);
        aylikGraph.getGridLabelRenderer().setVerticalLabelsColor(Color.BLACK);
        aylikGraph.getGridLabelRenderer().setGridColor(Color.WHITE);
        aylikGraph.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.HORIZONTAL);
        aylikGraph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter2);
    }

    public void getDbValues(){
        Cursor cursorDaily =daily_dataBaseHelper.readAllData();
        Cursor cursorMonthly =monthly_dataBaseHelper.readAllData();
        int temp=0,cursorDayCount=cursorDaily.getCount(),cursorMonthCount=cursorMonthly.getCount(),weekOfDay;
        gunlukGraphValues= new double[cursorDayCount];
        aylikGraphValues= new double[cursorMonthCount];
        calendar.set(Calendar.DAY_OF_YEAR,gunStart-cursorDayCount+1);
        weekOfDay=calendar.get(Calendar.DAY_OF_WEEK);

        for (int i = cursorDayCount; i >0; i--) {
            cursorDaily.moveToNext();
            gunlukGraphValues[temp]=(double)cursorDaily.getInt(2)/3600;
            dayArray.add(dayNames[(weekOfDay+temp++)%7]);
        }temp=0;
        for (int i = cursorMonthCount; i >0 ; i--) {
            cursorMonthly.moveToNext();
            aylikGraphValues[temp++]=(double) cursorMonthly.getInt(2)/3600;
            monthArray.add(monthNames[cursorMonthly.getInt(1)]);
        }
        cursorDaily.close();
        cursorMonthly.close();
    }
}