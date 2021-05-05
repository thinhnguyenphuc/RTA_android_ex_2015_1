package com.example.rta_demo.ProcessFile;

import com.example.rta_demo.File_Model;

import java.util.ArrayList;

public interface AsyncResponse {
    void processXMLFinish(ArrayList<File_Model> File_Models);
}
