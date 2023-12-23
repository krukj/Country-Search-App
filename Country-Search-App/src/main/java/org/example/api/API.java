package org.example.api;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class API {
    public static void main(String[] args) {
        try {
            connectToApi();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void connectToApi() throws IOException {
        try {
            URL url = new URL("https://restcountries.com/v3.1/all");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Get the response code
            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            // Read the response if the request was successful (response code 200)
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    String inputLine;
                    StringBuilder response = new StringBuilder();

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }

                    // Print the response
                    System.out.println(response.toString());

                    // Save the response to response.json
                    try (FileWriter fileWriter = new FileWriter("response.json")) {
                        fileWriter.write(response.toString());
                        System.out.println("Response saved to response.json");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                // Print an error message and check for more details in the response
                try (BufferedReader errorReader = new BufferedReader(new InputStreamReader(connection.getErrorStream()))) {
                    String errorInputLine;
                    StringBuilder errorResponse = new StringBuilder();

                    while ((errorInputLine = errorReader.readLine()) != null) {
                        errorResponse.append(errorInputLine);
                    }

                    // Print the error response
                    System.out.println("Error in HTTP request: " + responseCode);
                    System.out.println("Error Response: " + errorResponse.toString());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
