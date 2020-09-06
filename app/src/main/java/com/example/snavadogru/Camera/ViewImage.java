package com.example.snavadogru.Camera;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.snavadogru.R;

public class ViewImage extends AppCompatActivity {
ImageView imageView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image);


      //  File file = i.getExtras().getParcelable("img");
        String file = getIntent().getStringExtra("img");

        imageView2 = findViewById(R.id.image_view2);

        imageView2.setImageURI(Uri.parse(file));


    }
}
