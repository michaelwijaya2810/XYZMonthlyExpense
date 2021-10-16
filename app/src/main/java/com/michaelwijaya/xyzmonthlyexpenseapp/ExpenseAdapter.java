package com.michaelwijaya.xyzmonthlyexpenseapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder> {
    ArrayList<Expense> expenseList;
    public ExpenseAdapter(ArrayList<Expense> expenseData){
        expenseList = expenseData;
    }

    @NonNull
    @Override
    //first time ViewHolder is created, inflate the template view in parent view
    public ExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View expenseView = inflater.inflate(R.layout.item_expense, parent, false);
        return new ExpenseViewHolder(expenseView);
    }

    @Override
    //binding data to viewHolder
    public void onBindViewHolder(@NonNull ExpenseViewHolder holder, int position) {
        Expense expense = expenseList.get(position);

        String id = Long.toString(expense.getId());
        String name = expense.getName();
        String nominal = expense.getNominal();
        String date = expense.getDate();

        holder.tvExpenseName.setText(name);
        holder.tvExpenseNominal.setText("Rp. " + nominal);
        holder.tvExpenseDate.setText(date);
        holder.itemView.setOnClickListener(v -> {
            Context context = v.getContext(); //get the pressed view's context
            Intent intent = new Intent(context, EditItemActivity.class);
            intent.putExtra("id", id);
            intent.putExtra("name", name);
            intent.putExtra("nominal", nominal);
            context.startActivity(intent);
        });
    }

    @Override
    //affect how many item will be displayed in RecyclerView
    public int getItemCount() {
        return expenseList.size();
    }

    //ViewHolder to inflate template view layout item_expense in RecyclerView
    public static class ExpenseViewHolder extends RecyclerView.ViewHolder{
        TextView tvExpenseName;
        TextView tvExpenseNominal;
        TextView tvExpenseDate;

        public ExpenseViewHolder(@NonNull View itemView) {
            super(itemView);
            tvExpenseName = itemView.findViewById(R.id.tv_expense_name);
            tvExpenseNominal = itemView.findViewById(R.id.tv_expense_nominal);
            tvExpenseDate = itemView.findViewById(R.id.tv_expense_date);
        }
    }
}
