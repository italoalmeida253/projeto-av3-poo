package com.obank.app.screens;

import com.obank.app.elements.ButtonElement;
import com.obank.app.elements.LabelElement;
import com.obank.app.elements.PasswordTextField;
import com.obank.app.elements.TextFieldElement;
import com.obank.app.models.User;
import com.obank.app.repositories.UserRepository;
import com.obank.app.repositories.implementations.UserRepositoryJSON;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class RegisterScreen extends JFrame {
    private TextFieldElement nameTextField;
    private TextFieldElement emailTextField;
    private PasswordTextField passwordTextField;
    private UserRepository userRepository = new UserRepositoryJSON();

    public RegisterScreen() {
        this.setSize(400, 500);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        this.add(panel);

        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.setAlignmentY(Component.CENTER_ALIGNMENT);

        nameTextField = new TextFieldElement(32);
        emailTextField = new TextFieldElement(32);
        passwordTextField = new PasswordTextField(32);
        ButtonElement submitButtonElement = new ButtonElement("Cadastrar");
        LabelElement titleLabel = new LabelElement("Cadastre-se", 24);

        LabelElement nameLabel = new LabelElement("Nome:", 14);
        LabelElement emailLabel = new LabelElement("Email:", 14);
        LabelElement passwordLabel = new LabelElement("Senha:", 14);
        LabelElement goToLoginLabelElement = new LabelElement("Já possui uma conta? Faça login", 14, Color.BLUE,
                Font.PLAIN);

        goToLoginLabelElement.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                onGoToLoginClick();
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });

        submitButtonElement.addActionListener(e -> onSubmitClick());

        nameTextField.setAlignmentX(Component.CENTER_ALIGNMENT);
        emailTextField.setAlignmentX(Component.CENTER_ALIGNMENT);
        passwordTextField.setAlignmentX(Component.CENTER_ALIGNMENT);
        submitButtonElement.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        emailLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        passwordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        goToLoginLabelElement.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(Box.createVerticalGlue());
        panel.add(titleLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 8)));
        panel.add(nameLabel);
        panel.add(nameTextField);
        panel.add(Box.createRigidArea(new Dimension(0, 8)));
        panel.add(emailLabel);
        panel.add(emailTextField);
        panel.add(Box.createRigidArea(new Dimension(0, 8)));
        panel.add(passwordLabel);
        panel.add(passwordTextField);
        panel.add(Box.createRigidArea(new Dimension(0, 8)));
        panel.add(submitButtonElement);
        panel.add(Box.createRigidArea(new Dimension(0, 8)));
        panel.add(goToLoginLabelElement);
        panel.add(Box.createVerticalGlue());
    }

    private void onGoToLoginClick() {
        LoginScreen loginScreen = new LoginScreen();
        loginScreen.setVisible(true);
        this.dispose();
    }

    private void onSubmitClick() {
        try {
            String email = emailTextField.getText();
            String password = new String(passwordTextField.getPassword());
            String name = nameTextField.getText();

            if (email.isEmpty() || password.isEmpty() || name.isEmpty()) {
                throw new Exception("Todos os campos são obrigatórios.");
            }

            if (userRepository.checkIfUserExists(email)) {
                throw new Exception("Já existe um usuário com este email.");
            }

            User user = new User(name, email, password, 0.0);
            userRepository.createUser(user);

            LoginScreen loginScreen = new LoginScreen();
            loginScreen.setVisible(true);
            this.dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "Erro ao cadastrar usuário: " + e.getMessage());
        }
    }
}
