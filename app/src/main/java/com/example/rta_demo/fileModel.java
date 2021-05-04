package com.example.rta_demo;

public class fileModel {
    private String Name;
    private String Content;
    private String Path;

    public String getName() {
        return Name;
    }

    public String getContent() {
        return Content;
    }

    public String getPath() {
        return Path;
    }

    public void setPath(String path) {
        Path = path;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setContent(String content) {
        Content = content;
    }
}
