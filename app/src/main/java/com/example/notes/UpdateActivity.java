package com.example.notes;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity {
    String id, title, content;
    ImageView btnUpBack,btnDelete;
    EditText edUpTitle,edUpContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        findView();
        getAndSetIntentData();
        btnUpBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(UpdateActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirDialog();
            }
        });
    }
    private void findView(){
        edUpTitle=findViewById(R.id.edUpTitle);
        edUpContent=findViewById(R.id.edUpContent);
        btnUpBack=findViewById(R.id.btnUpBack);
        btnDelete=findViewById(R.id.btnDelete);
    }
    private void getAndSetIntentData() {
        if (getIntent().hasExtra("Key_id")&&
                getIntent().hasExtra("Key_title")&&
                getIntent().hasExtra("Key_content")
        ){
            id = getIntent().getStringExtra("Key_id");
            title = getIntent().getStringExtra("Key_title");
            content = getIntent().getStringExtra("Key_content");
//            Log.d("show_msg",id+":"+title+":"+content);
            edUpTitle.setText(title);
            edUpContent.setText(content);
        }else{
            Toast.makeText(this,"no data",Toast.LENGTH_SHORT).show();
        }
    }
    private void confirDialog() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Delete"+title+"?");
        builder.setMessage("Are you sure you want to delete "+title+" ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                Log.d("show_msg","confirmDialogButton");
                DB myDB = new DB(UpdateActivity.this);
                myDB.deleteOneRow(id);

                Intent intent = new Intent(UpdateActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }
    @Override
    protected void onPause() {
        super.onPause();
//        Log.d("show_msg","Add_onPause");
        DB db=new DB(UpdateActivity.this);
        db.updateData(id,edUpTitle.getText().toString(),edUpContent.getText().toString());
    }
}