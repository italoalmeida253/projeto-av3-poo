package com.obank.app.repositories.implementations;

import com.obank.app.repositories.UserRepository;
import com.obank.app.repositories.WithdrawRepository;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.obank.app.infrastructure.JSONDatabase;
import com.obank.app.models.Database;
import com.obank.app.models.User;
import com.obank.app.models.Withdraw;

public class WithdrawRepositoryJSON implements WithdrawRepository {
    private UserRepository userRepository = new UserRepositoryJSON();
    private Gson gson = new Gson();

    @Override
    public List<Withdraw> findAllWithdrawsByUserEmail(String email) {
        List<Withdraw> foundWithdraws = new ArrayList<>();

        Database database = gson.fromJson(JSONDatabase.getDatabaseDataAsString(), Database.class);

        for (Withdraw withdraw : database.withdraws) {
            if (withdraw.getUserDetails().email.equals(email)) {
                foundWithdraws.add(withdraw);
            }
        }
        
        return foundWithdraws;
    }

    @Override
    public void createWithdrawOperation(Withdraw withdraw) {
        try {
            User user = userRepository.findByEmail(withdraw.getUserDetails().email);
            if (withdraw.getAmount() > user.getBalance()) {
                throw new Exception("Saldo insuficiente.");
            }

            user.setBalance(user.getBalance() - withdraw.getAmount());
            userRepository.updateUser(user);

            Database database = gson.fromJson(JSONDatabase.getDatabaseDataAsString(), Database.class);
            database.withdraws.add(withdraw);

            JSONDatabase.saveDatabaseData(gson.toJson(database));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
