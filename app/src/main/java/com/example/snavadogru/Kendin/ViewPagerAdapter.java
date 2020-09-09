package com.example.snavadogru.Kendin;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.PagerAdapter;
import android.widget.TextView;
import android.widget.Chronometer;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Button;
import android.widget.Toast;

import com.example.snavadogru.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ViewPagerAdapter extends PagerAdapter implements addingPopUp.dialog{
    Context context;
    Calendar calendar;
    addingPopUp popUp;
    private Chronometer chronometer;
    private CountDownTimer countDownTimer;
    private LayoutInflater layoutInflater;
    private Button add1,add2,startStop1,startStop2,finish,getTime;
    private TextView showCountDownTimer,ayliktextView,haftaliktextView,gunluktextView;
    private ProgressBar countDownBar,chronometerBar;
    private SimpleDateFormat simpleDateFormat,simpleMonthFormat;
    private String dateTime,monthTime;
    private FragmentManager fragmentManager;
    private long[] gunlukGraphValues= new long[7];
    private long[] aylikGraphValues= new long[12];
    private int pauseOffset=0,numbMonth=0,numbWeek=0,numbDay=0,dayInputCount=0,monthInputCount=0;
    private long mTimeLeftMillis=0,setTimeMillis=0;
    private long calismaZamani=0,countDownCalismaZamani,haftalikTime=0,lastGettedCalismaZamani=0;
    private  boolean isChronometerRun=false,isCountDownRun=false;

    public ViewPagerAdapter(Context c,FragmentManager fm,TextView aylik,TextView haftalik,TextView gunluk){
        context=c; ayliktextView=aylik; haftaliktextView=haftalik; gunluktextView=gunluk; fragmentManager=fm;
        calendar = Calendar.getInstance();
        simpleDateFormat=new SimpleDateFormat("EEEE");
        simpleMonthFormat=new SimpleDateFormat("MM");
        dateTime=simpleDateFormat.format(calendar.getTime());
        monthTime=simpleMonthFormat.format(calendar.getTime());

        loadData();
        gunluktextView.setText(updateText(gunlukGraphValues[numbDay-1])+"");
        haftaliktextView.setText(updateText(haftalikTime)+"");
        ayliktextView.setText(updateText(aylikGraphValues[numbMonth-1])+"");
    }
    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
    //    Log.d("instantiateItem","pos"+position);
        View view=null;
        layoutInflater=(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);


        if (position==0) {
            view = layoutInflater.inflate(R.layout.chronometer_layout, container, false);
            add1=view.findViewById(R.id.addTime);
            startStop1=view.findViewById(R.id.start_stop1);
            finish=view.findViewById(R.id.addStop);
            chronometer=view.findViewById(R.id.chronometer);
            chronometerBar=view.findViewById(R.id.chronometerProgress);
            chronometer.setFormat("%s");
            startStop1.setOnClickListener(v -> chronometerStartStop());


            finish.setOnClickListener(v -> {
                chronometer.stop();
                startStop1.setText("BAŞLAT");
                isChronometerRun=false;
                chronometer.setBase(SystemClock.elapsedRealtime());
                saveData();
                calismaZamani=0;
                pauseOffset=0;
            });
            add1.setOnClickListener(v -> {

                if (chronometer.getText().length()==5)
                {
                    calismaZamani+=Integer.parseInt(chronometer.getText().toString().substring(3)+"")*1000;
                    calismaZamani+=Integer.parseInt(chronometer.getText().toString().substring(0,2)+"")*60000;
                }else if (chronometer.getText().length()==7)
                {
                    calismaZamani+=Integer.parseInt(chronometer.getText().toString().charAt(0)+"")*3600000;
                    calismaZamani+=Integer.parseInt(chronometer.getText().toString().substring(5)+"")*1000;
                    calismaZamani+=Integer.parseInt(chronometer.getText().toString().substring(2,4)+"")*60000;
                }
                else if (chronometer.getText().length()==8)
                {
                    calismaZamani+=Integer.parseInt(chronometer.getText().toString().substring(0,2)+"")*3600000;
                    calismaZamani+=Integer.parseInt(chronometer.getText().toString().substring(7)+"")*1000;
                    calismaZamani+=Integer.parseInt(chronometer.getText().toString().substring(3,5)+"")*60000;
                }
                Log.d("calismaZamani+",""+calismaZamani);
                calismaZamani-=lastGettedCalismaZamani;
                Log.d("calismaZamani-",""+calismaZamani);
                lastGettedCalismaZamani=calismaZamani+lastGettedCalismaZamani;

                gunlukKontrol();
                gunlukGraphValues[numbDay-1]+=calismaZamani;
                haftalikTime+=calismaZamani;
                aylikGraphValues[numbMonth-1]+=calismaZamani;
                calismaZamani=0;

                gunluktextView.setText(updateText(gunlukGraphValues[numbDay-1])+"");
                haftaliktextView.setText(updateText(haftalikTime)+"");
                ayliktextView.setText(updateText(aylikGraphValues[numbMonth-1])+"");

                Log.d("fistPosition","gunlukTime "+gunlukGraphValues[numbDay-1] );
                Log.d("fistPosition","haftalikTime "+haftalikTime);
                Log.d("fistPosition","aylikTime "+aylikGraphValues[numbMonth-1] );
                saveData();
            });

        }
        else if (position==1){
            view = layoutInflater.inflate(R.layout.count_down_timer,container,false);
            add2=view.findViewById(R.id.addTime_2);
            startStop2=view.findViewById(R.id.start_stop2);
            showCountDownTimer=view.findViewById(R.id.countDown);
            countDownBar=view.findViewById(R.id.countDownProgress);
            getTime=view.findViewById(R.id.getTime);
            countDownBar.setProgress(0);
            startStop2.setOnClickListener(v -> countDownStartStop(setTimeMillis));
            getTime.setOnClickListener(v->{
                popUp=new addingPopUp();
                popUp.show(fragmentManager, "Sinava Dogru");
          //      countDownReseted=true;
            });
            add2.setOnClickListener(v -> {
                if (setTimeMillis!=mTimeLeftMillis){
                    countDownCalismaZamani+=setTimeMillis-mTimeLeftMillis;
                    Log.d("add2", "countDownCalismaZamani: "+countDownCalismaZamani);
                    int hour=(int) (countDownCalismaZamani/1000)/3600;
                    int minute=(int) (countDownCalismaZamani/1000)%3600/60;
                    int second=(int) (countDownCalismaZamani/1000)%3600%60;

          /*          if(setTimeMillis!=mTimeLeftMillis && ((int)(calismaZamani/1000)%3600%60)==0){
                        second++;
                        Log.d("add2 for that",""+second);
                    }*/
                    setTimeMillis=mTimeLeftMillis;
                    String text = String.format(Locale.getDefault(),"%02d:%02d:%02d",hour,minute,second);

                    gunlukKontrol();
                    gunlukGraphValues[numbDay-1]+=countDownCalismaZamani;
                    haftalikTime+=countDownCalismaZamani;
                    aylikGraphValues[numbMonth-1]+=countDownCalismaZamani;

                    gunluktextView.setText(text+"");
                    haftaliktextView.setText(updateText(haftalikTime)+"");
                    ayliktextView.setText(updateText(aylikGraphValues[numbMonth-1])+"");

                    Log.d("secondPosition","gunlukTime "+gunlukGraphValues[numbDay-1] );
                    Log.d("secondPosition","haftalikTime "+haftalikTime);
                    Log.d("secondPosition","aylikTime "+aylikGraphValues[numbMonth-1] );
                    saveData();
                }
            });
        }
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
   //     Log.d("destroyed",object+"");
        container.removeView((RelativeLayout)object);
    }

    public void chronometerStartStop(){
        if (!isChronometerRun){
            startStop1.setText("DURAKLAT");
            chronometer.setBase(SystemClock.elapsedRealtime()-pauseOffset);
            chronometer.start();
            isChronometerRun=true;
        }else {
            chronometer.stop();
            pauseOffset=(int) (SystemClock.elapsedRealtime()-chronometer.getBase());
            startStop1.setText("BAŞLAT");
            isChronometerRun=false;
        }
    }
    public void countDownStartStop(long  setTime){
        if (!isCountDownRun){
            startStop2.setText("DURAKLAT");
            mTimeLeftMillis=setTime;
            countDownTimer = new CountDownTimer(setTime,1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    mTimeLeftMillis=millisUntilFinished;
                    showCountDownTimer.setText(updateText(mTimeLeftMillis));
                    countDownBar.setProgress((int)((1-(double)mTimeLeftMillis/setTimeMillis)*100));
        //            Log.d("showCountDown",""+mTimeLeftMillis);
                }
                @Override
                public void onFinish() {}
            }.start();
            isCountDownRun=true;
        }else {
            countDownTimer.cancel();
            startStop2.setText("BAŞLAT");
            isCountDownRun=false;
        }
    }
    public String updateText(long numb){
        int hour=(int) (numb/1000)/3600;
        int minute=(int) (numb/1000)%3600/60;
        int second=(int) (numb/1000)%3600%60;
      /*  if(second!=0)
            second=(int) ((numb/1000)+1)%3600%60;*/

        return String.format(Locale.getDefault(),"%02d:%02d:%02d",hour,minute,second);
    }

    @Override
    public void set() {
        try {
            int hour=(int) (setTimeMillis/1000)/3600;
            int minute=(int) (setTimeMillis/1000)%3600/60;
            int second=(int) (setTimeMillis/1000)%3600%60;

            setTimeMillis=(long)((Integer.parseInt(popUp.hourEdit.getText()+"")*3600)+(Integer.parseInt(popUp.minEdit.getText()+"")*60))*1000;
            showCountDownTimer.setText(String.format(Locale.getDefault(),"%02d:%02d:%02d",hour,minute,second)+"");
            countDownBar.setProgress(0);
        }catch (Exception e){
            Toast.makeText(context, "Lütfen Sayı Giriniz", Toast.LENGTH_SHORT).show();
        }
    }
    private void saveData() {
        SharedPreferences sP= context.getSharedPreferences("daySave", Context.MODE_PRIVATE);
        SharedPreferences spValues= context.getSharedPreferences("viewPagerAdapterValueSave", Context.MODE_PRIVATE);

        try {
            SharedPreferences.Editor editor = sP.edit();
            SharedPreferences.Editor editorValues = spValues.edit();

            editor.putLong("haftalikTime",haftalikTime);

            editor.putInt("day_Numb",numbDay);
            editor.putInt("week_Numb",numbWeek);
            editor.putInt("month_Numb",numbMonth);
            editor.putInt("dayInputCount",dayInputCount);
            editor.putInt("monthInputCount",monthInputCount);

    //        Log.d("gunNumb",""+numbDay);

            Gson gson = new Gson();
            String jsonGun = gson.toJson(gunlukGraphValues);
            String jsonAylik = gson.toJson(aylikGraphValues);

            editorValues.putString("gunluk_Array",jsonGun);
            editorValues.putString("aylik_Array",jsonAylik);
            editorValues.apply();

            editor.apply();
        }catch (Exception e){
            e.getMessage();
        }
    }
    private void loadData() {
        SharedPreferences sP= context.getSharedPreferences("daySave", Context.MODE_PRIVATE);
        SharedPreferences spValues= context.getSharedPreferences("viewPagerAdapterValueSave", Context.MODE_PRIVATE);

        haftalikTime=sP.getLong("haftalikTime",0);

        numbDay=sP.getInt("day_Numb",0);
        numbWeek=sP.getInt("week_Numb",0);
        numbMonth=sP.getInt("month_Numb",0);
        dayInputCount=sP.getInt("dayInputCount",0);
        monthInputCount=sP.getInt("monthInputCount",0);

  //      Log.d("START WITH","THESE "+numbDay+" "+numbWeek+" "+numbMonth);
        if (numbDay==0 || numbWeek==0 || numbMonth==0)
        {
            numbDay=translateDay(dateTime);
            numbWeek=calendar.get(Calendar.WEEK_OF_MONTH);
            numbMonth=translateMonth(monthTime);

            if (dayInputCount<7){
                gunlukGraphValues[numbDay-1]=0;
           //    Log.d("gunlukArray",""+gunlukGraphValues[numbDay-1]);
                dayInputCount++;}

            if (monthInputCount<12){
                aylikGraphValues[numbMonth-1]=0;
         //       Log.d("aylikArray",""+aylikGraphValues[numbMonth-1]);
                monthInputCount++;}

        }

        Type type = new TypeToken<long[]>() {}.getType();
        Gson gson = new Gson();
        String jsonGun = spValues.getString("gunluk_Array",null);
        String jsonAylik = spValues.getString("aylik_Array",null);

        gunlukGraphValues = gson.fromJson(jsonGun, type);
        aylikGraphValues = gson.fromJson(jsonAylik, type);

        if (gunlukGraphValues==null)
            gunlukGraphValues= new long[7];
        if (aylikGraphValues==null)
            aylikGraphValues= new long[12];
    }


    public int translateDay(String day){
    //    Log.d("day ", ""+day);
        switch (day){
            case "Monday": return 1;
            case "Tuesday":return 2;
            case "Wednesday": return 3;
            case "Thursday": return 4;
            case "Friday":return 5;
            case "Saturday": return 6;
            case "Sunday": return 7;
        }
        return -1;
    }
    public int translateMonth(String month){
        return Integer.parseInt(month);
    }

        public void gunlukKontrol(){
        if (translateDay(dateTime)!=numbDay)
            {
                if (numbDay==7){
                    numbDay--;
                    numbDay=1;}
                else
                    numbDay++;
                if (dayInputCount<7){
                gunlukGraphValues[numbDay-1]=0;
             //       Log.d("gunlukArray",""+gunlukGraphValues[numbDay-1]);
                dayInputCount++;}

            }
        if (calendar.get(Calendar.WEEK_OF_MONTH)!=numbWeek)
                if (numbWeek==4)
                {
                    numbWeek--;
                    numbWeek=1;
                    haftalikTime=0;
                }
                else
                    numbWeek++;
        if (translateMonth(monthTime)!=numbMonth)
            {
                if (numbMonth==12){
                    numbMonth--;
                    numbMonth=1;}
                else
                    numbMonth++;
                if (monthInputCount<7){
                aylikGraphValues[numbMonth-1]=-1;
             //       Log.d("gunlukArray",""+aylikGraphValues[numbMonth-1]);
                monthInputCount++;}
            }
    }
}