package com.http_tool;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.support.v7.widget.GridLayout;

import org.json.JSONException;

import java.io.IOException;

public class AsyncHTTPPost extends AsyncTask<String, String, String> {

    private ProgressDialog dialog;
    private GridLayout contentGrid;
    private Context context;
    private View.OnClickListener selectListener;

    public AsyncHTTPPost(GridLayout contentGrid,View.OnClickListener selectListener , Context context){
        this.contentGrid = contentGrid;
        this.context = context;
        this.selectListener = selectListener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = ProgressDialog.show(context, "",
                "載入縣市資料中...", true);
    }

    @Override
    protected void onPostExecute(String postResponse) {
        super.onPostExecute(postResponse);
        dialog.dismiss();
        if(postResponse!=null) {
            System.out.println("---------------------------------------");
            System.out.println(postResponse);
        }
    }

    @Override
    protected String doInBackground(String... pms) {
        String url = pms[0]; //"http://twtraffic.tra.gov.tw/twrail/Services/BaseDataServ.ashx"
        String param = null;
        try {
            param = pms[1];
        }catch (ArrayIndexOutOfBoundsException ae){
            System.out.println("Warning: POST Params is no set.");
        }

        String resp = null;
        try {
            HttpRequest hp = new HttpRequest();
            resp = hp.sendPost(url, param);
            System.out.println(resp);

        }catch (IOException eio){
            System.out.println(eio);
        }catch (JSONException ejs){
            System.out.println(ejs);
        }catch(Exception e){
            System.out.println(e);
        }

        return resp;
    }


}
