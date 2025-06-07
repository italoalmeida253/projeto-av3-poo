package com.obank.app;

import java.text.NumberFormat;
import java.util.Locale;

import com.obank.app.models.UserDetails;

public class Singleton {
    public static final String DB_PATH = "db.json";

    public static UserDetails userAuthenticated = new UserDetails("Italo Almeida", "italo@gmail.com");

    private static Locale brazil = new Locale("pt", "BR");
    public static NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(brazil);
}
