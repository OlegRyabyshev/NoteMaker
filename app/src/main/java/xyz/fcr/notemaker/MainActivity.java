package xyz.fcr.notemaker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.FragmentManager;
import android.content.res.Configuration;
import android.os.Bundle;

import xyz.fcr.notemaker.fragments.NoteEditor;
import xyz.fcr.notemaker.fragments.NoteList;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentTransaction transactionList = getSupportFragmentManager().beginTransaction();
        transactionList.replace(R.id.note_list_fragment, new NoteList());
        transactionList.commit();

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            FragmentTransaction transactionEditor = getSupportFragmentManager().beginTransaction();
            transactionEditor.replace(R.id.note_editor_fragment, new NoteEditor());
            transactionEditor.commit();
        }
    }
}