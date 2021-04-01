package xyz.fcr.notemaker.object_classes;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import xyz.fcr.notemaker.MainActivity;
import xyz.fcr.notemaker.R;

public class SharedPrefHandler {
    private static final String KEY_NOTE_LIST = "KEY_NOTE_LIST";
    private static final String KEY_NOTE_CURRENT = "KEY_NOTE_CURRENT";

    private static final Gson gson = new Gson();

    private static FirebaseDatabase firebaseDB;
    private static DatabaseReference dbReference;

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

        return note;
    }
    //

    public static void removeCurrentNote(Context context) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();
        editor.remove(KEY_NOTE_CURRENT);
        editor.apply();
    }

    public static int getPositionOfNote(Note note) {
        ArrayList<Note> mNoteList = SharedPrefHandler.getArrayFromPref(MainActivity.context);

        for (int i = 0; i < mNoteList.size(); i++) {
            if (mNoteList.get(i).getNoteID().equals(note.getNoteID())) return i;
        }

        return -1;
    }

    //FIREBASE
    public static void setFirebaseDB(Context context) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        String jList = pref.getString(KEY_NOTE_LIST, "");

        firebaseDB = FirebaseDatabase.getInstance();
        dbReference = firebaseDB.getReference().child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Notes");
        dbReference.setValue(jList);
    }

    public static void getNotesFromFirebaseDB(Context context){

        dbReference = FirebaseDatabase.getInstance().getReference().child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Notes");

        dbReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() != null){
                    String jList = snapshot.getValue().toString();

                    SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString(KEY_NOTE_LIST, jList);
                    editor.apply();

                    SharedPrefHandler.removeCurrentNote(context);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


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
