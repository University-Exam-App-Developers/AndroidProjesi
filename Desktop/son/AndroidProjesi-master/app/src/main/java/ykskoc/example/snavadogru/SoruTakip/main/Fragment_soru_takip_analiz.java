package ykskoc.example.snavadogru.SoruTakip.main;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.ykskoc.R;
import ykskoc.example.snavadogru.SoruTakip.main.SoruTakipSqLite.SoruTakipMyDataBaseHelper;
import ykskoc.example.snavadogru.SoruTakip.main.SoruTakipSqLite.SoruTakipMyDataBaseHelper_Month;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import java.util.Calendar;
import java.util.Objects;

public class Fragment_soru_takip_analiz extends Fragment{
    private GraphView graph;
    private GraphView graph2;
    private Calendar calendar;
    private String[] xAxisDayArray;
    private String[] xAxisMonthArray;
    private int daySize=0,monthSize=0;
    private LineGraphSeries<DataPoint> seriesDaily,seriesMonthly;
    private int[] dayArray;
    private int[] monthArray;
    private int today,weekOfDay,monthOfYear,lastDay=0,lastMonth=0;
    private SoruTakipMyDataBaseHelper soruTakipMyDataBaseHelper;
    private SoruTakipMyDataBaseHelper_Month soruTakipMyDataBaseHelper_Month;
    private String[] dayNames = new String[]{"Cumartesi","Pazar","Pazartesi","Salı","Çarşamba","Perşembe","Cuma"};
    private String[] monthNames = new String[]{"O","Ş","M","N","M","H","T","A","E","E","K","A"};
    private StaticLabelsFormatter staticLabelsFormatter2,staticLabelsFormatter;
    public Fragment_soru_takip_analiz(){}


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_sorutakip_analiz, container, false);
        calendar= Calendar.getInstance();
        today= calendar.get(Calendar.DAY_OF_YEAR);
        soruTakipMyDataBaseHelper = new SoruTakipMyDataBaseHelper(getContext());
        soruTakipMyDataBaseHelper_Month = new SoruTakipMyDataBaseHelper_Month(getContext());
        graph = view.findViewById(R.id.soruTakip_DailyGraph);
        graph2 = view.findViewById(R.id.soruTakip_Monthlygraph);
        seriesDaily = new LineGraphSeries<>();
        seriesMonthly = new LineGraphSeries<>();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        fill();
        graph.removeAllSeries();
        graph2.removeAllSeries();
        seriesDaily = new LineGraphSeries<>();
        seriesMonthly = new LineGraphSeries<>();
        buildGraph();
    }

    private void buildGraph(){
        new Runnable(){
            boolean runnable=true;
            @Override
            public void run() {
                if (!runnable)
                    return;
                for (int i = 0; i <daySize ; i++)
                    seriesDaily.appendData(new DataPoint(i, dayArray[i]), true, 1000);

                for (int i = 0; i <monthSize ; i++)
                    seriesMonthly.appendData(new DataPoint(i, monthArray[i]), true, 1000);

                String temp;
                if (daySize<2){
                    if(daySize==1)  temp=xAxisDayArray[0];
                    else  temp="";
                    xAxisDayArray=new String[2];
                    xAxisDayArray[1]=""; xAxisDayArray[0]=temp;
                }
                if (monthSize<2){
                    if(daySize==1)  temp=xAxisMonthArray[0];
                    else temp="";
                    xAxisMonthArray=new String[2];
                    xAxisMonthArray[1]=""; xAxisMonthArray[0]=temp;
                }

                staticLabelsFormatter = new StaticLabelsFormatter(graph);
                staticLabelsFormatter.setHorizontalLabels(xAxisDayArray);

                staticLabelsFormatter2 = new StaticLabelsFormatter(graph2);
                staticLabelsFormatter2.setHorizontalLabels(xAxisMonthArray);

                seriesDaily.setDrawBackground(true);
                seriesDaily.setBackgroundColor(Color.argb(60,29,131,72));
                seriesDaily.setColor(Color.GREEN);
                seriesDaily.setDrawDataPoints(true);
                seriesDaily.setDataPointsRadius(10);
                seriesDaily.setThickness(8);

                graph.getViewport().setMinX(0);
                graph.getViewport().setMaxX(daySize-1);
                graph.getViewport().setMinY(0);
                graph.getViewport().setXAxisBoundsManual(true);
                graph.setTitle("GÜNLÜK SORU TAKİBİ");
                graph.setTitleTextSize(40);
                graph.addSeries(seriesDaily);
                graph.getGridLabelRenderer().setTextSize(30);
                graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);


                seriesMonthly.setDrawBackground(true);
                seriesMonthly.setBackgroundColor(Color.argb(60,29,131,72));
                seriesMonthly.setColor(Color.GREEN);
                seriesMonthly.setDrawDataPoints(true);
                seriesMonthly.setDataPointsRadius(10);
                seriesMonthly.setThickness(8);

                graph2.getViewport().setMinX(0);
                graph2.getViewport().setMinY(0);
                graph2.getViewport().setMaxX(monthSize-1);
                graph2.getViewport().setXAxisBoundsManual(true);
                graph2.setTitle("AYLIK SORU TAKİBİ");
                graph2.setTitleTextSize(40);
                graph2.getGridLabelRenderer().setTextSize(30);
                graph2.addSeries(seriesMonthly);
                graph2.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter2);
                runnable=false;
            }
        }.run();
    }
    public void fill(){
        loadData();
        Cursor dayCursor = soruTakipMyDataBaseHelper.readAllData();
        Cursor monthCursor = soruTakipMyDataBaseHelper_Month.readAllData();
        daySize=dayCursor.getCount(); monthSize=monthCursor.getCount();
        calendar.set(Calendar.DAY_OF_YEAR,(today-daySize+1+355)%355);
        weekOfDay=calendar.get(Calendar.DAY_OF_WEEK);
        monthOfYear=(Calendar.getInstance().get(Calendar.MONTH)+1-monthSize+12)%12;

        dayArray=new int[daySize];
        monthArray=new int[monthSize];
        xAxisDayArray=new String[daySize];
        xAxisMonthArray=new String[monthSize];

        int temp=0;
        for (int i = daySize-1; i >0; i--) {
            dayCursor.moveToNext();
            xAxisDayArray[temp]=dayNames[(weekOfDay+temp)%7]+"";
            dayArray[temp++]=dayCursor.getInt(2);
        }
        if (daySize>0){
            xAxisDayArray[temp]=dayNames[(weekOfDay+temp)%7]+"";
            dayArray[temp]=lastDay;
        }

        temp=0;
        for (int i = monthSize-1; i >0 ; i--) {
            monthCursor.moveToNext();
            xAxisMonthArray[temp]=monthNames[(monthOfYear+temp)%12];
            monthArray[temp++]=monthCursor.getInt(2);
        }
        if (monthSize>0){
            xAxisMonthArray[temp]=monthNames[(monthOfYear+temp)%12];
            monthArray[temp]=lastMonth;}
        monthCursor.close();
        dayCursor.close();

    }
    private void loadData() {
        SharedPreferences sP = Objects.requireNonNull(getContext()).getSharedPreferences("soruSayilari102", Context.MODE_PRIVATE);
        lastDay=sP.getInt("tytToplam",0)+sP.getInt("aytToplam",0);
        lastMonth=sP.getInt("lastAddedMonth", 0)-sP.getInt("lastAddedDay", 0)+lastDay;
    }
}