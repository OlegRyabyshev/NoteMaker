package xyz.fcr.notemaker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.FragmentManager;
import android.content.res.Configuration;
import android.os.Bundle;

import xyz.fcr.notemaker.fragments.NoteEditor;
import xyz.fcr.notemaker.fragments.NoteList;

public class MainActivity extends AppCompatActivity {
    private Note note;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boolean onlyEditor = false;

        if (savedInstanceState != null) {
            note = (Note) savedInstanceState.getSerializable("note_id");
            if (note != null) {
                bundle.putSerializable("note_id", note);
                onlyEditor = true;
            }
        }

        startFragmentList();

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            startFragmentEditor();

    }

    private void startFragmentList() {
        FragmentTransaction transactionList = getSupportFragmentManager().beginTransaction();
        transactionList.replace(R.id.note_list_fragment, new NoteList());
        transactionList.commit();
    }

    private void startFragmentEditor() {
        NoteEditor fragment = new NoteEditor();
        if (bundle != null) fragment.setArguments(bundle);

        FragmentTransaction transactionEditor = getSupportFragmentManager().beginTransaction();
        transactionEditor.replace(R.id.note_editor_fragment, fragment);
        transactionEditor.commit();
    }

}