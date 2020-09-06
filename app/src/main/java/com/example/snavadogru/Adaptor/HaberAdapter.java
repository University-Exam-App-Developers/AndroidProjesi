package com.example.snavadogru.Adaptor;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.snavadogru.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class HaberAdapter extends RecyclerView.Adapter<HaberAdapter.ViewHolder> {



    public Context mContext;
    public List<Haber> mHaber;

    public HaberAdapter(Context mContext, List<Haber> mHaber) {
        this.mContext = mContext;
        this.mHaber = mHaber;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.haber_ogesi,parent,false);

        return new HaberAdapter.ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return mHaber.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        Haber haber =mHaber.get(position);
        holder.haberFotosu.setImageResource(R.drawable.haber_image);
        holder.haberTitle.setText(haber.getXmllitle());

        //Thread kullanarak haberfotosunu yerleştiridim
        new Thread(new Runnable() {
            @Override
            public void run() {
                String imgurl = haber.getXmlimage();
                try {
                    URL url00 = new URL(imgurl);
                    HttpURLConnection connection = (HttpURLConnection) url00.openConnection();
                    connection.setDoInput(true);
                    connection.connect();
                    InputStream is = connection.getInputStream();
                    Bitmap bmp = BitmapFactory.decodeStream(is);
                    holder.haberFotosu.post(new Runnable() {
                        @Override
                        public void run() {
                            holder.haberFotosu.setImageBitmap(bmp);

                        }
                    });

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();





       //haber baslıgına basınca haber linkine gitmesi
        holder.haberTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(haber.getXmllink());
                Intent openbrowser = new Intent(Intent.ACTION_VIEW,uri);
                mContext.startActivity(openbrowser);

            }
        });

    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView haberFotosu;
        public TextView haberTitle;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            haberTitle=itemView.findViewById(R.id.haber_title_haber_ogesi);
            haberFotosu=itemView.findViewById(R.id.haber_fotosu_haber_ogesi);
        }
    }

}
