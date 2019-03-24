package com.ali.rnp.khodemon.UtilsApp;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.AsyncTask;

public class UploadPhotosAsync extends AsyncTask<Bitmap,Integer,String> {

    ProgressDialog progressDialog;


    @Override
    protected String doInBackground(Bitmap... bitmaps) {
        return null;
    }

    @Override
    protected void onPreExecute() {

        super.onPreExecute();
    }



    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }


}
