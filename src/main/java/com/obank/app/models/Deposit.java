package com.obank.app.models;

public class Deposit {
    private UserDetails userDetails;
    private double amount;
    private String dateTime;

    public Deposit(UserDetails user, double amount, String dateTime) {
        this.userDetails = user;
        this.amount = amount;
        this.dateTime = dateTime;
    }

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public double getAmount() {
        return amount;
    }

    public String getDateTime() {
        return dateTime;
    }
}
