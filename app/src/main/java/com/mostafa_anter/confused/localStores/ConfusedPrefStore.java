package com.mostafa_anter.confused.localStores;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by mostafa_anter on 9/3/16.
 */
public class ConfusedPrefStore {
    private static final String PREFKEY = "MAKPreferencesStore";
    private SharedPreferences confusedPreferences;

    public ConfusedPrefStore(Context context){
        confusedPreferences = context.getSharedPreferences(PREFKEY, Context.MODE_PRIVATE);
    }

    public void addPreference(String key, String value){
        SharedPreferences.Editor editor = confusedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public void addPreference(String key, int value){
        SharedPreferences.Editor editor = confusedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public void removePreference(String key){
        SharedPreferences.Editor editor = confusedPreferences.edit();
        editor.remove(key);
        editor.apply();
    }

    public String getPreferenceValue(String key){
        return confusedPreferences.getString(key, "");
    }

    public int getIntPreferenceValue(String key){
        return confusedPreferences.getInt(key, 0);
    }
}
