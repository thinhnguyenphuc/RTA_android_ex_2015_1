package com.example.rta_demo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.example.rta_demo.ListViewProcess.ListViewAdapter;
import com.example.rta_demo.ProcessFile.ProcessWithSQL;
import com.example.rta_demo.R;

import java.util.ArrayList;

public class ReviewData extends AppCompatActivity {
    ListViewAdapter listViewAdapter;
    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_data);




        ProcessWithSQL db = new ProcessWithSQL(this);
        ArrayList<fileModel> tmp =  db.allFile();

        listViewAdapter = new ListViewAdapter(tmp);

        listView = findViewById(R.id.listView);
        listView.setAdapter(listViewAdapter);

    }
}