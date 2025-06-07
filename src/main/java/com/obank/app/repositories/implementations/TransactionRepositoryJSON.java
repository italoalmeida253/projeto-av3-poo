package com.obank.app.repositories.implementations;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.obank.app.infrastructure.JSONDatabase;
import com.obank.app.models.Database;
import com.obank.app.models.Transaction;
import com.obank.app.models.User;
import com.obank.app.repositories.TransactionRepository;
import com.obank.app.repositories.UserRepository;

public class TransactionRepositoryJSON implements TransactionRepository {
    private Gson gson = new Gson();
    private UserRepository userRepository = new UserRepositoryJSON();

    @Override
    public List<Transaction> findAllTransactionsByUserEmail(String email) {
        List<Transaction> foundTransactions = new ArrayList<>();

        Database database = gson.fromJson(JSONDatabase.getDatabaseDataAsString(), Database.class);

        for (Transaction transaction : database.transactions) {
            if (transaction.getOriginUser().email.equals(email)
                    || transaction.getDestinationUser().email.equals(email)) {
                foundTransactions.add(transaction);
            }
        }

        return foundTransactions;
    }

    @Override
    public void createTransactionOperation(Transaction transaction) {
        try {
            User originUser = userRepository.findByEmail(transaction.getOriginUser().email);
            User destinationUser = userRepository.findByEmail(transaction.getDestinationUser().email);

            if (originUser.getBalance() < transaction.getAmount()) {
                throw new Exception("Saldo insuficiente.");
            }

            originUser.setBalance(originUser.getBalance() - transaction.getAmount());
            destinationUser.setBalance(destinationUser.getBalance() + transaction.getAmount());

            userRepository.updateUser(originUser);
            userRepository.updateUser(destinationUser);

            Database database = gson.fromJson(JSONDatabase.getDatabaseDataAsString(), Database.class);
            database.transactions.add(transaction);

            JSONDatabase.saveDatabaseData(gson.toJson(database));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
