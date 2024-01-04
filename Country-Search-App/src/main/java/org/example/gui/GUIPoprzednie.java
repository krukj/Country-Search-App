package org.example.gui;

import org.example.country.Country;
import org.example.json.JsonFileReader;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GUI {
    public JFrame frame;
    private final List<Country> countries;
    private List<Country> filteredCountries;
    private int sliderValue;
    private JCheckBox lang1, lang2; // language choice
    private JCheckBox cont1, cont2, cont3, cont4, cont5, cont6; // continent choice
    private JRadioButton b1, b2, b3;
    private ButtonGroup buttonGroup;

    public GUI() throws IOException {
        this.countries = JsonFileReader.createCountriesList("Country-Search-App/response.json");
        this.filteredCountries = new ArrayList<>(countries);

        // frame
        frame = new JFrame("Country search app");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1400, 1400);
        frame.setLayout(new FlowLayout());

        frame.setLayout(null);

        JLabel label = new JLabel("Find your next travel destination!");
        label.setBounds(10, 10, 220, 100);
        frame.add(label);

        // distance panel
        JPanel sliderPanel = createSliderPanel();
        sliderPanel.setBounds(220, 230, 250, 100);
        frame.add(sliderPanel);

        // languages choices
        JPanel languagesPanel = createLanguagesCheckboxPanel();
        languagesPanel.setBounds(220, 10, 200, 100);
        frame.add(languagesPanel);

        // continent choices
        JPanel continentPanel = createContinentCheckboxPanel();
        continentPanel.setBounds(430, 10, 300, 150);
        frame.add(continentPanel);

        // sea choice
        JPanel landlockedPanel = createLandlockedChoicePanel();
        landlockedPanel.setBounds(10, 120, 200, 100);
        frame.add(landlockedPanel);

        // search button
        JButton searchButton = createSearchButton();
        searchButton.setBounds(10, 220, 100, 30);
        frame.add(searchButton);

        frame.setVisible(true);

    }

    public JPanel createSliderPanel(){
        JPanel sliderPanel = new JPanel();
        sliderPanel.setLayout(new BorderLayout());

        JSlider jSlider = createDistanceSlider();

        // listener for changing slider valuer
        jSlider.addChangeListener(e -> {
            sliderValue = jSlider.getValue(); // slider value actualisation
        });

        sliderPanel.add(jSlider, BorderLayout.CENTER);

        TitledBorder titledBorder = BorderFactory.createTitledBorder("Choose distance");
        sliderPanel.setBorder(titledBorder);

        return sliderPanel;
    }
    public JSlider createDistanceSlider(){
        JSlider jSlider = new JSlider(0, 8000, 4000);
        jSlider.setPaintTrack(true);
        jSlider.setPaintTicks(true);
        jSlider.setPaintLabels(true);

        // set spacing
        jSlider.setMajorTickSpacing(2000);
        jSlider.setMinorTickSpacing(500);

        return jSlider;
    }

    public JPanel createLanguagesCheckboxPanel(){
        JPanel languagesPanel = new JPanel();
        languagesPanel.setLayout(new BorderLayout());

        JPanel jPanel = new JPanel();
        lang1 = new JCheckBox("English");
        lang2 = new JCheckBox("Polish");
        jPanel.add(lang1);
        jPanel.add(lang2);

        languagesPanel.add(jPanel, BorderLayout.CENTER);

        TitledBorder titledBorder = BorderFactory.createTitledBorder("Choose language");
        languagesPanel.setBorder(titledBorder);

        return languagesPanel;
    }

    public JPanel createContinentCheckboxPanel(){
        JPanel continentPanel = new JPanel();
        continentPanel.setLayout(new BorderLayout());

        JPanel jPanel = new JPanel(new GridLayout(3, 2));
        cont1 = new JCheckBox("Europe");
        cont2 = new JCheckBox("Asia");
        cont3 = new JCheckBox("Africa");
        cont4 = new JCheckBox("Oceania");
        cont5 = new JCheckBox("North America");
        cont6 = new JCheckBox("South America");

        jPanel.add(cont1);
        jPanel.add(cont2);
        jPanel.add(cont3);
        jPanel.add(cont4);
        jPanel.add(cont5);
        jPanel.add(cont6);

        continentPanel.add(jPanel, BorderLayout.CENTER);

        TitledBorder titledBorder = BorderFactory.createTitledBorder("Choose continents");
        continentPanel.setBorder(titledBorder);


        return continentPanel;
    }

    public JPanel createLandlockedChoicePanel() {
        JPanel landlockedPanel = new JPanel();
        landlockedPanel.setLayout(new BorderLayout());

        JPanel jPanel = new JPanel();
        b1 = new JRadioButton("Yes");
        b2 = new JRadioButton("No");
        b3 = new JRadioButton("Don't care");

        // initially selected button
        b1.setSelected(true);

        // creating a button group
        buttonGroup = new ButtonGroup();
        buttonGroup.add(b1);
        buttonGroup.add(b2);
        buttonGroup.add(b3);

        jPanel.add(b1);
        jPanel.add(b2);
        jPanel.add(b3);

        landlockedPanel.add(jPanel, BorderLayout.CENTER);

        TitledBorder titledBorder = BorderFactory.createTitledBorder("Sea/ocean");
        landlockedPanel.setBorder(titledBorder);

        return landlockedPanel;
    }

    public JButton createSearchButton(){
        JButton jButton = new JButton("Search");

        jButton.addActionListener(e -> {
            filterCountriesByContinent();
            System.out.println(filteredCountries.size());
            filterCountriesByLandlocked();
            filterCountriesByDistance();// filter distance
            // filter language
            System.out.println(filteredCountries.size());
            for (Country country: filteredCountries) {
                System.out.println(country.getName().getCommon());
            }
        });
        return jButton;
    }

    public void filterCountriesByContinent(){
        // list with selected continents (names of selected buttons)
        List<String> selectedContinents = Stream.of(cont1, cont2, cont3, cont4, cont5, cont6)
                .filter(JCheckBox::isSelected)
                .map(AbstractButton::getText)
                .toList();
        System.out.println(selectedContinents);
        filteredCountries = filteredCountries.stream()
                .filter(country -> country.getContinents().stream()
                        .anyMatch(selectedContinents::contains))
                .collect(Collectors.toList());
    }

    public void filterCountriesByLandlocked(){
        // b2.isSelected =  (landlocked == false)
        if (b2.isSelected()) {
            filteredCountries = filteredCountries.stream()
                    .filter(Country::isLandlocked)
                    .collect(Collectors.toList());
        }
    }

    public void filterCountriesByDistance() {
        // for now we can assume that user is from Poland (Warsaw)
        double[] warsawLatLong = countries.stream()
                .filter(country -> country.getName().getCommon().equals("Poland"))
                .map(Country::getLatlng)
                .findFirst()
                .orElse(null);

        double warsawLattitude = warsawLatLong[0];
        double warsawLongitude = warsawLatLong[1];

        System.out.println("slajeeeer: " + this.sliderValue);

        filteredCountries = filteredCountries.stream()
                .filter(country -> {
                    System.out.println(country.getName().getCommon());
                    System.out.println(country.getCapital());
                    if (country.getCapital() != null) {
                        double countryLatitude = country.getCapitalInfo().getLatlng()[0];
                        double countryLongitude = country.getCapitalInfo().getLatlng()[1];

                        System.out.println("lat: " + countryLatitude);
                        System.out.println("long: " + countryLongitude);
                        System.out.println("--------------------");

                        double distance = calculateDistance(warsawLattitude, warsawLongitude, countryLatitude, countryLongitude);

                        //                    System.out.println("odleglosc k: " + distance);
                        //                    if (distance <= this.sliderValue) {
                        //                        System.out.println("tak");
                        //                    }
                        //                    System.out.println("--------------------");

                        return distance <= this.sliderValue;
                    }
                    else {
                        return false;
                    }
                })
                .collect(Collectors.toList());
    }

    public double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        // radians = degrees * (π / 180)
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        double earthRadius = 6371.0; // earth radius in km

        double a = Math.pow(Math.sin(dLat / 2), 2) +
                Math.pow(Math.sin(dLon / 2), 2) * Math.cos(lat1) * Math.cos(lat2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        // distance in km
        return earthRadius * c;
    }

}
