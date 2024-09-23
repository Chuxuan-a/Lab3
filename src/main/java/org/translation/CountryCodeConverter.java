package org.translation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class provides the service of converting country codes to their names.
 */
public class CountryCodeConverter {

    List<String> countryNames = new ArrayList<>();
    List<String> countryCodes_A2 = new ArrayList<>();
    List<String> countryCodes_A3 = new ArrayList<>();
    List<String> countryCodes_num = new ArrayList<>();
    // TODO Task: pick appropriate instance variable(s) to store the data necessary for this class


    /**
     * Default constructor which will load the country codes from "country-codes.txt"
     * in the resources folder.
     */
    public CountryCodeConverter() {
        this("country-codes.txt");
    }

    /**
     * Overloaded constructor which allows us to specify the filename to load the country code data from.
     * @param filename the name of the file in the resources folder to load the data from
     * @throws RuntimeException if the resource file can't be loaded properly
     */
    public CountryCodeConverter(String filename) {

        try {
            List<String> lines = Files.readAllLines(Paths.get(getClass()
                    .getClassLoader().getResource(filename).toURI()));

            // TODO Task: use lines to populate the instance variable(s)
            for (int i = 1; i < lines.size(); i++) {
                String line = lines.get(i);
                String[] columns = line.trim().split("\\s+");
                if (columns.length >= 4) {
                    int len = columns.length;
                    StringBuilder name = new StringBuilder();
                    for (int j = 0; j < len - 3; j++) {
                        if (j > 0) {name.append(" ");}
                        name.append(columns[j]);
                    }
                    // TODO: delete the test lines
                    countryNames.add(name.toString());
                    System.out.println("Processing " + i + name.toString());
                    countryCodes_A2.add(columns[len - 3]);
                    System.out.println("Processing " + i + columns[len-3]);
                    countryCodes_A3.add(columns[len - 2]);
                    System.out.println("Processing " + i + columns[len-2]);
                    countryCodes_num.add(columns[len - 1]);
                    System.out.println("Processing " + i + columns[len-1]);
                }
            }


        }
        catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }

    }

    /**
     * Returns the name of the country for the given country code.
     * @param code the 3-letter code of the country
     * @return the name of the country corresponding to the code
     */
    public String fromCountryCode(String code) {
        // TODO Task: update this code to use an instance variable to return the correct value
        /* Note that our input is case-sensitive */
        String uppercase = code.toUpperCase();
        int index = countryCodes_A3.indexOf(uppercase);
        /* note that .indexof returns -1 if it never occurs
         */
        return countryNames.get(index);
    }

    /**
     * Returns the code of the country for the given country name.
     * @param country the name of the country
     * @return the 3-letter code of the country
     */
    public String fromCountry(String country) {
        // TODO Task: update this code to use an instance variable to return the correct value
        int index = countryNames.indexOf(country);
        return countryCodes_A3.get(index);
    }

    /**
     * Returns how many countries are included in this code converter.
     * @return how many countries are included in this code converter.
     */
    public int getNumCountries() {
        // TODO Task: update this code to use an instance variable to return the correct value
        return countryNames.toArray().length;
    }
}
