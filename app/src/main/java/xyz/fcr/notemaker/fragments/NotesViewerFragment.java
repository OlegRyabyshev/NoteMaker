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

public class NotesViewerFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notes_viewer, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Note note1 = new Note(getResources().getString(R.string.note_title_1),
                getResources().getString(R.string.note_inside_1));
        Note note2 = new Note(getResources().getString(R.string.note_title_2),
                getResources().getString(R.string.note_inside_2));
        Note note3 = new Note(getResources().getString(R.string.note_title_3),
                getResources().getString(R.string.note_inside_3));

        TextView titleNote1 = (TextView) view.findViewById(R.id.titleTextPreview);
        TextView insideNote1 = (TextView) view.findViewById(R.id.noteTextInsidePreview);
        TextView dateNote1 = (TextView) view.findViewById(R.id.noteDatePreview);
        TextView timeNote1 = (TextView) view.findViewById(R.id.noteTimePreview);

        titleNote1.setText(note1.getNoteTitle());
        insideNote1.setText(note1.getNotePreviewContent());
        dateNote1.setText(note1.getNoteDate());
        timeNote1.setText(note1.getNoteTime());

    }
}