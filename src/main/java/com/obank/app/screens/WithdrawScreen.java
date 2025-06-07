package com.obank.app.screens;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.time.LocalDateTime;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.obank.app.Singleton;
import com.obank.app.elements.ButtonElement;
import com.obank.app.elements.LabelElement;
import com.obank.app.models.User;
import com.obank.app.models.Withdraw;
import com.obank.app.repositories.UserRepository;
import com.obank.app.repositories.WithdrawRepository;
import com.obank.app.repositories.implementations.UserRepositoryJSON;
import com.obank.app.repositories.implementations.WithdrawRepositoryJSON;

public class WithdrawScreen extends JFrame {
    private String withdrawValue = "0";
    private LabelElement withdrawValueLabel;
    private WithdrawRepository withdrawRepository = new WithdrawRepositoryJSON();
    private UserRepository userRepository = new UserRepositoryJSON();

    public WithdrawScreen() {
        this.setTitle("Sacar");
        this.setSize(500, 400);

        User user = null;
        try {
            user = userRepository.findByEmail(Singleton.userAuthenticated.email);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "Erro ao buscar usuário: " + e.getMessage());

            LoginScreen loginScreen = new LoginScreen();
            loginScreen.setVisible(true);

            this.dispose();
            return;
        }

        LabelElement actionLabel = new LabelElement("Quanto você deseja sacar?", 24);
        LabelElement balanceLabel = new LabelElement("Saldo", 16, Color.GRAY, Font.PLAIN);
        LabelElement balanceValueLabel = new LabelElement(
                Singleton.currencyFormatter.format(user.getBalance()), 20);
        withdrawValueLabel = new LabelElement(Singleton.currencyFormatter.format(0), 48);

        JPanel withdrawValuePanel = new JPanel();
        withdrawValuePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        withdrawValuePanel.add(withdrawValueLabel);

        JPanel labelsPanel = new JPanel();
        labelsPanel.setLayout(new BoxLayout(labelsPanel, BoxLayout.Y_AXIS));
        labelsPanel.add(actionLabel);
        labelsPanel.add(balanceLabel);
        labelsPanel.add(balanceValueLabel);

        JPanel labelsContainerPanel = new JPanel();
        labelsContainerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        labelsContainerPanel.add(labelsPanel);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));

        ButtonElement cancelButton = new ButtonElement("Cancelar");
        cancelButton.addActionListener(e -> onCancelClick());

        ButtonElement withdrawnButton = new ButtonElement("Sacar");
        withdrawnButton.addActionListener(e -> onWithdrawClick());

        buttonsPanel.add(Box.createHorizontalGlue());
        buttonsPanel.add(cancelButton);
        buttonsPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        buttonsPanel.add(withdrawnButton);
        buttonsPanel.add(Box.createHorizontalGlue());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        mainPanel.add(labelsContainerPanel);
        mainPanel.add(withdrawValuePanel);
        mainPanel.add(buttonsPanel);

        this.add(mainPanel);
        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                    if (!withdrawValue.isEmpty()) {
                        withdrawValue = withdrawValue.substring(0, withdrawValue.length() - 1);
                        formatWithdrawValueLabel();
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                char keyChar = e.getKeyChar();
                if (Character.isDigit(keyChar)) {
                    withdrawValue += keyChar;
                    formatWithdrawValueLabel();
                }
            }
        });
        this.setFocusable(true);
    }

    private void onWithdrawClick() {
        try {
            JOptionPane.showMessageDialog(rootPane, "Realizando saque...");

            Withdraw withdraw = new Withdraw(Singleton.userAuthenticated, Double.parseDouble(withdrawValue) / 100.0,
                    LocalDateTime.now().toString());
            withdrawRepository.createWithdrawOperation(withdraw);

            JOptionPane.showMessageDialog(rootPane, "Saque realizado com sucesso!");

            ActionsScreen actionsScreen = new ActionsScreen();
            actionsScreen.setVisible(true);

            this.dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "Erro ao realizar saque: " + e.getMessage());
        }
    }

    private void onCancelClick() {
        ActionsScreen actionsScreen = new ActionsScreen();
        actionsScreen.setVisible(true);
        this.dispose();
    }

    private void formatWithdrawValueLabel() {
        if (withdrawValue.isEmpty()) {
            withdrawValueLabel.setText("R$ 0,00");
            return;
        }
        double value = Double.parseDouble(withdrawValue) / 100.0;
        withdrawValueLabel.setText(Singleton.currencyFormatter.format(value));

        System.out.println(withdrawValue);
    }
}
