package com.justice.videoplayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.MediaController;
import android.widget.VideoView;

public class TestActivity extends AppCompatActivity {
    private VideoView videoView;

    MediaController mediaController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 3);
        }


        videoView = findViewById(R.id.videoView);

        String LINK = "https://drive.google.com/file/d/1givoGr4_Ql5cCsZHFDDGKF4cgZa6k2Qq/view?usp=sharing";
        playvideo(LINK);

    }

    public void playvideo(String videopath) {
        final VideoView videoView;
        videoView = (VideoView) findViewById(R.id.videoView);
       // videoView.setVideoPath("http://videocdn.bodybuilding.com/video/mp4/62000/62792m.mp4");
        //videoView.setVideoPath("https://www.learningcontainer.com/wp-content/uploads/2020/05/sample-mp4-file.mp4");
      // videoView.setVideoPath("http://techslides.com/demos/sample-videos/small.mp4");
       // videoView.setVideoPath("http://clips.vorwaerts-gmbh.de/VfE_html5.mp4");


        videoView.start();


    }
}