package xyz.fcr.notemaker;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Note {
    private String noteTitle;
    private String noteContent;
    private String notePreviewContent;
    private String noteDate;
    private String noteTime;


    public Note(String noteTitle, String noteContent){
        this.noteTitle = noteTitle;
        this.noteContent = noteContent;
        this.notePreviewContent = shortenContent(noteContent);
        this.noteDate = dateUpdate();
        this.noteTime = timeUpdate();
    }

    private String shortenContent(String noteContent) {
        int sizeOfStringToReturn = 30;

        if (noteContent == null){
            return "";
        }

        if (noteContent.length() < 30) sizeOfStringToReturn = noteContent.length();

        return noteContent.substring(0, sizeOfStringToReturn);
    }

    private String dateUpdate() {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        return formatter.format(date);
    }

    private String timeUpdate() {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        Date date = new Date();
        return formatter.format(date);
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public String getNoteContent() {
        return noteContent;
    }

    public String getNotePreviewContent() {
        return notePreviewContent;
    }

    public String getNoteDate() {
        return noteDate;
    }

    public String getNoteTime() {
        return noteTime;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public void setNoteContent(String noteContent) {
        this.noteContent = noteContent;
    }

    public void setNotePreviewContent(String notePreviewContent) {
        this.notePreviewContent = notePreviewContent;
    }

    public void setNoteDate(String noteDate) {
        this.noteDate = noteDate;
    }

    public void setNoteTime(String noteTime) {
        this.noteTime = noteTime;
    }
}
