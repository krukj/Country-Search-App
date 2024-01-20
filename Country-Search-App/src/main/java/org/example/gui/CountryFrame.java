package org.example.gui;

import org.example.country.Country;

import javax.swing.*;
import java.io.IOException;
import java.awt.*;
import java.util.List;

public class CountryFrame extends JFrame {

    public CountryFrame(Country country, JFrame jFrame, List<Country> countries, Country departureCountry) throws HeadlessException, IOException {
        JButton goBackButton = new JButton("Go Back");
        goBackButton.addActionListener(e -> {
            dispose(); // close current window
            jFrame.setVisible(true);
        });
        CountryPanel countryPanel = new CountryPanel(country, countries, departureCountry);
        add(countryPanel);
        add(goBackButton);
        setTitle("Country info");
        setSize(1400, 1400);
        setLayout(new FlowLayout());
        getContentPane().setBackground(Color.decode("#f4f1de"));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // close only the current window
        setLocationRelativeTo(null); // window on screen center
    }
}