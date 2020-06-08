package com.justice.videoplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.VideoView;

import com.flaviofaria.kenburnsview.KenBurnsView;
import com.universalvideoview.UniversalVideoView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    private GestureDetector gestureDetector;
    private static final String TAG = "MainActivity";
    private ProgressBar progressBar;
    private VideoView videoView;
    private List<Uri> uriList;

    private KenBurnsView video_1;
    private KenBurnsView video_2;
    private KenBurnsView video_3;
    private KenBurnsView video_4;

    private Handler progressHandler = new Handler();
    private Runnable progressRunnable = new Runnable() {
        @Override
        public void run() {
            progressBar.setProgress(videoView.getCurrentPosition());
            progressHandler.postDelayed(this, 3);

        }
    };


    public int nextSong;
    private int songCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //    startActivity(new Intent(this,TestActivity.class));
        initWidgets();
        addVideosToList();
        setUpVideoView();
        setOnSwipeListener();
    }


    private void addVideosToList() {
        String video_1 = "http://videocdn.bodybuilding.com/video/mp4/62000/62792m.mp4";
        String video_2 = "https://www.learningcontainer.com/wp-content/uploads/2020/05/sample-mp4-file.mp4";
        String video_3 = "http://techslides.com/demos/sample-videos/small.mp4";
        String video_4 = "http://clips.vorwaerts-gmbh.de/VfE_html5.mp4";
        uriList = new ArrayList<>();
        uriList.add(Uri.parse(video_1));
        uriList.add(Uri.parse(video_2));
        uriList.add(Uri.parse(video_3));
        uriList.add(Uri.parse(video_4));

    }

    private void setOnSwipeListener() {
        videoView.setOnTouchListener(this);
        gestureDetector = new GestureDetector(this, new OnSwipeListener(this));

    }


    private void initWidgets() {
        progressBar = findViewById(R.id.progressBar);
        videoView = findViewById(R.id.videoView);

        video_1 = findViewById(R.id.video_1);
        video_2 = findViewById(R.id.video_2);
        video_3 = findViewById(R.id.video_3);
        video_4 = findViewById(R.id.video_4);

        video_1.pause();
        video_2.pause();
        video_3.pause();
        video_4.pause();

    }

    public void setUpVideoView() {
        nextSong = songCounter++ % uriList.size(); //go to the next song


        videoView = findViewById(R.id.videoView);
        videoView.setVideoURI(uriList.get(nextSong));

        progressBar.setVisibility(View.VISIBLE);
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                progressBar.setVisibility(View.GONE);
                videoView.start();
                animateVideoThatsPlaying();

            }
        });


      /*  videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {

                videoView.setVideoURI(uriList.get(nextSong));
                videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        videoView.start();
                    }
                });

                startProgressBar();
                animateVideoThatsPlaying();
            }
        });*/
    }

    private void animateVideoThatsPlaying() {
        switch (nextSong) {
            case 1:
                video_1.resume();
                break;
            case 2:
                video_2.resume();
                break;
            case 3:
                video_3.resume();
                break;
            case 4:
                video_4.resume();
                break;

        }
    }

    private void startProgressBar() {
        progressBar.setProgress(0);
        progressBar.setMax(videoView.getDuration());
        progressHandler.post(progressRunnable);


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

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return true;
    }

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
            toast.show();
        }
    }


}
