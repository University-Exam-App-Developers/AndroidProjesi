package com.example.snavadogru.Camera;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.snavadogru.R;

public class SORULAR extends AppCompatActivity {
    Button goToCameraActivity;
    Button showPicturesActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sorular);

//            Intent intentToPhoto = new Intent(MainActivity.this, CameraActivity);
//            startActivity(intentToPhoto);

        goToCameraActivity = (Button) findViewById(R.id.camera_activity_button);

        goToCameraActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SORULAR.this, CameraActivity.class);
                startActivity(intent);
            }
        });

        showPicturesActivity = findViewById(R.id.show_pictures_activity_button);

        showPicturesActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SORULAR.this, ShowPicturesActivity.class);
                startActivity(intent);
            }
        });

    }


}
