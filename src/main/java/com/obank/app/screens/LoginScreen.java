package com.obank.app.screens;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.*;

import com.obank.app.elements.ButtonElement;
import com.obank.app.elements.LabelElement;
import com.obank.app.elements.PasswordTextField;
import com.obank.app.elements.TextFieldElement;

public class LoginScreen extends JFrame {
    public LoginScreen() {
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
}
