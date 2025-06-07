package com.obank.app.screens;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import com.obank.app.Singleton;
import com.obank.app.elements.ButtonElement;
import com.obank.app.elements.LabelElement;
import com.obank.app.models.Deposit;
import com.obank.app.models.Transaction;
import com.obank.app.models.Withdraw;
import com.obank.app.repositories.DepositRepository;
import com.obank.app.repositories.TransactionRepository;
import com.obank.app.repositories.WithdrawRepository;
import com.obank.app.repositories.implementations.DepositRepositoryJSON;
import com.obank.app.repositories.implementations.TransactionRepositoryJSON;
import com.obank.app.repositories.implementations.WithdrawRepositoryJSON;

public class ExtractScreen extends JFrame {
    private TransactionRepository transactionRepository = new TransactionRepositoryJSON();
    private DepositRepository depositRepository = new DepositRepositoryJSON();
    private WithdrawRepository withdrawRepository = new WithdrawRepositoryJSON();
    private JTable table;
    private DefaultTableModel tableModel;

    public ExtractScreen() {
        this.setTitle("Extrato");
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        JPanel mainPanel = new JPanel();

        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        String[] columns = { "Data", "Tipo", "Valor", "Descrição" };

        tableModel = new DefaultTableModel(columns, 0);

        ButtonElement backButton = new ButtonElement("Voltar");
        backButton.addActionListener(e -> {
            ActionsScreen actionsScreen = new ActionsScreen();
            actionsScreen.setVisible(true);
            this.dispose();
        });

        table = new JTable(tableModel);
        table.setFillsViewportHeight(true);
        JScrollPane scrollPane = new JScrollPane(table);

        mainPanel.add(new LabelElement("Extrato", 24));
        mainPanel.add(Box.createVerticalStrut(16));
        mainPanel.add(scrollPane);
        mainPanel.add(Box.createVerticalStrut(16));
        mainPanel.add(backButton);

        loadTransactions();
        loadDeposits();
        loadWithdraws();

        this.add(mainPanel);

        this.setVisible(true);
    }

    private void loadTransactions() {
        List<Transaction> transactions = transactionRepository
                .findAllTransactionsByUserEmail(Singleton.userAuthenticated.email);

        for (Transaction transaction : transactions) {
            String description = transaction.getOriginUser().email.equals(Singleton.userAuthenticated.email)
                    ? "Transferência enviada para " + transaction.getDestinationUser().email
                    : "Transferência recebida de " + transaction.getOriginUser().email;

            Object[] row = {
                    LocalDateTime.parse(transaction.getDateTime())
                            .format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")),
                    "Transferência",
                    Singleton.currencyFormatter.format(transaction.getAmount()),
                    description
            };
            tableModel.addRow(row);
        }
    }

    private void loadDeposits() {
        List<Deposit> deposits = depositRepository
                .findAllDepositsByUserEmail(Singleton.userAuthenticated.email);

        for (Deposit deposit : deposits) {
            Object[] row = {
                    LocalDateTime.parse(deposit.getDateTime())
                            .format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")),
                    "Depósito",
                    Singleton.currencyFormatter.format(deposit.getAmount()),
                    "Depósito realizado"
            };
            tableModel.addRow(row);
        }
    }

    private void loadWithdraws() {
        List<Withdraw> withdraws = withdrawRepository
                .findAllWithdrawsByUserEmail(Singleton.userAuthenticated.email);

        for (Withdraw withdraw : withdraws) {
            Object[] row = {
                    LocalDateTime.parse(withdraw.getDateTime())
                            .format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")),
                    "Saque",
                    Singleton.currencyFormatter.format(withdraw.getAmount()),
                    "Saque realizado"
            };
            tableModel.addRow(row);
        }
    }
}
