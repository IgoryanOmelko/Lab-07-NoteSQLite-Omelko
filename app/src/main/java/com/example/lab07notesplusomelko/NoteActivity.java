package com.example.lab07notesplusomelko;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
public class NoteActivity extends AppCompatActivity{
    EditText etContent;
    int nid;
    String content;
    Context cts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        etContent= findViewById(R.id.etContent);
        Intent i = getIntent();
        nid=i.getIntExtra("noteID",0);
        content = i.getStringExtra("noteContent");
        etContent.setText(content);
        cts=this;
        //etContent.setText(String.valueOf(nid));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        //return super.onCtreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu2,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        int id = item.getItemId();//id item in menu list
        switch (id){
            case R.id.itmSave:{
                content=etContent.getText().toString();
                if (!g.notes.saveNote(nid,content)){
                    Toast.makeText(this,R.string.ErrorDel, Toast.LENGTH_SHORT).show();
                    return false;
                }
                finish();
                return true;
            }
            case R.id.itmDelete:{
                DialogWindowDelete();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
    public void DialogWindowDelete(){ //created by Igor Omelko
        LayoutInflater myLayout = LayoutInflater.from(this);
        View dialogView = myLayout.inflate(R.layout.dialogwindow, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        AlertDialog dlg = builder.create();
        dlg.show();
        Button btnOK =dlg.findViewById(R.id.btnOk);
        Button btnCancel =dlg.findViewById(R.id.btnCancel);
        btnOK.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                if (!g.notes.delNote(nid)){
                    Toast.makeText(cts,R.string.ErrorDel, Toast.LENGTH_SHORT).show();
                    return;
                }
                finish();
                dlg.cancel ();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                dlg.cancel ();
            }
        });
    }
}
