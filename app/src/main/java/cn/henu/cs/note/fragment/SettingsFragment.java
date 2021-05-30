package cn.henu.cs.note.fragment;

import android.os.Bundle;
import android.preference.SwitchPreference;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import cn.henu.cs.note.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    private Preference header,email;
    private Preference password;

    private SwitchPreference patient,close,dark_mode;

    private Preference update,about;


    public SettingsFragment() {
        // required empty public constructor
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.pref_settings, rootKey);

    }
}