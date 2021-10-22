package com.example.myharddb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private DBHelper helper;
    private EditText nameInput;
    private EditText ageInput;
    private ListView usersList;
    private SimpleCursorAdapter usersAdapter;
    private EditText filterInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nameInput = findViewById(R.id.etName);
        ageInput = findViewById(R.id.etAge);
        usersList = findViewById(R.id.lvData);
        filterInput = findViewById(R.id.etFilter);
        filterInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                GetFilteredData(s.toString());
            }
        });
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
        GetFilteredData("");
    }

    public void deleteData(View view) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete(DBHelper.TABLE_USERS, null, null);
    }

    public void GetFilteredData(String filter) {
            SQLiteDatabase db = helper.getWritableDatabase();
            Cursor query = db.query(DBHelper.TABLE_USERS,
                    null,
                    DBHelper.KEY_NAME + " like ?",
                    new String[]{"%" + filter + "%"},
                    null,
                    null,
                    null);
            usersAdapter = new SimpleCursorAdapter(this, R.layout.user_item, query,
                    new String[]{DBHelper.KEY_ID, DBHelper.KEY_NAME, DBHelper.KEY_AGE}, new int[] {R.id.user_id, R.id.user_name, R.id.user_age}, 0);
            usersList.setAdapter(usersAdapter);
    }
}