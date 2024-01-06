package org.example.gui;

import javax.swing.*;
import java.awt.*;

public class WelcomeFrame extends JFrame {
    public WelcomeFrame() throws HeadlessException {
        WelcomePanel welcomePanel = new WelcomePanel();
        setTitle("Country search app");
        setSize(1400, 1400);
        setLayout(new GridBagLayout());
        add(welcomePanel);
        add(createButtonPanel());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.decode("#E07A5F"));
        setLocationRelativeTo(null);
        setVisible(true);
    }
    private JPanel createButtonPanel(){
        JPanel jPanel = new JPanel();
        JButton startButton = new JButton("Start");
        startButton.addActionListener(e -> {
            dispose();
        });
        jPanel.add(startButton);
        jPanel.setBackground(Color.decode("#E07A5F"));
        return jPanel;
    }
}
