package xyz.fcr.notemaker.fragment_classes;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import xyz.fcr.notemaker.object_classes.SharedPrefHandler;
import xyz.fcr.notemaker.object_classes.Note;
import xyz.fcr.notemaker.R;
import xyz.fcr.notemaker.object_classes.NoteAdapter;

public class NoteList extends Fragment {

    public static ArrayList<Note> mNoteArrayList;

    private RecyclerView mRecyclerView;
    public static NoteAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Fragment fragment;

    public NoteList() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mNoteArrayList = SharedPrefHandler.getArrayFromPref(getContext());

        View myView = inflater.inflate(R.layout.note_list_fragment, container, false);
        buildRecycleView(myView);

        return myView;
    }

    private void buildRecycleView(View myView) {
        mRecyclerView = myView.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(myView.getContext());
        mAdapter = new NoteAdapter(mNoteArrayList, fragment);

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
}