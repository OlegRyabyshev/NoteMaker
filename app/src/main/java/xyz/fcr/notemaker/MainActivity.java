package xyz.fcr.notemaker;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;

import xyz.fcr.notemaker.fragment_classes.NoteEditor;
import xyz.fcr.notemaker.fragment_classes.NoteList;
import xyz.fcr.notemaker.object_classes.Note;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private DrawerLayout drawer;

    private Note note;
    private boolean onlyEditor = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        Bundle data = getIntent().getExtras();
        if (data != null && data.containsKey("note_id")) {
            note = (Note) data.getSerializable("note_id");
            onlyEditor = true;
        }

        if (onlyEditor && orientationIsPortrait()) {
            startFragmentEditorInPortrait();
        } else {
            startFragmentList();
            if (getResources().getConfiguration().orientation == 2 && note != null) startFragmentEditor();
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
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.popup_menu, menu);

        //Search
        MenuItem search = menu.findItem(R.id.action_search);
        SearchView searchText = (SearchView) search.getActionView();
        searchText.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(MainActivity.this, query, Toast.LENGTH_SHORT).show();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Toast.makeText(MainActivity.this, newText, Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_notes:
                Toast.makeText(this, "Notes", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_trash:
                Toast.makeText(this, "Trash", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_theme:
                Toast.makeText(this, "Theme", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_about:
                Intent intent = new Intent(this, AboutActivity.class);
                startActivity(intent);
                return true;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                createANewNote();
                return true;
            case R.id.action_notes:
                Toast.makeText(this, "2 In development", Toast.LENGTH_SHORT).show();
                return true;
            default:
                Toast.makeText(this, "Error in onOptionsItemSelected", Toast.LENGTH_SHORT).show();
                return super.onOptionsItemSelected(item);
        }
    }

    private void createANewNote() {
        saveCurrentNote();

        note = new Note (getResources().getString(R.string.title), getResources().getString(R.string.content));

        if (orientationIsPortrait()) {
            startFragmentEditorInPortrait();
        } else {
            startFragmentList();
            if (getResources().getConfiguration().orientation == 2) startFragmentEditor();
        }
    }

    //TODO
    private void saveCurrentNote() {
    }


    private boolean orientationIsPortrait() {
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag("EDITOR_FRAGMENT");

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (fragment != null && fragment.isVisible() && orientationIsPortrait()) {
            startFragmentList();
        } else {
            finish();
        }
    }
}
