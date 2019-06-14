package com.ali.rnp.khodemon.Test;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class AliAsyncTask extends AsyncTask<Void,Integer,Void> {

    private ProgressDialog pd ;
    private Context context;
    private boolean isDown;

    private int sleep = 500;
    private int fileSize = 2500;
    private int downloaded = 0;

    private static final String TAG = "AliAsyncTask";

    public AliAsyncTask(Context context){
        this.context = context;
    }

    @Override
    protected Void doInBackground(Void... voids) {


        new Thread(new Runnable() {
            @Override
            public void run() {

                while (!isDown){


                    try {
                        int prs = (downloaded * 100 ) / fileSize;
                        publishProgress(prs);

                        if (downloaded >= fileSize){
                            isDown = true;
                            break;

                        }

                        Thread.sleep(sleep);
                        downloaded += 500;

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }


            }
        }).start();
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pd = new ProgressDialog(context);
        pd.setMessage("loading");
        pd.setIndeterminate(false);
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.setMax(100);
        pd.show();
    }


    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        pd.setProgress(values[0]);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if (isDown){
            pd.dismiss();
        }
    }
}
