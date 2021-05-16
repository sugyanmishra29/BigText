package com.example.bigtext.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.bigtext.model.Convo;
import com.example.bigtext.params.Params;

import java.util.ArrayList;
import java.util.List;

public class MyDbHandler extends SQLiteOpenHelper {
    public MyDbHandler(Context context) {
        super(context, Params.DB_NAME, null, Params.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //whenever i create an object of MyDbHandler, this code should get executed
        String create = "CREATE TABLE " + Params.TABLE_NAME + "(" + Params.KEY_ID + " INTEGER PRIMARY KEY," + Params.KEY_MSG + " TEXT, " + Params.KEY_SIZE + " TEXT" + ")";
        Log.d("dbSugy", "Query being run is : " + create);
        db.execSQL(create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addConvo(Convo convo) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Params.KEY_MSG, convo.getConvoText());
        values.put(Params.KEY_SIZE, convo.getConvoSize());
        db.insert(Params.TABLE_NAME, null, values);
        Log.d("dbSugy", "Successfully inserted");
        db.close();
    }

    //get all contacts to display as a list on activity_main.xml
    public List<Convo> getAllConvos() {
        List<Convo> contactList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        //Generate the query to read from the database
        String select = "SELECT * FROM " + Params.TABLE_NAME;
        Cursor cursor = db.rawQuery(select, null); //cursor is an iterator basically

        //Loop through now
        if (cursor.moveToFirst()) {
            do {
                Convo convo = new Convo();
                convo.setId(Integer.parseInt(cursor.getString(0)));
                convo.setConvoText(cursor.getString(1));
                convo.setConvoSize(cursor.getInt(2));
                //collected all contact details, now add them in the contact list

                contactList.add(convo);
            } while (cursor.moveToNext());
        }
        return contactList;
    }

    public int getCount() {
        String query = "SELECT  * FROM " + Params.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        return cursor.getCount();
    }
}
