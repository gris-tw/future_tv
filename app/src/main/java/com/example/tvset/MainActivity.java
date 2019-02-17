package com.example.tvset;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.os.StrictMode;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import java.net.URL;

public class MainActivity extends Activity {

    public static Context mainContext;

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainActivity.mainContext = this;

        View lay = (View)findViewById(R.id.infoboard_bg);
        lay.setBackgroundColor(Color.rgb(0,0,0));
        lay.setAlpha(0.6f);
        System.out.println("set!");

        applyBlinkAnimation((View)findViewById(R.id.view2));
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                applyBlinkAnimation((View)findViewById(R.id.view3));
            }
        }, 1000);

        ((VideoView) findViewById(R.id.videoView)).setVisibility(View.GONE);
        new ProcessImageBackground((ImageView)findViewById(R.id.imageView), MainActivity.this).execute("https://images.unsplash.com/photo-1548909410-6ad3285db47b?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=2000&q=80");

        //((ImageView) findViewById(R.id.imageView)).setVisibility(View.GONE);
        //new ProcessVideoBackground((VideoView)findViewById(R.id.videoView), MainActivity.this).execute("https://app.coverr.co/s3/mp4/best_buddys.mp4","https://app.coverr.co/s3/mp4/Moped.mp4 ");

        ((ConstraintLayout)findViewById(R.id.main_layout)).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TRA_setup_activity.class);
                startActivity(intent);
                return true;
            }
        });
    }

    public void applyBlinkAnimation(final View view){
        ValueAnimator animator = ValueAnimator.ofFloat(0f, 1f);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                view.setAlpha((Float) animation.getAnimatedValue());
            }
        });

        animator.setDuration(1000);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.setRepeatCount(-1);
        animator.start();
    }

    public void onResume(Bundle savedInstanceState){
        applyBlinkAnimation((View)findViewById(R.id.view2));
    }

}
