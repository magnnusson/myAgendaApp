package com.csci4211.myagendaapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataManager {
    // class for the database in order to persist our data. Uses SQLite
    private SQLiteDatabase db;

    public DataManager(Context context)
    {
        helperClass helper = new helperClass(context);
        db = helper.getWritableDatabase();
    }

    public void insert(String name, String date, String time, String description, int priority)
    {

        String query = "insert into item" +
                "(name, date, time, description, priority) values" +
                "( '" + name + "', '" + date + "', '" + time + "', '" + description + "', '" + priority +
                "')";

        try
        {
            db.execSQL(query);
        }
        catch(SQLException e)
        {
            Log.i("info", "insert method");
            Log.i("info", e.getMessage());
        }
        Log.i("info", "Added new item " + name);
    }

    public void delete(String name)
    {
        try
        {
            db.delete("item", "name=?", new String[] {name}); // ? is bound to where arg
        }
        catch(SQLException e)
        {
            Log.i("info", "delete method of dataManager");
            Log.i("info", e.getMessage());
        }
        Log.i("info", "Deleted item" + name);
    }

    public void update(String name, Item item)
    {
        String priorityText;
        if(item.getPriority())
        {
            priorityText = "Yes";
        }
        else
            {
                priorityText = "No";
            }

        ContentValues values = new ContentValues();
        values.put("name", item.getName());
        values.put("date", item.getDate());
        values.put("time", item.getTime());
        values.put("description", item.getDescription());
        values.put("priority", priorityText);

        try
        {
            db.update("item", values, "name=?", new String[]{name});
        }
        catch(SQLException e)
        {
            Log.i("info", "update method of dataManager");
            Log.i("info", e.getMessage());
        }
        Log.i("info", "Updated item " + name);
    }

    public Cursor selectAll()
    {
        Cursor cursor = null;
        String query = "select * from item order by date";

        try
        {
            cursor = db.rawQuery(query, null);
        }
        catch(Exception e)
        {
            Log.i("info", "selectAll method");
            Log.i("info", e.getMessage());
        }

        Log.i("info", "Loaded data " + cursor.getCount());
        return cursor;
    }

    private class helperClass extends SQLiteOpenHelper
    {
        public helperClass(Context context)
        {
            super(context, "TODOLIST", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // table to hold the list items
            String newTable = "create table item ("
                    + "_id integer primary key autoincrement not null, "
                    + "name text not null, "
                    + "date text, "
                    + "time text, "
                    + "description text, "
                    + "priority integer)";

            try
            {
                db.execSQL(newTable);
            }
            catch(SQLException e)
            {
                Log.i("info", "onCreate method of SQLiteOpenHelper");
                Log.i("info", e.getMessage());
            }

        }

        // onUpgrade method not needed.
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


        }
    }
}
