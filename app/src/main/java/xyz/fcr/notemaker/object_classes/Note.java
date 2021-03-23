package xyz.fcr.notemaker.object_classes;

import android.annotation.SuppressLint;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class Note implements Serializable {
    private String noteID;
    private String noteTitle;
    private String notePreviewTitle;
    private String noteContent;
    private String notePreviewContent;
    private String noteDate;
    private String noteTime;

    public Note(String noteTitle, String noteContent) {
        this.noteID = UUID.randomUUID().toString();
        this.noteTitle = noteTitle;
        this.notePreviewTitle = shortenContent(noteTitle, 30);
        this.noteContent = noteContent;
        this.notePreviewContent = shortenContent(noteContent, 80);
        this.noteDate = dateInitialSetter();
        this.noteTime = timeInitialSetter();
    }

    private String shortenContent(String noteContent, int sizeOfStringToReturn) {
        if (noteContent == null) return "";

        if (noteContent.length() < 30) {
            return noteContent;
        }

        return noteContent.substring(0, sizeOfStringToReturn).concat("...");
    }

    private String dateInitialSetter() {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        return formatter.format(date);
    }

    private String timeInitialSetter() {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        Date date = new Date();
        return formatter.format(date);
    }

    public void updateDateAndTimeOnSave(){
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
        @SuppressLint("SimpleDateFormat") SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm");

        Date date = new Date();

        this.noteDate = dateFormatter.format(date);
        this.noteTime = timeFormatter.format(date);
    }

    public String getNotePreviewTitle() {
        return notePreviewTitle;
    }

    public String getNoteID() {
        return noteID;
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



    public void setNotePreviewTitle(String notePreviewTitle) {
        this.notePreviewTitle = notePreviewTitle;
    }

    public void setNoteID(String noteID) {
        this.noteID = noteID;
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
