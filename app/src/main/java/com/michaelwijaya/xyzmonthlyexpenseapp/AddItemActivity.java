package com.michaelwijaya.xyzmonthlyexpenseapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddItemActivity extends AppCompatActivity {
    String name;
    String nominal;
    String date;

    ExpenseDbHelper dbHelper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        //Adding title and back button to action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("My Spending");

        EditText etExpenseName = findViewById(R.id.et_expense_name);
        EditText etExpenseNominal = findViewById(R.id.et_expense_nominal);

        Button btnSubmit = findViewById(R.id.btn_submit);
        btnSubmit.setOnClickListener(v -> {
            name = etExpenseName.getEditableText().toString();
            nominal = etExpenseNominal.getEditableText().toString();
            if(!name.isEmpty()){
                if(!nominal.isEmpty()){
                    //getting current date when adding new expense item

                    //get current date
                    Calendar currDate = Calendar.getInstance();
                    //format the date from Calendar
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    //save formatted date to String
                    date = simpleDateFormat.format(currDate.getTime());

                    //saving id, name, nominal & date to SQLite database
                    dbHelper = new ExpenseDbHelper(getApplicationContext());
                    db = dbHelper.getWritableDatabase();

                    //add expense name, nominal, and date to ContentValues, to be inserted as a new entry in the table
                    ContentValues values = new ContentValues();
                    values.put(ExpenseContract.ExpenseEntry.COLUMN_NAME_EXPENSE_NAME, name);
                    values.put(ExpenseContract.ExpenseEntry.COLUMN_NAME_EXPENSE_NOMINAL, nominal);
                    values.put(ExpenseContract.ExpenseEntry.COLUMN_NAME_EXPENSE_DATE, date);

                    //will return the ID of newly created entry in table
                    db.insert(ExpenseContract.ExpenseEntry.TABLE_NAME, null, values);

                    Toast.makeText(AddItemActivity.this, "Expense added to the list!", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(AddItemActivity.this, MainActivity.class));
                }else{
                    etExpenseNominal.setError("Fill in the expense nominal!");
                }
            }else{
                etExpenseName.setError("Fill in the expense name!");
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        //going back to MainActivity when back button is pressed
        if(item.getItemId() == android.R.id.home){
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        if(dbHelper != null){
            dbHelper.close();
        }
        super.onDestroy();
    }
}