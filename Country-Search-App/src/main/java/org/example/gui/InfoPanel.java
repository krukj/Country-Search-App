package org.example.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;

public class InfoPanel extends JPanel {
    private final Color textColor = Color.decode("#3d405b");
    private final Color backgroundColor = Color.decode("#F4F1DE");


    public InfoPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(createTitlePanel());
        add(createTextPanel());
        add(createAuthorsPanel());
        add(createDataSourcePanel());
    }
    private JPanel createTitlePanel(){
        JPanel jPanel = new JPanel();
        JLabel jLabel = new JLabel("<html><span style='font-size:30;'>Info</span></html>");
        jLabel.setForeground(textColor);
        jPanel.add(jLabel);
        jPanel.setBackground(backgroundColor);
        return jPanel;
    }
    private JPanel createTextPanel(){
        JPanel jPanel = new JPanel();
        JLabel jLabel = new JLabel("<html><span style='font-size:10px;'>Project made for a subject " +
                "Object Oriented Programming at the 3rd semester of Data Science program at the Faculty" +
                " of Mathematics and Information Systems, Warsaw University of Technology.</span></html>");
        jLabel.setForeground(textColor);
        jPanel.add(jLabel);
        jPanel.setBackground(backgroundColor);
        return jPanel;
    }
    private JPanel createAuthorsPanel(){
        JPanel jPanel = new JPanel();
        JLabel jLabel = new JLabel("<html><font size=4>Authors: </font><font size=5>"
                + "Julia Kruk, Nadia Serafin" + "</font></html>");
        Font labelFont = new Font("MONOSPACED", Font.PLAIN, 5);
        jLabel.setFont(labelFont);
        jLabel.setForeground(textColor);
        jPanel.add(jLabel);
        jPanel.setBackground(backgroundColor);
        return jPanel;
    }
    private JPanel createDataSourcePanel(){
        JLabel textLabel = new JLabel("<html><font size=5><u>" + "restcountries.com" + "</u></html>");

        Font labelFont = new Font("MONOSPACED", Font.PLAIN, 5);
        textLabel.setFont(labelFont);
        textLabel.setForeground(Color.BLUE);
        textLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        textLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    String link = "https://restcountries.com";
                    Desktop.getDesktop().browse(new URI(link));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });

        JLabel dataSourceLabel = new JLabel("<html><font size=4>Data source:</font></html>");
        dataSourceLabel.setFont(labelFont);

        JPanel jPanel = new JPanel();
        jPanel.add(dataSourceLabel);
        jPanel.add(textLabel);
        jPanel.setBackground(backgroundColor);

        return jPanel;
    }

}
