package com.example.andreas.p1finance;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Andreas on 2016-09-15.
 */
public class DataBase extends SQLiteOpenHelper {

    public static final String TABLE_EXPENSES = "EXPENSES";
    public static final String TABLE_INCOME = "INCOME";

    public static final String COLUMN_TITLE = "Title";
    public static final String COLUMN_DATE = "Date";
    public static final String COLUMN_CATEGORY = "Category";
    public static final String COLUMN_AMOUNT = "Amount";
    private static final String DATABASE_NAME = "p1financedb";
    private static final int DATABASE_VERSION = 22;

    // Database creation sql statement
    private static final String DATABASE_CREATE_EXPENSES =
            "create table " + TABLE_EXPENSES + "(" +
                    COLUMN_TITLE + " text not null primary key, " +
                    COLUMN_DATE + " text not null, " +
                    COLUMN_CATEGORY + " text, " +
                    COLUMN_AMOUNT + " text);";

    private static final String DATABASE_CREATE_INCOME =
            "create table " + TABLE_INCOME + "(" +
                    COLUMN_TITLE + " text not null primary key, " +
                    COLUMN_DATE + " text not null, " +
                    COLUMN_CATEGORY + " text, " +
                    COLUMN_AMOUNT + " text);";

    public DataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE_EXPENSES);
        db.execSQL(DATABASE_CREATE_INCOME);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DataBase.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXPENSES );
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INCOME );
        onCreate(db);
    }
    public void insertExpenses(Value value){
        //Problem, skapa bara en gång???
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DataBase.COLUMN_TITLE, value.getTitle());
        values.put(DataBase.COLUMN_DATE, value.getDate());
        values.put(DataBase.COLUMN_AMOUNT, value.getAmount());
        values.put(DataBase.COLUMN_CATEGORY, value.getCategory());
        db.insert(DataBase.TABLE_EXPENSES, null, values);
        Log.e("FromDataBase","Expenses");
        //db.close();
    }
    //method for showning the income data in the database.
    public void insertIncome(Value value){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DataBase.COLUMN_TITLE, value.getTitle());
        values.put(DataBase.COLUMN_DATE, value.getDate());
        values.put(DataBase.COLUMN_AMOUNT, value.getAmount());
        values.put(DataBase.COLUMN_CATEGORY, value.getCategory());
        db.insert(DataBase.TABLE_INCOME, null, values);
        Log.e("FromDataBase","Income");
        //db.close();
    }
}
