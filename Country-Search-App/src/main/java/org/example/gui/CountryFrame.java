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
            dispose(); // Zamyka bieżące okno
            // Dodaj kod tutaj, aby wrócić do poprzedniej ramki lub wykonać inną operację
            jFrame.setVisible(true);
        });
        CountryPanel countryPanel = new CountryPanel(country, countries, departureCountry);
        add(countryPanel);
        add(goBackButton);
        setTitle("Country description");
        setSize(1400, 1400); // MOZE USTAWIC POTEM MNIEJSZE OKNO
        setLayout(new FlowLayout()); // TEN UKLAD LEPSZY
        getContentPane().setBackground(Color.decode("#f4f1de"));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Zamyka tylko bieżące okno
        setLocationRelativeTo(null); // Ustawienie okna na środku ekranu
    }
}