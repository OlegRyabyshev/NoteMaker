package xyz.fcr.notemaker.object_classes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import xyz.fcr.notemaker.R;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    private ArrayList<Note> mNoteList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static class NoteViewHolder extends RecyclerView.ViewHolder {
        public TextView mTitleNote;
        public TextView mContentNote;
        public TextView mTimeNote;
        public TextView mDateNote;

        public NoteViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);

            mTitleNote = itemView.findViewById(R.id.note_title_preview);
            mContentNote = itemView.findViewById(R.id.note_content_preview);
            mTimeNote = itemView.findViewById(R.id.note_time_preview);
            mDateNote = itemView.findViewById(R.id.note_date_preview);

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION){
                        listener.onItemClick(position);
                    }
                }
            });
        }
    }

    public NoteAdapter(ArrayList<Note> noteList) {
        mNoteList = noteList;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false);
        return new NoteViewHolder(v, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note currentNote = mNoteList.get(position);
        holder.mTitleNote.setText(currentNote.getNotePreviewTitle());
        holder.mContentNote.setText(currentNote.getNotePreviewContent());
        holder.mTimeNote.setText(currentNote.getNoteTime());
        holder.mDateNote.setText(currentNote.getNoteDate());
    }

    @Override
    public int getItemCount() {
        return mNoteList.size();
    }
}
