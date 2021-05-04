package com.example.rta_demo.ProcessFile;

import com.example.rta_demo.fileModel;

import java.util.ArrayList;

public interface AsyncResponse {
    void processXMLFinish(ArrayList<fileModel> fileModels);
}
