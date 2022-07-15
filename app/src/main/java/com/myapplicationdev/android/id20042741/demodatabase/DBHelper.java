package com.myapplicationdev.android.id20042741.demodatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "simplenotes.db";
    private static final int DATABASE_VERSION = 2;
    private static final String TABLE_NOTE = "note";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NOTE_CONTENT = "note_content";

    public DBHelper (Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createNoteTablSQL = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                "%s TEXT ) ",TABLE_NOTE, COLUMN_ID, COLUMN_NOTE_CONTENT);

        db.execSQL(createNoteTablSQL);
        Log.i("info", "created tables");

        // dummy records
        ContentValues values = new ContentValues();
        for (int i = 0; i < 4; i++){
            values.put(COLUMN_NOTE_CONTENT, "Data number: " + i);
            db.insert(TABLE_NOTE, null, values);
        }
        Log.i("info", "dummy records inserted");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTE);
        //onCreate(db);

        db.execSQL("ALTER TABLE " + TABLE_NOTE + " ADD COLUMN module_name TEXT ");
    }

    public long insertNote(String noteContent){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOTE_CONTENT, noteContent);
        long result = db.insert(TABLE_NOTE, null, values);
        db.close();
        Log.d("SQL INSERT", "ID: " + result); // if id return is -1, means insert fail
        return result;
    }

    public int updateNote(Note data){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOTE_CONTENT, data.getNoteContent());
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(data.getID())};
        int result = db.update(TABLE_NOTE, values, condition, args);
        db.close();
        return result;
    }

    public int deleteNote(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(id)};
        int result = db.delete(TABLE_NOTE, condition, args);
        db.close();
        return result;
    }

    public ArrayList<Note> getAllNote(){
        ArrayList<Note> notesal = new ArrayList<Note>();
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {COLUMN_ID, COLUMN_NOTE_CONTENT};

        Cursor cursor = db.query(TABLE_NOTE, columns, null, null, null, null, null, null);

        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0); // retrieves value from _id column
                String noteContent = cursor.getString(1); // retrives value from note_content column
                Note note = new Note(id, noteContent); // creates a new object of the Note class using the constructor
                notesal.add(note); // adds the newly created object into the arraylist object
            } while(cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return notesal;

    }

    public ArrayList<Note> getAllNotes(String keyword){
        ArrayList<Note> notes = new ArrayList<Note>();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ID, COLUMN_NOTE_CONTENT};
        String conditions = COLUMN_NOTE_CONTENT + " Like ?";
        String[] args = {"%" + keyword + "%"};
        Cursor cursor = db.query(TABLE_NOTE, columns, conditions, args, null, null, null, null);

        if (cursor.moveToFirst()){
            do {
                int id = cursor.getInt(0);
                String noteContent = cursor.getString(1);
                Note note = new Note(id, noteContent);
                notes.add(note);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return notes;
    }
}
