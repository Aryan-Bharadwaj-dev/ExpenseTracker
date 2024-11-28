package com.example.expensetracker;

public class Expense {
    private String title;
    private String category;
    private String date;
    private double amount;

    public Expense() {}

    public Expense(String title, String category, String date, double amount) {
        this.title = title;
        this.category = category;
        this.date = date;
        this.amount = amount;
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public String getDate() {
        return date;
    }

    public double getAmount() {
        return amount;
    }
}