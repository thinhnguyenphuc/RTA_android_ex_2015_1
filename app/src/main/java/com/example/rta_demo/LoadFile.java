package com.example.rta_demo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.developer.filepicker.controller.DialogSelectionListener;
import com.developer.filepicker.model.DialogConfigs;
import com.developer.filepicker.model.DialogProperties;
import com.developer.filepicker.view.FilePickerDialog;
import com.example.rta_demo.ProcessFile.AsyncResponse;
import com.example.rta_demo.ProcessFile.LoadingData;
import com.example.rta_demo.ProcessFile.ProcessWithSQL;
import com.example.rta_demo.ProcessFile.ThreadForSQL;

import java.io.File;
import java.util.ArrayList;

import java.util.Collections;


public class LoadFile extends AppCompatActivity {

    private ArrayList<String> listOfFile;
    private LoadingData loadingData;
    private ArrayList<fileModel> files =new ArrayList<fileModel>();
    ProcessWithSQL db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_file);


        DialogProperties properties = new DialogProperties();
        properties.selection_mode = DialogConfigs.MULTI_MODE;
        properties.selection_type = DialogConfigs.FILE_SELECT;
        properties.root = new File("mnt/sdcard/data");
        properties.error_dir = new File(DialogConfigs.DEFAULT_DIR);
        properties.offset = new File(DialogConfigs.DEFAULT_DIR);
        properties.extensions = null;
        properties.show_hidden_files = false;
        FilePickerDialog dialog = new FilePickerDialog(LoadFile.this,properties);
        dialog.setTitle("Select a File");
        dialog.setDialogSelectionListener(new DialogSelectionListener() {
            @Override
            public void onSelectedFilePaths(String[] files_tmp) {
                listOfFile = new ArrayList<String>();
                Collections.addAll(listOfFile,files_tmp);
                loadingData = new LoadingData(LoadFile.this, new AsyncResponse() {
                    @Override
                    public void processXMLFinish(ArrayList<fileModel> fileModels) {
                        files=fileModels;
                        db = new ProcessWithSQL(LoadFile.this);
                        ThreadForSQL threadForSQL = new ThreadForSQL(LoadFile.this, db, files);
                        threadForSQL.execute();
                    }
                },listOfFile);
                loadingData.execute();



            }
        });
        dialog.show();


    }
}