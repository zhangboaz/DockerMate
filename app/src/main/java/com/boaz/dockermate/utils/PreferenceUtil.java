package com.boaz.dockermate.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceUtil {
    private SharedPreferences sharedPreferences;
    private static final String PREF_NAME = "MyPrefs";
    private static final String KEY_USERNAME = "userId";

    public PreferenceUtil(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public void setUserId(Long userId) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(KEY_USERNAME, userId);
        editor.apply();
    }

    public Long getUserId() {
        return sharedPreferences.getLong(KEY_USERNAME, -1);
    }

    public void clear() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
