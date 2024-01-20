package org.example.gui;

import org.example.country.Country;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class CountryPanel extends JPanel {
    private final Country country;
    private final List<Country> countries;
    private final Map<String, String> countryCodeToName;
    private final Color textColor = Color.decode("#3d405b");
    private final Country departureCountry;

    public CountryPanel(Country country, List<Country> countries, Country departureCountry) throws IOException {
        this.country = country;
        this.countries = countries;
        this.countryCodeToName = CountryMapper();
        this.departureCountry = departureCountry;

        setLayout(new FlowLayout(FlowLayout.CENTER));

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));

        addLabelToPanel(textPanel, this::createNameLabel);
        addLabelToPanel(textPanel, this::createCapitalLabel);
        addLabelToPanel(textPanel, this::createContinentLabel);
        addLabelToPanel(textPanel, this::createSubregionLabel);
        addLabelToPanel(textPanel, this::createLanguagesLabel);
        addLabelToPanel(textPanel, this::createPopulationLabel);
        addLabelToPanel(textPanel, this::createBordersLabel);
        addLabelToPanel(textPanel, this::createTimezonesLabel);
        addLabelToPanel(textPanel, this::createAreaLabel);
        addLabelToPanel(textPanel, this::createCurrenciesLabel);
        addLabelToPanel(textPanel, this::createCarLabel);
        addLabelToPanel(textPanel, this::createDistanceLabel);
        addLabelToPanel(textPanel, this::createGoogleMapsLabel);

        int labelTopGap = 5;
        int labelLeftGap = 10;
        int labelBottomGap = 5;
        int labelRightGap = 10;

        for (Component component : textPanel.getComponents()) {
            if (component instanceof JLabel) {
                ((JLabel) component).setBorder(
                        new EmptyBorder(labelTopGap, labelLeftGap, labelBottomGap, labelRightGap));
            }
        }
        JPanel imagePanel = createImagePanel();
        textPanel.setPreferredSize(new Dimension(700,700));
        textPanel.setBackground(Color.decode("#f4f1de"));
        imagePanel.add(textPanel);
        imagePanel.setBackground(Color.decode("#f4f1de"));
        add(imagePanel, BorderLayout.WEST);
        setBackground(Color.decode("#f4f1de"));
    }

    private JLabel createNameLabel(){
        JLabel jLabel;
        if (country.getName() != null & country.getName().getCommon() != null) {
            jLabel = new JLabel("<html><span style='font-size:20px;'>" + country.getName().getCommon()
                    + "</span></html>");
        } else {
            jLabel = new JLabel("<html><font size=20>No info</font></html>");
        }
        Font labelFont = new Font("MONOSPACED", Font.BOLD, 20);
        jLabel.setFont(labelFont);
        jLabel.setForeground(textColor);
        return jLabel;
    }

    private JLabel createCapitalLabel(){
        List<String> capitals = List.of(country.getCapitals());

        // Build HTML content for the label
        StringBuilder htmlContent = new StringBuilder("<html><font size=4>Capital(s): </font>");
        if (capitals.isEmpty()) {
            htmlContent.append("<font size=5>No info</font>");
        } else {
            for (int i = 0; capitals.size() > i; i++) {
                String capital = capitals.get(i);
                htmlContent.append("<font size=5>").append(capital).append("</font>");

                // Add comma and space if it's not the last capital
                if (i < capitals.size() - 1) {
                    htmlContent.append(", ");
                }
            }
        }

        htmlContent.append("</html>");

        // Create and configure the JLabel
        JLabel jLabel = new JLabel(htmlContent.toString());
        Font labelFont = new Font("MONOSPACED", Font.PLAIN, 5);
        jLabel.setFont(labelFont);
        jLabel.setForeground(textColor);
        return jLabel;
    }

    private JLabel createContinentLabel(){
        List<String> continents = country.getContinents();

        // Build HTML content for the label
        StringBuilder htmlContent = new StringBuilder("<html><font size=4>Continent(s): </font>");

        if (continents.isEmpty()) {
            htmlContent.append("<font size=5>No info</font>");
        } else {
            for (int i = 0; continents.size() > i; i++) {
                String continent = continents.get(i);
                htmlContent.append("<font size=5>").append(continent).append("</font>");

                // Add comma and space if it's not the last continent
                if (i < continents.size() - 1) {
                    htmlContent.append(", ");
                }
            }
        }

        htmlContent.append("</html>");

        // Create and configure the JLabel
        JLabel jLabel = new JLabel(htmlContent.toString());
        Font labelFont = new Font("MONOSPACED", Font.PLAIN, 5);
        jLabel.setFont(labelFont);
        jLabel.setForeground(textColor);
        return jLabel;
    }

    private JLabel createSubregionLabel(){
        JLabel jLabel;
        if (country.getSubregion() != null) {
            jLabel = new JLabel("<html><font size=4>Subregion: </font><font size=5>" + country.getSubregion()
                    + "</font></html>");
        } else {
            jLabel = new JLabel("<html><font size=4>Subregion: </font><font size=5>No info</font></html>");
        }
        Font labelFont = new Font("MONOSPACED", Font.PLAIN, 5);
        jLabel.setFont(labelFont);
        jLabel.setForeground(textColor);
        return jLabel;
    }

    private JLabel createPopulationLabel(){
        JLabel jLabel = new JLabel("<html><font size=4>Population: </font><font size=5>" + country.getPopulation()
                + "</font></html>");
        Font labelFont = new Font("MONOSPACED", Font.PLAIN, 5);
        jLabel.setFont(labelFont);
        jLabel.setForeground(textColor);
        return jLabel;
    }

    private JLabel createAreaLabel(){
        JLabel jLabel = new JLabel("<html><font size=4>Area: </font><font size=5>" + country.getArea()
                + " km²" + "</font></html>");
        Font labelFont = new Font("MONOSPACED", Font.PLAIN, 5);
        jLabel.setFont(labelFont);
        jLabel.setForeground(textColor);
        return jLabel;
    }

    private JLabel createLanguagesLabel(){
        Map<String, String> languagesMap = country.getLanguages();

        // Using StringBuilder to build the HTML content
        StringBuilder htmlContent = new StringBuilder("<html><font size=4>Language(s) spoken: </font><font size=5>");

        // Iterate through the values of the languagesMap and append them to the HTML content
        if (!languagesMap.values().isEmpty()) {
            languagesMap.values().forEach(language -> htmlContent.append(language).append(", "));

            // Remove the trailing comma and space
            if (htmlContent.length() > 0) {
                htmlContent.setLength(htmlContent.length() - 2);
            }
        } else {
            htmlContent.append("No info");
        }

        // Complete the HTML content and create the JLabel
        JLabel jLabel = new JLabel(htmlContent.append("</font></html>").toString());
        Font labelFont = new Font("MONOSPACED", Font.PLAIN, 5);
        jLabel.setFont(labelFont);
        jLabel.setForeground(textColor);
        return jLabel;
    }

    private JLabel createBordersLabel(){
        String[] borderCodes = country.getBorders();
        StringBuilder htmlContent = new StringBuilder("<html><font size=4>Borders: </font><font size=5>");
        // borders are a list of country codes, so we need to convert them to names
        if (borderCodes != null) {
            String[] borderNames = new String[borderCodes.length];
            for (int i = 0; i < borderCodes.length; i++) {
                borderNames[i] = countryCodeToName.getOrDefault(borderCodes[i], "Unknown country");
            }

            // Build HTML content for the label
            for (int i = 0; i < borderNames.length; i++) {
                htmlContent.append(borderNames[i]);

                // Add comma and space if it's not the last country
                if (i < borderNames.length - 1) {
                    htmlContent.append(", ");
                }
            }
        } else {
            htmlContent.append("No info or none");
        }
        htmlContent.append("</font></html>");

        // Create and configure the JLabel
        JLabel jLabel = new JLabel(htmlContent.toString());
        Font labelFont = new Font("MONOSPACED", Font.PLAIN, 5);
        jLabel.setFont(labelFont);
        jLabel.setForeground(textColor);

        return jLabel;
    }

    private JLabel createTimezonesLabel(){
        List<String> timezones = List.of(country.getTimezones());

        // Build HTML content for the label
        StringBuilder htmlContent = new StringBuilder("<html><font size=4>Timezone(s): </font>");

        for (int i = 0; timezones.size() > i; i++) {
            String timezone = timezones.get(i);
            htmlContent.append("<font size=5>").append(timezone).append("</font>");

            // Add comma and space if it's not the last timezone
            if (i < timezones.size() - 1) {
                htmlContent.append(", ");
            }
        }

        htmlContent.append("</html>");

        // Create and configure the JLabel
        JLabel jLabel = new JLabel(htmlContent.toString());
        Font labelFont = new Font("MONOSPACED", Font.PLAIN, 5);
        jLabel.setFont(labelFont);
        jLabel.setForeground(textColor);
        return jLabel;
    }

    private JLabel createCurrenciesLabel() {
        Map<String, Country.Currency> currencies = country.getCurrencies();
        // Build HTML content for the label
        StringBuilder htmlContent = new StringBuilder("<html><font size=4>Currencies: </font>");

        // Iterate through the currencies map and append the currency information
        currencies.forEach((code, currency) ->
                htmlContent.append("<font size=5>").append(currency.getName())
                        .append(" (").append(currency.getSymbol()).append(")</font>, "));

        // Remove the trailing comma and space
        if (!currencies.isEmpty()) {
            htmlContent.setLength(htmlContent.length() - 2);
        }

        htmlContent.append("</html>");

        // Create and configure the JLabel
        JLabel jLabel = new JLabel(htmlContent.toString());
        Font labelFont = new Font("MONOSPACED", Font.PLAIN, 5);
        jLabel.setFont(labelFont);
        jLabel.setForeground(textColor);
        return jLabel;
    }

    private JLabel createCarLabel() {
        Country.Car car = country.getCar();
        // Build HTML content for the label
        StringBuilder htmlContent = new StringBuilder("<html><font size=4>Car Information: </font>");

        // Append signs information
        htmlContent.append("<font size=5>Signs: ");
        List<String> signs = List.of(car.getSigns());
        for (int i = 0; i < signs.size(); i++) {
            htmlContent.append(signs.get(i));
            // Add comma and space if it's not the last sign
            if (i < signs.size() - 1) {
                htmlContent.append("<font size=5>, </font>");
            }
        }
        htmlContent.append("</font>");

        // Append side information
        htmlContent.append("<font size=5>, Side: ").append(car.getSide()).append("</font></html>");

        // Create and configure the JLabel
        JLabel jLabel = new JLabel(htmlContent.toString());
        Font labelFont = new Font("MONOSPACED", Font.PLAIN, 5);
        jLabel.setFont(labelFont);
        jLabel.setForeground(textColor);
        return jLabel;
    }

    private JLabel createDistanceLabel(){
        double[] userCoordinates = countries.stream()
                .filter(country -> country.getName().getCommon().equals(departureCountry.getName().getCommon()))
                .map(Country::getLatlng)
                .findFirst()
                .orElse(null);

        assert userCoordinates != null;
        double userLat = userCoordinates[0];
        double userLong = userCoordinates[1];

        JLabel jLabel;
        if (country.getCapitalInfo() != null) {
            double countryLatitude = country.getCapitalInfo().getLatlng()[0];
            double countryLongitude = country.getCapitalInfo().getLatlng()[1];
            int distance = calculateDistance(userLat, userLong, countryLatitude, countryLongitude);
            jLabel = new JLabel("<html><font size=4>Distance: </font><font size=5>" + distance + " km" +
                    "</font> <font size=3> (from " +
                    Arrays.toString(departureCountry.getCapital()).replaceAll("[\\[\\]]", "")
                    + " to " + Arrays.toString(country.getCapital()).replaceAll("[\\[\\]]", "")
                    + ")</font></html>");
        } else {
            jLabel = new JLabel("<html><font size=4>Distance: </font><font size=5>No info</font></html>");
        }
        Font labelFont = new Font("MONOSPACED", Font.PLAIN, 5);
        jLabel.setFont(labelFont);
        jLabel.setForeground(textColor);
        return jLabel;
    }

    private JLabel createGoogleMapsLabel() {
        JLabel jLabel = new JLabel("<html><font size=4<u>" + "See on Google Maps" + "</u></html>");
        Font labelFont = new Font("MONOSPACED", Font.PLAIN, 5);
        jLabel.setFont(labelFont);
        jLabel.setForeground(Color.BLUE);
        jLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        jLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    String mapLink = country.getMaps().getGoogleMaps();
                    Desktop.getDesktop().browse(new URI(mapLink));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            @Override
            public void mouseEntered(MouseEvent e) {
            }
            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
        return jLabel;
    }

    private JPanel createImagePanel() throws IOException {
        // Create a panel for the image
        JPanel imagePanel = new JPanel();
        imagePanel.setLayout(new BoxLayout(imagePanel, BoxLayout.X_AXIS));

        String imageUrl = country.getFlags().get("png");
        BufferedImage image = ImageIO.read(new URL(imageUrl));

        JLabel imageLabel = new JLabel();
        ImageIcon imageIcon = new ImageIcon(image.getScaledInstance(200, 100, Image.SCALE_DEFAULT));
        imageLabel.setIcon(imageIcon);

        // Add image to the left of text
        imagePanel.add(imageLabel, BorderLayout.CENTER);
        imagePanel.add(Box.createHorizontalStrut(10));
        return imagePanel;
    }

    private Map<String, String> CountryMapper() {
        // map with country codes to country name
        HashMap<String, String> countryCodeToName = new HashMap<>();
        for (Country country1: countries) {
            if (country1.getCca2() != null) {
                countryCodeToName.put(country1.getCca3(), country1.getName().getCommon());
            }
        }
        return countryCodeToName;
    }

    private void addLabelToPanel(JPanel panel, Supplier<JLabel> labelSupplier) {
        JLabel label = leftJustifyLabel(labelSupplier.get());
        panel.add(label);
    }

    private JLabel leftJustifyLabel(JLabel label) {
        Box b = Box.createHorizontalBox();
        b.add(label);
        b.add(Box.createHorizontalGlue());

        // Create a wrapper panel to ensure the returned component is a JLabel
        JPanel wrapperPanel = new JPanel();
        wrapperPanel.setLayout(new BorderLayout());
        wrapperPanel.add(b, BorderLayout.LINE_START);

        return label;
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
