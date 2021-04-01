package xyz.fcr.notemaker.fragment_classes;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.Objects;

import xyz.fcr.notemaker.object_classes.Note;
import xyz.fcr.notemaker.R;
import xyz.fcr.notemaker.object_classes.NoteAdapter;
import xyz.fcr.notemaker.object_classes.SharedPrefHandler;

public class NoteEditor extends Fragment {

    private Note note;
    private TextView title;
    private TextView content;
    private ArrayList<Note> mNoteArrayList;
    public static NoteAdapter mAdapter;
    private Fragment fragment;

    public NoteEditor() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.note_editor_fragment, container, false);

        mNoteArrayList = SharedPrefHandler.getArrayFromPref(getContext());

        title = view.findViewById(R.id.note_editor_title);
        content = view.findViewById(R.id.note_editor_content);

        title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                note.update(s.toString(), content.getText().toString());
                saveData(note);
            }
        });

        content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                note.update(title.getText().toString(), s.toString());
                saveData(note);
            }
        });

        MaterialButton button_share = view.findViewById(R.id.button_share);
        MaterialButton button_copy = view.findViewById(R.id.button_copy);
        MaterialButton button_delete = view.findViewById(R.id.button_delete);

        button_share.setOnClickListener((v) -> {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, getTextFromNote());
            startActivity(Intent.createChooser(intent, getResources().getString(R.string.share)));
        });


        button_copy.setOnClickListener((v) -> {
            ClipboardManager clipboard = (ClipboardManager) requireActivity().getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("CopiedText", getTextFromNote());
            clipboard.setPrimaryClip(clip);
            Toast.makeText(getContext(), getResources().getString(R.string.copied), Toast.LENGTH_SHORT).show();
        });

        button_delete.setOnClickListener((v) -> {
            if (mNoteArrayList.size() > 0) {
                for (int i = 0; i < mNoteArrayList.size(); i++) {
                    if (note.getNoteID().equals(mNoteArrayList.get(i).getNoteID())) {
                        mNoteArrayList.remove(i);
                        break;
                    }
                }
            }

            SharedPrefHandler.saveArrayInPref(getContext(), mNoteArrayList);
            Objects.requireNonNull(getActivity()).onBackPressed();
        });

        note = SharedPrefHandler.getCurrentNote(getContext());
        if (note != null) displayNote(note.getNoteTitle(), note.getNoteContent());

        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        //SharedPrefHandler.setCurrentNote(getContext(), note);
    }

    private String getTextFromNote() {
        String titleM = title.getText().toString();
        String contentM = content.getText().toString();

        String dot = ".";
        if (!titleM.endsWith(dot)) titleM += dot;
        if (!contentM.endsWith(dot)) contentM += dot;

        return titleM + " " + contentM;
    }

    private void displayNote(String noteTitle, String noteContent) {
        final int hintColor = getResources().getColor(R.color.secondary);

        if (noteTitle.equals(getString(R.string.title))) {
            title.setHint(noteTitle);
            title.setHintTextColor(hintColor);
        } else {
            title.setText(noteTitle);
        }

        if (noteContent.equals(getString(R.string.content))) {
            content.setHint(noteContent);
            content.setHintTextColor(hintColor);
        } else {
            content.setText(noteContent);
        }
    }

    private void saveData(Note note) {
        if (note == null) return;

        boolean noteAlreadyInsideArray = false;
        if (!mNoteArrayList.isEmpty()) {
            for (int i = 0; i < mNoteArrayList.size(); i++) {
                if (note.getNoteID().equals(mNoteArrayList.get(i).getNoteID())) {
                    noteAlreadyInsideArray = true;
                    mNoteArrayList.get(i).update(title.getText().toString(), content.getText().toString());
                }
            }
        }

        if (!noteAlreadyInsideArray) {
            note.update(title.getText().toString(), content.getText().toString());
            mNoteArrayList.add(note);
        }

        SharedPrefHandler.setCurrentNote(getContext(), note);
        SharedPrefHandler.saveArrayInPref(getContext(), mNoteArrayList);
        NoteList.updateListView();
    }
}