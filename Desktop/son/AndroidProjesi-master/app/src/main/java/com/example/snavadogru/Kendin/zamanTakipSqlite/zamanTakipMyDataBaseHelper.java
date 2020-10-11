package com.example.snavadogru.Kendin.zamanTakipSqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class zamanTakipMyDataBaseHelper extends SQLiteOpenHelper {
    private Context context;
    private static final String DataBase_Name="zamanTakipDay_Table.db";
    private static final int DataBase_Version=1;

    private static final String Table_Name="SoruTakipDay_Table";
    private static final String Column_ID="Day_ID";
    private static final String Column_Name="Day_Name";
    private static final String Column_Value="Day_Value";

    public zamanTakipMyDataBaseHelper(@Nullable Context context) {
        super(context,DataBase_Name,null,DataBase_Version);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    //    Log.d("onCreate","CREATED");
        String query = "CREATE TABLE "+Table_Name +
                " (" + Column_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                Column_Name + " INTEGER, " +
                Column_Value + " INTEGER);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS "+Table_Name);
            onCreate(db);
    }
    public void addValue(int dayName, int dayValue){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(Column_Name,dayName);
        cv.put(Column_Value,dayValue);
        long result = db.insert(Table_Name,null,cv);
        if (result==-1){
      //      Log.d("KANkA DAY","EKLENMEDİ");
            Toast.makeText(context,"Hata Oluştu",Toast.LENGTH_SHORT).show();
        }
     //   else Log.d("DAY","OK");
    }
    public Cursor readAllData (){
        String query = "SELECT * FROM " + Table_Name;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;
        if (db!=null)
            cursor=db.rawQuery(query,null);
        return cursor;
    }


    public Cursor readWeekData(int weekNumb){
        String query = "SELECT * FROM " + Table_Name+" WHERE Gorev_Week = "+weekNumb;
        SQLiteDatabase db = this.getWritableDatabase();
        //   String[] columns={Column_ID,Column_Week,Column_Day,Column_Gorev_Numb,Column_GorevText,Column_GorevCheck};
        Cursor cursor = null;
        if (db!=null)
            cursor=db.rawQuery(query,null);
        return cursor;
    }
    public Cursor readDayData(int weekNumb,int dayNumb){
        String query = "SELECT * FROM " + Table_Name+ " WHERE Gorev_Week = "+weekNumb+" AND Gorev_Day = "+dayNumb; // AND Gorev_Day = dayNumb";
        SQLiteDatabase db = this.getWritableDatabase();
        //   String[] columns={Column_ID,Column_Week,Column_Day,Column_Gorev_Numb,Column_GorevText,Column_GorevCheck};
        Cursor cursor = null;
        if (db!=null)
            cursor=db.rawQuery(query,null);
        return cursor;
    }

    public Cursor queryLastItem(int dayNumb){
        String query = "SELECT * FROM " + Table_Name+ " WHERE Day_Name = "+dayNumb;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;
        if (db!=null)
            cursor=db.rawQuery(query,null);
    //    else
    //        Log.d("queryLastItem","Day Not Found");
        return cursor;
    }

    public void updateData(int dayName, int dayValue){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(Column_Name,dayName);
        cv.put(Column_Value,dayValue);
        long result = db.update(Table_Name,cv,"Day_Name=?",new String[]{dayName+""});
        if (result==-1)
            Toast.makeText(context,"Hata Oluştu",Toast.LENGTH_SHORT).show();
   //     else
   //     Log.d("updateData","HÇIKTIIata OluşMADI");
    }

    public void removeAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+Table_Name);
    }

    public void removeSingleData(int dayName){
        SQLiteDatabase db = this.getWritableDatabase();

        long result = db.delete(Table_Name,"Day_Name=?",new String[]{dayName+""});
        if (result==-1){
   //         Log.d("HATALANDIN","Day_Name HATALANDI");
            Toast.makeText(context,"Hata Oluştu",Toast.LENGTH_SHORT).show();
        }
     //   else
      //      Log.d("removeSingleData","Day_Name ÇIKTII "+dayName);
    }
}
