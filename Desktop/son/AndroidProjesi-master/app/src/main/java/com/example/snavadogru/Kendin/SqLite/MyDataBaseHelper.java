package com.example.snavadogru.Kendin.SqLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyDataBaseHelper extends SQLiteOpenHelper {
    private Context context;
    private static final String DataBase_Name="KendiniDene_Table.db";
    private static final int DataBase_Version=1;

    private static final String Table_Name="Gorevler_Table";
    private static final String Column_ID="Gorev_ID";
    private static final String Column_Week="Gorev_Week";
    private static final String Column_Day="Gorev_Day";
    private static final String Column_Gorev_Numb="Gorev_Numb";
    private static final String Column_GorevText="Gorev_Text";
    private static final String Column_GorevCheck="Gorev_Check";

    public MyDataBaseHelper(@Nullable Context context) {
        super(context,DataBase_Name,null,DataBase_Version);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE "+Table_Name +
                " (" + Column_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                Column_Week + " INTEGER, " +
                Column_Day + " INTEGER, " +
                Column_Gorev_Numb + " INTEGER, " +
                Column_GorevText + " TEXT, " +
                Column_GorevCheck + " INTEGER);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS "+Table_Name);
            onCreate(db);
    }
    public void addGorev(int dayNumb, int weekNumb, int dayGorevNumb, String gorevText, int gorevCheck){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(Column_Week,weekNumb);
        cv.put(Column_Day,dayNumb);
        cv.put(Column_Gorev_Numb,dayGorevNumb);
        cv.put(Column_GorevText,gorevText);
        cv.put(Column_GorevCheck,gorevCheck);
        long result = db.insert(Table_Name,null,cv);
        if (result==-1)
            Toast.makeText(context,"Hata Oluştu",Toast.LENGTH_SHORT).show();
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
        String query = "SELECT * FROM " + Table_Name+" WHERE Gorev_Week = "+weekNumb+" AND "+"Gorev_Day = "+dayNumb;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;
        if (db!=null)
            cursor=db.rawQuery(query,null);
        return cursor;
    }

    public Cursor queryItem(int gorevWeek,int gorevDay,int gorevNumb){
        String query = "SELECT * FROM " + Table_Name+ " WHERE Gorev_Week = "+gorevWeek+" AND "+"Gorev_Day = "+gorevDay+" AND "+"Gorev_Numb = "+gorevNumb;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;
        if (db!=null)
            cursor=db.rawQuery(query,null);
        else
            Log.d("queryItem","gorev Not Found");
        return cursor;
    }

    public void updateData(int weekNumb,int dayNumb,  int dayGorevNumb, String gorevText, int gorevCheck){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Column_Week,weekNumb);
        cv.put(Column_Day,dayNumb);
        cv.put(Column_Gorev_Numb,dayGorevNumb);
        cv.put(Column_GorevText,gorevText);
        cv.put(Column_GorevCheck,gorevCheck);
    //    Log.d("updateData","updateData "+weekNumb+" "+dayNumb+" "+dayGorevNumb);
        long result = db.update(Table_Name,cv,Column_Week+" = ?"+" AND "+Column_Day+" = ?"+" AND "+Column_Gorev_Numb+" = ?",new String[]{weekNumb+"",dayNumb+"",dayGorevNumb+""});
        if (result==-1)
            Toast.makeText(context,"Hata Oluştu",Toast.LENGTH_SHORT).show();
     /*   else
            Log.d("updateData","HÇIKTIIata OluşMADI");*/
    }

    public void removeWeekData(int gorevWeek){
        SQLiteDatabase db = this.getWritableDatabase();

       /* long result = */ db.delete(Table_Name,Column_Week+" = ?",new String[]{gorevWeek+""});
    /*   if (result==-1)
            Log.d("Bulunamadi","removeDay");
        else
            Log.d("removeSingleData","position ÇIKTII");*/

    }
    public void removeAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+Table_Name);
    }

}
