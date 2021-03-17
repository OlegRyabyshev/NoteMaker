package xyz.fcr.notemaker.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.Objects;

import xyz.fcr.notemaker.classes.Note;
import xyz.fcr.notemaker.R;

public class NoteEditor extends Fragment {

    public NoteEditor() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.note_editor_fragment, container, false);

        Bundle bundle = getArguments();
        Note note;

        if (bundle.containsKey("note_id")) {
            note = (Note) bundle.getSerializable("note_id");
            Log.d("NOTE_LOG", note.getNoteID());
        } else {
            note = new Note(getString(R.string.open_a_note), "");
        }

        displayNote(myView, note.getNoteTitle(), note.getNoteContent());

        saveData(note);
        return myView;
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