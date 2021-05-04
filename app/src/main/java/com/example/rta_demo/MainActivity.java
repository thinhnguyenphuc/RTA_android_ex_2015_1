package com.example.rta_demo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.rta_demo.ProcessFile.ProcessWithSQL;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity {
    private static final String permission_read = Manifest.permission.READ_EXTERNAL_STORAGE;
    private static final String permission_write = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    private static final String permission_manager = Manifest.permission.MANAGE_EXTERNAL_STORAGE;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestPermission();

        Button importFile = findViewById(R.id.buttonImport);

        importFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loadFile = new Intent(MainActivity.this, LoadFile.class);
                startActivityForResult(loadFile,RequestCode.REQUEST_CODE_LOAD_FILE);
            }
        });

        Button reviewFile = findViewById(R.id.buttonReview);

        reviewFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent reviewData = new Intent(MainActivity.this, ReviewData.class);
                startActivityForResult(reviewData,RequestCode.REQUEST_CODE_REVIEW_DATA);
            }
        });

        Button clear = findViewById(R.id.buttonClear);

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProcessWithSQL db = new ProcessWithSQL(MainActivity.this);
                db.clear();
                Toast toast = Toast.makeText(MainActivity.this, "Done", Toast.LENGTH_LONG);
            }
        });
    }


    private void requestPermission() {
        String[] perms = {permission_read,permission_write,permission_manager};
        if (!EasyPermissions.hasPermissions(this, perms)) {
            EasyPermissions.requestPermissions(this,"Must allow to use this app",
                    RequestCode.REQUEST_CODE_PERMISSION,perms);
        }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
}

