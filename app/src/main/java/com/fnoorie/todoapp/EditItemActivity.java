package com.fnoorie.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {

    private EditText etEditText;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        String taskData = getIntent().getStringExtra("taskData");
        position = getIntent().getIntExtra("position",0);
        etEditText = (EditText) findViewById(R.id.editText);
        etEditText.setText(taskData);
        etEditText.setSelection(etEditText.getText().length());
    }

    public void onTextSave(View view) {
        // Prepare data intent
        Intent data = new Intent();
        // Pass relevant data back as a result
        data.putExtra("taskData", etEditText.getText().toString());
        System.out.print(etEditText.getText().toString());
        data.putExtra("position", position); // ints work too
        // Activity finished ok, return the data
        setResult(RESULT_OK, data); // set result code and bundle data for response
        finish(); // closes the activity, pass data to parent
    }
}
