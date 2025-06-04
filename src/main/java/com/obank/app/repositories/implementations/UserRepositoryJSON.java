package com.obank.app.repositories.implementations;

import com.obank.app.infrastructure.JSONDatabase;
import com.obank.app.models.Database;
import com.obank.app.models.User;
import com.obank.app.repositories.UserRepository;
import com.google.gson.*;

public class UserRepositoryJSON implements UserRepository {
    @Override
    public User findByEmail(String email) {
        Gson gson = new Gson();
        Database database = gson.fromJson(JSONDatabase.getDatabaseDataAsString(), Database.class);

        User foundUser = null;
        for (User user : database.users) {
            if (user.getEmail().equals(email)) {
                foundUser = user;
                break;
            }
        }

        if (foundUser == null) {
            throw new RuntimeException("Não foi possível encontrar um usuário com o email especificado.");
        }

        return foundUser;
    }
}
