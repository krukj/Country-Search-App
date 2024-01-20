package org.example.gui;

import javax.swing.*;
import java.awt.*;

public class InfoFrame extends JFrame {
    public InfoFrame() throws HeadlessException {
        InfoPanel infoPanel = new InfoPanel();
        add(infoPanel);
        setSize(1400, 1400);
        setLayout(new FlowLayout());
        getContentPane().setBackground(Color.decode("#f4f1de"));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
