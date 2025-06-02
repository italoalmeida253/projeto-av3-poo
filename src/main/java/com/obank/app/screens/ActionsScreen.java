package com.obank.app.screens;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.obank.app.elements.ButtonElement;
import com.obank.app.elements.LabelElement;
public class ActionsScreen extends JFrame {
    public ActionsScreen() {
        this.setSize(800, 600);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(3, 2, 16, 16));

        LabelElement titleLabel = new LabelElement("Bem-vindo ao OBank", 24);
        ButtonElement depositButtonElement = new ButtonElement("Depositar");
        ButtonElement transferButtonElement = new ButtonElement("Transferir");
        ButtonElement withdrawButtonElement = new ButtonElement("Sacar");
        ButtonElement extractButtonElement = new ButtonElement("Extrato");
        ButtonElement changePasswordButtonElement = new ButtonElement("Mudar senha");
        ButtonElement exitButtonElement = new ButtonElement("Sair");
        
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        gridPanel.add(depositButtonElement);
        gridPanel.add(withdrawButtonElement);
        gridPanel.add(transferButtonElement);
        gridPanel.add(extractButtonElement);
        gridPanel.add(changePasswordButtonElement);
        gridPanel.add(exitButtonElement);

        mainPanel.add(titleLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 16)));
        mainPanel.add(gridPanel);

        this.add(mainPanel);
    }
}
