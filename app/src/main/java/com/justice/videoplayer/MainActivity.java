package com.justice.videoplayer;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    private GestureDetector gestureDetector;
    private static final String TAG = "MainActivity";
    //widgets//
    private ProgressBar progressBar;
    private VideoView videoView;

    private List<Uri> uriList; //stores online urls

    public int nextSong;//determines currently playing song

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initWidgets();
        addVideosToList();
        setUpVideoView();
        setOnSwipeListener();
    }

    private void initWidgets() {
        progressBar = findViewById(R.id.progressBar);
        videoView = findViewById(R.id.videoView);

    }

    private void addVideosToList() {
        String video_1 = getString(R.string.video_1);
        String video_2 = getString(R.string.video_2);
        String video_3 = getString(R.string.video_3);
        String video_4 = getString(R.string.video_4);
        uriList = new ArrayList<>();
        uriList.add(Uri.parse(video_1));
        uriList.add(Uri.parse(video_2));
        uriList.add(Uri.parse(video_3));
        uriList.add(Uri.parse(video_4));

    }

    public void setUpVideoView() {
        videoView.setVideoURI(uriList.get(nextSong));
        progressBar.setVisibility(View.VISIBLE);
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                progressBar.setVisibility(View.GONE);
                videoView.start();


            }
        });

    }

    private void setOnSwipeListener() {
        videoView.setOnTouchListener(this);
        gestureDetector = new GestureDetector(this, new OnSwipeListener(this));

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return true;
    }

    ///Handles the gestures when user swipes on the screen
    public void onSwipe(OnSwipeListener.Direction direction) {

        Log.d(TAG, "onSwipe: ");

        if (direction == OnSwipeListener.Direction.up) {

            //CHOOSE RANDOM VIDEO BETWEEN THE TWO VIDEOS ABOVE THE THE PLAYING VIDEO
            if (nextSong == 2) {
                nextSong = 3;
            } else {
                nextSong = 2;
            }
            setUpVideoView();
        } else if (direction == OnSwipeListener.Direction.down) {


            //CHOOSE RANDOM VIDEO BETWEEN THE TWO VIDEOS BELOW THE THE PLAYING VIDEO
            if (nextSong == 0) {
                nextSong = 1;
            } else {
                nextSong = 0;
            }
            setUpVideoView();


        } else if (direction == OnSwipeListener.Direction.right) {
            startActivity(new Intent(this, ProfileActivity.class));
        } else if (direction == OnSwipeListener.Direction.left) {
            View view = LayoutInflater.from(this).inflate(R.layout.toast_image, (ViewGroup) findViewById(R.id.linearLayout), false);
            Toast toast = Toast.makeText(this, null, Toast.LENGTH_SHORT);
            toast.setView(view);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (videoView != null) {
            videoView.stopPlayback();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (videoView != null) {
            videoView.resume();
        }

    }

}
