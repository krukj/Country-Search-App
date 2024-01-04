package org.example.gui;

import org.example.country.Country;

import javax.swing.*;

public class CountryPanel extends JPanel {
    Country country;
    public CountryPanel(Country country) {
        JLabel name = new JLabel(country.getName().getCommon());
        JLabel capital = new JLabel(country.getCapital()[0]);
        add(name);
        add(capital);
    }
}
