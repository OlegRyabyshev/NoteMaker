package xyz.fcr.notemaker.fragment_classes;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.button.MaterialButton;

import java.util.Objects;

import xyz.fcr.notemaker.object_classes.Note;
import xyz.fcr.notemaker.R;

import static xyz.fcr.notemaker.fragment_classes.NoteList.mAdapter;
import static xyz.fcr.notemaker.fragment_classes.NoteList.mNoteArrayList;

public class NoteEditor extends Fragment {

    private Note note;
    private TextView title;
    private TextView content;

    public NoteEditor() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.note_editor_fragment, container, false);

        title = view.findViewById(R.id.note_editor_title);
        content = view.findViewById(R.id.note_editor_content);

        title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                saveData(note);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                saveData(note);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        MaterialButton button_share = view.findViewById(R.id.button_share);
        MaterialButton button_copy = view.findViewById(R.id.button_copy);
        MaterialButton button_delete = view.findViewById(R.id.button_delete);

        button_share.setOnClickListener((v) ->
                Toast.makeText(getContext(), "In development", Toast.LENGTH_SHORT).show());

        button_copy.setOnClickListener((v) -> {
            ClipboardManager clipboard = (ClipboardManager) requireActivity().getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("CopiedText", getTextFromNote());
            clipboard.setPrimaryClip(clip);

            Toast.makeText(getContext(), "Copied", Toast.LENGTH_SHORT).show();
        });

        button_delete.setOnClickListener((v) -> {
            if (mNoteArrayList != null) {
                for (int i = 0; i < mNoteArrayList.size(); i++) {
                    if (note.getNoteID().equals(mNoteArrayList.get(i).getNoteID())) {
                        mNoteArrayList.remove(i);
                        break;
                    }
                }
            }
            Objects.requireNonNull(getActivity()).onBackPressed();
        });


        Bundle bundle = getArguments();

        if (bundle != null) {
            if (bundle.containsKey("note_id")) {
                note = (Note) bundle.getSerializable("note_id");
                displayNote(note.getNoteTitle(), note.getNoteContent());
            } else {
                displayNote(getResources().getString(R.string.title), getResources().getString(R.string.content));
            }
        }
        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putSerializable("note_id", note);
        Intent intent = Objects.requireNonNull(getActivity()).getIntent();
        intent.putExtras(outState);
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
        if (title.getText().toString().equals(getResources().getString(R.string.title))) {
            title.setHint(noteTitle);
        } else {
            title.setText(noteTitle);
        }

        if (content.getText().toString().equals(getResources().getString(R.string.content))) {
            content.setHint(noteContent);
        } else {
            content.setText(noteContent);
        }
    }

    private void saveData(Note note) {
        boolean noteAlreadyInsideArray = false;

        for (int i = 0; i < mNoteArrayList.size(); i++) {
            if (note.getNoteID().equals(mNoteArrayList.get(i).getNoteID())) {
                noteAlreadyInsideArray = true;
                mNoteArrayList.get(i).update(title.getText().toString(), content.getText().toString());
            }
        }

        if (!noteAlreadyInsideArray) {
            note.update(title.getText().toString(), content.getText().toString());
            mNoteArrayList.add(note);
        }

        mAdapter.notifyDataSetChanged();
    }
}