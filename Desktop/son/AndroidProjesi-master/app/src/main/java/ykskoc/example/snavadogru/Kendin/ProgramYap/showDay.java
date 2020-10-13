package ykskoc.example.snavadogru.Kendin.ProgramYap;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ykskoc.example.snavadogru.Kendin.SqLite.MyDataBaseHelper;
import com.ykskoc.R;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.HorizontalCalendarListener;

import static java.util.Calendar.DAY_OF_WEEK;

public class showDay extends AppCompatActivity {
    Bundle extras;
    Calendar calendar;
    Context context= this;
    long today=0;
    RelativeLayout relativeLayout;
    MyDataBaseHelper dataBaseHelper;
    HorizontalCalendar horizontalCalendar;
    ArrayList[] checked = new ArrayList[7];
    ArrayList[] gorev = new ArrayList[7];
    private planRecyclerViewAdapter arrayAdapter;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_YES)
            setTheme(R.style.AppTheme_DarkMode);
        else
            setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_todolist_today);
        recyclerView= findViewById(R.id.rv_todoList);
        relativeLayout= findViewById(R.id.notFoundGorev);

        extras = getIntent().getExtras();
        assert extras != null;
        today=extras.getLong("today",0);
        calendar = Calendar.getInstance();
        calendar.setTimeInMillis(today);
        dataBaseHelper= new MyDataBaseHelper(this);


        Calendar defaultDay = Calendar.getInstance();
        defaultDay.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));

        Calendar endDate = Calendar.getInstance();
        endDate.set(Calendar.DAY_OF_YEAR,defaultDay.get(Calendar.DAY_OF_YEAR)+((8-defaultDay.get(DAY_OF_WEEK))%7));
        endDate.set(Calendar.YEAR,defaultDay.get(Calendar.YEAR));

        Calendar startDate = Calendar.getInstance();
        startDate.set(Calendar.DAY_OF_YEAR,defaultDay.get(Calendar.DAY_OF_YEAR)+(1-(defaultDay.get(DAY_OF_WEEK)+6))%7);
        startDate.set(Calendar.YEAR,defaultDay.get(Calendar.YEAR));

        horizontalCalendar = new HorizontalCalendar.Builder(this, R.id.horizontalCalendarView).
                startDate(startDate.getTime()).endDate(endDate.getTime()).defaultSelectedDate(defaultDay.getTime())
                .datesNumberOnScreen(5)
                .dayNameFormat("EEE")
                .dayNumberFormat("dd")
                .monthFormat("MMM")
                .showDayName(true)
                .showMonthName(true)
                .textColor(Color.LTGRAY, Color.WHITE)
                .selectorColor(Color.RED)
                .build();
        getDataFromDb(calendar.get(Calendar.WEEK_OF_YEAR));
        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Date date, int position) {
                Calendar calendar= Calendar.getInstance();
                calendar.setTimeInMillis(date.getTime());
                getRefreshFromDb(calendar.get(Calendar.WEEK_OF_YEAR),calendar.get(DAY_OF_WEEK));
                arrayAdapter = new planRecyclerViewAdapter(context,gorev[calendar.get(DAY_OF_WEEK)-1],checked[calendar.get(DAY_OF_WEEK)-1],calendar.get(Calendar.WEEK_OF_YEAR),calendar.get(DAY_OF_WEEK));

                if (gorev[calendar.get(DAY_OF_WEEK)-1].size()==0)
                    relativeLayout.setVisibility(View.VISIBLE);
                else
                    relativeLayout.setVisibility(View.INVISIBLE);
                recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
                recyclerView.setAdapter(arrayAdapter);
                arrayAdapter.setOnItemClickListener(this::removeItem);
            }
            void removeItem(int position){
                arrayAdapter.notifyItemRemoved(position);
            }
        });
    }


    public void getDataFromDb(int week){
        Cursor cursor =dataBaseHelper.readWeekData(week);
        for (int i = 0; i <7 ; i++) {
            checked[i] = new ArrayList<Integer>();
            gorev[i] = new ArrayList<String>();
        }
        if (cursor.getCount()==0)
            Toast.makeText(this,"Bu Hafta için Kayıtlı Ders Planı Yok",Toast.LENGTH_SHORT).show();
        else
            while (cursor.moveToNext())
            {
                checked[cursor.getInt(2)-1].add(cursor.getInt(5));
                gorev[cursor.getInt(2)-1].add(cursor.getString(4));
            }
    }

    public void getRefreshFromDb(int week,int Day){
        Cursor cursor =dataBaseHelper.readDayData(week,Day);
        checked[Day-1] = new ArrayList<Integer>();
        gorev[Day-1] = new ArrayList<String>();
        if (cursor.getCount()!=0)
            while (cursor.moveToNext())
            {
                checked[Day-1].add(cursor.getInt(5));
                gorev[Day-1].add(cursor.getString(4));
                Log.d("cursor",""+cursor.getInt(0)+" "+cursor.getInt(1)+" "+cursor.getInt(2)
                        +" "+cursor.getInt(4)
                        +" "+cursor.getInt(5));
            }
    }
}