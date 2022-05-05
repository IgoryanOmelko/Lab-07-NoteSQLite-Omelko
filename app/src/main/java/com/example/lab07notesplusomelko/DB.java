package com.example.lab07notesplusomelko;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DB extends SQLiteOpenHelper{

    public DB(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE notes (id INT PRIMARY KEY, content TEXT);";
        db.execSQL(sql);
    }
    public int getMaxID(){
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT MAX(id) FROM notes;";
        Cursor cur = db.rawQuery(sql, null);
        if (cur.moveToFirst()==true) return cur.getInt(0);
        return 0;
    }
    public void addNone(int id, String content){
        String sid=String.valueOf(id);
        SQLiteDatabase db = getWritableDatabase();
        String sql = "INSERT INTO notes VALUES ("+sid+", '"+content+"');";
        db.execSQL(sql);
    }
    public String getNote(int id){//get note with specified id (sid)
        String sid = String.valueOf(id);
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT content FROM notes Where id = "+sid+";";
        Cursor cur = db.rawQuery(sql, null);
        if (cur.moveToFirst()==true) return cur.getString(0);
        return "";
    }
    public boolean saveNote(int id,String value){//save note with specified id (sid)
        String sid = String.valueOf(id);
        SQLiteDatabase db = getWritableDatabase();
        String sql = "UPDATE notes SET content = '"+value+"' WHERE id = "+sid+";";
        //String sql = "SELECT content FROM notes Where id = "+sid+" ;";
        db.execSQL(sql);
        return true;
    }
    public boolean delNote(int id){//delete note with specified id (sid)
        String sid = String.valueOf(id);
        SQLiteDatabase db = getWritableDatabase();
        String sql = "DELETE FROM notes WHERE id = "+sid+";";
        //String sql = "DELETE FROM notes;";
        db.execSQL(sql);
        return true;
    }
    public void getAllNote(ArrayList<mynote> lst){//get all note from DB to array
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT id, content FROM notes;";
        Cursor cur = db.rawQuery(sql, null);
        if (cur.moveToFirst()==true) {
            do {//fill the note list while cursor can move to next row
                mynote n=new mynote();
                n.ID=cur.getInt(0);
                n.Content=cur.getString(1);
                lst.add(n);
            }while (cur.moveToNext()==true);
        }
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
