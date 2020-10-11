package com.example.snavadogru.SoruTakip.main;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.snavadogru.SoruTakip.main.SoruTakipSqLite.SoruTakipMyDataBaseHelper;
import com.example.snavadogru.SoruTakip.main.SoruTakipSqLite.SoruTakipMyDataBaseHelper_Month;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter /*implements Fragment_soru_takip_analiz.getCalculated*/{

    public ArrayList<Fragment> listFragment = new ArrayList<>();
    private ArrayList<String> listFragmentName = new ArrayList<>();
    SoruTakipMyDataBaseHelper daily_dataBaseHelper;
    SoruTakipMyDataBaseHelper_Month monthly_dataBaseHelper;
    private int bugun=0,buay=0,calculated=0,lastAddedDay=0,lastAddedMonth=0;
    Context context;

    public SectionsPagerAdapter(FragmentManager fm,Context context) {
        super(fm,fm.getBackStackEntryCount());
        this.context=context;
        Calendar calendar= Calendar.getInstance();
        this.buay=calendar.get(Calendar.MONTH);
        this.bugun=calendar.get(Calendar.DAY_OF_YEAR);
        daily_dataBaseHelper= new SoruTakipMyDataBaseHelper(context);
        monthly_dataBaseHelper= new SoruTakipMyDataBaseHelper_Month(context);
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        return listFragment.get(position);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {return listFragmentName.get(position);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        super.destroyItem(container, position, object);
      //  Log.d("SectionsPagerAdapter","Destroyed "+ position);

        loadData();
      //  Log.d("calculated", "destroyItem: ."+calculated);
        updateValue();
    }

    @Override
    public int getCount() {return listFragment.size();}
    public void addFragment(Fragment f,String name){
        listFragment.add(f);
        listFragmentName.add(name);
    }
    private void updateValue(){

        new Runnable(){
            boolean runnable=true;
            @Override
            public void run() {
                if (!runnable)
                    return;
                int temp=0,tempDaily=0;
                loadData();
                Cursor cursorDaily =daily_dataBaseHelper.queryItem(bugun);
                Cursor cursorMonth =monthly_dataBaseHelper.queryItem(buay);
                cursorMonth.moveToNext();
                cursorDaily.moveToNext();

                if (cursorDaily.getCount()!=0)
                    tempDaily = lastAddedDay;
                else
                    daily_dataBaseHelper.addValue(bugun,0);

                if (cursorMonth.getCount()!=0)
                    temp = lastAddedMonth;
                else
                    monthly_dataBaseHelper.addValue(buay,0);


        //        Log.d("calculated", "updateValue: "+calculated);
                daily_dataBaseHelper.updateData(bugun,calculated);
                monthly_dataBaseHelper.updateData(buay,temp+calculated-tempDaily);
                saveData(calculated,temp+calculated-tempDaily);

                cursorMonth.close();
                cursorDaily.close();
                runnable=false;
            }
        }.run();
    }
      private void saveData(int day , int month) {
        SharedPreferences sP = context.getSharedPreferences("soruSayilari102", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sP.edit();
        editor.putInt("lastAddedDay",day);
        editor.putInt("lastAddedMonth",month);
        editor.apply();
    }
    private void loadData() {
        SharedPreferences sP = context.getSharedPreferences("soruSayilari102", Context.MODE_PRIVATE);
        calculated=sP.getInt("tytToplam",0)+sP.getInt("aytToplam",0);
        lastAddedDay = sP.getInt("lastAddedDay", 0);
        lastAddedMonth = sP.getInt("lastAddedMonth", 0);
    }
}


