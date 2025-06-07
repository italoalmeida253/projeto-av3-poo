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
import com.obank.app.elements.TextFieldElement;
import com.obank.app.models.Transaction;
import com.obank.app.models.User;
import com.obank.app.models.UserDetails;
import com.obank.app.repositories.TransactionRepository;
import com.obank.app.repositories.UserRepository;
import com.obank.app.repositories.implementations.TransactionRepositoryJSON;
import com.obank.app.repositories.implementations.UserRepositoryJSON;

public class TransactionScreen extends JFrame {
    private String transactionValue = "0";
    private LabelElement transactionValueLabel;
    private TransactionRepository transactionRepository = new TransactionRepositoryJSON();
    private UserRepository userRepository = new UserRepositoryJSON();
    private TextFieldElement destinationUserTextField;

    public TransactionScreen() {
        this.setTitle("Transferir");
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

        LabelElement actionLabel = new LabelElement("Quanto você deseja transferir?", 24);
        LabelElement balanceLabel = new LabelElement("Saldo", 16, Color.GRAY, Font.PLAIN);
        LabelElement balanceValueLabel = new LabelElement(Singleton.currencyFormatter.format(user.getBalance()), 20);
        transactionValueLabel = new LabelElement(Singleton.currencyFormatter.format(0), 48);

        JPanel depositValuePanel = new JPanel();
        depositValuePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        depositValuePanel.add(transactionValueLabel);

        JPanel labelsPanel = new JPanel();
        labelsPanel.setLayout(new BoxLayout(labelsPanel, BoxLayout.Y_AXIS));
        labelsPanel.add(actionLabel);
        labelsPanel.add(balanceLabel);
        labelsPanel.add(balanceValueLabel);

        JPanel labelsContainerPanel = new JPanel();
        labelsContainerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        labelsContainerPanel.add(labelsPanel);

        JPanel destinationUserPanel = new JPanel();
        destinationUserTextField = new TextFieldElement(32);
        destinationUserPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        destinationUserPanel.add(new LabelElement("Para:", 16));
        destinationUserPanel.add(destinationUserTextField);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));

        ButtonElement cancelButton = new ButtonElement("Cancelar");
        cancelButton.addActionListener(e -> onCancelClick());

        ButtonElement transferButton = new ButtonElement("Transferir");
        transferButton.addActionListener(e -> onTransferClick());

        buttonsPanel.add(Box.createHorizontalGlue());
        buttonsPanel.add(cancelButton);
        buttonsPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        buttonsPanel.add(transferButton);
        buttonsPanel.add(Box.createHorizontalGlue());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        mainPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                TransactionScreen.this.requestFocus();
            }
        });

        mainPanel.add(labelsContainerPanel);
        mainPanel.add(depositValuePanel);
        mainPanel.add(destinationUserPanel);
        mainPanel.add(buttonsPanel);

        this.add(mainPanel);
        this.addKeyListener(frameKeyListener);
        this.setFocusable(true);
        setupFocusListener();
    }

    private final KeyListener frameKeyListener = new KeyListener() {
        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                if (!transactionValue.isEmpty()) {
                    transactionValue = transactionValue.substring(0, transactionValue.length() - 1);
                    formatDepositValueLabel();
                }
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            char keyChar = e.getKeyChar();
            if (Character.isDigit(keyChar)) {
                transactionValue += keyChar;
                formatDepositValueLabel();
            }
        }
    };

    private void onTransferClick() {
        try {
            JOptionPane.showMessageDialog(rootPane, "Realizando transferência...");

            String destinationUserEmail = destinationUserTextField.getText();
            User destinationUser = userRepository.findByEmail(destinationUserEmail);
            UserDetails destinationUserDetails = new UserDetails(destinationUser.getName(), destinationUser.getEmail());

            if (destinationUser.getEmail().equals(Singleton.userAuthenticated.email)) {
                throw new Exception("Você não pode transferir para você mesmo.");
            }

            Transaction transaction = new Transaction(Singleton.userAuthenticated, destinationUserDetails,
                    Double.parseDouble(transactionValue) / 100.0,
                    LocalDateTime.now().toString());

            transactionRepository.createTransactionOperation(transaction);

            JOptionPane.showMessageDialog(rootPane, "Transferência realizada com sucesso!");

            ActionsScreen actionsScreen = new ActionsScreen();
            actionsScreen.setVisible(true);

            this.dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "Erro ao realizar transferência: " + e.getMessage());
        }
    }

    private void onCancelClick() {
        ActionsScreen actionsScreen = new ActionsScreen();
        actionsScreen.setVisible(true);
        this.dispose();
    }

    private void formatDepositValueLabel() {
        if (transactionValue.isEmpty()) {
            transactionValueLabel.setText("R$ 0,00");
            return;
        }
        double value = Double.parseDouble(transactionValue) / 100.0;
        transactionValueLabel.setText(Singleton.currencyFormatter.format(value));

        System.out.println(transactionValue);
    }

    private void setupFocusListener() {
        destinationUserTextField.addFocusListener(new java.awt.event.FocusListener() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                TransactionScreen.this.removeKeyListener(frameKeyListener);
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                TransactionScreen.this.addKeyListener(frameKeyListener);
                TransactionScreen.this.requestFocus();
            }
        });
    }
}
