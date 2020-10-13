package ykskoc.example.snavadogru.Kendin;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.PagerAdapter;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Chronometer;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Button;
import android.widget.Toast;

import com.ykskoc.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.util.Calendar;
import java.util.Locale;

import ykskoc.example.snavadogru.Kendin.zamanTakipSqlite.zamanTakipMyDataBaseHelper;
import ykskoc.example.snavadogru.Kendin.zamanTakipSqlite.zamanTakipMyDataBaseHelper_Month;

import static android.content.Context.VIBRATOR_SERVICE;


public class ViewPagerAdapter extends PagerAdapter implements addingPopUp.dialog{
    private Context context;
    private addingPopUp popUp;
    private Vibrator vibrator;
    private Animation roundingAlone,bounce,finishTimer;
    private ImageView arrow;
    private Chronometer chronometer;
    private CountDownTimer countDownTimer;
    private Button add1,add2,startStop1,startStop2,getTime,pomodoro;
    private TextView showCountDownTimer,ayliktextView,haftaliktextView,gunluktextView;
    private ProgressBar countDownBar;
    private boolean isitFirstSave,isitFirstLoad;
    private FragmentManager fragmentManager;
    private int pauseOffset=0;
    private int bugun=0,buhafta=0,buay=0,buyil=0,kayitgun=0,kayithafta=0,kayitay=0,kayityil=0;
    private long mTimeLeftMillis=0,setProgressBarMillis=0,lastgettedCountDown=0;
    private long calismaZamani=0,countDownCalismaZamani,lastGettedCalismaZamani=0;
    private zamanTakipMyDataBaseHelper daily_dataBaseHelper;
    private zamanTakipMyDataBaseHelper_Month monthly_dataBaseHelper;
    boolean isChronometerRun=false,isCountDownRun=false;
    private int aylikZaman=0,haftalikTime=0,gunlukZaman=0,valueSizeMonth=0,valueSizeDay=0;

