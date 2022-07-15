package com.myapplicationdev.android.id20042741.demodatabase;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button btnInsert, btnEdit, btnRetrieve;
    TextView tvDBContent;
    EditText etContent;
    ArrayList<Note> noteAL;
    ArrayAdapter<Note> noteAA;
    ListView lv;
    int contentID;

    @Override
    protected void onResume() {
        super.onResume();
        btnRetrieve.performClick();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnInsert = findViewById(R.id.btnInsert);
        btnEdit = findViewById(R.id.btnEdit);
        btnRetrieve = findViewById(R.id.btnRetrieve);
        etContent = findViewById(R.id.etContent);
        lv = findViewById(R.id.lvContents);

        noteAL = new ArrayList<Note>();
        noteAA = new ArrayAdapter<Note>(MainActivity.this, android.R.layout.simple_list_item_1, noteAL);
        lv.setAdapter(noteAA);


        btnInsert.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String data = etContent.getText().toString();
                DBHelper db = new DBHelper(MainActivity.this);
                long inserted_id = db.insertNote(data);

                if(inserted_id != -1){
                    noteAL.clear();
                    noteAL.addAll(db.getAllNote());
                    noteAA.notifyDataSetChanged();
                    Toast.makeText(MainActivity.this, "Insert successful", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnRetrieve.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                DBHelper db = new DBHelper(MainActivity.this);
                noteAL.clear();
                //noteAL.addAll(db.getAllNote());
                String filteredText = etContent.getText().toString().trim();
                if(filteredText.length() == 0){ // means if the string does not contain any letter
                    noteAL.addAll(db.getAllNote()); // if no word, we show everything
                }
                else{
                    noteAL.addAll(db.getAllNotes(filteredText)); // if got word, then we show only the data that contains the filtered text
                }
                noteAA.notifyDataSetChanged();
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(MainActivity.this, String.format("Selected ID: %d", noteAL.get((int) id).getID()),Toast.LENGTH_SHORT).show();
                contentID = position;
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(noteAL.size() > 0){
                    Note target = noteAL.get(contentID);
                    Intent i = new Intent (MainActivity.this, EditActivity.class);
                    i.putExtra("data", target);
                    startActivity(i);
                }

            }
        });


    }
}