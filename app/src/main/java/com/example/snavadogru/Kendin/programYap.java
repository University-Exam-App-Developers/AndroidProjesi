package com.example.snavadogru.Kendin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.CalendarView;
import com.example.snavadogru.R;

public class programYap extends AppCompatActivity {
    CalendarView calendar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program_yap);
        calendar=findViewById(R.id.calendarView);

    }
}
