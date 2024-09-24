package org.translation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * An implementation of the Translator interface which reads in the translation
 * data from a JSON file. The data is read in once each time an instance of this class is constructed.
 */
public class JSONTranslator implements Translator {

    private HashMap<String, List<String>> countryToLanguages;
    private HashMap<String, HashMap<String, String>> countryToTranslations;
    /**
     * Constructs a JSONTranslator using data from the sample.json resources file.
     */

    public JSONTranslator() {
        this("sample.json");
    }

    /**
     * Constructs a JSONTranslator populated using data from the specified resources file.
     * @param filename the name of the file in resources to load the data from
     * @throws RuntimeException if the resource file can't be loaded properly
     */
    public JSONTranslator(String filename) {
        // read the file to get the data to populate things...
        countryToLanguages = new HashMap<>();
        countryToTranslations = new HashMap<>();

        try {

            String jsonString = Files.readString(Paths.get(getClass().getClassLoader().getResource(filename).toURI()));

            JSONArray jsonArray = new JSONArray(jsonString);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String countryCode = jsonObject.getString("alpha3");

                List<String> languages = new ArrayList<>();
                HashMap<String, String> translations = new HashMap<>();

                for (String key : jsonObject.keySet()) {
                    if (!"id".equals(key) && !"alpha2".equals(key) && !"alpha3".equals(key)) {
                        languages.add(key);
                        translations.put(key, jsonObject.getString(key));
                    }
                }
                countryToLanguages.put(countryCode, languages);
                countryToTranslations.put(countryCode, translations);
            }
        }
        catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<String> getCountryLanguages(String country) {
        List<String> languages = countryToLanguages.get(country);
        if (languages == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(languages);
        // Since we're only asked to make sure there is no aliasing to a **mutable** object,
        // so a shallow copy would suffice.
    }

    @Override
    public List<String> getCountries() {
        List<String> countries = new ArrayList<>();
        for (HashMap.Entry<String, List<String>> entry : countryToLanguages.entrySet()) {
            if (!entry.getValue().isEmpty()) {
                countries.add(entry.getKey());
            }
        }
        return countries;
    }

    @Override
    public String translate(String country, String language) {
        HashMap<String, String> translations = countryToTranslations.get(country);
        if (translations != null) {
            return translations.get(language);
            // to avoid null pointer
        }
        return null;
    }
}
        // Note that this will return null if there is no translation
