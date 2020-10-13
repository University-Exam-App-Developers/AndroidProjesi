package ykskoc.example.snavadogru.SoruTakip;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;
import com.ykskoc.R;
import ykskoc.example.snavadogru.SoruTakip.main.Fragment_soru_takip_analiz;
import ykskoc.example.snavadogru.SoruTakip.main.Fragment_soru_takip_ayt;
import ykskoc.example.snavadogru.SoruTakip.main.Fragment_soru_takip_tyt;
import ykskoc.example.snavadogru.SoruTakip.main.SectionsPagerAdapter;
import ykskoc.example.snavadogru.SoruTakip.main.SoruTakipSqLite.SoruTakipMyDataBaseHelper;
import ykskoc.example.snavadogru.SoruTakip.main.SoruTakipSqLite.SoruTakipMyDataBaseHelper_Month;
import com.google.android.material.tabs.TabLayout;
import java.util.Calendar;

public class SoruTakip extends AppCompatActivity {
    private int bugun=0,buhafta=0,buay=0,buyil=0,kayitgun=0,kayithafta=0,kayitay=0,kayityil=0,valueSizeDay=0,valueSizeMonth=0;
    private SoruTakipMyDataBaseHelper_Month monthly_dataBaseHelper;
    private SoruTakipMyDataBaseHelper daily_dataBaseHelper;
    private SectionsPagerAdapter adapter;
    int[] aytSoruSayisi = new int[11];
    int[] tytSoruSayisi = new int[8];
    FragmentManager fragmentManager;
    boolean resetCounter=false;
    Calendar calendar;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        if (AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_YES)
            setTheme(R.style.AppTheme_DarkMode);
        else
            setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_takip_sorutakip);
        fragmentManager=this.getSupportFragmentManager();
        daily_dataBaseHelper= new SoruTakipMyDataBaseHelper(this);
        monthly_dataBaseHelper= new SoruTakipMyDataBaseHelper_Month(this);

        TabLayout tabLayout = findViewById(R.id.tablayout);
        ViewPager viewPager = findViewById(R.id.vPager);
        calendar = Calendar.getInstance();

        todayValueGet();
        loadDayData();
        update();
        saveDayData();
        saveData();

        adapter=new SectionsPagerAdapter(getSupportFragmentManager(),this);
        adapter.addFragment(new Fragment_soru_takip_tyt(this),"TYT");
        adapter.addFragment(new Fragment_soru_takip_ayt(this),"AYT");
        adapter.addFragment(new Fragment_soru_takip_analiz(),"ANALİZ");
        resetCounter=true;
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
          //      Log.d("onPageSelected",""+position);
                if(position==2){
                        adapter.listFragment.get(0).onDestroy();
                        adapter.listFragment.get(1).onDestroy();
                        adapter.listFragment.get(2).onStart();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        tabLayout.setupWithViewPager(viewPager,true);
    }

    private void update(){
        int changedYear=Math.abs(buyil-kayityil);
        int changedMonth=(buay+12-kayitay)%12;
        int changeWeek=(buhafta+52-kayithafta)%52;
        int changedDay=(bugun+365-kayitgun)%365;

     /*   Log.d("changedDay",""+kayitgun+"-"+bugun);
        Log.d("changedMonth",""+kayitay+"-"+buay);
        Log.d("changeWeek",""+kayithafta+"-"+buhafta);
        Log.d("changedYear",""+kayityil+"-"+buyil);*/

        new Runnable() {
            boolean loaded = false;
            @Override
            public void run() {
          //      Log.d("loaded", "loaded " + loaded);
                if (loaded) return;
            //    Log.d("changedYear", "changedYear " + changedYear);
                if (changedYear > 0) {
                    tytSoruSayisi = new int[8];
                    aytSoruSayisi = new int[11];
                    resetCounter=true;

                    if (changedYear > 1 || (buay - kayitay > 0)) {
                        daily_dataBaseHelper.removeAllData();
                        monthly_dataBaseHelper.removeAllData();
                        monthly_dataBaseHelper.addValue(buay, 0);
                        valueSizeMonth = 1;
                        daily_dataBaseHelper.addValue(bugun, 0);
                        valueSizeDay = 1;
                        return;
                    }
               //     Log.d("UPDATE", "changedYear " + changedYear);
                }
            //    Log.d("changedMonth", "changedMonth: "+changedMonth);
                if (changedMonth > 0) {
                    int temp = 0;
                    for (int i = 1; i < changedMonth + 1; i++)
                        if (valueSizeMonth == 12) {
                            monthly_dataBaseHelper.removeSingleData((kayitay + (temp++)) % 12);
                            monthly_dataBaseHelper.addValue((kayitay + i) % 12, 0);
                        } else {
                            monthly_dataBaseHelper.addValue((kayitay + i) % 12, 0);
                            valueSizeMonth++;
                        }
                    tytSoruSayisi = new int[8];
                    aytSoruSayisi = new int[11];
                    resetCounter=true;

               //     Log.d("UPDATE", "changedMonth " + changedMonth);
                    if (changedMonth > 1) {
                        daily_dataBaseHelper.removeAllData();
                        daily_dataBaseHelper.addValue(bugun, 0);
                        valueSizeDay = 1;
                        return;
                    }
                }
             //   Log.d("changeWeek", "changeWeek: "+changeWeek);
                if (changeWeek > 0) {  // Log.d("UPDATE","changeWeek "+changeWeek) ;
                    tytSoruSayisi = new int[8];
                    aytSoruSayisi = new int[11];
                    resetCounter=true;

                    if (changeWeek != 1 || changedDay > 6) {
                        daily_dataBaseHelper.removeAllData();
                        daily_dataBaseHelper.addValue(bugun, 0);
                        valueSizeDay = 1;
                        return;
                    }
                }
          //      Log.d("changedDay", "changedDay: "+changedDay);

                if (changedDay > 0) {
                    int temp = 0;
                    if (changedDay > 7)
                        daily_dataBaseHelper.removeAllData();
                    else
                        for (int i = 0; i < changedDay; i++)
                            if (valueSizeDay == 7) {
                                daily_dataBaseHelper.removeSingleData((bugun - (temp++) - 7));
                                daily_dataBaseHelper.addValue(((kayitgun + i) % 365) + 1, 0);
                            } else {
                                daily_dataBaseHelper.addValue(((kayitgun + i) % 365) + 1, 0);
                                valueSizeDay++;
                            }

                    tytSoruSayisi = new int[8];
                    aytSoruSayisi = new int[11];
                    resetCounter=true;
              //      Log.d("changedDay", "run:");
                }
                loaded=true;
            }
        }.run();
    }
    private void saveDayData(){
        SharedPreferences sP = this.getSharedPreferences("daySave101", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sP.edit();
        editor.putInt("year",buyil);
        editor.putInt("month",buay);
        editor.putInt("week",buhafta);
        editor.putInt("day",bugun);
        editor.putInt("valueSizeDay",valueSizeDay);
        editor.putInt("valueSizeMonth",valueSizeMonth);
        editor.apply();
    }
    private void loadDayData(){
        SharedPreferences sP= this.getSharedPreferences("daySave101", Context.MODE_PRIVATE);
        kayityil=sP.getInt("year",buyil);
        kayitay=sP.getInt("month",buay);
        kayithafta=sP.getInt("week",buhafta);
        kayitgun=sP.getInt("day",bugun);
        valueSizeDay=sP.getInt("valueSizeDay",0);
        valueSizeMonth=sP.getInt("valueSizeMonth",0);
    }
    private void todayValueGet(){
        Calendar calendar=Calendar.getInstance();
        bugun=calendar.get(Calendar.DAY_OF_YEAR);
        buhafta=calendar.get(Calendar.WEEK_OF_YEAR);
        buay=calendar.get(Calendar.MONTH);
        buyil=calendar.get(Calendar.YEAR);
    }
    private void saveData() {
        SharedPreferences sP = this.getSharedPreferences("soruSayilari102", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sP.edit();
        editor.putBoolean("reset",resetCounter);
        editor.apply();
    }
}
