package com.example.andreas.p1finance;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Andreas on 2016-09-17.
 */
public class MainController {

    private DataBase dbHelper;
    private ExpensesActivity expensesActivity;
    private IncomeActivity incomeActivity;
    private MainActivity mainActivity;
    private int totIncome, totExpenses, balance;

    public final static String VALUES = "com.example.andreas.p1finance.VALUE";

    //Constructors
    public MainController(ExpensesActivity expensesActivity) {
        this.expensesActivity = expensesActivity;
        dbHelper = new DataBase(expensesActivity);
    }
    public MainController(IncomeActivity incomeActivity){
        this.incomeActivity = incomeActivity;
        dbHelper = new DataBase(incomeActivity);
    }
    public MainController(MainActivity mainActivity){
        this.mainActivity = mainActivity;
        dbHelper = new DataBase(mainActivity);
    }

    public int getTotIncome() {
        return totIncome;
    }

    public int getTotExpenses() {
        return totExpenses;
    }

    public int getBalance() {
        return balance;
    }

    //Insert Expenses
    public void insertExpenses (Value value){
        dbHelper.insertExpenses(value);
    }
    //Insert Income
    public void insertIncome(Value value){
        dbHelper.insertIncome(value);
    }

    //Methods for calculating income, expenses and balance
    public Integer calcIncome(){
        int totIncome = 0;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DataBase.TABLE_INCOME + " ORDER BY " + DataBase.COLUMN_DATE, null);
        int amountIndex = cursor.getColumnIndex(DataBase.COLUMN_AMOUNT);
        for(int i = 0; i < cursor.getCount(); i++){
            cursor.moveToPosition(i);
            totIncome += Integer.parseInt(cursor.getString(amountIndex));
        }
        return totIncome;
    }
    public Integer calcExpenses(){
        int totExpenses = 0;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DataBase.TABLE_EXPENSES + " ORDER BY " + DataBase.COLUMN_DATE, null);
        int amountIndex = cursor.getColumnIndex(DataBase.COLUMN_AMOUNT);
        for(int i = 0; i < cursor.getCount(); i++){
            cursor.moveToPosition(i);
            totExpenses += Integer.parseInt(cursor.getString(amountIndex));
        }
        return totExpenses;
    }
    public Integer balance(){
        int balance = totIncome - totExpenses;
        return balance;
    }

    public void updateTexts() {
        totIncome = calcIncome();
        totExpenses = calcExpenses();
        balance = balance();
    }

    public void showList() {
        int titleIndex, dateIndex, categoryIndex, amountIndex;
        int titleIndex1, dateIndex1, categoryIndex1, amountIndex1;

        Value[] values = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DataBase.TABLE_EXPENSES + " ORDER BY " + DataBase.COLUMN_DATE, null);
        Cursor cursor1 = db.rawQuery("SELECT * FROM " + DataBase.TABLE_INCOME + " ORDER BY " + DataBase.COLUMN_DATE, null);

        values = new Value[cursor.getCount() + cursor1.getCount()];
        titleIndex = cursor.getColumnIndex(DataBase.COLUMN_TITLE);
        dateIndex = cursor.getColumnIndex(DataBase.COLUMN_DATE);
        categoryIndex = cursor.getColumnIndex(DataBase.COLUMN_CATEGORY);
        amountIndex = cursor.getColumnIndex(DataBase.COLUMN_AMOUNT);
        titleIndex1 = cursor1.getColumnIndex(DataBase.COLUMN_TITLE);
        dateIndex1 = cursor1.getColumnIndex(DataBase.COLUMN_DATE);
        categoryIndex1 = cursor1.getColumnIndex(DataBase.COLUMN_CATEGORY);
        amountIndex1 = cursor1.getColumnIndex(DataBase.COLUMN_AMOUNT);

        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            values[i] = new Value(
                    cursor.getString(titleIndex),
                    cursor.getString(dateIndex),
                    cursor.getString(amountIndex),
                    cursor.getString(categoryIndex));
        }
        cursor.close();
        for (int i = 0; i < cursor1.getCount(); i++){
            cursor1.moveToPosition(i);
            values[i  + cursor.getCount()] = new Value(
                    cursor1.getString(titleIndex1),
                    cursor1.getString(dateIndex1),
                    cursor1.getString(amountIndex1),
                    cursor1.getString(categoryIndex1));
        }
        cursor1.close();
        Intent intent = new Intent(mainActivity, StatementActivity.class);
        intent.putExtra(VALUES, values);
        mainActivity.startActivity(intent);
    }
}
