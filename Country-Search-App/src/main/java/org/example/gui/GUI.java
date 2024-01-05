package org.example.gui;

import org.example.country.Country;
import org.example.json.JsonFileReader;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GUI {
    public JFrame frame;
    public JPanel mainPanel;  // search panel
    public JPanel result; // flags
    private final List<Country> countries;
    private List<Country> filteredCountries;
    private JCheckBox cont1, cont2, cont3, cont4, cont5, cont6; // continent choice
    private JRadioButton b1, b2;
    private JComboBox<String> sorterBox;
    private JComboBox<String> fromBox;
    public GUI() throws IOException {
        this.countries = JsonFileReader.createCountriesList("response.json");
        this.filteredCountries = new ArrayList<>(countries);

        // frame
        frame = new JFrame("Country search app");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1400, 1400);
        frame.setLayout(new FlowLayout());


        mainPanel = new JPanel(new FlowLayout());
        result = new JPanel(new GridLayout(10, 10));


        JLabel label = new JLabel("Find your next travel destination!");
        mainPanel.add(label);


        // from panel
        JPanel fromPanel = createFromPanel();
        mainPanel.add(fromPanel);

        // continent choices
        JPanel continentPanel = createContinentCheckboxPanel();
        mainPanel.add(continentPanel);

        // sea choice
        JPanel landlockedPanel = createLandlockedChoicePanel();
        mainPanel.add(landlockedPanel);

        JPanel sorterPanelBox = createSorterPanel();
        mainPanel.add(sorterPanelBox);
        // search button
        JButton searchButton = createSearchButton();
        mainPanel.add(searchButton);

        result.setBackground(Color.BLUE);

        mainPanel.setBackground(Color.GREEN);
        frame.getContentPane().add(BorderLayout.NORTH, mainPanel);
        frame.getContentPane().add(BorderLayout.AFTER_LAST_LINE, result);
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

        TitledBorder titledBorder = BorderFactory.createTitledBorder("Sea/ocean");
        landlockedPanel.setBorder(titledBorder);

        return landlockedPanel;
    }

    public JButton createSearchButton(){
        JButton jButton = new JButton("Search");

        jButton.addActionListener(e -> {
            result.removeAll();
            filteredCountries = new ArrayList<>(countries);
            filterCountriesByContinent();
            filterCountriesByLandlocked();
            if (filteredCountries.isEmpty()){
                // info that there are no countries with user's choices
            } else {
                filteredCountries.removeIf(country -> country.getName().getCommon().equals(fromBox.getSelectedItem()));
                sortCountries();
                displayCountryFlags(result, filteredCountries);
            }
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

    public void displayCountryFlags(JPanel panel, List<Country> countriesList) {
        for (Country country : countriesList) {
            try {
                String imageUrl = country.getFlags().get("png");
                BufferedImage image = ImageIO.read(new URL(imageUrl));

                JLabel imageLabel = new JLabel();
                ImageIcon imageIcon = new ImageIcon(image.getScaledInstance(100, 50, Image.SCALE_DEFAULT));
                imageLabel.setIcon(imageIcon);

                JPanel imagePanel = new JPanel();
                imagePanel.setLayout(new BoxLayout(imagePanel, BoxLayout.Y_AXIS));
                imagePanel.add(imageLabel, BorderLayout.CENTER);

                JLabel name = new JLabel(country.getName().getCommon());
                imageLabel.setToolTipText(country.getName().getCommon());
                imageLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 20, 5));
                imagePanel.add(name);
                imagePanel.add(imageLabel);

                panel.add(imagePanel);

                imagePanel.addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        mainPanel.removeAll();
                        frame.revalidate();
                        frame.repaint();
                    }

                    @Override
                    public void mousePressed(MouseEvent e) {

                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {

                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {

                    }

                    @Override
                    public void mouseExited(MouseEvent e) {

                    }

                });

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private JPanel createSorterPanel(){
        JPanel jPanel = new JPanel();
        String[] choices = {"A-Z", "Distance"};
        sorterBox = new JComboBox<>(choices);
        jPanel.add(sorterBox);
        TitledBorder titledBorder = BorderFactory.createTitledBorder("Sort by");
        jPanel.setBorder(titledBorder);
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
                    .sorted(Comparator.comparing(country -> calculateDistance(userLat, userLong,
                            country.getCapitalInfo().getLatlng()[0], country.getCapitalInfo().getLatlng()[1])))
                    .collect(Collectors.toList());
        }
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
