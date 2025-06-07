package com.obank.app.repositories;

import java.util.List;

import com.obank.app.models.Withdraw;

public interface WithdrawRepository {
    List<Withdraw> findAllWithdrawsByUserEmail(String email);
    void createWithdrawOperation(Withdraw withdraw);
}
