package com.example.rta_demo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;

import com.example.rta_demo.ProcessFile.AsyncResponse;
import com.example.rta_demo.ProcessFile.LoadingData;
import com.example.rta_demo.ProcessFile.ProcessWithSQL;
import com.example.rta_demo.ProcessFile.ThreadForSQL;

import java.util.ArrayList;

public class LoadFile_Ver2 extends AppCompatActivity {

    private ArrayList<String> listOfFile = new ArrayList<String>();
    private LoadingData loadingData;
    private ArrayList<File_Model> files =new ArrayList<File_Model>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_file__ver2);


        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType( "*/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(Intent.createChooser(intent, "Open folder"),RequestCode.REQUEST_CODE_OPEN_FILE_MANAGER);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case RequestCode.REQUEST_CODE_OPEN_FILE_MANAGER:{
                if (resultCode == RESULT_OK){
                    if(data.getData()==null){
                        ClipData tmp = data.getClipData();
                        for (int i=0;i<tmp.getItemCount();i++) {
                             listOfFile.add(getPath(tmp.getItemAt(i).getUri()));
                        }
                    } else{
                        listOfFile.add(getPath(data.getData()));
                    }
                }
                Load();
            }
        }

    }

    private void Load(){
        loadingData = new LoadingData(LoadFile_Ver2.this, new AsyncResponse() {
            @Override
            public void processXMLFinish(ArrayList<File_Model> File_Models) {
                files= File_Models;
                File_Model.AppDatabase database = Room.databaseBuilder(LoadFile_Ver2.this,
                        File_Model.AppDatabase.class,
                        "fileDB")
                        .allowMainThreadQueries()
                        .build();
                ThreadForSQL threadForSQL = new ThreadForSQL(LoadFile_Ver2.this, database, files);
                threadForSQL.execute();
            }
        },listOfFile);
        loadingData.execute();
    }

    private String getPath(Uri uri){
        final String docId = DocumentsContract.getDocumentId(uri);
        final String[] split = docId.split(":");

        final String tmp = "storage/"+split[0]+"/"+split[1];
        return tmp;
    }
}