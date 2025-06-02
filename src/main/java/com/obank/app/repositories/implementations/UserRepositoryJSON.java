package com.obank.app.repositories.implementations;

import org.json.JSONObject;

import com.obank.app.infrastructure.JSONDatabase;
import com.obank.app.models.Database;
import com.obank.app.models.User;
import com.obank.app.repositories.UserRepository;
import com.google.gson.*;

public class UserRepositoryJSON implements UserRepository {
    @Override
    public User findByEmail(String email) throws Exception {
        try {
            Gson gson = new Gson();
            Database database = gson.fromJson(JSONDatabase.getDatabaseDataAsString(), Database.class);
            return new User();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }
}
