package ykskoc.example.snavadogru.Camera;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.ykskoc.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CozemediginSorular extends AppCompatActivity {
    Button photo,makeTest;
    ImageView im;
    Bitmap capturedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_YES)
            setTheme(R.style.AppTheme_DarkMode);
        else
            setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cozemedigin_sorular);
        photo=findViewById(R.id.fotoCek);
        makeTest=findViewById(R.id.testOlustur);
      //  im = findViewById(R.id.imageview_cozemedigin_sorular);
        //capturedImage = BitmapFactory.decodeResource(getResources(), R.drawable.white_question);

        photo.setOnClickListener(v -> {
            Intent open_camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(open_camera,1);
        });

        makeTest.setOnClickListener(v -> {
            Intent camera_activity = new Intent(CozemediginSorular.this, NewCamera.class);
            startActivity(camera_activity);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data==null)
            data = new Intent(String.valueOf(CozemediginSorular.class));
        Bundle bundle = new Bundle();
        if(data.getExtras()!=null)
            bundle = data.getExtras();
        if(data != null){
             capturedImage =(Bitmap) bundle.get("data");

           // Bitmap mBitmap = Bitmap.createScaledBitmap(capturedImage, 480, 300, true);
            //im.setImageBitmap(capturedImage);
            if(capturedImage!=null)

            storeImage(capturedImage);//bunun nereye kaydettiğini bulamadım
            //  createFile(capturedImage);
        }

    }
    public void store(Bitmap bitmap){

        String path = Environment.getExternalStorageDirectory().toString();
        OutputStream fOut = null;
        String title = "Soru"+System.currentTimeMillis();
        File file = new File(path, title+".jpg"); // the File to save , append increasing numeric counter to prevent files from getting overwritten.
        try {
            fOut = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, fOut); // saving the Bitmap to a file compressed as a JPEG with 85% compression rate
        try {
            fOut.flush(); // Not really required
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fOut.close(); // do not forget to close the stream
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            MediaStore.Images.Media.insertImage(getContentResolver(),file.getAbsolutePath(),file.getName(),file.getName());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    private void storeImage(Bitmap image) {
        File pictureFile = getOutputMediaFile();
        if (pictureFile == null) {
            Log.d("TAG",
                    "Error creating media file, check storage permissions: ");
            return;
        }
        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            image.compress(Bitmap.CompressFormat.PNG, 90, fos);
            fos.close();
        } catch (FileNotFoundException e) {
            Log.d("TAG", "File not found: " + e.getMessage());
        } catch (IOException e) {
            Log.d("TAG", "File not found: " + e.getMessage());
        }
    }
    private  File getOutputMediaFile(){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() +
                "/Yks");
        Toast.makeText(getApplicationContext(),Environment.getExternalStorageDirectory() +
                        "/Yks",Toast.LENGTH_LONG).show();
        Log.d("Konum: ",Environment.getExternalStorageDirectory().getAbsolutePath() +
                "/Yks");
      /*Environment.getExternalStorageDirectory()
                + "/Android/data/"
                + getApplicationContext().getPackageName()
                + "/Files"
*/

        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmm").format(new Date());
        File mediaFile;
        String mImageName="Soru_"+ timeStamp +".jpg";
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + mImageName);
        return mediaFile;
    }

    public void createFile(Bitmap bitmap){

        File storageDir = Environment.getExternalStorageDirectory();
        assert storageDir != null;
        File dir= new File(storageDir.getAbsolutePath()+"/Yks'ye Koş");
        dir.mkdirs();

        File outFile= new File(dir,System.currentTimeMillis() + ".jpg");

        FileOutputStream outputStream=null;
        try {
            outputStream=new FileOutputStream(outFile);
            Log.d("createFile","createFile"+"-");
        }catch (IOException e)
        {
            Log.d("Exception","Ex "+e.toString());
        }
        bitmap.compress(Bitmap.CompressFormat.PNG,100,outputStream);
        try {
            assert outputStream != null;
            outputStream.flush();
            outputStream.close();
        }catch (Exception e){e.getStackTrace();}
    }


}
