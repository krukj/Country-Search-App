package org.example.gui;

import org.example.country.Country;
import org.example.json.JsonFileReader;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class CountryFrame extends JFrame{
    public JFrame frame;
    public CountryPanel countryPanel;

    public CountryFrame() throws IOException {
        frame = new JFrame("Country search app");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1400, 1400);
        setLayout(new FlowLayout());

        List<Country> countries = JsonFileReader.createCountriesList("response.json");
        Country country = countries.get(1);
        countryPanel = new CountryPanel(country, countries);
        add(countryPanel);
       setVisible(true);

    }
}
