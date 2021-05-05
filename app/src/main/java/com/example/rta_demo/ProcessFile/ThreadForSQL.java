package com.example.rta_demo.ProcessFile;

import android.app.Activity;
import android.os.AsyncTask;

import com.example.rta_demo.File_Model;

import java.util.ArrayList;

public class ThreadForSQL extends AsyncTask<Void, Integer, Void> {

    private Activity context;
    private File_Model.AppDatabase db;
    private ArrayList<File_Model> files;
    public ThreadForSQL(Activity context, File_Model.AppDatabase db, ArrayList<File_Model> files){
        this.context = context;
        this.db = db;
        this.files = files;


    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... voids) {

        for(int i=0;i<files.size();i++){
            File_Model.File_Model_DAO fileModelDao = db.getDAO();
            File_Model tmp = fileModelDao.getItemById(i);
            if(tmp==null){
                fileModelDao.insert(files.get(i));
            }
            onProgressUpdate(i);
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}
