package com.example.jyotirmay.contactapplication.com.example.jyotirmay.contactapplication.datasource;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Krishnendu on 11-Feb-18.
 */

public class DbHandler {

    public static String TABLE_NAME= "CONTACT_TABLE";
    public static String COL_ID= "C_ID";
    public static String COL_NAME= "NAME";
    public static String COL_PHONE= "PHONE";
    public static String COL_EMAIL= "EMAIL";

    OpenHandler openHandler;

    public DbHandler(Context context)
    {
        String db_name= "User";
        int db_version= 1;
        openHandler=new OpenHandler(context,db_name, null, db_version);
    }

    public boolean insert(String name, String phone, String email)
    {
        SQLiteDatabase db=openHandler.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(COL_NAME, name);
        values.put(COL_PHONE, phone);
        values.put(COL_EMAIL,email);

        long row= db.insert(TABLE_NAME,null,values);
        db.close();
        return row != -1;
    }

    public Cursor read()
    {
        SQLiteDatabase db=openHandler.getReadableDatabase();
        String COL[]={COL_ID, COL_NAME, COL_PHONE, COL_EMAIL};
        Cursor cursor = db.query(TABLE_NAME, COL, null, null, null, null, COL_NAME);
        return cursor;
    }

    public Cursor readDetail(String id)
    {
        SQLiteDatabase db=openHandler.getReadableDatabase();
        String COL[]={COL_NAME, COL_PHONE, COL_EMAIL};
        String whereAgs[]={id};
        String where=COL_ID + "= ?";
        Cursor cursor=db.query(TABLE_NAME, COL, where, whereAgs, null, null, null);
        return cursor;
    }

    public void deleteContact(String id)
    {
        SQLiteDatabase db=openHandler.getWritableDatabase();
        String whereAgs[]={id};
        String where=COL_ID + "= ?";
        db.delete(TABLE_NAME, where, whereAgs);
    }

    public boolean updateDetail(String id, String name, String phone, String email)
    {
        SQLiteDatabase db=openHandler.getReadableDatabase();
        ContentValues values=new ContentValues();
        values.put(COL_NAME, name);
        values.put(COL_PHONE, phone);
        values.put(COL_EMAIL,email);
        String whereAgs[]={id};
        String where=COL_ID + "= ?";
        int row=db.update(TABLE_NAME, values,where, whereAgs);
        db.close();
        return row != -1;
    }

    public class OpenHandler extends SQLiteOpenHelper {
        public OpenHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL("CREATE TABLE "+TABLE_NAME+" (" +
                    COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," +
                    COL_NAME + " VARCHAR (100) ," +
                    COL_PHONE + " VARCHAR (20) ," +
                    COL_EMAIL + " VARCHAR (100))");
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            onCreate(sqLiteDatabase);
        }
    }
}
