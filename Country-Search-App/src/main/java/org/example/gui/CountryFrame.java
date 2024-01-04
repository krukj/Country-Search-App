package org.example.gui;

import org.example.country.Country;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CountryFrame extends JFrame {
    private JFrame frame;

    public CountryFrame(Country country, JFrame jFrame) throws HeadlessException {  /// JFrame NOWE
        frame = new JFrame("Country description");
        JLabel name = new JLabel(country.getName().getCommon());
        JLabel capital = new JLabel(country.getCapital()[0]);

        JButton goBackButton = new JButton("Go Back");
        goBackButton.addActionListener(e -> {
            dispose(); // Zamyka bieżące okno
            // Dodaj kod tutaj, aby wrócić do poprzedniej ramki lub wykonać inną operację
            jFrame.setVisible(true); /// NOWE
        });

        add(name);
        add(capital);
        add(goBackButton);

        setSize(1400, 1400);
        setLayout(new GridLayout(3, 1)); // Ustawienie układu ramki

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Zamyka tylko bieżące okno
        setLocationRelativeTo(null); // Ustawienie okna na środku ekranu
    }
}