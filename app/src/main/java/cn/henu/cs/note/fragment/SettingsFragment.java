package cn.henu.cs.note.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.SwitchPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import cn.henu.cs.note.R;
import cn.henu.cs.note.activity.UpdatePwdActivity;
import cn.henu.cs.note.activity.set;


public class SettingsFragment extends PreferenceFragmentCompat {

    private Preference header,email;
    private Preference password;
    private SwitchPreference patient,close,dark_mode;
    private Preference update,about;
    private SharedPreferences sharedPreferences;
    private boolean nightState = false;


    public SettingsFragment() {
        // required empty public constructor
    }
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.pref_settings, rootKey);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());

        password = findPreference("setting_password");
        password.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent intent = new Intent(getContext(), UpdatePwdActivity.class);
                startActivity(intent);
                return true;
            }
        });

        dark_mode = (SwitchPreference) findPreference("dark_mode");
        dark_mode.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                Intent intent = new Intent(getContext(), set.class);
                startActivity(intent);

                if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }
                return true;
            }
        });
    }

    private void openNight(boolean open){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("nightState", open);
        editor.commit();
    }


}