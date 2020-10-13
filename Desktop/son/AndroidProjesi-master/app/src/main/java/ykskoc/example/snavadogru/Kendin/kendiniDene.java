package ykskoc.example.snavadogru.Kendin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.text.Html;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import ykskoc.example.snavadogru.Kendin.ProgramYap.programYap;
import ykskoc.example.snavadogru.Kendin.zamanTakipSqlite.zamanTakipMyDataBaseHelper;
import ykskoc.example.snavadogru.Kendin.zamanTakipSqlite.zamanTakipMyDataBaseHelper_Month;
import com.ykskoc.R;

import android.widget.TextView;
import android.widget.Button;


public class kendiniDene extends AppCompatActivity implements addingPopUp.dialog{
    Intent i;
    ViewPager viewPager;
    Button sureTakip,programYapButton,sifirla;
    LinearLayout sliderDotsPanel;
    ViewPagerAdapter viewPagerAdapter;
    TextView aylikTime,haftalikTime,gunlukTime;
    private zamanTakipMyDataBaseHelper daily_dataBaseHelper;
    private zamanTakipMyDataBaseHelper_Month monthly_dataBaseHelper;
    TextView[] dots = new TextView[2];
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kendinidene);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.TYPE_STATUS_BAR);
        viewPager=findViewById(R.id.viewPager);
        sliderDotsPanel=findViewById(R.id.dotLayout);
        aylikTime=findViewById(R.id.aylikKayit);
        haftalikTime=findViewById(R.id.haftalikKayit);
        gunlukTime=findViewById(R.id.gunlukKayit);
        programYapButton=findViewById(R.id.programYap);
        sureTakip=findViewById(R.id.suretakip);
        sifirla=findViewById(R.id.sifirla);
        daily_dataBaseHelper= new zamanTakipMyDataBaseHelper(this);
        monthly_dataBaseHelper= new zamanTakipMyDataBaseHelper_Month(this);

        viewPagerAdapter = new ViewPagerAdapter(this,getSupportFragmentManager(),aylikTime,haftalikTime,gunlukTime);

        viewPager.setAdapter(viewPagerAdapter);
        addDotsIndicator(0);

        viewPager.addOnPageChangeListener(viewListener);

        sureTakip.setOnClickListener(v -> {
            viewPagerAdapter.reklamgoster();
            viewPagerAdapter.updateDbValues();
            didTapButton(v,R.id.suretakip);
            i= new Intent(kendiniDene.this,zamanTakip.class);
            startActivity(i);
        });

        programYapButton.setOnClickListener(v -> {
            didTapButton(v,R.id.programYap);
            i= new Intent(kendiniDene.this, programYap.class);
            startActivity(i);
        });

        sifirla.setOnClickListener(v->{
            didTapButton(v,R.id.sifirla);
            daily_dataBaseHelper.removeAllData();
            monthly_dataBaseHelper.removeAllData();
            aylikTime.setText("00:00:00");
            haftalikTime.setText("00:00:00");
            gunlukTime.setText("00:00:00");
            saveDayData();
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        viewPagerAdapter.updateDbValues();
    //    Log.d("KANKA ","KAPATIYORUM BİRAZDAN");
    }

    public void addDotsIndicator(int position){
        for (int i = 0; i <2 ; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(40);
            dots[i].setTextColor(getResources().getColor(R.color.dotWhite));
            sliderDotsPanel.addView(dots[i]);
        }
            dots[position].setTextColor(getResources().getColor(R.color.White));
    }
    ViewPager.OnPageChangeListener viewListener =new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

        @Override
        public void onPageSelected(int position) {
      //      Log.d("position","pos"+position);
            if (position==0)
            {
                dots[1].setTextColor(getResources().getColor(R.color.dotWhite));
                dots[0].setTextColor(getResources().getColor(R.color.White));
                if (viewPagerAdapter.isCountDownRun)
                    {viewPagerAdapter.countDownStartStop();}
            }
            else if (position==1)
            {
                dots[0].setTextColor(getResources().getColor(R.color.dotWhite));
                dots[1].setTextColor(getResources().getColor(R.color.White));
                if (viewPagerAdapter.isChronometerRun)
                {viewPagerAdapter.chronometerStartStop();}
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {}
    };

    @Override
    public void set() {
        viewPagerAdapter.set();
    }
    public void didTapButton(View view, int recourceID) {
        Button button = view.findViewById(recourceID);
        Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce_button);
        button.startAnimation(myAnim);
    }
    private void saveDayData(){
        SharedPreferences sP = getSharedPreferences("SaveZamanTakipAlfa6", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sP.edit();
        editor.putInt("haftalikTime",0);
        editor.putInt("gunlukZaman",0);
        editor.putInt("aylikZaman",0);
        editor.apply();
    }
}