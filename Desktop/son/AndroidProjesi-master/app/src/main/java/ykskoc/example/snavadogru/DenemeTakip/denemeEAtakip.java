package ykskoc.example.snavadogru.DenemeTakip;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.WindowManager;

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


public class denemeEAtakip extends AppCompatActivity implements denemeEA_TakipPopUpAdding.dialog{
    ArrayList<LineGraphSeries<DataPoint>> values= new ArrayList<>();
    ArrayList<eaDenemesi> Denemeler= new ArrayList<>();
    ArrayList<GraphView> graphs = new ArrayList<>();
    ArrayList<String> namesArray = new ArrayList<>();
    StaticLabelsFormatter staticLabelsFormatter;
    denemeEA_TakipPopUpAdding popUp;
    FloatingActionButton libraryButton,addButton;
    int sizeStart=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_denemeea);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.TYPE_STATUS_BAR);
        buildScreen();
    }
    public void buildScreen(){
        graphs.add(findViewById(R.id.deneme_eaGraph));
        graphs.add(findViewById(R.id.deneme_edebGraph));
        graphs.add(findViewById(R.id.deneme_tarihGraph));
        graphs.add(findViewById(R.id.deneme_cogGraph));
        graphs.add(findViewById(R.id.deneme_matGraph));

        addButton = findViewById(R.id.floatAdd);
        libraryButton=findViewById(R.id.floatLibrary);
        for (int i = 0; i <5 ; i++)
            values.add(new LineGraphSeries<>());
        loadData();

        addButton.setOnClickListener(view -> {
            popUp = new denemeEA_TakipPopUpAdding();
            popUp.setNameIncrement(Denemeler.size()+1);
            reklamgoster();
            popUp.show(getSupportFragmentManager(), "Sinava Dogru");
        });
        libraryButton.setOnClickListener(view->{
            Intent i = new Intent(denemeEAtakip.this,denemeTakipLibrary.class);
            i.putExtra("key","EA");
            startActivity(i);
        });
    }
    private void drawLineChart(){
        loadData();
        if (Denemeler.size() > 10){
            sizeStart=Denemeler.size()-10;
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

            for (int i = 0; i <graphs.size() ; i++){
                values.set(i,new LineGraphSeries<>());
                graphs.get(i).removeAllSeries();}
            for (int i = 0; i < Denemeler.size()-sizeStart; i++) {
                values.get(0).appendData(new DataPoint(i, Denemeler.get(i+sizeStart).totalNet), true, 500);
                values.get(1).appendData(new DataPoint(i, Denemeler.get(i+sizeStart).edebiyat), true, 500);
                values.get(2).appendData(new DataPoint(i, Denemeler.get(i+sizeStart).tarih), true, 500);
                values.get(3).appendData(new DataPoint(i, Denemeler.get(i+sizeStart).cografya), true, 500);
                values.get(4).appendData(new DataPoint(i, Denemeler.get(i+sizeStart).Mat), true, 500); }
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

            graphs.get(c).setTitleTextSize(40);
            graphs.get(c).setTitleColor(Color.WHITE);
            graphs.get(c).getViewport().setMaxX(Denemeler.size()-1-sizeStart);
            graphs.get(c).getViewport().setMaxY(40);
            graphs.get(c).getViewport().setMinY(-10);
            graphs.get(c).getViewport().setMinX(0);
            graphs.get(c).getViewport().setXAxisBoundsManual(true);

            values.get(c).setDrawDataPoints(true);
            values.get(c).setDrawBackground(true);
            values.get(c).setBackgroundColor(Color.argb(60,75,35,65));
            values.get(c).setColor(Color.WHITE);
            values.get(c).setDataPointsRadius(15);
            values.get(c).setThickness(10);

            graphs.get(c).addSeries(values.get(c));
            graphs.get(c).getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
            graphs.get(c).getGridLabelRenderer().setVerticalLabelsColor(Color.WHITE);
            graphs.get(c).getGridLabelRenderer().setHorizontalLabelsColor(Color.WHITE);
            graphs.get(c).getGridLabelRenderer().setGridColor(Color.WHITE);
            graphs.get(c).getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.HORIZONTAL);
            graphs.get(c).getGridLabelRenderer().setTextSize(30);

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    //    Log.d("onResume","onResume");
        drawLineChart();
    }
    private void saveData() {
        SharedPreferences sP = getSharedPreferences("denemeTakipSave", MODE_PRIVATE);
        SharedPreferences.Editor editor = sP.edit();
        Gson gson = new Gson();
        String json = gson.toJson(Denemeler);
        editor.putString("eaDenemeler_List", json);
        editor.apply();
    }

    private void loadData() {
        SharedPreferences sP = getSharedPreferences("denemeTakipSave", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sP.getString("eaDenemeler_List", null);
        Type type = new TypeToken<ArrayList<eaDenemesi>>() {}.getType();
        Denemeler = gson.fromJson(json, type);
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

