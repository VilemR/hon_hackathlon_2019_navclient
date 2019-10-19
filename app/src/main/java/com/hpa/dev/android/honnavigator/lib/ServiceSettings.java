package com.hpa.dev.android.honnavigator.lib;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class ServiceSettings {

    private static SharedPreferences prefsStatic = null;
    private static Context ctx = null;

    public ServiceSettings(Context ctxIn){
        ctx = ctxIn;
    }

    public String getValue(String key) {
        prefsStatic = PreferenceManager.getDefaultSharedPreferences(ctx);
        return prefsStatic.getString(key, null);
    }

    public void setValue(String key, String value) {
        prefsStatic = PreferenceManager.getDefaultSharedPreferences(ctx);
        SharedPreferences.Editor editor = prefsStatic.edit();
        if (value != null) {
            editor.putString(key, value);
            editor.commit();
        } else {
            deleteKey(key);
        }
    }

    public void deleteKey(String key) {
        prefsStatic = PreferenceManager.getDefaultSharedPreferences(ctx);
        SharedPreferences.Editor editor = prefsStatic.edit();
        editor.remove(key);
        editor.commit();
    }

}
