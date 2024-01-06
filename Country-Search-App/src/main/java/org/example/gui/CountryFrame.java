package org.example.gui;

import org.example.country.Country;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class CountryFrame extends JFrame {

    public CountryFrame(Country country, JFrame jFrame, List<Country> countries) throws HeadlessException, IOException {
        JButton goBackButton = new JButton("Go Back");
        goBackButton.addActionListener(e -> {
            dispose(); // Zamyka bieżące okno
            // Dodaj kod tutaj, aby wrócić do poprzedniej ramki lub wykonać inną operację
            jFrame.setVisible(true);
        });
        CountryPanel countryPanel = new CountryPanel(country, countries);
        add(countryPanel);
        add(goBackButton);
        setTitle("Country description");
        setSize(1400, 1400); // MOZE USTAWIC POTEM MNIEJSZE OKNO
        setLayout(new FlowLayout()); // TEN UKLAD LEPSZY
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Zamyka tylko bieżące okno
        setLocationRelativeTo(null); // Ustawienie okna na środku ekranu
    }
}