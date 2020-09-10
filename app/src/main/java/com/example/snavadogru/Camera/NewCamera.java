package com.example.snavadogru.Camera;

import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.snavadogru.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class NewCamera extends AppCompatActivity {
    Button button_pdf;
    OutputStream outputStream;
    ImageView soru1,soru2,soru3,soru4,soru5,soru6,
              take1,take2,take3,take4,take5,take6,
              select1,select2,select3,select4,select5,select6;
   Bitmap bitmap_soru1,bitmap_soru2,bitmap_soru3,bitmap_soru4,bitmap_soru5,bitmap_soru6,bitmap_sonuc;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        Bundle bundle = data.getExtras();
        switch(requestCode){
            case 1:
                bitmap_soru1 =(Bitmap) bundle.get("data");
                soru1.setImageBitmap(bitmap_soru1);
                break;
            case 2:
                bitmap_soru2 =(Bitmap) bundle.get("data");
                soru2.setImageBitmap(bitmap_soru2);
                break;
            case 3:
                bitmap_soru3 =(Bitmap) bundle.get("data");
                soru3.setImageBitmap(bitmap_soru3);
                break;
            case 4:
                bitmap_soru4 =(Bitmap) bundle.get("data");
                soru4.setImageBitmap(bitmap_soru4);
                break;
            case 5:
                bitmap_soru5 =(Bitmap) bundle.get("data");
                soru5.setImageBitmap(bitmap_soru5);
                break;
            case 6:
                bitmap_soru6 =(Bitmap) bundle.get("data");
                soru6.setImageBitmap(bitmap_soru6);
                break;
            case 11:
                Uri image_data1 =data.getData();
                try {
                    bitmap_soru1 =  MediaStore.Images.Media.getBitmap(this.getContentResolver(), image_data1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                soru1.setImageURI(image_data1);
                break;
            case 12:
                Uri image_data2 =data.getData();
                try {
                    bitmap_soru2 =  MediaStore.Images.Media.getBitmap(this.getContentResolver(), image_data2);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                soru2.setImageURI(image_data2);
                break;
            case 13:
                Uri image_data3 =data.getData();
                try {
                    bitmap_soru3 =  MediaStore.Images.Media.getBitmap(this.getContentResolver(), image_data3);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                soru3.setImageURI(image_data3);
                break;
            case 14:
                Uri image_data4 =data.getData();
                try {
                    bitmap_soru4 =  MediaStore.Images.Media.getBitmap(this.getContentResolver(), image_data4);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                soru4.setImageURI(image_data4);
                break;
            case 15:
                Uri image_data5 =data.getData();
                try {
                    bitmap_soru5 =  MediaStore.Images.Media.getBitmap(this.getContentResolver(), image_data5);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                soru5.setImageURI(image_data5);
                break;
            case 16:
                Uri image_data6 =data.getData();
                try {
                    bitmap_soru6 =  MediaStore.Images.Media.getBitmap(this.getContentResolver(), image_data6);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                soru6.setImageURI(image_data6);
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + requestCode);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_camera);
        button_pdf = findViewById(R.id.bt_make_pdf_newcamera);

        soru1 = (ImageView) findViewById(R.id.imv_camera_soru1);
        soru2 = (ImageView) findViewById(R.id.imv_camera_soru2);
        soru3 = (ImageView) findViewById(R.id.imv_camera_soru3);
        soru4 = (ImageView) findViewById(R.id.imv_camera_soru4);
        soru5 = (ImageView) findViewById(R.id.imv_camera_soru5);
        soru6 = (ImageView) findViewById(R.id.imv_camera_soru6);

        take1  = (ImageView) findViewById(R.id.imv_camera_take_photo1);
        take2  = (ImageView) findViewById(R.id.imv_camera_take_photo2);
        take3  = (ImageView) findViewById(R.id.imv_camera_take_photo3);
        take4  = (ImageView) findViewById(R.id.imv_camera_take_photo4);
        take5  = (ImageView) findViewById(R.id.imv_camera_take_photo5);
        take6  = (ImageView) findViewById(R.id.imv_camera_take_photo6);

        select1  = (ImageView) findViewById(R.id.imv_camera_select_photo1);
        select2  = (ImageView) findViewById(R.id.imv_camera_select_photo2);
        select3  = (ImageView) findViewById(R.id.imv_camera_select_photo3);
        select4  = (ImageView) findViewById(R.id.imv_camera_select_photo4);
        select5  = (ImageView) findViewById(R.id.imv_camera_select_photo5);
        select6  = (ImageView) findViewById(R.id.imv_camera_select_photo6);
        bitmap_soru1 = BitmapFactory.decodeResource(getResources(), R.drawable.white_question);
        bitmap_soru2 = BitmapFactory.decodeResource(getResources(), R.drawable.white_question);
        bitmap_soru3 = BitmapFactory.decodeResource(getResources(), R.drawable.white_question);
        bitmap_soru4 = BitmapFactory.decodeResource(getResources(), R.drawable.white_question);
        bitmap_soru5 = BitmapFactory.decodeResource(getResources(), R.drawable.white_question);
        bitmap_soru6 = BitmapFactory.decodeResource(getResources(), R.drawable.white_question);


        button_pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bitmap_sonuc = makeTest(bitmap_soru1,bitmap_soru2,bitmap_soru3,bitmap_soru4,bitmap_soru5,bitmap_soru6);
                showpopup(bitmap_sonuc);
                saveImageMedia(bitmap_sonuc);

            }
        });

        take1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent open_camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(open_camera,1);
            }
        });
        take2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent open_camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(open_camera,2);
            }
        });
        take3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent open_camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
               startActivityForResult(open_camera,3);
            }
        });
        take4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent open_camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(open_camera,4);
            }
        });
        take5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent open_camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(open_camera,5);
            }
        });
        take6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent open_camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(open_camera,6);
            }
        });

        select1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent open_gallery = new Intent();
                open_gallery.setType("image/^");
                open_gallery.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(open_gallery,11);
            }
        });
        select2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent open_gallery = new Intent();
                open_gallery.setType("image/^");
                open_gallery.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(open_gallery,12);
            }
        });
        select3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent open_gallery = new Intent();
                open_gallery.setType("image/^");
                open_gallery.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(open_gallery,13);
            }
        });
        select4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent open_gallery = new Intent();
                open_gallery.setType("image/^");
                open_gallery.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(open_gallery,14);
            }
        });
        select5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent open_gallery = new Intent();
                open_gallery.setType("image/^");
                open_gallery.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(open_gallery,15);
            }
        });
        select6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent open_gallery = new Intent();
                open_gallery.setType("image/^");
                open_gallery.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(open_gallery,16);
            }
        });
    }
    public Bitmap makeTest(Bitmap b1,Bitmap b2,Bitmap b3,Bitmap b4,Bitmap b5,Bitmap b6)
    {
        Bitmap result_bitmap;
        int h=15,w=20;    //tüm bitmapler aynı bouyuttan olmalı boyut ayarı sonra yapılacak
        b1=getResizedBitmap(b1,w,h);
        b2=getResizedBitmap(b2,w,h);
        b3=getResizedBitmap(b3,w,h);
        b4=getResizedBitmap(b4,w,h);
        b5=getResizedBitmap(b5,w,h);
        b6=getResizedBitmap(b6,w,h);

        int width, height;
        width = b1.getWidth() + b3.getWidth();
        height = b1.getHeight()+b2.getHeight()+b3.getHeight();
        result_bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas comboImage = new Canvas(result_bitmap);
        comboImage.drawBitmap(b1, 0f, 0f, null);
        comboImage.drawBitmap(b2, 0f, b1.getHeight() , null);
        comboImage.drawBitmap(b3, 0f, b1.getHeight() +b2.getHeight() , null);
        comboImage.drawBitmap(b4, b1.getWidth(), 0f , null);
        comboImage.drawBitmap(b5, b1.getWidth(), b4.getHeight() , null);
        comboImage.drawBitmap(b6, b1.getWidth(), b4.getHeight() + b5.getHeight() , null);

        return  result_bitmap;
    }
    public void saveImageMedia(Bitmap bitmap)
    {
        ContentResolver cr = getContentResolver();
        String title = "Test"+System.currentTimeMillis();
        String description = "My bitmap created by Android-er";
        String savedURL = MediaStore.Images.Media
                .insertImage(cr, bitmap, title, description);
        Toast.makeText(getApplicationContext(),savedURL, Toast.LENGTH_LONG).show();

    }
    public void showpopup(Bitmap bitmap)
    {
        LayoutInflater layout = LayoutInflater.from(this);
        View tasarim = layout.inflate(R.layout.popup_camera,null);

        final ImageView imageView= tasarim.findViewById(R.id.popup_imageview);
        imageView.setImageBitmap(bitmap);


        AlertDialog.Builder ad = new AlertDialog.Builder(this);
        ad.setTitle("Test");
        ad.setView(tasarim);
        ad.setPositiveButton("Pdf yap", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            //convert bitmap to pdf
                Toast.makeText(getApplicationContext(),"kaydetme öncesi",Toast.LENGTH_SHORT).show();
            }
        });
        ad.setNegativeButton("iptal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
 }
        });

        ad.create().show();
    }
    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight)
    {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }
    public static Bitmap mergeToPin(Bitmap back, Bitmap front)
    {
        Bitmap result = Bitmap.createBitmap(back.getWidth(), back.getHeight(), back.getConfig());
        Canvas canvas = new Canvas(result);
        int widthBack = back.getWidth();
        int widthFront = front.getWidth();
        float move = (widthBack - widthFront) / 2;
        canvas.drawBitmap(back, 0f, 0f, null);
        canvas.drawBitmap(front, move, move, null);
        return result;
    }
    public Bitmap mergeBitmap(Bitmap fr, Bitmap sc)
    {   Bitmap comboBitmap;
        int width, height;

        width = fr.getWidth() + sc.getWidth();
        height = fr.getHeight();
        comboBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas comboImage = new Canvas(comboBitmap);
        comboImage.drawBitmap(fr, 0f, 0f, null);
        comboImage.drawBitmap(sc, fr.getWidth(), 0f , null);
        return comboBitmap;

    }
    private void SaveImage(Bitmap finalBitmap) {
        File filepath = Environment.getExternalStorageDirectory();
        File dir = new File(filepath.getAbsolutePath() +"/Demo/");
        dir.mkdir();
        File file = new File(dir,System.currentTimeMillis()+".jpg");
        try {
            OutputStream outputStream = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        finalBitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
        Toast.makeText(getApplicationContext(),"Kaydedildi",Toast.LENGTH_SHORT).show();
        try {
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



        }


