package com.example.lab07notesplusomelko;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView lvNote;
    ArrayList<mynote> lst = new ArrayList<mynote>();
    ArrayAdapter<mynote> adp;
    Context ctx;
    void updateList(){
        lst.clear();
        g.notes.getAllNote(lst);
        adp.notifyDataSetChanged();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
       /* if (data == null && requestCode==001){
            adp.remove(adp.getItem(adp.getCount()-1));
            adp.notifyDataSetChanged();
        }
        else{

        }*/
        updateList();
        super.onActivityResult(requestCode,resultCode,data);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ctx=this;
        g.notes= new DB(this, "notes.db", null,1);
        lvNote = findViewById(R.id.lvNotes);
        adp= new ArrayAdapter<mynote>(this, android.R.layout.simple_list_item_1,lst);
        lvNote.setAdapter(adp);
        lvNote.setOnItemClickListener((parent, view, position, id) -> {
            mynote n = adp.getItem(position);
            Intent i = new Intent(ctx,NoteActivity.class);
            i.putExtra("noteID",n.ID);
            i.putExtra("noteContent", n.Content);
            startActivityForResult(i,001);
        });
        updateList();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        //return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu1,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        int id = item.getItemId();
        switch (id){
            case R.id.itmNew:{
                int nid = g.notes.getMaxID()+1;
                g.notes.addNone(nid,"Hello, world!");
                updateList();
                return true;
              //      Toast toast = Toast.makeText(getApplicationContext(), "Error of database", Toast.LENGTH_SHORT);
              //      toast.show();
            }
        }
        return super.onOptionsItemSelected(item);
    }

}