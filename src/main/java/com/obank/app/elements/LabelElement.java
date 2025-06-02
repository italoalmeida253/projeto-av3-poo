package com.obank.app.elements;

import java.awt.Font;

import javax.swing.JLabel;

public class LabelElement extends JLabel {
    public LabelElement(String text, int fontSize) {
        super(text);
        this.setFont(new Font("Poppins", Font.BOLD, fontSize));
    }
}
