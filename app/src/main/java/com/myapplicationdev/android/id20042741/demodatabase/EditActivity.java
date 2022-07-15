package com.myapplicationdev.android.id20042741.demodatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EditActivity extends AppCompatActivity {
    Button btnUpdate, btnDelete;
    EditText etContent;
    TextView tvID, tvContent;
    Note data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        btnUpdate = findViewById(R.id.btnUpdate2);
        btnDelete = findViewById(R.id.btnDelete2);
        etContent = findViewById(R.id.etContent2);
        tvID = findViewById(R.id.tvID2);
        tvContent = findViewById(R.id.tvContent2);

        Intent i = getIntent();

        data = (Note) i.getSerializableExtra("data");
        tvID.setText("ID: " + data.getID());
        etContent.setText(data.getNoteContent());

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(EditActivity.this);
                data.setNoteContent(etContent.getText().toString());
                dbh.updateNote(data);
                dbh.close();
                Toast.makeText(EditActivity.this, "Updated!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper db = new DBHelper(EditActivity.this);
                db.deleteNote(data.getID());
                finish();
            }
        });

    }
}