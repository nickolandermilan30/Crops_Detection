package com.example.cropsdetection;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DiseaseManager {
    private static final String PREF_NAME = "DiseasePrefs";
    private static final String KEY_DISEASES = "diseases";

    private Context context;
    private SharedPreferences sharedPreferences;

    public DiseaseManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    // Add a disease to the list and save it
    public void addDisease(String disease) {
        // Retrieve existing diseases
        Set<String> diseases = getDiseases();
        // Add the new disease
        diseases.add(disease);
        // Save the updated list
        saveDiseases(diseases);
    }

    // Get the list of diseases
    public List<String> getDiseaseList() {
        Set<String> diseases = getDiseases();
        return new ArrayList<>(diseases);
    }

    // Initialize the list with blank items
    public void initializeList() {
        Set<String> diseases = new HashSet<>();
        saveDiseases(diseases);
    }

    // Private helper method to get diseases from SharedPreferences
    private Set<String> getDiseases() {
        return sharedPreferences.getStringSet(KEY_DISEASES, new HashSet<>());
    }

    // Private helper method to save diseases to SharedPreferences
    private void saveDiseases(Set<String> diseases) {
        sharedPreferences.edit().putStringSet(KEY_DISEASES, diseases).apply();
    }
}
