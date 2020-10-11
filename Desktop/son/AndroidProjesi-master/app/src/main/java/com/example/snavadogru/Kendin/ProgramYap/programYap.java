package com.example.snavadogru.Kendin.ProgramYap;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.CalendarView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.snavadogru.Kendin.SqLite.MyDataBaseHelper;
import com.example.snavadogru.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;

public class programYap extends AppCompatActivity {
    CalendarView calendarview;
    Calendar calendar;
    MyDataBaseHelper dataBaseHelper;
    long dayInfo=0;
    FloatingActionButton addPlan,todayPlan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_program_yap);
        calendarview=findViewById(R.id.calendarView);
        addPlan=findViewById(R.id.addPlan);
        todayPlan=findViewById(R.id.showDay);
        calendar = Calendar.getInstance();
        dataBaseHelper= new MyDataBaseHelper(this);

        //today= calendar.getTime().getTime();
        addPlan.setOnClickListener(v -> {
            Intent i = new Intent(programYap.this, addPlanActivity.class);
            i.putExtra("today",dayInfo);
            startActivity(i);
        });
        todayPlan.setOnClickListener(v->{
            Intent i = new Intent(programYap.this, showDay.class);
            i.putExtra("today",dayInfo);
            startActivity(i);
        });
        calendar.set(Calendar.WEEK_OF_YEAR,(Math.abs(calendar.get(Calendar.WEEK_OF_YEAR)-1)));
        Log.d("firstDay",calendarview.getFirstDayOfWeek()+" "+Math.abs(calendar.get(Calendar.WEEK_OF_YEAR)-1));
        calendarview.setMinDate(calendar.getTimeInMillis());

        Calendar maxdate = Calendar.getInstance();
        maxdate.set(2022,6,21);
        calendarview.setMaxDate(maxdate.getTimeInMillis());
        garbageCollectorDb();

        calendar = Calendar.getInstance();
        calendarview.setDate(calendar.getTime().getTime());
        dayInfo=calendarview.getDate();


        calendarview.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            calendar.set(year,month,dayOfMonth);
            dayInfo=calendar.getTimeInMillis();
            Log.d("calendarview","dayInfo "+dayInfo+" -> "+calendar.get(Calendar.DATE));
        });
        //calendarview.setMaxDate(calendar.getTimeInMillis());
    }

    public void garbageCollectorDb(){
        Calendar calendar= Calendar.getInstance();

        for (int i = 0; i <calendar.get(Calendar.WEEK_OF_YEAR) ; i++)
             dataBaseHelper.removeWeekData(i);
    }
}