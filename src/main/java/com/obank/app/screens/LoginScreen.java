package com.obank.app.screens;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.*;

import com.obank.app.constants.Constants;
import com.obank.app.elements.ButtonElement;
import com.obank.app.elements.LabelElement;
import com.obank.app.elements.PasswordTextField;
import com.obank.app.elements.TextFieldElement;
import com.obank.app.models.User;
import com.obank.app.repositories.UserRepository;

public class LoginScreen extends JFrame {
    private UserRepository userRepository;

    public LoginScreen(UserRepository userRepository) {
        this.userRepository = userRepository;

        this.setSize(300, 500);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        this.add(panel);

        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.setAlignmentY(Component.CENTER_ALIGNMENT);

        TextFieldElement emailTextField = new TextFieldElement(32);
        PasswordTextField passwordTextField = new PasswordTextField(32);
        ButtonElement submitButtonElement = new ButtonElement("Entrar");
        LabelElement titleLabel = new LabelElement("Faça login", 24);

        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        emailTextField.setAlignmentX(Component.CENTER_ALIGNMENT);
        passwordTextField.setAlignmentX(Component.CENTER_ALIGNMENT);
        submitButtonElement.setAlignmentX(Component.CENTER_ALIGNMENT);
        submitButtonElement
                .addActionListener(e -> onSubmit(emailTextField.getText(), passwordTextField.getPassword()));

        panel.add(Box.createVerticalGlue());
        panel.add(titleLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 8)));
        panel.add(emailTextField);
        panel.add(Box.createRigidArea(new Dimension(0, 8)));
        panel.add(passwordTextField);
        panel.add(Box.createRigidArea(new Dimension(0, 8)));
        panel.add(submitButtonElement);
        panel.add(Box.createVerticalGlue());
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

            Constants.authenticated = true;
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
