package xyz.fcr.notemaker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.res.Configuration;
import android.os.Bundle;

import xyz.fcr.notemaker.fragments.NoteEditor;
import xyz.fcr.notemaker.fragments.NoteList;

public class MainActivity extends AppCompatActivity {
    private Note note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boolean onlyEditor = false;

        Bundle data = getIntent().getExtras();
        if (data != null) {
            note = (Note) data.getSerializable("note_id");
            onlyEditor = true;
        }

        final int orientation = getResources().getConfiguration().orientation;

        if (onlyEditor && orientation == 1) startFragmentEditorInPortrait();
        else {
            startFragmentList();
            if (orientation == 2) startFragmentEditor();
        }

    }

    private void startFragmentList() {
        FragmentTransaction transactionList = getSupportFragmentManager().beginTransaction();
        transactionList.replace(R.id.note_list_fragment, new NoteList(), "LIST_FRAGMENT");
        transactionList.commit();
    }

    private void startFragmentEditor() {
        Bundle safe = new Bundle();
        if (note != null) safe.putSerializable("note_id", note);

        NoteEditor fragment = new NoteEditor();
        fragment.setArguments(safe);

        FragmentTransaction transactionEditor = getSupportFragmentManager().beginTransaction();
        transactionEditor.replace(R.id.note_editor_fragment, fragment, "EDITOR_FRAGMENT");
        transactionEditor.commit();
    }

    private void startFragmentEditorInPortrait() {
        Bundle safe = new Bundle();
        if (note != null) safe.putSerializable("note_id", note);

        NoteEditor fragment = new NoteEditor();
        fragment.setArguments(safe);

        FragmentTransaction transactionEditor = getSupportFragmentManager().beginTransaction();
        transactionEditor.replace(R.id.note_list_fragment, fragment, "EDITOR_FRAGMENT");
        transactionEditor.commit();
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag("EDITOR_FRAGMENT");

        if (fragment != null && fragment.isVisible() && getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            startFragmentList();
        } else {
            finish();
        }
    }
}