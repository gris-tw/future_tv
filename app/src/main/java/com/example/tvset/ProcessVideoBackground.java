package com.example.tvset;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import java.net.URL;
import java.util.Arrays;

public class ProcessVideoBackground extends AsyncTask<String, String, VideoView> {
    //AOCST1
    /*public int count = 0;
    public static String[] playList;*/

    private VideoView videoView;
    private Context context;
    private ProgressDialog dialog;
    public ProcessVideoBackground(VideoView videoView, Context context){
        this.videoView = videoView;
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = ProgressDialog.show(context, "",
                "Loading. Please wait...", true);
    }

    @Override
    protected void onPostExecute(VideoView videoView) {
        super.onPostExecute(videoView);
        dialog.dismiss();
        if(videoView!=null) {
            videoView.start();
        }
    }

    @Override
    protected VideoView doInBackground(String... url) {
        //AOCST1
        //ProcessVideoBackground.playList = url;

        try {
            //MediaController mediaController = new MediaController(context);
            //mediaController.setAnchorView(videoView);
            //videoView.setMediaController(mediaController);
            videoView.setMediaController(null); //by no one can control
            videoView.setVideoURI(Uri.parse(url[0]));
            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {

                    //AOCST1
                    //if(ProcessVideoBackground.playList.length > 1){
                    //    mp.start();
                    //}else{
                        mp.setLooping(true);
                    //}

                }
            });

            //AOCST1
            /*if(ProcessVideoBackground.playList.length > 1) {
                videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    public void onCompletion(MediaPlayer mp) {

                        // Do whatever u need to do here
                        if (count == ProcessVideoBackground.playList.length - 1) {
                            count = 0;
                        } else {
                            count += 1;
                        }

                        mp.reset();
                        videoView.setVideoPath(ProcessVideoBackground.playList[count]);
                        videoView.start();
                    }
                });
            }*/

            return videoView;
        }catch(Exception e){
            System.out.println("Problem::::::");
            System.out.println(e);
            return null;
        }
    }
}