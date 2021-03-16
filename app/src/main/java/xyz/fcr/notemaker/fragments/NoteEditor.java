package xyz.fcr.notemaker.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import xyz.fcr.notemaker.Note;
import xyz.fcr.notemaker.R;

public class NoteEditor extends Fragment {

    private Note note;

    public NoteEditor() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.note_editor_fragment, container, false);

        Bundle bundle = getArguments();

        if (bundle != null) {
            note = (Note) bundle.getSerializable("note_id");
        } else {
            note = new Note(getString(R.string.open_a_note), "");
        }

        displayNote(myView, note.getNoteTitle(), note.getNoteContent());

        return myView;
    }

    private void displayNote(View view, String noteTitle, String noteContent) {
        TextView title = view.findViewById(R.id.note_editor_title);
        TextView content = view.findViewById(R.id.note_editor_content);

        title.setText(noteTitle);
        content.setText(noteContent);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


}