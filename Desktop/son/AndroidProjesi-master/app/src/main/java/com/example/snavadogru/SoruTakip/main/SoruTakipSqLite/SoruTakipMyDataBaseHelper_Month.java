package com.example.snavadogru.SoruTakip.main.SoruTakipSqLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class SoruTakipMyDataBaseHelper_Month extends SQLiteOpenHelper {
    private Context context;
    private static final String DataBase_Name="SoruTakipMonth_Table.db";
    private static final int DataBase_Version=1;

    private static final String Table_Name="SoruTakipMonth_Table";
    private static final String Column_ID="Monh_ID";
    private static final String Column_Name="Month_Name";
    private static final String Column_Value="Month_Value";

    public SoruTakipMyDataBaseHelper_Month(@Nullable Context context) {
        super(context,DataBase_Name,null,DataBase_Version);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
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
        if (result==-1)
            Toast.makeText(context,"Hata Oluştu",Toast.LENGTH_SHORT).show();
    }
    public Cursor readAllData (){
        String query = "SELECT * FROM " + Table_Name;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;
        if (db!=null)
            cursor=db.rawQuery(query,null);
        return cursor;
    }

    public Cursor queryItem(int monthNumb){
        String query = "SELECT * FROM " + Table_Name+ " WHERE Month_Name = "+monthNumb;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;
        if (db!=null){
            cursor=db.rawQuery(query,null);
            Log.d("CursorCursor","Cursor "+cursor.getCount()+"- "+cursor);
        }
        else
            Log.d("queryLastItem","dontGetted");

        return cursor;
    }

    public void updateData(int monthNumb, int monthValue){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(Column_Name,monthNumb);
        cv.put(Column_Value,monthValue);
        long result = db.update(Table_Name,cv,"Month_Name=?",new String[]{monthNumb+""});
        if (result==-1)
            Toast.makeText(context,"Hata Oluştu",Toast.LENGTH_SHORT).show();
    }

    public void removeAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+Table_Name);
    }

    public void removeSingleData(int monthName){
        SQLiteDatabase db = this.getWritableDatabase();

        long result = db.delete(Table_Name,"Month_Name=?",new String[]{monthName+""});
        if (result==-1)
            Toast.makeText(context,"Hata Oluştu",Toast.LENGTH_SHORT).show();
        else
            Log.d("removeSingleData","Month_Name ÇIKTII");
    }
}
