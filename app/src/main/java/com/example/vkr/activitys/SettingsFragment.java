package com.example.vkr.activitys;

import android.os.Bundle;

import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;

import com.example.vkr.R;

public class SettingsFragment extends PreferenceFragmentCompat {


    SwitchPreference switchPreference;

    PreferenceCategory accountPreferenceCategory;


    PreferenceCategory generalPreferenceCategory;



    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_xml, rootKey);
        switchPreference = (SwitchPreference) findPreference("switch_key");
//        switchPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
//            @Override
//            public boolean onPreferenceChange(Preference preference, Object newValue) {
//                return false;
//            }
//        });
        accountPreferenceCategory = (PreferenceCategory) findPreference("account_pref");
        generalPreferenceCategory  = (PreferenceCategory) findPreference("general_pref");
    }

    public SettingsFragment() {
    }


}