package xyz.fcr.notemaker.fragment_classes;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import xyz.fcr.notemaker.object_classes.Note;
import xyz.fcr.notemaker.R;

public class NoteList extends Fragment {

    public NoteList(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View myView = inflater.inflate(R.layout.note_list_fragment, container, false);

        Note note1 = new Note(getResources().getString(R.string.note_title_1),
                getResources().getString(R.string.note_inside_1));
        Note note2 = new Note(getResources().getString(R.string.note_title_2),
                getResources().getString(R.string.note_inside_2));
        Note note3 = new Note(getResources().getString(R.string.note_title_3),
                getResources().getString(R.string.note_inside_3));

        ConstraintLayout constraintLayout1 = myView.findViewById(R.id.note1);
        ConstraintLayout constraintLayout2 = myView.findViewById(R.id.note2);
        ConstraintLayout constraintLayout3 = myView.findViewById(R.id.note3);

        //Filling note 1
        TextView titleNote1 = (TextView) constraintLayout1.getViewById(R.id.titleTextPreview);
        TextView contentNote1 = (TextView) constraintLayout1.getViewById(R.id.noteTextInsidePreview);
        TextView dateNote1 = (TextView) constraintLayout1.getViewById(R.id.noteDatePreview);
        TextView timeNote1 = (TextView) constraintLayout1.getViewById(R.id.noteTimePreview);

        titleNote1.setText(note1.getNoteTitle());
        contentNote1.setText(note1.getNotePreviewContent());
        dateNote1.setText(note1.getNoteDate());
        timeNote1.setText(note1.getNoteTime());

        //Filling note 2
        TextView titleNote2 = (TextView) constraintLayout2.getViewById(R.id.titleTextPreview);
        TextView contentNote2 = (TextView) constraintLayout2.getViewById(R.id.noteTextInsidePreview);
        TextView dateNote2 = (TextView) constraintLayout2.getViewById(R.id.noteDatePreview);
        TextView timeNote2 = (TextView) constraintLayout2.getViewById(R.id.noteTimePreview);

        titleNote2.setText(note2.getNoteTitle());
        contentNote2.setText(note2.getNotePreviewContent());
        dateNote2.setText(note2.getNoteDate());
        timeNote2.setText(note2.getNoteTime());

        //Filling note 3
        TextView titleNote3 = (TextView) constraintLayout3.getViewById(R.id.titleTextPreview);
        TextView contentNote3 = (TextView) constraintLayout3.getViewById(R.id.noteTextInsidePreview);
        TextView dateNote3 = (TextView) constraintLayout3.getViewById(R.id.noteDatePreview);
        TextView timeNote3 = (TextView) constraintLayout3.getViewById(R.id.noteTimePreview);

        titleNote3.setText(note3.getNoteTitle());
        contentNote3.setText(note3.getNotePreviewContent());
        dateNote3.setText(note3.getNoteDate());
        timeNote3.setText(note3.getNoteTime());

        constraintLayout1.setOnClickListener((v) -> replaceView(note1));
        constraintLayout2.setOnClickListener((v) -> replaceView(note2));
        constraintLayout3.setOnClickListener((v) -> replaceView(note3));

        return myView;
    }

    private void replaceView(Note note){
        NoteEditor fragment = new NoteEditor();

        Bundle bundle = new Bundle();
        bundle.putSerializable("note_id", note);
        fragment.setArguments(bundle);

        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            transaction.replace(R.id.note_editor_fragment, fragment, "EDITOR_FRAGMENT");
        } else {
            transaction.replace(R.id.note_list_fragment, fragment, "EDITOR_FRAGMENT");
            transaction.addToBackStack("Back");
        }

        transaction.commit();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}