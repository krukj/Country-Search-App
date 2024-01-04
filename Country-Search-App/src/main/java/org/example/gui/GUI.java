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
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GUI {
    public JFrame frame;
    public JPanel mainPanel;  // search panel
    public JPanel result; // flags
    private final List<Country> countries;
    private List<Country> filteredCountries;
    private JCheckBox lang1, lang2; // language choice
    private JCheckBox cont1, cont2, cont3, cont4, cont5, cont6; // continent choice
    private JRadioButton b1, b2, b3;
    private int sliderValue;
    private ButtonGroup buttonGroup;

    public GUI() throws IOException {
        this.countries = JsonFileReader.createCountriesList("response.json");
        this.filteredCountries = new ArrayList<>(countries);

        // frame
        frame = new JFrame("Country search app");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1400, 1400);
        frame.setLayout(new FlowLayout());


        mainPanel = new JPanel(new FlowLayout(20, 20, 50));
        result = new JPanel(new GridLayout(10, 5));


        JLabel label = new JLabel("Find your next travel destination!");
        mainPanel.add(label);


        //distance panel
        JPanel sliderPanel = createSliderPanel();
        mainPanel.add(sliderPanel);

        // languages choices
        JPanel languagesPanel = createLanguagesCheckboxPanel();
        mainPanel.add(languagesPanel);

        // continent choices
        JPanel continentPanel = createContinentCheckboxPanel();
        mainPanel.add(continentPanel);

        // sea choice
        JPanel landlockedPanel = createLandlockedChoicePanel();
        mainPanel.add(landlockedPanel);

        // search button
        JButton searchButton = createSearchButton();
        mainPanel.add(searchButton);

        result.setBackground(Color.BLUE);

        mainPanel.setBackground(Color.GREEN);
        frame.getContentPane().add(BorderLayout.NORTH, mainPanel);
        frame.getContentPane().add(BorderLayout.AFTER_LAST_LINE, result);
        frame.setVisible(true);

    }

    public JPanel createSliderPanel(){
        JPanel sliderPanel = new JPanel();
        sliderPanel.setLayout(new BorderLayout());

        JSlider jSlider = createDistanceSlider();
        sliderPanel.add(jSlider, BorderLayout.CENTER);

        TitledBorder titledBorder = BorderFactory.createTitledBorder("Choose distance");
        sliderPanel.setBorder(titledBorder);

        return sliderPanel;
    }
    public JSlider createDistanceSlider(){
        JSlider jSlider = new JSlider(0, 200, 100);
        jSlider.setPaintTrack(true);
        jSlider.setPaintTicks(true);
        jSlider.setPaintLabels(true);

        // set spacing
        jSlider.setMajorTickSpacing(50);
        jSlider.setMinorTickSpacing(5);

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
            result.removeAll();
            filteredCountries = new ArrayList<>(countries);
            filterCountriesByContinent();
            filterCountriesByLandlocked();
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
        // b2.isSelected =  (landlocked == true)
        if (b2.isSelected()) {
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

                        CountryPanel countryPanel = new CountryPanel(country);
                        frame.add(countryPanel);

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


}
