package xyz.fcr.notemaker.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import java.util.Objects;

import xyz.fcr.notemaker.MainActivity;
import xyz.fcr.notemaker.classes.Note;
import xyz.fcr.notemaker.R;

public class NoteEditor extends Fragment {



    public NoteEditor() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.note_editor_fragment, container, false);

        ImageButton button_share = view.findViewById(R.id.button_share);
        ImageButton button_copy = view.findViewById(R.id.button_copy);
        ImageButton button_save = view.findViewById(R.id.button_save);

        button_share.setOnClickListener((v) ->{
            Toast.makeText(getContext(), "In development", Toast.LENGTH_SHORT).show();
        });

        button_copy.setOnClickListener((v) ->{
            Toast.makeText(getContext(), "In development", Toast.LENGTH_SHORT).show();
        });

        button_save.setOnClickListener((v) ->{
            Toast.makeText(getContext(), "In development", Toast.LENGTH_SHORT).show();
        });

        Bundle bundle = getArguments();
        Note note;

        if (bundle.containsKey("note_id")) {
            note = (Note) bundle.getSerializable("note_id");
            Log.d("NOTE_LOG", note.getNoteID());
            displayNote(view, note.getNoteTitle(), note.getNoteContent());
            saveData(note);
        } else {
            displayNote(view, "Create a new note", "Content");
        }

        return view;
    }

    private void displayNote(View view, String noteTitle, String noteContent) {
        TextView title = view.findViewById(R.id.note_editor_title);
        TextView content = view.findViewById(R.id.note_editor_content);

        title.setText(noteTitle);
        content.setText(noteContent);
    }

    private void saveData(Note note){
        Bundle data = new Bundle();
        data.putSerializable("note_id", note);
        Intent intent = Objects.requireNonNull(getActivity()).getIntent();
        intent.putExtras(data);
    }
}