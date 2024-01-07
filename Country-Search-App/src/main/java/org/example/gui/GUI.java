package org.example.gui;

import org.example.country.Country;
import org.example.json.JsonFileReader;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GUI {
    public JFrame frame;
    public JPanel mainPanel;  // search panel
    public JPanel result; // flags
    public final List<Country> countries; // countries from api response
    private List<Country> filteredCountries;
    private JCheckBox cont1, cont2, cont3, cont4, cont5, cont6; // continent choice
    private JRadioButton b1, b2;
    private JComboBox<String> sorterBox;
    private JComboBox<String> fromBox;
    private int sliderValue;
    private final Color backgroundColor = Color.decode("#F4F1DE");
    private Country departureCountry;

    public GUI() throws IOException {
        this.countries = JsonFileReader.createCountriesList("response.json");
        // frame
        frame = new JFrame("Country search app");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1400, 1400);
        frame.setLayout(new BorderLayout());


        mainPanel = new JPanel(new FlowLayout());
        result = new JPanel();
        result.setBackground(backgroundColor);

        // departure panel and slider panel
        JPanel fromPanel = createFromPanel();
        JPanel sliderPanel = createSliderPanel();

        // departure panel above slider
        JPanel fromAndSliderPanel = new JPanel();
        fromAndSliderPanel.setLayout(new BoxLayout(fromAndSliderPanel, BoxLayout.Y_AXIS));
        fromAndSliderPanel.add(fromPanel);
        fromAndSliderPanel.add(sliderPanel);
        mainPanel.add(fromAndSliderPanel);

        String departureCountryName = String.valueOf(fromBox.getSelectedItem());
        Optional<Country> optionalCountry = countries.stream()
                .filter(country -> Objects.equals(country.getName().getCommon(), departureCountryName))
                .findFirst();
        optionalCountry.ifPresent(country -> departureCountry = country);

        // continent choices
        JPanel continentPanel = createContinentCheckboxPanel();
        mainPanel.add(continentPanel);

        // sea choice
        JPanel landlockedPanel = createLandlockedChoicePanel();
        mainPanel.add(landlockedPanel);

        // sorting
        JPanel sorterPanelBox = createSorterPanel();
        mainPanel.add(sorterPanelBox);

        // search button
        JButton searchButton = createSearchButton();
        mainPanel.add(searchButton);

        // random country choice
        JButton randomButton = createRandomCountryButton();
        mainPanel.add(randomButton);

        // layout
        mainPanel.setBackground(backgroundColor);
        frame.getContentPane().add(BorderLayout.NORTH, mainPanel);
        frame.add(BorderLayout.CENTER, new JScrollPane(result));
        frame.getContentPane().setBackground(backgroundColor);
        frame.setVisible(true);

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
        jPanel.setBackground(backgroundColor);
        continentPanel.add(jPanel, BorderLayout.CENTER);

        TitledBorder titledBorder = BorderFactory.createTitledBorder("Choose continents");
        continentPanel.setBorder(titledBorder);
        continentPanel.setBackground(backgroundColor);


        return continentPanel;
    }

    public JPanel createLandlockedChoicePanel() {
        JPanel landlockedPanel = new JPanel();
        landlockedPanel.setLayout(new BorderLayout());

        JPanel jPanel = new JPanel();
        jPanel.setBackground(backgroundColor);
        b1 = new JRadioButton("Yes");
        b2 = new JRadioButton("No");
        JRadioButton b3 = new JRadioButton("Don't care");

        // initially selected button
        b1.setSelected(true);

        // creating a button group
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(b1);
        buttonGroup.add(b2);
        buttonGroup.add(b3);

        jPanel.add(b1);
        jPanel.add(b2);
        jPanel.add(b3);

        landlockedPanel.add(jPanel, BorderLayout.CENTER);

        TitledBorder titledBorder = BorderFactory.createTitledBorder("Sea or ocean?");
        landlockedPanel.setBorder(titledBorder);
        landlockedPanel.setBackground(backgroundColor);
        return landlockedPanel;
    }

    public JButton createSearchButton(){
        JButton jButton = new JButton("Search");

        jButton.addActionListener(e -> {
            result.removeAll();
            filteredCountries = new ArrayList<>(countries);
            filterCountriesByContinent();
            filterCountriesByLandlocked();
            filterCountriesByDistance();
            if (filteredCountries.isEmpty()){
                result.add(createNoInfoPanel(), BorderLayout.CENTER);
            } else {
                String departureCountryName = String.valueOf(fromBox.getSelectedItem());
                Optional<Country> optionalCountry = countries.stream()
                        .filter(country -> Objects.equals(country.getName().getCommon(), departureCountryName))
                        .findFirst();
                optionalCountry.ifPresent(country -> departureCountry = country);
                filteredCountries.removeIf(country -> country.getName().getCommon()
                        .equals(departureCountryName));
                sortCountries();
                displayCountryFlags(result, filteredCountries);
            }
            result.revalidate();
            result.repaint();
        });
        return jButton;
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

        TitledBorder titledBorder = BorderFactory.createTitledBorder("Choose max distance (km)");
        sliderPanel.setBorder(titledBorder);
        sliderPanel.setBackground(backgroundColor);
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
    public JButton createRandomCountryButton(){
        JButton jButton = new JButton("Random country");
        jButton.addActionListener(e -> { // TUTAJ ZMIENIC LAYOUT ZEBY FLAGA BYLA PO SRODKU
            result.removeAll();
            filteredCountries = new ArrayList<>(countries);
            int random = new Random().nextInt(filteredCountries.size());
            while (filteredCountries.get(random).getName().getCommon().equals(fromBox.getSelectedItem())) {
                random = new Random().nextInt(filteredCountries.size());
            }
            filteredCountries = filteredCountries.subList(random, random + 1);
            displayCountryFlags(result, filteredCountries);
            result.revalidate();
            result.repaint();
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
        if (b1.isSelected()) {
            filteredCountries = filteredCountries.stream()
                    .filter(country -> !country.isLandlocked())
                    .collect(Collectors.toList());
        } else if (b2.isSelected()) {
            filteredCountries = filteredCountries.stream()
                    .filter(Country::isLandlocked)
                    .collect(Collectors.toList());
        }
    }
    // CZY TO FILTROWANIE PO DISTANCE NA PEWNO DOBRZE DZIALA ?
    public void filterCountriesByDistance() {
        // JAKIS WYJATEK TRZEBA OGARNAC JESLI DEPARTURE COUNTRY NIE MA CAPITALINFO
        // NP ZIGNOROWANIE TEGO I WYSWIETLENIE INFO O TYM
        double[] userCoordinates = countries.stream()
                .filter(country -> country.getName().getCommon().equals(departureCountry.getName().getCommon()))
                .map(Country::getLatlng)
                .findFirst()
                .orElse(null);

        assert userCoordinates != null;
        double userLat = userCoordinates[0];
        double userLong = userCoordinates[1];

        filteredCountries = filteredCountries.stream()
                .filter(country -> {
                    if (country.getCapitalInfo().getLatlng() != null) {
                        double countryLatitude = country.getCapitalInfo().getLatlng()[0];
                        double countryLongitude = country.getCapitalInfo().getLatlng()[1];

                        double distance = calculateDistance(userLat, userLong, countryLatitude, countryLongitude);
                        return distance <= this.sliderValue;
                    }
                    else {
                        return false;
                    }
                })
                .collect(Collectors.toList());
    }

    public void displayCountryFlags(JPanel panel, List<Country> countriesList) {
        panel.setLayout(new GridLayout(0, 5));
        for (Country country : countriesList) {
            try {
                String imageUrl = country.getFlags().get("png");
                BufferedImage image = ImageIO.read(new URL(imageUrl));

                JLabel imageLabel = new JLabel();
                ImageIcon imageIcon = new ImageIcon(image.getScaledInstance(200, 100, Image.SCALE_DEFAULT));
                imageLabel.setIcon(imageIcon);
                imageLabel.setBackground(backgroundColor);

                JPanel imagePanel = new JPanel();
                imagePanel.setLayout(new BoxLayout(imagePanel, BoxLayout.Y_AXIS));
                imagePanel.add(imageLabel, BorderLayout.CENTER);

                imagePanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 20, 5));

                JLabel name = new JLabel(country.getName().getCommon());
                if (Objects.equals(name.getText(), "Saint Helena, Ascension and Tristan da Cunha")) {
                    name.setText("Saint Helena");
                }
                if (Objects.equals(name.getText(), "United States Minor Outlying Islands")) {
                    name.setText("US Minor Outlying Islands");
                }
                imageLabel.setToolTipText("See more information");
                name.setForeground(Color.decode("#3d405b"));
                imagePanel.add(name);
                imagePanel.add(imageLabel);
                imagePanel.setBackground(backgroundColor);

                //imagePanel.setMaximumSize(new Dimension(110, 60));
                panel.add(imagePanel);

                imageLabel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        try {
                            CountryFrame countryFrame = new CountryFrame(country, frame, countries, departureCountry);
                            frame.setVisible(false);
                            countryFrame.setVisible(true);
                        } catch (HeadlessException ex) {
                            ex.printStackTrace();
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                });
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        panel.setBorder(BorderFactory.createEmptyBorder(5, 10, 20, 5));
    }

    private JPanel createSorterPanel(){
        JPanel jPanel = new JPanel();
        String[] choices = {"A-Z", "Distance"};
        sorterBox = new JComboBox<>(choices);
        jPanel.add(sorterBox);
        TitledBorder titledBorder = BorderFactory.createTitledBorder("Sort by");
        jPanel.setBorder(titledBorder);
        sorterBox.addActionListener(e -> {
            if (this.filteredCountries != null) {
                result.removeAll();
                sortCountries();
                displayCountryFlags(result, filteredCountries);
                result.revalidate();
                result.repaint();
            }
        });
        jPanel.setBackground(backgroundColor);
        return jPanel;
    }

    private JPanel createFromPanel(){
        JPanel jPanel = new JPanel();
        List<String> names = new ArrayList<>();
        for (Country country : countries) {
            names.add(country.getName().getCommon());
        }
        Collections.sort(names);
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(names.toArray(new String[0]));
        fromBox = new JComboBox<>(model);
        jPanel.add(fromBox);
        TitledBorder titledBorder = BorderFactory.createTitledBorder("Country of departure");
        jPanel.setBorder(titledBorder);
        jPanel.setBackground(backgroundColor);
        return jPanel;
    }

    private void sortCountries(){
        if(sorterBox.getSelectedIndex() == 0) {
            filteredCountries = filteredCountries.stream()
                    .sorted(Comparator.comparing(country -> country.getName().getCommon()))
                    .collect(Collectors.toList());
        } else if (sorterBox.getSelectedIndex() == 1) {
            double[] userCoordinates = countries.stream()
                    .filter(country -> country.getName().getCommon().equals(fromBox.getSelectedItem()))
                    .map(Country::getLatlng)
                    .findFirst()
                    .orElse(null);

            assert userCoordinates != null;
            double userLat = userCoordinates[0];
            double userLong = userCoordinates[1];
            filteredCountries = filteredCountries.stream()
                    .filter(country -> country.getCapitalInfo() != null)
                    .filter(country -> country.getCapitalInfo().getLatlng() != null)
                    .sorted(Comparator.comparing(country -> calculateDistance(userLat, userLong,
                            country.getCapitalInfo().getLatlng()[0], country.getCapitalInfo().getLatlng()[1])))
                    .collect(Collectors.toList());
        }
    }

    private JPanel createNoInfoPanel(){
        JPanel jPanel = new JPanel();
        JLabel jLabel;
        if (isAnyContinentSelected()) {
            jLabel = new JLabel("Sorry, we couldn't find any country that matches your choices :(");
        } else {
            jLabel = new JLabel("Please select at least one continent");
        }
        jPanel.setBackground(backgroundColor);
        jPanel.add(jLabel);
        return jPanel;
    }
    public boolean isAnyContinentSelected() {
        return cont1.isSelected() || cont2.isSelected() || cont3.isSelected() ||
                cont4.isSelected() || cont5.isSelected() || cont6.isSelected();
    }

    public int calculateDistance(double lat1, double lon1, double lat2, double lon2) {
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
        return (int) (earthRadius * c);
    }


}
