package com.immortal.volloy;


import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Downloader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by IMMORTAl on 7/19/2017.
 */

public class Volloy  {

    private static Volloy mySingleTon;
    private RequestQueue requestQueue;
    private static Context mctx;
    private Volloy(Context context){
        this.mctx=context;
        this.requestQueue=getRequestQueue();

    }
    public RequestQueue getRequestQueue(){
        if (requestQueue==null){
            requestQueue= Volley.newRequestQueue(mctx.getApplicationContext());
        }
        return requestQueue;
    }
    public static synchronized Volloy getInstance(Context context){
        if (mySingleTon==null){
            mySingleTon=new Volloy(context);
        }
        return mySingleTon;
    }
    public<T> void addToRequestQue(Request<T> request){
        requestQueue.add(request);

    }

}
