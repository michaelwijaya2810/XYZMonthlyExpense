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


public class EditItemActivity extends AppCompatActivity {
    String name;
    String nominal;
    String id;

    ExpenseDbHelper dbHelper;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        //Adding title and back button to action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("My Spending");

        EditText etExpenseName = findViewById(R.id.et_expense_name);
        EditText etExpenseNominal = findViewById(R.id.et_expense_nominal);

        //getting intent from ExpenseAdapter's onClickListener method
        Intent intent = getIntent();
        id = intent.getExtras().getString("id");
        etExpenseName.setText(intent.getExtras().getString("name"));
        etExpenseNominal.setText(intent.getExtras().getString("nominal"));

        Button btnSubmit = findViewById(R.id.btn_submit);
        btnSubmit.setOnClickListener(v -> {
            name = etExpenseName.getEditableText().toString();
            nominal = etExpenseNominal.getEditableText().toString();
            if(!name.isEmpty()){
                if(!nominal.isEmpty()){
                    //update name and nominal on column where the ID is similar from intent
                    dbHelper = new ExpenseDbHelper(getApplicationContext());
                    db = dbHelper.getWritableDatabase();

                    //adding updated name and nominal to ContentValues, to update those columns' current values
                    ContentValues values = new ContentValues();
                    values.put(ExpenseContract.ExpenseEntry.COLUMN_NAME_EXPENSE_NAME, name);
                    values.put(ExpenseContract.ExpenseEntry.COLUMN_NAME_EXPENSE_NOMINAL, nominal);

                    //WHERE ID LIKE id
                    String selection = ExpenseContract.ExpenseEntry._ID + " LIKE ?";
                    String[] selectionArgs = {id};

                    //will return the amount of rows affected by the update query
                    db.update(
                            ExpenseContract.ExpenseEntry.TABLE_NAME,
                            values,
                            selection,
                            selectionArgs
                    );

                    Toast.makeText(EditItemActivity.this, "Expense info has been edited!", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(EditItemActivity.this, MainActivity.class));
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
