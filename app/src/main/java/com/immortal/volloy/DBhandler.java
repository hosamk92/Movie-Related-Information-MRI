package com.immortal.volloy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

/**
 * Created by IMMORTAl on 7/22/2017.
 */

public class DBhandler extends SQLiteOpenHelper {
    public DBhandler(Context context) {
        super(context, contractor.table_name, null, contractor.version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String cr = "Create table "+ contractor.table_name + " ( "
                + contractor.title + " TEXT , "
                + contractor.overview +" TEXT , "
                + contractor.poster_path + " BLOB , "
                + contractor.vote_average +" TEXT , "
                + contractor.backdrop_path + " BLOB , "
                + contractor.release_date+ " TEXT , "
                + contractor.id + " TEXT  " + ")";
        db.execSQL(cr);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public static byte[] getBitmapAsByteArray(Bitmap bitmap)
    {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0,outputStream);
        return outputStream.toByteArray();
    }
    public void insert(String title, String overview, Bitmap poster, float vote, Bitmap back, String release,String id)
    {
        SQLiteDatabase db=getWritableDatabase();
        ContentValues row=new ContentValues();
        row.put(contractor.title,title);
        row.put(contractor.overview,overview);
        row.put(contractor.poster_path,getBitmapAsByteArray(poster));
        row.put(contractor.vote_average,vote+"");
        row.put(contractor.backdrop_path,getBitmapAsByteArray(back));
        row.put(contractor.release_date,release);
        row.put(contractor.id,id);
        db.insert(contractor.table_name,null,row);
        db.close();
    }
    public ArrayList<Movie> Select()
    {
        ArrayList<Movie> list=new ArrayList<>();
        SQLiteDatabase dp=getReadableDatabase();
        Cursor cur = dp.query(contractor.table_name,null,null,null,null,null,null);
        cur.moveToFirst();
        for(int i=0;i<cur.getCount();i++)
        {
            list.add(new Movie(
                    cur.getString(0),
                    cur.getString(1),
                    cur.getBlob(2),
                    Float.parseFloat(cur.getString(3)),
                    cur.getBlob(4),
                    cur.getString(5),
                    cur.getString(6)
            ));
            cur.moveToNext();
        }
        cur.close();
        dp.close();
        return  list;
    }
    public void delete(String id)
    {
        SQLiteDatabase db=getWritableDatabase();
        db.delete(contractor.table_name,contractor.id +" = ?",new String[]{id+""});
        db.close();

    }
}
