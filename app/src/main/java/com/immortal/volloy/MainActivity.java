package com.immortal.volloy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.immortal.volloy.R.id.imageView;

public class MainActivity extends AppCompatActivity implements MovieListener {

    RequestQueue requestQueue;
    String server_url="http://api.themoviedb.org/3/movie/top_rated?api_key=107ed75bf9e25ec06bfe9fd33d042579";
    List<Movie>LMovie,listMovie;
    int pos;
    DBhandler db;
    boolean check=false;
    MovieAdapter MovieA;
    RecyclerView Recy;
    RecyclerView.LayoutManager La;
    SharedPreferences.Editor ed;
    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sh = getSharedPreferences("file", Context.MODE_PRIVATE);
        ed = sh.edit();
        LMovie = new ArrayList<>() ;
        listMovie=new ArrayList<>();
        db = new DBhandler(this);
        Recy = (RecyclerView)findViewById(R.id.Rec);
        La = new GridLayoutManager(this,2);
        MovieA=new MovieAdapter(getApplicationContext(),LMovie,this);
        Recy.setLayoutManager(La);
        Recy.setAdapter(MovieA);
        requestQueue= Volley.newRequestQueue(this.getApplicationContext());

        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, server_url, (JSONObject) null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.v("Asd", response.toString());
                try {
                    JSONArray Jo=response.getJSONArray("results");

                    for(int i=0;i< Jo.length();i++) {
                        Movie Mov = new Movie();

                        JSONObject jo = Jo.getJSONObject(i);

                        Mov.setId(jo.getString("id"));
                        Mov.setTitle(jo.getString("title"));
                        Mov.setPoster_path(jo.getString("poster_path"));
                        Mov.setOverview(jo.getString("overview"));
                        Mov.setVote_average((float) jo.getDouble("vote_average"));
                        Mov.setBackdrop_path(jo.getString("backdrop_path"));
                        Mov.setRelease_date(jo.getString("release_date"));


                        LMovie.add(Mov);
                        listMovie.add(Mov);
                    }
                    MovieA.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("Asd","Error !!");
            }
        });
        requestQueue.add(stringRequest);

     /*   if(sh.getBoolean("check",false)){
            Log.v("sss","True");
            LMovie.clear();
            //    MovieA.notifyItemRangeRemoved(0,size);
            //  Recy.invalidate();
            MovieA.notifyDataSetChanged();
            //Recy.removeAllViews();
            LMovie = db.Select();
            MovieA = new MovieAdapter(getApplicationContext(), LMovie, this);
            //Recy.swapAdapter(MovieA, false);
            MovieA.notifyDataSetChanged();
            check = true;
        }*/
    }

    @Override
    public void onMovieClicked(int postion) {
        Intent intent = new Intent(getApplicationContext(),Details.class);
        pos=postion;
        intent.putExtra("Movie",LMovie.get(postion));
        intent.putExtra("check",check);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(check) {
            if (LMovie.size() > db.Select().size()) {
                LMovie.remove(pos);
                MovieA.notifyItemRemoved(pos);
            }
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item ) {
        Log.v("sd",LMovie.size()+" "+listMovie.size()+"");


        if((item.getItemId()==R.id.Fav&& !check)) {
            ed.clear();
            ed.putBoolean("check",true);
            ed.commit();
            LMovie.clear();
            Recy.removeAllViews();
            LMovie = db.Select();
            MovieA = new MovieAdapter(getApplicationContext(), LMovie, this);
            Recy.swapAdapter(MovieA, false);
            check = true;
        }
        else if(item.getItemId()==R.id.All_Movies&& check)
        {
            ed.clear();
            ed.putBoolean("check",false);
            ed.commit();
            LMovie.clear();
            Recy.removeAllViews();
            for(int i=0;i<listMovie.size();i++) {
                LMovie.add(listMovie.get(i));
            }
            MovieA = new MovieAdapter(getApplicationContext(), LMovie, this);
            Recy.swapAdapter(MovieA, false);
            check = false;
        }


        Log.v("sd",LMovie.size()+" "+listMovie.size()+"");

        return true;
    }

}
