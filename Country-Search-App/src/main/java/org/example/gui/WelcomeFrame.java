package org.example.gui;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class WelcomeFrame extends JFrame {
    private GUI gui;
    public WelcomeFrame() throws HeadlessException {
        WelcomePanel welcomePanel = new WelcomePanel();
        setTitle("Country search app");
        setSize(1400, 1400);
        setLayout(new GridBagLayout());

        createButtonPanel();

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
            try {
                openGUI();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        jPanel.add(startButton);
        jPanel.setBackground(Color.decode("#E07A5F"));
        return jPanel;
    }

    private void openGUI() throws IOException {
        gui = new GUI();
        gui.frame.setVisible(true);
        dispose();
    }
}
