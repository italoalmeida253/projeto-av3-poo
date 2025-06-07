package com.obank.app.screens;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.time.LocalDateTime;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Box;
import java.awt.Dimension;
import java.awt.FlowLayout;

import com.obank.app.Singleton;
import com.obank.app.elements.ButtonElement;
import com.obank.app.elements.LabelElement;
import com.obank.app.models.Deposit;
import com.obank.app.models.User;
import com.obank.app.repositories.DepositRepository;
import com.obank.app.repositories.UserRepository;
import com.obank.app.repositories.implementations.DepositRepositoryJSON;
import com.obank.app.repositories.implementations.UserRepositoryJSON;

public class DepositScreen extends JFrame {
    private String depositValue = "0";
    private LabelElement depositValueLabel;
    private DepositRepository depositRepository = new DepositRepositoryJSON();
    private UserRepository userRepository = new UserRepositoryJSON();

    public DepositScreen() {
        this.setTitle("Depósito");
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

        LabelElement actionLabel = new LabelElement("Quanto você deseja depositar?", 24);
        LabelElement balanceLabel = new LabelElement("Saldo", 16, Color.GRAY, Font.PLAIN);
        LabelElement balanceValueLabel = new LabelElement(Singleton.currencyFormatter.format(user.getBalance()), 20);
        depositValueLabel = new LabelElement(Singleton.currencyFormatter.format(0), 48);

        JPanel depositValuePanel = new JPanel();
        depositValuePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        depositValuePanel.add(depositValueLabel);

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

        ButtonElement depositButton = new ButtonElement("Depositar");
        depositButton.addActionListener(e -> onDepositClick());

        buttonsPanel.add(Box.createHorizontalGlue());
        buttonsPanel.add(cancelButton);
        buttonsPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        buttonsPanel.add(depositButton);
        buttonsPanel.add(Box.createHorizontalGlue());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        mainPanel.add(labelsContainerPanel);
        mainPanel.add(depositValuePanel);
        mainPanel.add(buttonsPanel);

        this.add(mainPanel);
        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                    if (!depositValue.isEmpty()) {
                        depositValue = depositValue.substring(0, depositValue.length() - 1);
                        formatDepositValueLabel();
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                char keyChar = e.getKeyChar();
                if (Character.isDigit(keyChar)) {
                    depositValue += keyChar;
                    formatDepositValueLabel();
                }
            }
        });
        this.setFocusable(true);
    }

    private void onDepositClick() {
        try {
            JOptionPane.showMessageDialog(rootPane, "Realizando deposito...");

            Deposit deposit = new Deposit(Singleton.userAuthenticated, Double.parseDouble(depositValue) / 100.0,
                    LocalDateTime.now().toString());
            depositRepository.createDepositOperation(deposit);

            JOptionPane.showMessageDialog(rootPane, "Deposito realizado com sucesso!");

            ActionsScreen actionsScreen = new ActionsScreen();
            actionsScreen.setVisible(true);

            this.dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "Erro ao realizar deposito: " + e.getMessage());
        }

    }

    private void onCancelClick() {
        ActionsScreen actionsScreen = new ActionsScreen();
        actionsScreen.setVisible(true);
        this.dispose();
    }

    private void formatDepositValueLabel() {
        if (depositValue.isEmpty()) {
            depositValueLabel.setText("R$ 0,00");
            return;
        }
        double value = Double.parseDouble(depositValue) / 100.0;
        depositValueLabel.setText(Singleton.currencyFormatter.format(value));

        System.out.println(depositValue);
    }
}
