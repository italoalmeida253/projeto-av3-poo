package com.obank.app.screens;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Color;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import com.obank.app.Singleton;
import com.obank.app.elements.ButtonElement;
import com.obank.app.elements.LabelElement;
import com.obank.app.models.User;
import com.obank.app.repositories.UserRepository;
import com.obank.app.repositories.implementations.UserRepositoryJSON;

public class ActionsScreen extends JFrame {
    private LabelElement dateTimeLabel;
    private UserRepository userRepository = new UserRepositoryJSON();

    public ActionsScreen() {
        this.setSize(800, 600);

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

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel line1WelcomePanel = new JPanel();
        line1WelcomePanel.setLayout(new BorderLayout());

        JPanel line2WelcomePanel = new JPanel();
        line2WelcomePanel.setLayout(new BorderLayout());

        JPanel actionsPanel = new JPanel();
        actionsPanel.setLayout(new GridLayout(3, 2, 16, 16));

        LabelElement welcomeLabel = new LabelElement("Olá, " + Singleton.userAuthenticated.name + "!", 18);
        LabelElement titleLabel = new LabelElement("Bem-vindo ao OBank!", 30);
        dateTimeLabel = new LabelElement(
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy")), 14, Color.GRAY,
                Font.PLAIN);
        LabelElement balanceLabel = new LabelElement("Saldo: " + Singleton.currencyFormatter.format(user.getBalance()),
                16, Color.BLACK, Font.PLAIN);

        ButtonElement depositButtonElement = new ButtonElement("Depositar");
        depositButtonElement.addActionListener(e -> onDepositClick());

        ButtonElement transferButtonElement = new ButtonElement("Transferir");
        transferButtonElement.addActionListener(e -> onTransferClick());

        ButtonElement withdrawButtonElement = new ButtonElement("Sacar");
        withdrawButtonElement.addActionListener(e -> onWithdrawClick());

        ButtonElement extractButtonElement = new ButtonElement("Extrato");
        extractButtonElement.addActionListener(e -> onExtractClick());

        ButtonElement exitButtonElement = new ButtonElement("Sair");
        exitButtonElement.addActionListener(e -> onLogoutClick());

        line1WelcomePanel.add(welcomeLabel, BorderLayout.WEST);
        line1WelcomePanel.add(dateTimeLabel, BorderLayout.EAST);

        line2WelcomePanel.add(titleLabel, BorderLayout.WEST);
        line2WelcomePanel.add(balanceLabel, BorderLayout.EAST);

        line1WelcomePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, line1WelcomePanel.getPreferredSize().height));
        line2WelcomePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, line2WelcomePanel.getPreferredSize().height));

        actionsPanel.add(depositButtonElement);
        actionsPanel.add(withdrawButtonElement);
        actionsPanel.add(transferButtonElement);
        actionsPanel.add(extractButtonElement);
        actionsPanel.add(exitButtonElement);

        mainPanel.add(line1WelcomePanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        mainPanel.add(line2WelcomePanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 16)));
        mainPanel.add(Box.createVerticalGlue());

        LabelElement actionPromptLabel = new LabelElement("Qual ação deseja realizar?", 16, Color.BLACK, Font.PLAIN);
        actionPromptLabel.setAlignmentX(CENTER_ALIGNMENT);
        mainPanel.add(actionPromptLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        mainPanel.add(actionsPanel);

        this.add(mainPanel);

        Timer timer = new Timer(1000, e -> {
            dateTimeLabel.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy")));
        });
        timer.start();
    }

    private void onExtractClick() {
        ExtractScreen extractScreen = new ExtractScreen();
        extractScreen.setVisible(true);
        this.dispose();
    }

    private void onTransferClick() {
        TransactionScreen transactionScreen = new TransactionScreen();
        transactionScreen.setVisible(true);
        this.dispose();
    }

    private void onDepositClick() {
        DepositScreen depositScreen = new DepositScreen();
        depositScreen.setVisible(true);
        this.dispose();
    }

    private void onWithdrawClick() {
        WithdrawScreen withdrawScreen = new WithdrawScreen();
        withdrawScreen.setVisible(true);
        this.dispose();
    }

    void onLogoutClick() {
        int logoutConfirmed = JOptionPane.showOptionDialog(rootPane, "Tem certeza que deseja sair da sua conta?",
                "Sair",
                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, null, null);

        switch (logoutConfirmed) {
            case JOptionPane.YES_OPTION:
                Singleton.userAuthenticated = null;

                this.setVisible(false);

                LoginScreen loginScreen = new LoginScreen();
                loginScreen.setVisible(true);
                break;

            case JOptionPane.NO_OPTION:
                break;
        }
    }
}
