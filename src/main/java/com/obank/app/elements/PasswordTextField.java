package com.obank.app.elements;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JPasswordField;

public class PasswordTextField extends JPasswordField {
    public PasswordTextField(int columns) {
        super(columns);
        this.setPreferredSize(new Dimension(this.getPreferredSize().width, 40));
        this.setMaximumSize(new Dimension(this.getPreferredSize().width, 40));
        this.setFont(new Font("Poppins", Font.PLAIN, 14));
    }
}
