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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import xyz.fcr.notemaker.object_classes.Note;
import xyz.fcr.notemaker.R;
import xyz.fcr.notemaker.object_classes.NoteAdapter;

public class NoteList extends Fragment {

    private ArrayList<Note> mNoteArrayList;

    private RecyclerView mRecyclerView;
    private NoteAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public NoteList() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        createNoteList();
        View myView = inflater.inflate(R.layout.note_list_fragment, container, false);
        buildRecycleView(myView);

        return myView;
    }

    private void createNoteList() {
        mNoteArrayList = new ArrayList<>();
        mNoteArrayList.add(new Note(getResources().getString(R.string.note_title_1), getResources().getString(R.string.note_inside_1)));
        mNoteArrayList.add(new Note(getResources().getString(R.string.note_title_2), getResources().getString(R.string.note_inside_2)));
        mNoteArrayList.add(new Note(getResources().getString(R.string.note_title_3), getResources().getString(R.string.note_inside_3)));
    }

    private void buildRecycleView(View myView) {
        mRecyclerView = myView.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(myView.getContext());
        mAdapter = new NoteAdapter(mNoteArrayList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(position -> replaceView(mNoteArrayList.get(position)));
    }

    private void replaceView(Note note) {
        NoteEditor fragment = new NoteEditor();

        Bundle bundle = new Bundle();
        bundle.putSerializable("note_id", note);
        fragment.setArguments(bundle);

        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
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