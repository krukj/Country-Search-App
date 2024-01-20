package org.example.gui;

import javax.swing.*;
import java.awt.*;

public class WelcomePanel extends JPanel {
    private final Color textColor = Color.decode("#3d405b");
    private final Color backgroundColor = Color.decode("#E07A5F");


    public WelcomePanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(createTitlePanel());
        add(createSubtitlePanel());
    }
    private JPanel createTitlePanel(){
        JPanel jPanel = new JPanel();
        JLabel jLabel = new JLabel("<html><span style='font-size:50px;'>Country Search App</span></html>");
        Font labelFont = new Font("MONOSPACED", Font.BOLD, 50);
        jLabel.setFont(labelFont);
        jLabel.setForeground(textColor);
        jPanel.add(jLabel);
        jPanel.setBackground(backgroundColor);
        return jPanel;
    }
    private JPanel createSubtitlePanel(){
        JPanel jPanel = new JPanel();
        JLabel jLabel = new JLabel("<html><span style='font-size:20px;'>Find your next travel destination!</span></html>");
        Font labelFont = new Font("MONOSPACED", Font.BOLD, 20);
        jLabel.setFont(labelFont);
        jLabel.setForeground(textColor);
        jPanel.add(jLabel);
        jPanel.setBackground(backgroundColor);
        return jPanel;
    }

}
