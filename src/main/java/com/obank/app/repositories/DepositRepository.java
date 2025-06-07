package com.obank.app.repositories;

import java.util.List;

import com.obank.app.models.Deposit;

public interface DepositRepository {
    List<Deposit> findAllDepositsByUserEmail(String email);
    void createDepositOperation(Deposit deposit);
}
