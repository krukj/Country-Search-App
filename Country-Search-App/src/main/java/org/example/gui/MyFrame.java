package org.example.gui;

import org.example.country.Country;
import org.example.json.JsonFileReader;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MyFrame extends JFrame {

    public MyFrame() throws HeadlessException, IOException {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new FlowLayout());
        this.setSize(400, 400);
        this.setTitle("Country Search Application");

        JsonFileReader jsonFileReader = new JsonFileReader();
        java.util.List<Country> countriesJson = JsonFileReader.createCountriesList("Country-Search-App/response.json");

        List<String> countryNames = new ArrayList<>();

        // Przechodzimy przez każdy obiekt Country i dodajemy jego nazwę common do listy countryNames
        for (Country country : countriesJson) {
            countryNames.add(country.getName().getCommon());
        }
//
//        // Wyświetlenie nazw krajów
////        for (String name : countryNames) {
////            System.out.println(name);
////        }
//
        String[] animals = {"dog", "cat", "turtle"};
        String[] namesCountries = new String[countryNames.size()];
        int i = 0;
        for (String name : countryNames) {
            namesCountries[i] = name;
            i ++;
        }
        JComboBox comboBox = new JComboBox(namesCountries);

        this.add(comboBox);
        this.pack();
        this.setVisible(true);
    }
}
