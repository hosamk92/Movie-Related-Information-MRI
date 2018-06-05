package com.immortal.volloy;

import android.os.AsyncTask;

/**
 * Created by IMMORTAl on 7/23/2017.
 */

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;


public class AsyncTaskLoadImage  extends AsyncTask<String, String, Bitmap> {
    private final static String TAG = "AsyncTaskLoadImage";

    public AsyncTaskLoadImage() {
    }
    @Override
    protected Bitmap doInBackground(String... params) {
        Bitmap bitmap = null;
        try {
            URL url = new URL(params[0]);
            bitmap = BitmapFactory.decodeStream((InputStream)url.getContent());
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
        return bitmap;
    }
    @Override
    protected void onPostExecute(Bitmap bitmap) {

    }
}