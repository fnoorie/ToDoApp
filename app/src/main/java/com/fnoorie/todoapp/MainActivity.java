package com.fnoorie.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> todoItems;
    private ArrayAdapter<String> aToDoAdapter;
    private ListView lvItems;
    private EditText etEditText;
    private final int REQUEST_CODE = 20;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        populateArrayItems();
        lvItems = (ListView) findViewById(R.id.listView);
        etEditText = (EditText) findViewById(R.id.etEditText);
        lvItems.setAdapter(aToDoAdapter);
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                todoItems.remove(position);
                aToDoAdapter.notifyDataSetChanged();
                writeItems();
                return true;
            }
        });
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String taskData = todoItems.get(position);
                launchEditItemActivity(taskData, position);
            }

        });
    }


    public void launchEditItemActivity(String taskData, int position) {
        // first parameter is the context, second is the class of the activity to launch
        Intent i = new Intent(MainActivity.this, EditItemActivity.class);
        i.putExtra("taskData", taskData);
        i.putExtra("position", position);
        startActivityForResult(i, REQUEST_CODE); // brings up the second activity
    }
    public void populateArrayItems() {

        readItems();
        aToDoAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, todoItems);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onAddItem(View view) {
        String text = etEditText.getText().toString();
        aToDoAdapter.add(text);
//        todoItems.add(text);
//        aToDoAdapter.notifyDataSetChanged();
        etEditText.setText("");
        writeItems();
    }

    private void readItems(){
        File filesDir = getFilesDir();
        File file = new File(filesDir, "todo.txt");
        try{
            todoItems = new ArrayList<String>(FileUtils.readLines(file));
        }
        catch(IOException e){
            todoItems = new ArrayList<String>();
        }

    }

    private void writeItems(){
        File filesDir = getFilesDir();
        File file = new File(filesDir, "todo.txt");
        try{
           FileUtils.writeLines(file, todoItems);
        }
        catch(IOException e){
            e.printStackTrace();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // REQUEST_CODE is defined above
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            // Extract name value from result extras
            String taskData = data.getExtras().getString("taskData");
            int position = data.getExtras().getInt("position",0);
            System.out.print(etEditText.getText().toString());
            todoItems.set(position, taskData);
            aToDoAdapter.notifyDataSetChanged();
            writeItems();
        }
    }
}
