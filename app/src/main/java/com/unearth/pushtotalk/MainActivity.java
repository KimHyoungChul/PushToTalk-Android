package com.unearth.pushtotalk;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.widget.Toast;
import com.unearth.pushtotalk.data.source.AudioModel;
import com.unearth.pushtotalk.presenter.MainScreen.MainScreenContract;
import com.unearth.pushtotalk.presenter.MainScreen.MainScreenPresenter;
import com.unearth.pushtotalk.util.DateTimeUtil;
import com.unearth.pushtotalk.util.FileUtil;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements MainScreenContract.View {

    private boolean permissionGranted = true;

    private String[] permissions = {
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE};

    private static final int REQUEST_PERMISSIONS_CODE = 200;

    private MainScreenContract.Presenter mPresenter;

    private MainScreenPresenter mMainScreenPresenter;
    private FloatingActionButton mFabRecordButton;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(this, permissions, REQUEST_PERMISSIONS_CODE);

        mFabRecordButton = findViewById(R.id.fabRecordAudio);

        mMainScreenPresenter = new MainScreenPresenter(
                Injection.provideAudioFileRepository(getApplicationContext()),
                this,
                new MediaManager());

        mFabRecordButton.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                mPresenter.startRecording(FileUtil.getNewAudioFileName(getString(R.string.app_name)
                        , DateTimeUtil.millisecondsToDateForFileName(System.currentTimeMillis())));
                showSnackBar(R.string.recording_started);
            } else if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
                mPresenter.stopRecording();
                showSnackBar(R.string.recording_stopped);
            }
            return true;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.subscribe();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPresenter.unsubscribe();
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


    @Override
    public void setPresenter(MainScreenContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showListOfAudioFiles(ArrayList<AudioModel> audioModelArrayList) {
        //TODO:bind data to a recyclerview.
    }

    @Override
    public void onNetworkError(int errorMessageId) {
        showSnackBar(errorMessageId);
    }

    @Override
    public void uploadSuccessFull(AudioModel audioModel) {
        //TODO:invalidate recyclerview item
        showToast(R.string.file_uploaded_successfully);
    }

    @Override
    public void onNetworkError(Throwable throwable) {
        showToast(throwable.getMessage());
    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void showToast(int stringId) {
        Toast.makeText(getApplicationContext(), stringId, Toast.LENGTH_SHORT).show();
    }

    public void showSnackBar(int stringId) {
        Snackbar.make(mFabRecordButton, stringId, Snackbar.LENGTH_SHORT).show();
    }
}
