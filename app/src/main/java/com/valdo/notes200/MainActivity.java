package com.valdo.notes200;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {
    static ArrayList<String> notes=new ArrayList<>();
    static ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        ListView listView=(ListView)findViewById(R.id.listView);


        //Checking if there is any set in shared preferences
        SharedPreferences sharedPreferences= getApplicationContext().getSharedPreferences("com.valdo.notes200", Context.MODE_PRIVATE);
        HashSet<String> set= (HashSet<String>) sharedPreferences.getStringSet("notes",null);

        if(set == null){
            notes.add("Example one");

        }
        else
        {
            notes= new ArrayList<>(set);
        }






         arrayAdapter= new ArrayAdapter(this,android.R.layout.simple_list_item_1,notes);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(getApplicationContext(),EditYourNote.class);
                intent.putExtra("noteId",i);
                startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                final int itemToDelete=i;

               new AlertDialog.Builder(MainActivity.this)
                       .setIcon(android.R.drawable.alert_dark_frame)
                       .setTitle("Are you sure")
                       .setMessage("Do you want to delete this note?")
                       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialogInterface, int i) {
                               notes.remove(itemToDelete);
                               arrayAdapter.notifyDataSetChanged();
                               SharedPreferences sharedPreferences= getApplicationContext().getSharedPreferences("com.valdo.notes200", Context.MODE_PRIVATE);
                               HashSet<String> set= new HashSet<>(MainActivity.notes);
                               sharedPreferences.edit().putStringSet("notes",set).apply();




                           }
                       }
                       )
                       .setNegativeButton("No",null)
                       .show();







                return true;
            }
        });



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
          super.onOptionsItemSelected(item);

        if(item.getItemId() == R.id.add_note){

           Intent intent= new Intent(getApplicationContext(),EditYourNote.class);
           startActivity(intent);
         return true;

        }
        return  false;

    }
}
