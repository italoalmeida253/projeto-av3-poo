package com.obank.app.elements;

import javax.swing.*;
import java.awt.*;

public class TextFieldElement extends JTextField {
    public TextFieldElement(int columns) {
        super(columns);
        this.setPreferredSize(new Dimension(this.getPreferredSize().width, 40));
        this.setMaximumSize(new Dimension(this.getPreferredSize().width, 40));
        this.setFont(new Font("Poppins", Font.PLAIN, 14));
    }
}
