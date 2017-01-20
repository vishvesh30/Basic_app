package com.example.vishvraj.basic_app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by vishvraj on 19-01-2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    public  static final String DATABASE_NAME="info.db";
    public  static final String Table_NAME="info_table";
    public  static final String col_id="id";
    public  static final String col_name="name";
    public  static final String col_surname="surname";
    public  static final String col_email="email";
    public  static final String col_pass="password";
    public  static final String col_phn="phnno";
    public  static final String col_gender="gender";
    public  static final String col_city="city";
    public  static final String col_photo="photo";
    public  static final String col_hooby="hobby";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table "+Table_NAME+" (ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,SURNAME TEXT,EMAIL TEXT,PASSWORD TEXT,PHNNO TEXT,GENDER TEXT,CITY TEXT,PHOTO TEXT,HOBBY TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXIST"+Table_NAME);
        onCreate(sqLiteDatabase);
    }
    public boolean insertData(String name,String surname,String email,String password,String phn_no,String gender,String city,String photo, String hooby){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(col_name,name);
        contentValues.put(col_surname,surname);
        contentValues.put(col_email,email);
        contentValues.put(col_pass,password);
        contentValues.put(col_phn,phn_no);
        contentValues.put(col_gender,gender);
        contentValues.put(col_city,city);
        contentValues.put(col_photo,photo);
        contentValues.put(col_hooby,hooby);
        long result=db.insert(Table_NAME,null,contentValues);
       if(result==-1){
            return false;
        }
     else
         return true;
    }
    public Cursor getAllData(){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("select * from "+Table_NAME,null);
        return cursor;
    }
}
