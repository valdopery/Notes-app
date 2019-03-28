package com.valdo.notes200;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.util.HashSet;


public class EditYourNote extends AppCompatActivity {
     int noteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_your_note);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

       // Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
       // setSupportActionBar(myToolbar);






        EditText editText=(EditText)findViewById(R.id.editNoteT);
        Intent intent=getIntent();
         int noteId=intent.getIntExtra("noteId",-1);
        if(noteId!=-1){
            editText.setText(MainActivity.notes.get(noteId));

        }else{
            MainActivity.notes.add("");
            noteId=MainActivity.notes.size()-1;
            MainActivity.arrayAdapter.notifyDataSetChanged();


        }


        final int finalNoteId = noteId;
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                 MainActivity.notes.set(finalNoteId,String.valueOf(charSequence));
                 MainActivity.arrayAdapter.notifyDataSetChanged();

                 //Saving the notes permanently in the app
                SharedPreferences sharedPreferences= getApplicationContext().getSharedPreferences("com.valdo.notes200", Context.MODE_PRIVATE);
                HashSet<String> set= new HashSet<>(MainActivity.notes);
                sharedPreferences.edit().putStringSet("notes",set).apply();


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        return super.onCreateOptionsMenu(menu);
    }




}
