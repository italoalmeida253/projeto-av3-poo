package com.obank.app.infrastructure;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class JSONDatabase {
    private static final String databaseFileName = "db.json";

    public static String getDatabaseDataAsString() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(JSONDatabase.databaseFileName));
            StringBuilder stringBuilder = new StringBuilder();
            String line = null;
            String ls = System.getProperty("line.separator");
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append(ls);
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            reader.close();

            String content = stringBuilder.toString();
            return content;
        } catch (IOException ioException) {
            return "";
        }
    }
}
