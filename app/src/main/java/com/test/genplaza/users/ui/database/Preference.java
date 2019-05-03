package com.test.genplaza.users.ui.database;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Preference {

    private static SharedPreferences pref;

    // Shared preferences file name
    private static final String PREF_NAME = "axxess-shared-preference";

    private static final String COMMENTS = "comments";
    private static final String SHOW_MSP_ENABLED_STORES = "SHOW_MSP_ENABLED_STORES";
    private static final String IN_MSP_STORE = "inMspStore";
    private static final String IN_MSP_REGION = "inMspRegion";

    /**
     * Initializes the shared preferences file.
     *
     * @param context A reference to the Application's Context.
     */
    public static void init(Context context) {
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static Set<String> getComments(String id, String comment) {
        if (comment.isEmpty()) {
            return new HashSet<String>();
        }
        SharedPreferences.Editor editor = getPreferences().edit();
        if (getPreferences().contains(COMMENTS + id)) {
            Set<String> comments = getPreferences().getStringSet(COMMENTS + id, null);
            if (comments != null) {
                comments.add(comment);
            } else {
                comments = new HashSet<String>();
                comments.add(comment);
            }
            editor.putStringSet(COMMENTS + id, comments);
            editor.apply();
            return comments;
        } else {
            //Set the values
            Set<String> set = new HashSet<String>();
            List<String> comments = new ArrayList<>();
            comments.add(comment);
            set.addAll(comments);
            editor.putStringSet(COMMENTS + id, set);
            editor.commit();
            return set;
        }
    }

    public static Set<String> getComments(String id) {
        SharedPreferences.Editor editor = getPreferences().edit();
        if (getPreferences().contains(COMMENTS + id)) {
            Set<String> comments = getPreferences().getStringSet(COMMENTS + id, null);
            return comments;
        } else {
            return new HashSet<>();
        }
    }

    @NonNull
    public static SharedPreferences getPreferences() {
        return pref;
    }

}
