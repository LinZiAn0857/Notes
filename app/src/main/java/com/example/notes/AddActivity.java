package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class AddActivity extends AppCompatActivity {
    ImageView btnBack;
    EditText edTitle,edContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        findView();
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AddActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private void findView(){
        edTitle=findViewById(R.id.edTitle);
        edContent=findViewById(R.id.edContent);
        btnBack=findViewById(R.id.btnBack);
    }

    @Override
    protected void onPause() {
        super.onPause();
//        Log.d("show_msg","Add_onPause");
        DB myDB = new DB(AddActivity.this);
        if(edTitle.getText().toString().equals("") && edContent.getText().toString().equals("")){
        }else {
            myDB.addNotes(edTitle.getText().toString().trim(),
                    edContent.getText().toString().trim());
        }

    }

}