package com.styles;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.net.URL;

public class ProcessImageBackground extends AsyncTask<String, String, Bitmap> {

    private ImageView view;
    private Context context;
    private ProgressDialog dialog;
    public ProcessImageBackground(ImageView view, Context context){
        this.view = view;
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        dialog = ProgressDialog.show(context, "",
                "圖片背景載入中...", true);
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        dialog.dismiss();
        if(bitmap!=null) {
            view.setImageBitmap(bitmap);
        }
    }

    @Override
    protected Bitmap doInBackground(String... url) {
        try {
            URL newurl = new URL(url[0]);
            Bitmap bg = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
            //((ImageView) findViewById(R.id.imageView)).setImageBitmap(bg);
            //.setBackground(new BitmapDrawable(bg));

            System.out.println("Finish to rendering background");
            return bg;
        }catch(Exception e){
            System.out.println("Problem::::::");
            System.out.println(e);
            return null;
        }
    }
}