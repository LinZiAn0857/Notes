package com.example.notes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DB extends SQLiteOpenHelper {
    private Context mCtx;
    private static final String DATABASE_NAME = "Notes.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "my_notes";
    private static final String ID = "note_id";
    private static final String TITLE = "note_title";
    private static final String CONTENT = "note_content";
    public DB(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.mCtx=context;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE IF NOT EXISTS "+ TABLE_NAME +
                " (" + ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                TITLE+" TEXT, "+CONTENT+" TEXT);";
        db.execSQL(query);
//        db.execSQL("INSERT INTO " + TABLE_NAME + " VALUES(NULL, '測試0217', '測試')");//測試資料庫可否使用
        Log.d("show_msg","db_create");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("show_msg","onUpgrade()");
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }
    public void addNotes(String title, String content){//不用寫入id，因為AUTOINCREMENT
        SQLiteDatabase db = this.getWritableDatabase();//取得目前資料庫
        ContentValues cv = new ContentValues();
        cv.put(TITLE, title);
        cv.put(CONTENT, content);
        long result = db.insert(TABLE_NAME, null ,cv);
        if (result == -1){
            Toast.makeText(mCtx,"Failed", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(mCtx,"Add Successfully!", Toast.LENGTH_SHORT).show();
        }
    }
    Cursor readAllData(){
        String query = "select * from "+TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();//取得目前資料庫
        Cursor cursor = null;
        if (db!=null){
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }
    public void updateData(String row_id, String title, String content){
        SQLiteDatabase db = this.getWritableDatabase();//取得目前資料庫
        ContentValues cv = new ContentValues();
        cv.put(TITLE, title);
        cv.put(CONTENT, content);
        long result = db.update(TABLE_NAME, cv, "note_id=?", new String[]{row_id});
        if (result == -1){
            Toast.makeText(mCtx,"Failed to Update", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(mCtx,"Updated Successfully!", Toast.LENGTH_SHORT).show();
        }
    }
    public void deleteOneRow(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();//取得目前資料庫
        long result=db.delete(TABLE_NAME,ID+"=?",new String[]{row_id});
        if (result == -1){
            Toast.makeText(mCtx,"Failed to Delete", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(mCtx,"Delete Successfully!", Toast.LENGTH_SHORT).show();
        }
    }
    public void deleteAllData(){
        SQLiteDatabase db = this.getWritableDatabase();//取得目前資料庫
        db.execSQL("Delete FROM "+TABLE_NAME);
    }
}
