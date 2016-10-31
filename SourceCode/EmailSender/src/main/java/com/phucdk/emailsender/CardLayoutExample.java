/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phucdk.emailsender;

/**
 *
 * @author Administrator
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class CardLayoutExample
{
    public static void main(final String[] args) {
        final JFrame parent = new JFrame();
        JButton button = new JButton();

        button.setText("Click me to show dialog!");
        parent.add(button);
        parent.pack();
        parent.setVisible(true);

        button.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                String name = JOptionPane.showInputDialog(parent,
                        "What is your name?", null);
            }
        });
    }
}

