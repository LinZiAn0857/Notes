package com.example.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    DB db;
    List<DataClass> dataList;
    DataClass dataClass;
    RecyclerView recyclerView;
    TextView emptyTextView;
    FloatingActionButton mainAddButton;
    CustomAdapter customAdapter;
    ImageView btnDeleteAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();
        setOnClickListener();
        db = new DB(MainActivity.this);
//        db.addNotes("測試0218", "測試");
        dataList = new ArrayList<>();
        storeDataInArrays();
//
        customAdapter = new CustomAdapter(MainActivity.this, MainActivity.this, dataList);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

    }

    private void findView() {
        recyclerView = findViewById(R.id.recyclerview);
        emptyTextView = findViewById(R.id.emptyTextView);
        mainAddButton = findViewById(R.id.mainAddButton);
        btnDeleteAll=findViewById(R.id.btnDeleteAll);
    }
    private void setOnClickListener(){
        mainAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.d("show_msg","main_add_button");
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });
        btnDeleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirDialog();
            }
        });
    }
    private void storeDataInArrays() {
        dataList.clear();
        Cursor cursor = db.readAllData();//Cursor可稱為資料指標，要查詢某一筆紀錄必須將Cursor指標指到它，才能讀取其內容，以下方法可移動Cursor指標
//        cursor.moveToNext();
//        Log.d("show_msg", String.valueOf(cursor.getCount()));
//        Log.d("show_msg", String.valueOf(cursor.getString(0)));
//        Log.d("show_msg", String.valueOf(cursor.getString(1)));
//        Log.d("show_msg", String.valueOf(cursor.getString(3)));
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
            emptyTextView.setVisibility(View.VISIBLE);
        } else {
            while (cursor.moveToNext()) {
                dataClass = new DataClass(cursor.getString(0),cursor.getString(1), cursor.getString(2));
                dataList.add(dataClass);
            }
//            Log.d("show_msg", dataList.get(0).getNote_title());
//            Log.d("show_msg", dataList.get(0).getNote_content());
        }
    }


    private void confirDialog() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Delete ALL");
        builder.setMessage("Are you sure you want to delete Delete all data ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                Log.d("show_msg","confirmDialogButton");
                DB myDB = new DB(MainActivity.this);
                myDB.deleteAllData();

                Intent intent = new Intent(MainActivity.this,MainActivity.class);
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
    protected void onResume() {
        super.onResume();
//        Log.d("show_msg","Main_onResume");
        storeDataInArrays();
        customAdapter = new CustomAdapter(MainActivity.this, MainActivity.this, dataList);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
//        Intent intent=new Intent(MainActivity.this,MainActivity.class);
//        startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("show_msg","Main_onPause");
    }
}
