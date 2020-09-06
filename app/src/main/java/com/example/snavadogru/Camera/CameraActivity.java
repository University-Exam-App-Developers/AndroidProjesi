package com.example.snavadogru.Camera;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.snavadogru.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class CameraActivity extends AppCompatActivity {
    ImageView imageView;
    Button button;
    Bitmap captureImage;
    Button makePdf;
    File file;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

//            Intent intentToPhoto = new Intent(MainActivity.this, CameraActivity);
//            startActivity(intentToPhoto);

        imageView = findViewById(R.id.image_view);
        button = findViewById(R.id.bt_open);
        makePdf = findViewById(R.id.make_pdf);

        //REQUEST FOR CAMERA PERMISSION

        if(ContextCompat.checkSelfPermission(CameraActivity.this,Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(CameraActivity.this,new String[]{
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

        makePdf.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());

                PdfDocument pdfDocument = new PdfDocument();
                PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(bitmap.getWidth(),bitmap.getHeight(), 1).create();
                PdfDocument.Page page = pdfDocument.startPage(pageInfo);

                Canvas canvas = page.getCanvas();
                Paint paint = new Paint();
                paint.setColor(Color.parseColor("#FFFFFF"));
                canvas.drawPaint(paint);


                bitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(),bitmap.getHeight(),true);
                paint.setColor(Color.BLUE);
                canvas.drawBitmap(bitmap, 0,0,null);

                pdfDocument.finishPage(page);

                // save the bitmap image

                File root = new File(getExternalCacheDir(), "PDF Folder 12");
                if(!root.exists()){
                    root.mkdir();
                }
                File file = new File(root, System.currentTimeMillis() + ".pdf");
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

                pdfDocument.close();
                Toast.makeText(CameraActivity.this, "PDF DOCUMENT IS SUCCESFULLY SAVED TO FILES!", Toast.LENGTH_SHORT).show();
            }
        });
    }



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 120 && data != null) {
            // GETTING CAPTURED IMAGE
            captureImage = (Bitmap) data.getExtras().get("data");
            //SET CAPTURE IMAGE TO IMAGEVIEW VARIABLE
            imageView.setImageBitmap(captureImage);

            BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
            Bitmap bitmap1 = drawable.getBitmap();




            File fp = getExternalCacheDir();
            File dir = new File(fp.getAbsolutePath() + "/Demo/");
            dir.mkdir();
            file = new File(dir,System.currentTimeMillis() + ".jpg");


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
        }
    }
}
