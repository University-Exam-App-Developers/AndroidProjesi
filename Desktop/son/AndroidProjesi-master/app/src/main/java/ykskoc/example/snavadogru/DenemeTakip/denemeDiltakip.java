package ykskoc.example.snavadogru.DenemeTakip;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.ykskoc.R;

import java.lang.reflect.Type;
import java.util.ArrayList;


public class denemeDiltakip extends AppCompatActivity implements denemeDIL_TakipPopUpAdding.dialog{
    FloatingActionButton addButton,libraryButton;
    ArrayList<dilDenemesi> Denemeler= new ArrayList<>();
    LineGraphSeries<DataPoint> values = new LineGraphSeries<>();
    StaticLabelsFormatter staticLabelsFormatter;
    ArrayList<String> namesArray= new ArrayList<>();
    denemeDIL_TakipPopUpAdding popUp;
    GraphView dilTakipChart;
    Animation appearance;
    int sizeStart=0;
  //  boolean isItFirst=true;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_denemeing);
        appearance= AnimationUtils.loadAnimation(this, R.anim.start_spawn);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.TYPE_STATUS_BAR);

        buildScreen();
    }
    public void buildScreen(){
        dilTakipChart = findViewById(R.id.ydLineChart);
        addButton = findViewById(R.id.floatAdd);
        libraryButton=findViewById(R.id.floatLibrary);

        loadData();
        addButton.setOnClickListener(view -> {
            popUp = new denemeDIL_TakipPopUpAdding();
            popUp.setIncrement(Denemeler.size()+1);
            reklamgoster();
            popUp.show(getSupportFragmentManager(), "Sinava Dogru");

        });
        libraryButton.setOnClickListener(view->{
            Intent i = new Intent(denemeDiltakip.this,denemeTakipLibrary.class);
            i.putExtra("key","DIL");
            startActivity(i);
        });
    }
    public void drawLineChart() {
        loadData();
        if (Denemeler.size() > 10){
            sizeStart=Denemeler.size()-10;
         //   isItFirst=true;
        }
        else  sizeStart=0;
        namesArray.clear();
        if (Denemeler.size()==0){namesArray.add(" "); namesArray.add(" ");}
        else
        {
            for (int i = 0; i < Denemeler.size()-sizeStart; i++){
                if (Denemeler.size()<4)
                    if (Denemeler.get(i+sizeStart).Name.length()<10)
                        namesArray.add(Denemeler.get(i+sizeStart).Name);
                    else
                        namesArray.add(Denemeler.get(i+sizeStart).Name.substring(0,10));
                else if (Denemeler.size()<6)
                    if (Denemeler.get(i+sizeStart).Name.length()<6)
                        namesArray.add(Denemeler.get(i+sizeStart).Name);
                    else
                        namesArray.add(Denemeler.get(i+sizeStart).Name.substring(0,5));
                else
                    if (Denemeler.get(i+sizeStart).Name.length()<4)
                        namesArray.add(Denemeler.get(i+sizeStart).Name);
                    else
                        namesArray.add(Denemeler.get(i+sizeStart).Name.substring(0,4));
                if (Denemeler.size()==1)
                    namesArray.add(" ");
            }
        }
            values= new LineGraphSeries<>(); dilTakipChart.removeAllSeries();
                for (int i = 0; i <Denemeler.size()-sizeStart ; i++)
                        values.appendData(new DataPoint(i,Denemeler.get(i+sizeStart).totalNet),true,500);

        staticLabelsFormatter = new StaticLabelsFormatter(dilTakipChart);
        staticLabelsFormatter.setHorizontalLabels(namesArray.toArray(new String[0]));
        values.setDrawBackground(true);
        values.setDrawDataPoints(true);
        values.setBackgroundColor(R.color.guzelbirlaci);
        values.setDataPointsRadius(15);
        values.setThickness(10);
        values.setColor(Color.WHITE);

        dilTakipChart.getViewport().setMinX(0);
        dilTakipChart.getViewport().setMaxY(80);
        dilTakipChart.getViewport().setMinY(-20);
        dilTakipChart.getViewport().setMaxX(Denemeler.size()-1-sizeStart);
        dilTakipChart.getViewport().setXAxisBoundsManual(true);
     //   dilTakipChart.getViewport().setDrawBorder(true);

        dilTakipChart.setTitleTextSize(40);
        dilTakipChart.setTitleColor(Color.WHITE);
        dilTakipChart.setTitle("YABANCI DİL DENEMELERİ");

        dilTakipChart.addSeries(values);
        dilTakipChart.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
        dilTakipChart.getGridLabelRenderer().setVerticalLabelsColor(Color.WHITE);
        dilTakipChart.getGridLabelRenderer().setHorizontalLabelsColor(Color.WHITE);
        dilTakipChart.getGridLabelRenderer().setGridColor(Color.WHITE);
        dilTakipChart.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.HORIZONTAL);
        dilTakipChart.getGridLabelRenderer().setTextSize(30);
    }
    @Override
    protected void onResume() {
        super.onResume();
      //  Log.d("onResume","onResume");
     //   isItFirst=true;
        drawLineChart();
    }
    private void saveData() {
        SharedPreferences sP = getSharedPreferences("denemeTakipSave", MODE_PRIVATE);
        SharedPreferences.Editor editor = sP.edit();
        Gson gson = new Gson();
        String json = gson.toJson(Denemeler);
        editor.putString("dilDenemeler_List", json);
        editor.apply();
    }

    private void loadData() {
        SharedPreferences sP = getSharedPreferences("denemeTakipSave", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sP.getString("dilDenemeler_List", null);
        Type type = new TypeToken<ArrayList<dilDenemesi>>() {}.getType();
        Denemeler = gson.fromJson(json, type);
//        Log.d("Denemeler",""+Denemeler);
        if (Denemeler == null)
            Denemeler = new ArrayList<>();
    }
    @Override
    public void set() {
        if (popUp.comfirm)
            Denemeler.add(popUp.getDeneme());
        saveData();
        drawLineChart();
    }
    public void reklamgoster(){
        InterstitialAd mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-7739380766735309/1906072524"); //test için olan id değiştirilecek unutma
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener(){
            @Override
            public void onAdLoaded() {
                mInterstitialAd.show();
                super.onAdLoaded();
            }
        });
    }
}