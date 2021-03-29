package xyz.fcr.notemaker.object_classes;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.appcompat.app.AppCompatDelegate;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import xyz.fcr.notemaker.R;

public class SharedPrefHandler {
    private static final String KEY_NOTE_LIST = "note_list";
    private static final String KEY_NOTE_CURRENT = "note_current";
    private static final Gson gson = new Gson();

    //Getter and setter for NOTE_LIST
    public static void saveArrayInPref(Context context, ArrayList<Note> list) {
        String jList = gson.toJson(list);

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(KEY_NOTE_LIST, jList);
        editor.apply();
    }

    public static ArrayList<Note> getArrayFromPref(Context context) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        String jList = pref.getString(KEY_NOTE_LIST, "");
        Type type = new TypeToken<ArrayList<Note>>() {
        }.getType();
        ArrayList<Note> list = gson.fromJson(jList, type);

        if (list == null) {
            list = new ArrayList<>();
            list.add(new Note(
                    context.getResources().getString(R.string.title),
                    context.getResources().getString(R.string.content)));
        }

        return list;
    }
    //


    //Getter and setter for NOTE_CURRENT
    public static void setCurrentNote(Context context, Note note) {
        String currentNote = gson.toJson(note);

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(KEY_NOTE_CURRENT, currentNote);
        editor.apply();
    }

    public static Note getCurrentNote(Context context) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        String currentNote = pref.getString(KEY_NOTE_CURRENT, "");

        Note note = gson.fromJson(currentNote, Note.class);

        if (note == null) note = new Note("Error", "Error");

        return note;
    }
    //

    //Theme
    public static void setTheme(Context context) {
        SharedPreferences sharedPreferences = context.getApplicationContext().getSharedPreferences("MyUserPref", Context.MODE_PRIVATE);
        String savedTheme = sharedPreferences.getString("note_theme", "");

        if (savedTheme.equals("light"))
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        else if (savedTheme.equals("dark"))
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
    }
}
