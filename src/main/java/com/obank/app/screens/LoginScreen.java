package com.obank.app.screens;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;

import com.obank.app.Singleton;
import com.obank.app.elements.ButtonElement;
import com.obank.app.elements.LabelElement;
import com.obank.app.elements.PasswordTextField;
import com.obank.app.elements.TextFieldElement;
import com.obank.app.models.User;
import com.obank.app.models.UserDetails;
import com.obank.app.repositories.UserRepository;
import com.obank.app.repositories.implementations.UserRepositoryJSON;

public class LoginScreen extends JFrame {
    private UserRepository userRepository = new UserRepositoryJSON();

    public LoginScreen() {
        this.setSize(400, 500);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        this.add(panel);

        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.setAlignmentY(Component.CENTER_ALIGNMENT);

        TextFieldElement emailTextField = new TextFieldElement(32);
        PasswordTextField passwordTextField = new PasswordTextField(32);
        ButtonElement submitButtonElement = new ButtonElement("Entrar");
        LabelElement goToRegisterLabelElement = new LabelElement("Não possui uma conta? Cadastre-se", 14, Color.BLUE,
                Font.PLAIN);
        goToRegisterLabelElement.addMouseListener(new MouseListener() {
            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                onGoToRegisterClick();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });

        LabelElement titleLabel = new LabelElement("Faça login", 24);

        LabelElement emailLabel = new LabelElement("Email:", 14);
        LabelElement passwordLabel = new LabelElement("Senha:", 14);

        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        emailTextField.setAlignmentX(Component.CENTER_ALIGNMENT);
        passwordTextField.setAlignmentX(Component.CENTER_ALIGNMENT);
        goToRegisterLabelElement.setAlignmentX(Component.CENTER_ALIGNMENT);
        submitButtonElement.setAlignmentX(Component.CENTER_ALIGNMENT);
        emailLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        passwordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        submitButtonElement
                .addActionListener(e -> onSubmit(emailTextField.getText(), passwordTextField.getPassword()));

        panel.add(Box.createVerticalGlue());
        panel.add(titleLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 8)));
        panel.add(emailLabel);
        panel.add(emailTextField);
        panel.add(Box.createRigidArea(new Dimension(0, 8)));
        panel.add(passwordLabel);
        panel.add(passwordTextField);
        panel.add(Box.createRigidArea(new Dimension(0, 8)));
        panel.add(submitButtonElement);
        panel.add(Box.createRigidArea(new Dimension(0, 8)));
        panel.add(goToRegisterLabelElement);
        panel.add(Box.createVerticalGlue());
    }

    private void onGoToRegisterClick() {
        RegisterScreen registerScreen = new RegisterScreen();
        registerScreen.setVisible(true);
        this.dispose();
    }

    private void onSubmit(String email, char[] password) {
        try {
            User user = userRepository.findByEmail(email);
            String passwordString = "";

            for (char letter : password) {
                passwordString += letter;
            }

            if (!user.getPassword().equals(passwordString)) {
                JOptionPane.showMessageDialog(rootPane,
                        "A senha que você digitou é diferente da cadastrada no sistema. Tente novamente.",
                        "Senha incorreta", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Singleton.userAuthenticated = new UserDetails(user.getName(), user.getEmail());
            this.setVisible(false);
            ActionsScreen actionsScreen = new ActionsScreen();
            actionsScreen.setVisible(true);
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(rootPane,
                    e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
