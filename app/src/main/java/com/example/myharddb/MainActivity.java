package com.example.myharddb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private DBHelper helper;
    private EditText nameInput;
    private EditText ageInput;
    private ListView usersList;
    private SimpleCursorAdapter usersAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nameInput = findViewById(R.id.etName);
        ageInput = findViewById(R.id.etAge);
        usersList = findViewById(R.id.lvData);
        helper = new DBHelper(this);
    }

    public void addData(View view) {
        String name = nameInput.getText().toString();
        if (!name.isEmpty() && !ageInput.getText().toString().isEmpty()) {
            int age = Integer.parseInt(ageInput.getText().toString());
            SQLiteDatabase db = helper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(DBHelper.KEY_NAME, name);
            values.put(DBHelper.KEY_AGE, age);
            db.insert(DBHelper.TABLE_USERS, null, values);
        }
        else {
            Toast.makeText(getApplicationContext(),"Введите данные", Toast.LENGTH_SHORT).show();
        }
    }

    public void showData(View view) {
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor query = db.query(DBHelper.TABLE_USERS, null, null, null, null, null, null);
        usersAdapter = new SimpleCursorAdapter(this, android.R.layout.two_line_list_item, query,
                new String[]{DBHelper.KEY_NAME, DBHelper.KEY_AGE}, new int[] {android.R.id.text1, android.R.id.text2}, 0);
        usersList.setAdapter(usersAdapter);
    }

    public void deleteData(View view) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete(DBHelper.TABLE_USERS, null, null);
    }
}