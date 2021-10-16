package com.michaelwijaya.xyzmonthlyexpenseapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//extends to SQLiteOpenHelper to help managing SQLite database
public class ExpenseDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "ExpenseDatabase.db";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE "  + ExpenseContract.ExpenseEntry.TABLE_NAME + " (" +
                    ExpenseContract.ExpenseEntry._ID + " INTEGER PRIMARY KEY, " +
                    ExpenseContract.ExpenseEntry.COLUMN_NAME_EXPENSE_NAME + " TEXT," +
                    ExpenseContract.ExpenseEntry.COLUMN_NAME_EXPENSE_NOMINAL + " TEXT," +
                    ExpenseContract.ExpenseEntry.COLUMN_NAME_EXPENSE_DATE + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + ExpenseContract.ExpenseEntry.TABLE_NAME;

    public ExpenseDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}
