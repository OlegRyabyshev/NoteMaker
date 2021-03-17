package xyz.fcr.notemaker;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import xyz.fcr.notemaker.classes.Note;
import xyz.fcr.notemaker.fragments.NoteEditor;
import xyz.fcr.notemaker.fragments.NoteList;

public class MainActivity extends AppCompatActivity {

    private Note note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = findViewById(R.id.customToolbar);
        toolbar.setPopupTheme(R.style.Theme_Note_PopUpOverlay);
        setSupportActionBar(toolbar);

        boolean onlyEditor = false;

        Bundle data = getIntent().getExtras();
        if (data != null) {
            note = (Note) data.getSerializable("note_id");
            onlyEditor = true;
        }

        if (onlyEditor && getResources().getConfiguration().orientation == 1) {
            startFragmentEditorInPortrait();
        } else {
            startFragmentList();
            if (getResources().getConfiguration().orientation == 2) startFragmentEditor();
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

        if (fragment != null
                && fragment.isVisible()
                && getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            startFragmentList();
        } else {
            finish();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.option_one) {
            Toast.makeText(getApplicationContext(), "Not available yet", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.option_two) {
            Toast.makeText(getApplicationContext(), "Not available yet", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("ResourceType")
    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.popup_menu, menu);
        return true;
    }
}