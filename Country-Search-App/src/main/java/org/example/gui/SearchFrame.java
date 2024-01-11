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
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SearchFrame extends JFrame {
    public JPanel mainPanel;  // search panel
    public JPanel result; // flags
    public List<Country> countries; // countries from api response
    private List<Country> filteredCountries;
    private JCheckBox cont1, cont2, cont3, cont4, cont5, cont6; // continent choice
    private JRadioButton b1, b2;
    private JComboBox<String> sorterBox;
    private JComboBox<String> fromBox;
    private int sliderValue;
    private final Color backgroundColor = Color.decode("#F4F1DE");
    private Country departureCountry;
    private double[] userCoordinates = {};
    private final Color textColor = Color.decode("#3d405b");
    private final Font borderFont = new Font("MONOSPACED", Font.PLAIN, 13);

    public SearchFrame() throws IOException {
        this.countries = JsonFileReader.createCountriesList("response.json");
        // frame
        setTitle("Country search app");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1400, 1400);
        setLayout(new BorderLayout());


        mainPanel = new JPanel(new FlowLayout());
        result = new JPanel();
        result.setBackground(backgroundColor);

        // departure panel and slider panel
        JPanel departurePanel = createDeparturePanel();
        JPanel sliderPanel = createSliderPanel();

        // departure panel above slider
        JPanel departureAndSliderPanel = new JPanel();
        departureAndSliderPanel.setLayout(new BoxLayout(departureAndSliderPanel, BoxLayout.Y_AXIS));
        departureAndSliderPanel.add(departurePanel);
        departureAndSliderPanel.add(sliderPanel);
        mainPanel.add(departureAndSliderPanel);

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
        getContentPane().add(BorderLayout.NORTH, mainPanel);
        add(BorderLayout.CENTER, new JScrollPane(result));
        getContentPane().setBackground(backgroundColor);
        setVisible(true);

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
        titledBorder.setTitleFont(borderFont);
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
        titledBorder.setTitleFont(borderFont);
        landlockedPanel.setBorder(titledBorder);
        landlockedPanel.setBackground(backgroundColor);
        return landlockedPanel;
    }

    public JButton createSearchButton(){
        JButton jButton = new JButton("Search");

        jButton.addActionListener(e -> {
            // clear result panel
            result.removeAll();
            filteredCountries = new ArrayList<>(countries);

            // get departure country
            String departureCountryName = String.valueOf(fromBox.getSelectedItem());
            Optional<Country> optionalCountry = countries.stream()
                    .filter(country -> Objects.equals(country.getName().getCommon(), departureCountryName))
                    .findFirst();
            optionalCountry.ifPresent(country -> departureCountry = country);

            if (departureCountry.getCapitalInfo().getLatlng() == null) {
                result.setLayout(new FlowLayout());
                // display info about no coordinates of departure country's coordinates
                result.add(createNoCoordinatesPanel(), BorderLayout.CENTER);
            } else {
                // filter countries
                filterCountriesByContinent();
                filterCountriesByLandlocked();
                filterCountriesByDistance();
                // remove departure country from the list
                filteredCountries.removeIf(country -> country.getName().getCommon()
                        .equals(departureCountryName));
                // display info about no countries matching the choices or no selected continent
                if (filteredCountries.isEmpty() || noContinentSelected()){
                    result.add(createNoInfoPanel(), BorderLayout.CENTER);
                } else {
                    sortCountries();
                    // display flags of filtered countries
                    displayCountryFlags(result, filteredCountries);
                }
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
        titledBorder.setTitleFont(borderFont);
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
        sliderValue = 4000;
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
    public void filterCountriesByDistance() {

        userCoordinates = departureCountry.getCapitalInfo().getLatlng();

        if (userCoordinates != null) {
            double userLat = userCoordinates[0];
            double userLong = userCoordinates[1];
            filteredCountries = filteredCountries.stream()
                    .filter(country -> {
                        if (country.getCapitalInfo().getLatlng() != null) {
                            double countryLatitude = country.getCapitalInfo().getLatlng()[0];
                            double countryLongitude = country.getCapitalInfo().getLatlng()[1];

                            double distance = calculateDistance(userLat, userLong, countryLatitude, countryLongitude);
                            return distance <= this.sliderValue;
                        } else {
                            return false;
                        }
                    })
                    .collect(Collectors.toList());
        }
    }

    public void displayCountryFlags(JPanel panel, List<Country> countriesList) {
        JFrame parentFrame = this;
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
                Font nameFont = new Font("MONOSPACED", Font.PLAIN, 14);
                name.setFont(nameFont);
                if (Objects.equals(name.getText(), "Saint Helena, Ascension and Tristan da Cunha")) {
                    name.setText("Saint Helena");
                }
                if (Objects.equals(name.getText(), "United States Minor Outlying Islands")) {
                    name.setText("US Minor Outlying Islands");
                }
                imageLabel.setToolTipText("See more information");
                imageLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
                name.setForeground(Color.decode("#3d405b"));
                imagePanel.add(name);
                imagePanel.add(imageLabel);
                imagePanel.setBackground(backgroundColor);
                panel.add(imagePanel);

                imageLabel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        try {
                            CountryFrame countryFrame = new CountryFrame(country, parentFrame, countries, departureCountry);
                            parentFrame.setVisible(false);
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
        titledBorder.setTitleFont(borderFont);
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

    private JPanel createDeparturePanel(){
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
        titledBorder.setTitleFont(borderFont);
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
            userCoordinates = countries.stream()
                    .filter(country -> country.getName().getCommon().equals(fromBox.getSelectedItem()))
                    .map(Country::getLatlng)
                    .findFirst()
                    .orElse(null);
            if (userCoordinates != null) {
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
    }

    private JPanel createNoCoordinatesPanel(){
        JPanel jPanel = new JPanel();
        JLabel jLabel = new JLabel("<html><font size=5>Sorry, we couldn't find info about your coordinates." +
                "</font></html>");
        Font labelFont = new Font("MONOSPACED", Font.PLAIN, 5);
        jLabel.setFont(labelFont);
        jLabel.setForeground(textColor);
        jPanel.setBackground(backgroundColor);
        jPanel.add(jLabel);
        return jPanel;
    }
    private JPanel createNoInfoPanel(){
        JPanel jPanel = new JPanel();
        JLabel jLabel = new JLabel();
        if (departureCountry.getCapitalInfo().getLatlng() == null) {
            jLabel = new JLabel("<html><font size=5>Sorry, we couldn't find info about your coordinates." +
                    "</font></html>");
        } else if (noContinentSelected()){
            jLabel = new JLabel("<html><font size=5>Please select at least one continent</font></html>");
        } else if (filteredCountries.isEmpty()) {
            jLabel = new JLabel("<html><font size=5>Sorry, we couldn't find any country that matches" +
                    " your choices :(</font></html>");
        }
        Font labelFont = new Font("MONOSPACED", Font.PLAIN, 5);
        jLabel.setFont(labelFont);
        jLabel.setForeground(textColor);
        jPanel.setBackground(backgroundColor);
        jPanel.add(jLabel);
        return jPanel;
    }
    private boolean noContinentSelected() {
        return !(cont1.isSelected() || cont2.isSelected() || cont3.isSelected() ||
                cont4.isSelected() || cont5.isSelected() || cont6.isSelected());
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
