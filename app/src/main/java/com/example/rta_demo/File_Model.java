package com.example.rta_demo;

import androidx.annotation.NonNull;
import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Delete;
import androidx.room.Entity;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.PrimaryKey;
import androidx.room.Query;
import androidx.room.RoomDatabase;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "fileDB")
public class File_Model {
    @PrimaryKey
    @NonNull private String id;
    private String Name;
    private String Content;
    private String Path;

    public String getId() {
        return id;
    }

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

    public void setId(String id) {
        this.id = id;
    }

    @Dao
    public interface File_Model_DAO {
        @Query("SELECT * FROM fileDB")
        List<File_Model> getAll();

        @Insert
        void insert(File_Model file_model);

        @Query("SELECT * FROM fileDB WHERE id = :id")
        public File_Model getItemById(long id);

        @Query("DELETE FROM fileDB")
        void delete();



    }
    @Database(entities = {File_Model.class}, version = 2)
    public abstract static class AppDatabase extends RoomDatabase {
        public abstract File_Model.File_Model_DAO getDAO();
    }
}