    ViewPagerAdapter(Context c, FragmentManager fm, TextView aylik, TextView haftalik, TextView gunluk){
        context=c; ayliktextView=aylik; haftaliktextView=haftalik; gunluktextView=gunluk; fragmentManager=fm;
        vibrator=(Vibrator) c.getSystemService(VIBRATOR_SERVICE);
        daily_dataBaseHelper= new zamanTakipMyDataBaseHelper(context);
        monthly_dataBaseHelper= new zamanTakipMyDataBaseHelper_Month(context);
        isitFirstSave=true; isitFirstLoad=true;

        todayValueGet();
        loadDayData();

        getDbValues();
        update();
        saveDayData();

        gunluktextView.setText(updateText(gunlukZaman)+"");
        haftaliktextView.setText(updateText(haftalikTime)+"");
        ayliktextView.setText(updateText(aylikZaman)+"");
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
        View view=null;
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        bounce=AnimationUtils.loadAnimation(context,R.anim.bounce_button);

        if (position==0) {
            assert layoutInflater != null;
            view = layoutInflater.inflate(R.layout.chronometer_layout, container, false);
            arrow = view.findViewById(R.id.chronometerProgressArrow);
            roundingAlone= AnimationUtils.loadAnimation(context,R.anim.arrowrotate);
            finishTimer= AnimationUtils.loadAnimation(context,R.anim.timer_finish);
            //       Glide.with(context).load(R.mipmap.myxp5).into(background);

            add1=view.findViewById(R.id.addTime);
            startStop1=view.findViewById(R.id.start_stop1);
            Button finish = view.findViewById(R.id.addStop);
            chronometer=view.findViewById(R.id.chronometer);
            chronometer.setFormat("%s");
            startStop1.setOnClickListener(v -> { loadDayData();
                didTapButton(v,R.id.start_stop1);
                chronometerStartStop();});

            finish.setOnClickListener(v -> {
                chronometer.stop();
                isChronometerRun=false;
                chronometer.setBase(SystemClock.elapsedRealtime());
                arrow.clearAnimation();
                didTapButton(v,R.id.addStop);
                lastGettedCalismaZamani=0;
                calismaZamani=0;
                pauseOffset=0;
            });
            add1.setOnClickListener(v -> {// long first=SystemClock.elapsedRealtime();
                loadDayData();
                didTapButton(v,R.id.addTime);
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
                calismaZamani-=lastGettedCalismaZamani;
                lastGettedCalismaZamani=calismaZamani+lastGettedCalismaZamani;

                gunlukZaman+=calismaZamani;
                haftalikTime+=calismaZamani;
                aylikZaman+=calismaZamani;
                calismaZamani=0;

                gunluktextView.setText(updateText(gunlukZaman)+"");
                haftaliktextView.setText(updateText(haftalikTime)+"");
                ayliktextView.setText(updateText(aylikZaman)+"");
                saveDayData();
                if (gunlukZaman>300000) reklamgoster();
            });

        }

        else if (position==1){
            view = layoutInflater.inflate(R.layout.count_down_timer,container,false);
            add2=view.findViewById(R.id.addTime_2);
            pomodoro=view.findViewById(R.id.pomodoro);
            startStop2=view.findViewById(R.id.start_stop2);
            showCountDownTimer=view.findViewById(R.id.countDown);
            countDownBar=view.findViewById(R.id.countDownProgress);
            getTime=view.findViewById(R.id.getTime);
            countDownBar.setProgress(0);
            startStop2.setOnClickListener(v -> { loadDayData();
                didTapButton(v,R.id.start_stop2);
                countDownStartStop();});
            getTime.setOnClickListener(v->{
                didTapButton(v,R.id.getTime);
                popUp=new addingPopUp();
                popUp.show(fragmentManager, "Sinava Dogru");
            });
            pomodoro.setOnClickListener(v->{
                mTimeLeftMillis=1500000;
                setProgressBarMillis=1500000;
                lastgettedCountDown=1500000;
                showCountDownTimer.setText(updateText(1500000));
                add2.clearAnimation();
                Toast.makeText(context, "1 Pomodoro Eklendi", Toast.LENGTH_SHORT).show();
            });
            add2.setOnClickListener(v -> {
                //   long first=SystemClock.elapsedRealtime();
                loadDayData();

                didTapButton(v,R.id.addTime_2);
                if (lastgettedCountDown!=mTimeLeftMillis){
                    countDownCalismaZamani+=lastgettedCountDown-mTimeLeftMillis;
                    lastgettedCountDown=mTimeLeftMillis;

                    gunlukZaman+=countDownCalismaZamani;
                    haftalikTime+=countDownCalismaZamani;
                    aylikZaman+=countDownCalismaZamani;
                    countDownCalismaZamani=0;

                    gunluktextView.setText(updateText(gunlukZaman)+"");
                    haftaliktextView.setText(updateText(haftalikTime)+"");
                    ayliktextView.setText(updateText(aylikZaman)+"");
                    saveDayData();

                    if (gunlukZaman>300000) reklamgoster();
                }
            });
        }
        container.addView(view);
        return view;
    }


