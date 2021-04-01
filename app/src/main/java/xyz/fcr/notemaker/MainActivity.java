package xyz.fcr.notemaker;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import xyz.fcr.notemaker.activity_classes.AboutActivity;
import xyz.fcr.notemaker.activity_classes.LoginActivity;
import xyz.fcr.notemaker.activity_classes.RegisterActivity;
import xyz.fcr.notemaker.fragment_classes.NoteEditor;
import xyz.fcr.notemaker.fragment_classes.NoteList;
import xyz.fcr.notemaker.object_classes.Note;
import xyz.fcr.notemaker.object_classes.SharedPrefHandler;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private DrawerLayout drawer;
    public static Context context;

    private Note note;
    private boolean onlyEditor = false;
    private TextView userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPrefHandler.setTheme(this);
        MainActivity.context = getApplicationContext();

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        note = SharedPrefHandler.getCurrentNote(this);
        if (note != null) onlyEditor = true;

        if (onlyEditor && orientationIsPortrait()) {
            startFragmentEditorInPortrait();
        } else {
            startFragmentList();
            if (getResources().getConfiguration().orientation == 2 && note != null)
                startFragmentEditor();
        }

        //If user is logged in: Welcome him, replace buttons
        View header = navigationView.getHeaderView(0);
        userEmail = (TextView) header.findViewById(R.id.user_email);

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            userEmail.setText(getString(R.string.welcome) + "\n"
                    + FirebaseAuth.getInstance().getCurrentUser().getEmail());

            navigationView.getMenu().findItem(R.id.action_register).setVisible(false);
            navigationView.getMenu().findItem(R.id.action_log_in).setVisible(false);

            navigationView.getMenu().findItem(R.id.action_get_from_firebase).setVisible(true);
            navigationView.getMenu().findItem(R.id.action_save_to_firebase).setVisible(true);
            navigationView.getMenu().findItem(R.id.action_logout).setVisible(true);
        } else {
            userEmail.setText(R.string.not_logged_in_yet);
        }
    }

    private void startFragmentList() {
        FragmentTransaction transactionList = getSupportFragmentManager().beginTransaction();
        transactionList.replace(R.id.note_list_fragment, new NoteList(), "LIST_FRAGMENT");
        transactionList.commit();
    }

    private void startFragmentEditor() {
        if (note != null) SharedPrefHandler.setCurrentNote(this, note);

        NoteEditor fragment = new NoteEditor();

        FragmentTransaction transactionEditor = getSupportFragmentManager().beginTransaction();
        transactionEditor.replace(R.id.note_editor_fragment, fragment, "EDITOR_FRAGMENT");
        transactionEditor.commit();
    }

    private void startFragmentEditorInPortrait() {
        if (note != null) SharedPrefHandler.setCurrentNote(this, note);

        NoteEditor fragment = new NoteEditor();

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
            case R.id.action_register:
                Intent intentRegister = new Intent(this, RegisterActivity.class);
                startActivity(intentRegister);
                return true;
            case R.id.action_log_in:
                Intent intentLogin = new Intent(this, LoginActivity.class);
                startActivity(intentLogin);
                return true;
            case R.id.action_get_from_firebase:
                Toast.makeText(this, "Updated from Firebase", Toast.LENGTH_SHORT).show();
                SharedPrefHandler.getNotesFromFirebaseDB(this);
                recreate();
                return true;
            case R.id.action_save_to_firebase:
                Toast.makeText(this, "Saved in Firebase", Toast.LENGTH_SHORT).show();
                SharedPrefHandler.setFirebaseDB(this);
                return true;
            case R.id.action_logout:
                FirebaseAuth.getInstance().signOut();
                recreate();
                Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_theme:
                showOptionsDialog();
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
            default:
                Toast.makeText(this, "Error in onOptionsItemSelected", Toast.LENGTH_SHORT).show();
                return super.onOptionsItemSelected(item);
        }
    }

    private void createANewNote() {
        note = new Note(getResources().getString(R.string.title), getResources().getString(R.string.content));
        SharedPrefHandler.setCurrentNote(this, note);

        if (orientationIsPortrait()) {
            startFragmentEditorInPortrait();
        } else {
            startFragmentList();
            if (getResources().getConfiguration().orientation == 2) startFragmentEditor();
        }
    }

    private boolean orientationIsPortrait() {
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
    }

    @Override
    public void onBackPressed() {
        Fragment fragmentEditor = getSupportFragmentManager().findFragmentByTag("EDITOR_FRAGMENT");

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (fragmentEditor != null && fragmentEditor.isVisible() && orientationIsPortrait()) {
            SharedPrefHandler.removeCurrentNote(this);
            onlyEditor = false;
            startFragmentList();
        } else {
            finish();
        }
    }

    //THEMES
    private void showOptionsDialog() {
        final String[] themes = {
                getResources().getString(R.string.action_theme_light),
                getResources().getString(R.string.action_theme_dark)
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(getResources().getString(R.string.action_theme));

        SharedPreferences sharedPreferences = getSharedPreferences("MyUserPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        int themeInt = getCurrentTheme();

        builder.setSingleChoiceItems(themes, themeInt, (dialog, which) -> {
            if (which == 0) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                editor.putString("note_theme", "light");
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                editor.putString("note_theme", "dark");
            }
            editor.apply();
        });

        builder.show();
    }

    //THEMES
    private int getCurrentTheme() {
        int currentNightMode = getResources().getConfiguration().uiMode
                & Configuration.UI_MODE_NIGHT_MASK;
        switch (currentNightMode) {
            case Configuration.UI_MODE_NIGHT_NO:
                return 0;
            case Configuration.UI_MODE_NIGHT_YES:
                return 1;
            default:
                return -1;
        }
    }
}
