package com.example.doordrink;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

/**
 * Class that handles the database for the game.
 */
public class MyDbHandler extends SQLiteOpenHelper {

    private String name;

    public MyDbHandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.name = name.replace(".db", "");
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + name + "(Question TEXT ,ID INTEGER PRIMARY KEY)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + name);
        //onCreate(db);
    }

    public void addQuestion(String s) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Question", s);
        DB.insert(name, null, contentValues);
    }

    public ArrayList<Question> getQuestions() {
        ArrayList<Question> list = new ArrayList<>();
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from " + name, null);
        while (cursor.moveToNext()) {
            String q = cursor.getString(0);
            list.add(new Question(q));
        }
        return list;
    }

}
