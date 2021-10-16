package com.michaelwijaya.xyzmonthlyexpenseapp;

public class Expense {
    private Long id; //store ID of the row
    private String name;
    private String nominal;
    private String date;

    public Expense(Long id, String name, String nominal, String date){
        this.id = id;
        this.name = name;
        this.nominal = nominal;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNominal() {
        return nominal;
    }

    public void setNominal(String nominal) {
        this.nominal = nominal;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
