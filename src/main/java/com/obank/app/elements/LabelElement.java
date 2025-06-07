package com.obank.app.elements;

import java.awt.Font;
import java.awt.Color;

import javax.swing.JLabel;

public class LabelElement extends JLabel {
    public LabelElement(String text, int fontSize, Color color, int fontWeight) {
        super(text);
        this.setFont(new Font("Poppins", fontWeight, fontSize));
        this.setForeground(color);
    }

    public LabelElement(String text, int fontSize) {
        this(text, fontSize, Color.BLACK, Font.BOLD);
    }
}
