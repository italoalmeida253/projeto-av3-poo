package com.obank.app.repositories.implementations;

import com.obank.app.infrastructure.JSONDatabase;
import com.obank.app.models.Database;
import com.obank.app.models.User;
import com.obank.app.repositories.UserRepository;
import com.google.gson.*;

public class UserRepositoryJSON implements UserRepository {
    private Gson gson = new Gson();

    @Override
    public User findByEmail(String email) {
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

    @Override
    public void updateUser(User user) {
        Database database = gson.fromJson(JSONDatabase.getDatabaseDataAsString(), Database.class);

        database.users.removeIf(u -> u.getEmail().equals(user.getEmail()));
        database.users.add(user);

        JSONDatabase.saveDatabaseData(gson.toJson(database));
    }

    @Override
    public void createUser(User user) {
        Database database = gson.fromJson(JSONDatabase.getDatabaseDataAsString(), Database.class);

        database.users.add(user);

        JSONDatabase.saveDatabaseData(gson.toJson(database));
    }

    @Override
    public boolean checkIfUserExists(String email) {
        try {
            findByEmail(email);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
