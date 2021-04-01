package xyz.fcr.notemaker.object_classes;

import android.annotation.SuppressLint;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class Note implements Serializable {
    private final String noteID;
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

        if (noteContent.length() < sizeOfStringToReturn) {
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

    public void update(String newTitle, String newContent){
        noteTitle = newTitle;
        noteContent = newContent;

        notePreviewTitle = shortenContent(noteTitle, 30);
        notePreviewContent = shortenContent(noteContent, 80);

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
}
