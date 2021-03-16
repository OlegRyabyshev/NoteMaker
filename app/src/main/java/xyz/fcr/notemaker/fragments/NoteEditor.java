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

    public NoteEditor() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.note_editor_fragment, container, false);

        Bundle bundle = getArguments();

        TextView title = myView.findViewById(R.id.note_editor_title);
        TextView content = myView.findViewById(R.id.note_editor_content);

        if (bundle != null) {
            Note note = (Note) bundle.getSerializable("note_id");

            title.setText(note.getNoteTitle());
            content.setText(note.getNoteContent());
        } else {
            title.setText(R.string.open_a_note);
            content.setText("");
        }

        return myView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}