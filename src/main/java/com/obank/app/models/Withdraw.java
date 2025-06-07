package com.obank.app.models;

public class Withdraw {
    private UserDetails userDetails;
    private double amount;
    private String dateTime;

    public Withdraw(UserDetails userDetails, double amount, String dateTime) {
        this.userDetails = userDetails;
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
