package com.example.camerademo;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.nio.file.Files;
import java.io.*;

public class MainActivity extends AppCompatActivity {
    ImageView imageView;
    Button button;
    Bitmap captureImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//            Intent intentToPhoto = new Intent(MainActivity.this, CameraActivity);
//            startActivity(intentToPhoto);

        imageView = findViewById(R.id.image_view);
        button = findViewById(R.id.bt_open);

        //REQUEST FOR CAMERA PERMISSION

        if(ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{
                   Manifest.permission.CAMERA
                    },
                    120);
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // OPEN CAMERA
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 120);

            }
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
      //  super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 120 && data != null) {
            // GETTING CAPTURED IMAGE
            captureImage = (Bitmap) data.getExtras().get("data");
            //SET CAPTURE IMAGE TO IMAGEVIEW VARIABLE
            imageView.setImageBitmap(captureImage);
       //     Toast.makeText(MainActivity.this, "IMAGE SUCCESSFULLY CAPTURED", Toast.LENGTH_SHORT).show();
    //        Uri selectedImageUri = data.getData();

//            String [] filePath = {MediaStore.Images.Media.DATA};
//            Cursor cursor = getContentResolver().query(selectedImageUri, filePath,null,null,null);
//            cursor.moveToFirst();
//            int columnIndex = cursor.getColumnIndex(filePath[0]);
//            String myPath = cursor.getString(columnIndex);
//            cursor.close();
//
//            Bitmap bitmap = BitmapFactory.decodeFile(myPath);
//            imageView.setImageBitmap(bitmap);
//
//            PdfDocument pdfDocument = new PdfDocument();
//            PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(bitmap.getWidth(),bitmap.getHeight(), 1).create();
//            PdfDocument.Page page = pdfDocument.startPage(pageInfo);
//
//            Canvas canvas = page.getCanvas();
//            Paint paint = new Paint();
//            paint.setColor(Color.parseColor("#FFFFFF"));
//            canvas.drawPaint(paint);
//
//
//            bitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(),bitmap.getHeight(),true);
//            paint.setColor(Color.BLUE);
//            canvas.drawBitmap(bitmap, 0,0,null);
//
//            pdfDocument.finishPage(page);


            BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
            Bitmap bitmap1 = drawable.getBitmap();




            File fp = Environment.getExternalStorageDirectory();
            File dir = new File(fp.getAbsolutePath() + "/Demo/");
            dir.mkdir();
            File file = new File(dir,System.currentTimeMillis() + ".jpg");

            FileOutputStream fileOutputStream = null;
            try {
                fileOutputStream = new FileOutputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            bitmap1.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            Toast.makeText(getApplicationContext(), "Image Saved to Internal" , Toast.LENGTH_SHORT).show();
            try {
                fileOutputStream.flush();
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }















        /*

            // save the bitmap image

            File root = new File(Environment.getExternalStorageDirectory(), "PDF Folder 12");
            if(!root.exists()){
                root.mkdir();
            }
            File file = new File(root, "CameraDemo.pdf");
            FileOutputStream fileOutputStream = null;
            try {
                fileOutputStream = new FileOutputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                pdfDocument.writeTo(fileOutputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
*/
   //         pdfDocument.close();


        }
    }
}
