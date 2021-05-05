package com.example.rta_demo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.widget.ListView;

import com.example.rta_demo.listViewProcess.ListViewAdapter;
import com.example.rta_demo.ProcessFile.ProcessWithSQL;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ReviewData extends AppCompatActivity {
    ListViewAdapter listViewAdapter;
    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_data);




        File_Model.AppDatabase database = Room.databaseBuilder(ReviewData.this,
                File_Model.AppDatabase.class,
                "fileDB")
                .allowMainThreadQueries()
                .build();
        File_Model.File_Model_DAO fileModelDao = database.getDAO();
        List<File_Model> data =  fileModelDao.getAll();

        ArrayList<File_Model> tmp = new ArrayList<>(data);
        listViewAdapter = new ListViewAdapter(tmp);

        listView = findViewById(R.id.listView);
        listView.setAdapter(listViewAdapter);

    }
}