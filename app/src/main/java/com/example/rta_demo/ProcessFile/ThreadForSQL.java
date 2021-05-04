package com.example.rta_demo.ProcessFile;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.rta_demo.R;
import com.example.rta_demo.fileModel;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ThreadForSQL extends AsyncTask<Void, Integer, Void> {

    private Activity context;
    private ProcessWithSQL db;
    private ArrayList<fileModel> files;
    public ThreadForSQL(Activity context, ProcessWithSQL db, ArrayList<fileModel> files){
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
        ProcessWithSQL processWithSQL = new ProcessWithSQL(this.context);

        for(int i=0;i<files.size();i++){
            processWithSQL.addFile(files.get(i));
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
