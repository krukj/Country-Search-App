package org.example.gui;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class WelcomeFrame extends JFrame {
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
        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));
        JButton startButton = new JButton("Start");
        startButton.addActionListener(e -> {
            try {
                openSearchFrame();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        JButton infoButton = new JButton("Info");
        infoButton.addActionListener(e -> openInfo());
        jPanel.add(startButton);
        jPanel.add(infoButton);
        jPanel.setBackground(Color.decode("#E07A5F"));
        return jPanel;
    }
    private void openSearchFrame() throws IOException {
        SearchFrame searchFrame = new SearchFrame();
        searchFrame.setVisible(true);
        dispose();
    }
    private void openInfo() {
        InfoFrame infoFrame = new InfoFrame();
        infoFrame.setVisible(true);
        dispose();
    }
}
