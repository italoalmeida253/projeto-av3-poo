package com.obank.app.infrastructure;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.obank.app.Singleton;

public class JSONDatabase {
    public static String getDatabaseDataAsString() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(Singleton.DB_PATH));
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

    public static void saveDatabaseData(String data) {
        try {
            FileWriter fileWriter = new FileWriter(Singleton.DB_PATH);
            fileWriter.write(data);
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
