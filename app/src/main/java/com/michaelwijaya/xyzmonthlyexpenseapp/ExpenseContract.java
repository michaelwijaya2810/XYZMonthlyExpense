package com.michaelwijaya.xyzmonthlyexpenseapp;

import android.provider.BaseColumns;

//specify the layout of the schema in database
public final class ExpenseContract {
    private ExpenseContract(){}

    public static class ExpenseEntry implements BaseColumns{
        public static final String TABLE_NAME = "expense_table";
        public static final String COLUMN_NAME_EXPENSE_NAME = "expense_name";
        public static final String COLUMN_NAME_EXPENSE_NOMINAL = "expense_nominal";
        public static final String COLUMN_NAME_EXPENSE_DATE = "expense_date";
    }
}
