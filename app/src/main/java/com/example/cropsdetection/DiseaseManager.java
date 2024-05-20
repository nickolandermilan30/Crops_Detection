package com.example.cropsdetection;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
        Map<String, Integer> diseasesMap = getDiseasesMap();
        int count = diseasesMap.containsKey(disease) ? diseasesMap.get(disease) + 1 : 1;
        diseasesMap.put(disease, count);
        saveDiseases(diseasesMap);
    }

    // Get the list of diseases
    public List<String> getDiseaseList() {
        Map<String, Integer> diseasesMap = getDiseasesMap();
        List<String> diseasesList = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : diseasesMap.entrySet()) {
            String disease = entry.getKey();
            int count = entry.getValue();
            if (count == 1) {
                diseasesList.add(disease);
            } else {
                diseasesList.add(disease + " (" + count + ")");
            }
        }
        return diseasesList;
    }

    // Initialize the list with blank items
    public void initializeList() {
        saveDiseases(new HashMap<>());
    }

    // Private helper method to get diseases map from SharedPreferences
    private Map<String, Integer> getDiseasesMap() {
        Map<String, Integer> diseasesMap = new HashMap<>();
        Set<String> diseasesSet = sharedPreferences.getStringSet(KEY_DISEASES, null);
        if (diseasesSet != null) {
            for (String disease : diseasesSet) {
                if (disease != null && !disease.isEmpty()) {
                    int index = disease.lastIndexOf(" ");
                    String name = disease.substring(0, index);
                    int count = Integer.parseInt(disease.substring(index + 1));
                    diseasesMap.put(name, count);
                }
            }
        }
        return diseasesMap;
    }

    // Private helper method to save diseases map to SharedPreferences
    private void saveDiseases(Map<String, Integer> diseasesMap) {
        Set<String> diseasesSet = new HashSet<>();
        for (Map.Entry<String, Integer> entry : diseasesMap.entrySet()) {
            String disease = entry.getKey() + " " + entry.getValue();
            diseasesSet.add(disease);
        }
        sharedPreferences.edit().putStringSet(KEY_DISEASES, diseasesSet).apply();
    }

    public void clearDiseases() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet(KEY_DISEASES, new HashSet<String>());
        editor.apply();
    }

}
