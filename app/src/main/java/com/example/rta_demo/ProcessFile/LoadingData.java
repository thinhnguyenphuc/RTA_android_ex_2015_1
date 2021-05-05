package com.example.rta_demo.ProcessFile;



import android.app.Activity;

import android.os.AsyncTask;

import android.os.Environment;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.example.rta_demo.FilePath;

import com.example.rta_demo.R;
import com.example.rta_demo.File_Model;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import java.nio.channels.FileChannel;
import java.util.ArrayList;

public class LoadingData extends AsyncTask<Void, Integer, ArrayList<File_Model>> {

    private Activity context;
    public AsyncResponse delegate = null;
    private ArrayList<String> input;

    public LoadingData(Activity context, AsyncResponse asyncResponse,ArrayList<String> input){
        this.context = context;
        this.delegate = asyncResponse;
        this.input = input;
    }


    @Override
    protected ArrayList<File_Model> doInBackground(Void... voids) {
        ArrayList<File_Model> File_Models = new ArrayList<File_Model>();
        int percent = 100/input.size();
        for (int i = 0; i < input.size(); i++) {

            publishProgress(percent*(i+1));
            try {
                copy(input.get(i), FilePath.fileOutSrc);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                File_Models.add(loadFile(input.get(i)));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }

        }
        return File_Models;
    }

    @Override
    protected void onPostExecute(ArrayList<File_Model> File_Models) {
        delegate.processXMLFinish(File_Models);
    }

    private File_Model loadFile(String path) throws IOException, XmlPullParserException {
        File_Model file = new File_Model();
        XmlPullParserFactory fc=XmlPullParserFactory.newInstance();
        XmlPullParser parser= fc.newPullParser();
        String[] tmp=path.split("/");
        String xmlfile = Environment.getExternalStorageDirectory()+ "/data/"+tmp[tmp.length-1];
        FileInputStream fIn=new FileInputStream(xmlfile);
        parser.setInput(fIn,"UTF-8");
        int eventType=-1;
        String nodeName;
        while(eventType!=XmlPullParser.END_DOCUMENT)
        {
            eventType=parser.next();
            switch(eventType)
            {
                case XmlPullParser.START_DOCUMENT:
                case XmlPullParser.END_DOCUMENT:
                case XmlPullParser.END_TAG:
                    break;
                case XmlPullParser.START_TAG:
                    nodeName=parser.getName();
                    if(nodeName.equals("instanceID")){
                        file.setContent(parser.nextText());
                    }
                    break;
            }
        }
        File in = new File(path);
        file.setName(in.getName());
        file.setPath(FilePath.fileOutSrc+"/"+in.getName());
        file.setId(file.getName());
        return  file;
    }


    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        ProgressBar progressBar = (ProgressBar) context.findViewById(R.id.prbDemo_ver2);
        int number = values[0];
        progressBar.setProgress(number);
        TextView textView = (TextView) context.findViewById(R.id.txtStatus_ver2);
        textView.setText(number + "%");
    }



    public void copy(String src, String dst) throws IOException {
        File in = new File(src);
        File out = new File(dst+"/"+in.getName());
        FileInputStream inStream = new FileInputStream(in);
        File folder = new File(dst);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        if(!out.exists()){
            out.createNewFile();
        }
        FileOutputStream outStream = new FileOutputStream(out);
        FileChannel inChannel = inStream.getChannel();
        FileChannel outChannel = outStream.getChannel();
        inChannel.transferTo(0, inChannel.size(), outChannel);
        inStream.close();
        outStream.close();
    }
}
