package com.obank.app.elements;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.*;

public class ButtonElement extends JButton {
    public ButtonElement(String text) {
        this.setPreferredSize(new Dimension(200, 40));
        this.setMinimumSize(new Dimension(200, 40));
        this.setMaximumSize(new Dimension(200, 40));
        this.setText(text);
        this.setFont(new Font("Poppins", Font.BOLD, 16));
        this.setBackground(Color.decode("#6666ff"));
        this.setForeground(Color.WHITE);
    }
}