    private void didTapButton(View view, int recourceID) {
        Button button = view.findViewById(recourceID);
        Animation myAnim = AnimationUtils.loadAnimation(context, R.anim.bounce_button);
        button.startAnimation(myAnim);
    }
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout)object);
    }

    void chronometerStartStop(){
        if (!isChronometerRun){
            //     startService(1);
            chronometer.setBase(SystemClock.elapsedRealtime()-pauseOffset);
            startStop1.startAnimation(bounce);
            startStop1.setBackgroundResource(R.drawable.pause_button);
            arrow.startAnimation(roundingAlone);
            chronometer.start();
            isChronometerRun=true;
        }else {
            //      stopService();
            chronometer.stop();
            arrow.clearAnimation();
            pauseOffset=(int) (SystemClock.elapsedRealtime()-chronometer.getBase());
            startStop1.startAnimation(bounce);
            startStop1.setBackgroundResource(R.drawable.play_buttonsvg);
            isChronometerRun=false;
        }
    }
    void countDownStartStop(){
        if (!isCountDownRun){
            countDownTimer = new CountDownTimer(mTimeLeftMillis,1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    mTimeLeftMillis=millisUntilFinished;
                    showCountDownTimer.setText(updateText(mTimeLeftMillis));
                    //    startService(2);

                    if (((1-(double)mTimeLeftMillis/setProgressBarMillis)*100)>1)
                        countDownBar.setProgress((int) ((1-(double)mTimeLeftMillis/setProgressBarMillis)*100));
                    else
                        countDownBar.setProgress(1);
                }
                @Override
                public void onFinish() {
                    vibrator.vibrate(3000);
                    add2.startAnimation(finishTimer);
                    showCountDownTimer.setText(updateText(0));
                    countDownBar.setProgress(100);
                    mTimeLeftMillis=1;
                }
            }.start();
            startStop2.startAnimation(bounce);
            startStop2.setBackgroundResource(R.drawable.pause_button);
            isCountDownRun=true;
        }else {
            countDownTimer.cancel();
            startStop2.startAnimation(bounce);
            startStop2.setBackgroundResource(R.drawable.play_buttonsvg);
            isCountDownRun=false;
        }
    }
    private String updateText(long numb){
        int hour=(int) (numb/1000)/3600;
        int minute=(int) (numb/1000)%3600/60;
        int second=(int) (numb/1000)%3600%60;
        return String.format(Locale.getDefault(),"%02d:%02d:%02d",hour,minute,second);
    }

    @Override
    public void set() {
        try {
            mTimeLeftMillis=(long)((Integer.parseInt(popUp.hourEdit.getText()+"")*3600)+(Integer.parseInt(popUp.minEdit.getText()+"")*60))*1000;
            setProgressBarMillis=mTimeLeftMillis;
            lastgettedCountDown=mTimeLeftMillis;
            showCountDownTimer.setText(updateText(mTimeLeftMillis));
            add2.clearAnimation();
        }catch (Exception e){
            Toast.makeText(context, "Lütfen Sayı Giriniz", Toast.LENGTH_SHORT).show();
        }
    }

    private void update(){
        int changedYear= Math.abs(buyil-kayityil);
        int changedMonth=(buay+12-kayitay)%12;
        int changeWeek=(buhafta+52-kayithafta)%52;
        int changedDay=(bugun+365-kayitgun)%365;

        new Runnable() {
            boolean loaded = false;
            @Override
            public void run() {
                //      Log.d("loaded","loaded "+loaded) ;
                if (loaded)
                    return;
                if (changedYear>0){
                    if (changedYear>1 || (buay-kayitay>0)){
                        daily_dataBaseHelper.removeAllData();
                        monthly_dataBaseHelper.removeAllData();
                        monthly_dataBaseHelper.addValue(buay,0); valueSizeMonth=1;
                        daily_dataBaseHelper.addValue(bugun,0); valueSizeDay=1;
                        haftalikTime=0;
                        return;
                    }
                    //     Log.d("UPDATE","changedYear "+changedYear) ;
                }

                if (changedMonth>0){
                    int temp=0;
                    //   Log.d("UPDATE","changedMonth "+changedMonth+" valueSizeMonth "+valueSizeMonth+" kayitay "+kayitay) ;
                    for (int i = 1; i <changedMonth+1 ; i++)
                        if(valueSizeMonth==12)
                        { //  Log.d("UPDATE","forforfor "+valueSizeMonth+" i "+"- "+kayitay) ;
                            monthly_dataBaseHelper.removeSingleData((kayitay+(temp++))%12);
                            monthly_dataBaseHelper.addValue((kayitay+i)%12,0);
                        }
                        else{monthly_dataBaseHelper.addValue((kayitay+i)%12,0); valueSizeMonth++;}

                    haftalikTime=0;
                    //      Log.d("UPDATE","changedMonth "+haftalikTime) ;
                    if (changedMonth>1)
                    { daily_dataBaseHelper.removeAllData();
                        daily_dataBaseHelper.addValue(bugun,0); valueSizeDay=1; return; }
                }

                if (changeWeek>0)
                {//Log.d("UPDATE","changeWeek "+changeWeek) ;
                    haftalikTime=0;
                    if (changeWeek!=1 || changedDay>6)
                    {
                        daily_dataBaseHelper.removeAllData();
                        gunlukZaman=0;
                        daily_dataBaseHelper.addValue(bugun,0); valueSizeDay=1;
                        return;
                    }
                }

                if (changedDay>0){
                    int temp=0;
                    if (changedDay>7)
                        daily_dataBaseHelper.removeAllData();
                    else
                        for (int i = 0; i <changedDay ; i++)
                            if(valueSizeDay==7)
                            {//Log.d("UPDATE","changedDay "+changedDay+" i "+i) ;
                                daily_dataBaseHelper.removeSingleData((bugun-(temp++)-7));
                                daily_dataBaseHelper.addValue(((kayitgun+i)%365)+1,0);
                            }
                            else{daily_dataBaseHelper.addValue(((kayitgun+i)%365)+1,0); valueSizeDay++;}
                    //     Log.d("UPDATE","changedDay "+changedDay) ;
                    gunlukZaman=0;
                }
                loaded=true;
            }
        }.run();

    }

    private void getDbValues(){
        Cursor cursorDaily =daily_dataBaseHelper.queryLastItem(bugun);
        Cursor cursorMonthly = monthly_dataBaseHelper.queryLastItem(buay);
        cursorDaily.moveToNext();
        cursorMonthly.moveToNext();
        if (cursorDaily.getCount()!=0)
            gunlukZaman = cursorDaily.getInt(2)*1000;
        else
            gunlukZaman = 0;

        if (cursorMonthly.getCount()!=0)
            aylikZaman = cursorMonthly.getInt(2)*1000;
        else
            aylikZaman = 0;

    }
    void updateDbValues(){
        Cursor cursorDaily =daily_dataBaseHelper.queryLastItem(bugun);
        Cursor cursorMonthly =monthly_dataBaseHelper.queryLastItem(buay);
        cursorMonthly.moveToNext();
        cursorDaily.moveToNext();
        if (cursorDaily.getCount()==0){
            daily_dataBaseHelper.addValue(bugun,0); valueSizeDay=1;}

        if (cursorMonthly.getCount()==0)
            monthly_dataBaseHelper.addValue(buay,0); valueSizeMonth=1;

        daily_dataBaseHelper.updateData(bugun,gunlukZaman/1000);
        monthly_dataBaseHelper.updateData(buay,aylikZaman/1000);
        cursorMonthly.close();
        cursorDaily.close();
    }

    private void saveDayData(){

        new Runnable() {
            boolean loaded = false;
            @Override
            public void run() {
                if(loaded)
                    return;
                SharedPreferences sP = context.getSharedPreferences("SaveZamanTakipAlfa7", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sP.edit();
                if (isitFirstSave){
                    editor.putInt("year",buyil);
                    editor.putInt("month",buay);
                    editor.putInt("week",buhafta);
                    editor.putInt("day",bugun);
                    isitFirstSave=false;
                }
                editor.putInt("gunlukZaman",gunlukZaman);
                editor.putInt("aylikZaman",aylikZaman);
                editor.putInt("haftalikTime",haftalikTime);
                editor.putInt("valueSizeDay",valueSizeDay);
                editor.putInt("valueSizeMonth",valueSizeMonth);
                editor.apply();
                loaded=true;
            }
        }.run();
    }

    private void loadDayData(){
        new Runnable(){
            boolean loaded=false;
            @Override
            public void run() {
                if(loaded)
                    return;
                SharedPreferences sP= context.getSharedPreferences("SaveZamanTakipAlfa7", Context.MODE_PRIVATE);
                if (isitFirstLoad){
                    kayityil=sP.getInt("year",buyil);
                    kayitay=sP.getInt("month",buay);
                    kayithafta=sP.getInt("week",buhafta);
                    kayitgun=sP.getInt("day",bugun);
                    isitFirstLoad=false;
                }
                haftalikTime=sP.getInt("haftalikTime",0);
                aylikZaman= sP.getInt("aylikZaman",0);
                gunlukZaman=sP.getInt("gunlukZaman",0);
                valueSizeDay=sP.getInt("valueSizeDay",0);
                valueSizeMonth=sP.getInt("valueSizeMonth",0);
                loaded=true;
            }
        }.run();
    }
    private void todayValueGet(){
        Calendar calendar=Calendar.getInstance();
        bugun=calendar.get(Calendar.DAY_OF_YEAR);
        buhafta=calendar.get(Calendar.WEEK_OF_YEAR);
        buay=calendar.get(Calendar.MONTH);
        buyil=calendar.get(Calendar.YEAR);
        //     Log.d("bugün",bugun+"");
    }

    public void reklamgoster(){
        InterstitialAd mInterstitialAd = new InterstitialAd(context);
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