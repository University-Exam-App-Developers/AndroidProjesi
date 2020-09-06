package com.example.snavadogru.Camera;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;


import com.example.snavadogru.R;

import java.io.File;
import java.util.ArrayList;

public class ShowPicturesActivity extends AppCompatActivity {
GridView gv;

ArrayList<File> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_pictures);

        list = imageReader(getExternalCacheDir());
        gv =  findViewById(R.id.grid_view);
        gv.setAdapter(new GridAdapter());


        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(getApplicationContext(), ViewImage.class).putExtra("img",list.get(position).toString()));
            }
        });
    }

    class GridAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.single_grid, parent , false);

            ImageView imageView = convertView.findViewById(R.id.single_grid_imageView);
            imageView.setImageURI(Uri.parse(getItem(position).toString()));

            return convertView;
        }
    }

    ArrayList<File> imageReader(File root){
        ArrayList<File> tmp = new ArrayList<>();

        File[] files = root.listFiles();
        for(int i = 0; i < files.length; i++){
            if(files[i].isDirectory()){
                tmp.addAll(imageReader(files[i]));
            }
            else {
                if(files[i].getName().endsWith(".jpg")){
                    tmp.add(files[i]);
                }
            }
        }
        return tmp;
    }

}
