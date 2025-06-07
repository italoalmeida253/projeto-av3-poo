package com.obank.app.models;

public class Transaction {
    private UserDetails originUser;
    private UserDetails destinationUser;
    private double amount;
    private String dateTime;

    public Transaction(UserDetails originUser,UserDetails destinationUser, double amount, String dateTime) {
        this.destinationUser = destinationUser;
        this.originUser = originUser;
        this.amount = amount;
        this.dateTime = dateTime;
    }

    public UserDetails getDestinationUser() {
        return destinationUser;
    }

    public UserDetails getOriginUser() {
        return originUser;
    }

    public double getAmount() {
        return amount;
    }

    public String getDateTime() {
        return dateTime;
    }

}
