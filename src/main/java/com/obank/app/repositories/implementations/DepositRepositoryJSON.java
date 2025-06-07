package com.obank.app.repositories.implementations;

import com.obank.app.repositories.DepositRepository;
import com.obank.app.repositories.UserRepository;
import com.google.gson.Gson;
import com.obank.app.infrastructure.JSONDatabase;
import com.obank.app.models.Database;
import com.obank.app.models.Deposit;
import com.obank.app.models.User;

import java.util.ArrayList;
import java.util.List;

public class DepositRepositoryJSON implements DepositRepository {
    private UserRepository userRepository = new UserRepositoryJSON();
    private Gson gson = new Gson();

    @Override
    public List<Deposit> findAllDepositsByUserEmail(String email) {
        List<Deposit> foundDeposits = new ArrayList<>();

        Database database = gson.fromJson(JSONDatabase.getDatabaseDataAsString(), Database.class);

        for (Deposit deposit : database.deposits) {
            if (deposit.getUserDetails().email.equals(email)) {
                foundDeposits.add(deposit);
            }
        }

        return foundDeposits;
    }

    @Override
    public void createDepositOperation(Deposit deposit) {
        try {
            User user = userRepository.findByEmail(deposit.getUserDetails().email);

            user.setBalance(user.getBalance() + deposit.getAmount());
            userRepository.updateUser(user);

            Database database = gson.fromJson(JSONDatabase.getDatabaseDataAsString(), Database.class);
            database.deposits.add(deposit);

            JSONDatabase.saveDatabaseData(gson.toJson(database));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
