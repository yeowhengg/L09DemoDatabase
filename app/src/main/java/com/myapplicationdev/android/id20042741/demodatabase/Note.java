package com.myapplicationdev.android.id20042741.demodatabase;

import java.io.Serializable;

public class Note implements Serializable {

    private int id;
    private String noteContent;

    public Note(int id, String noteContent){
        this.noteContent = noteContent;
        this.id = id;
    }

    public int getID(){
        return this.id;
    }

    public String getNoteContent(){
        return this.noteContent;
    }

    public void setNoteContent(String noteContent){
        this.noteContent = noteContent;
    }

    @Override
    public String toString(){ return "ID:" + id + ", " + noteContent; }
}
