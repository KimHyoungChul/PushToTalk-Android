package com.unearth.pushtotalk;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;

import com.unearth.pushtotalk.util.DateTimeUtil;
import com.unearth.pushtotalk.util.FileUtil;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    // Requesting permission to RECORD_AUDIO
    private boolean permissionGranted = true;
    private String[] permissions = {
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE};

    private static final int REQUEST_PERMISSIONS_CODE = 200;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(this, permissions, REQUEST_PERMISSIONS_CODE);
        FloatingActionButton fabRecordButton = (FloatingActionButton) findViewById(R.id.fabRecordAudio);

    }


    private void makeDirectories() {
        FileUtil.createAppDirectory(getString(R.string.app_name));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_PERMISSIONS_CODE:
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED)
                        permissionGranted = false;
                }
                break;
        }
        if (!permissionGranted) finish();
        else
            initMainActivity();
    }

    private void initMainActivity() {
        makeDirectories();
    }
}
