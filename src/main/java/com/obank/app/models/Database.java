package com.obank.app.models;

import java.util.ArrayList;
import java.util.List;

public class Database {
    public List<User> users = new ArrayList<>();
    public List<Withdraw> withdraws = new ArrayList<>();
    public List<Deposit> deposits = new ArrayList<>();
    public List<Transaction> transactions = new ArrayList<>();
}
