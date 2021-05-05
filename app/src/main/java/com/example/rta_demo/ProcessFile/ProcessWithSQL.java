package com.example.rta_demo.ProcessFile;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.rta_demo.File_Model;

import java.util.ArrayList;

public class ProcessWithSQL extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "DB";
    private static final String TABLE_NAME = "xmlFile";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_CONTENT = "content";
    private static final String KEY_PATH = "path";
    private static final String[] COLUMNS = { KEY_ID, KEY_NAME, KEY_CONTENT, KEY_PATH };
    public ProcessWithSQL(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATION_TABLE = "CREATE TABLE xmlFile ( "
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, " + "name TEXT, "+ "content TEXT, "
                + "path TEXT )";

        db.execSQL(CREATION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        this.onCreate(db);
    }

    public ArrayList<File_Model> allFile() {

        ArrayList<File_Model> files = new ArrayList<File_Model>();
        String query = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        File_Model file = null;

        if (cursor.moveToFirst()) {
            do {
                file = new File_Model();
                file.setName(cursor.getString(1));
                file.setContent(cursor.getString(2));
                file.setPath(cursor.getString(3));
                files.add(file);
            } while (cursor.moveToNext());
        }
        return files;
    }
    public void addFile(File_Model file) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, file.getName());
        values.put(KEY_CONTENT, file.getContent());
        values.put(KEY_PATH,file.getPath());
        db.insert(TABLE_NAME,null, values);
        db.close();
    }

    public void clear() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_NAME);
        db.close();
    }


}
