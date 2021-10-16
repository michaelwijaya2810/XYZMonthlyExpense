package com.michaelwijaya.xyzmonthlyexpenseapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<Expense> expenseList;

    ExpenseDbHelper dbHelper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView rvExpenseList = findViewById(R.id.rv_expense_list);

        //setting action bar title
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("My Spending");

        //getting instance of SQLiteOpenHelper class, and SQLiteDatabase
        dbHelper = new ExpenseDbHelper(getApplicationContext());
        db = dbHelper.getReadableDatabase();

        //all columns to be used for selection, SELECT *
        String[] projection = {
                BaseColumns._ID,
                ExpenseContract.ExpenseEntry.COLUMN_NAME_EXPENSE_NAME,
                ExpenseContract.ExpenseEntry.COLUMN_NAME_EXPENSE_NOMINAL,
                ExpenseContract.ExpenseEntry.COLUMN_NAME_EXPENSE_DATE
        };

        //running SQLite Query
        /*SELECT *
          FROM TABLE_NAME
          ORDER BY _ID ASC
         */
        Cursor cursor = db.query(
                ExpenseContract.ExpenseEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                ExpenseContract.ExpenseEntry._ID + " ASC"
        );

        //using cursor to get all values from each rows in the table
        expenseList = makeArrayList(cursor);

        //setting RecyclerView Adapter & LayoutManager
        ExpenseAdapter adapter = new ExpenseAdapter(expenseList);
        rvExpenseList.setAdapter(adapter);
        rvExpenseList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //add plus button to the action bar
        getMenuInflater().inflate(R.menu.add_item_action_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        //start AddItemActivity when plus button is pressed
        if(item.getItemId() == R.id.action_add_item){
            startActivity(new Intent(MainActivity.this, AddItemActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public ArrayList<Expense> makeArrayList(Cursor cursor){
        ArrayList<Expense> expenseList = new ArrayList<>();
        //Cursor will read each rows in the table and read the values of the row
        while(cursor.moveToNext()){
            Long itemId = cursor.getLong(
                    cursor.getColumnIndexOrThrow(ExpenseContract.ExpenseEntry._ID));
            String name = cursor.getString(
                    cursor.getColumnIndexOrThrow(ExpenseContract.ExpenseEntry.COLUMN_NAME_EXPENSE_NAME));
            String nominal = cursor.getString(
                    cursor.getColumnIndexOrThrow(ExpenseContract.ExpenseEntry.COLUMN_NAME_EXPENSE_NOMINAL));
            String date = cursor.getString(
                    cursor.getColumnIndexOrThrow(ExpenseContract.ExpenseEntry.COLUMN_NAME_EXPENSE_DATE));
            Expense expense = new Expense(itemId, name, nominal, date);
            expenseList.add(expense);
        }
        cursor.close();
        return expenseList;
    }

    @Override
    protected void onDestroy() {
        if(dbHelper != null){
            dbHelper.close();
        }
        super.onDestroy();
    }
}