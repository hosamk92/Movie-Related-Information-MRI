package com.immortal.volloy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Details extends AppCompatActivity {
    TextView title,overview,release_date;
    ImageView img;
    RatingBar RatBar;
    ImageButton imageButton;
    Movie movie;
    DBhandler db;
    boolean check,inFav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent =getIntent();
        db = new DBhandler(this);

        check=intent.getBooleanExtra("check",false);
        movie = intent.getParcelableExtra("Movie");


        // check image in favorites
        // if in favorites add its bitmap image value and turn on light in image button

        inFav = false;
        List<Movie> checkList=db.Select();
        for(int i=0;i<checkList.size();i++)
        {
            if(checkList.get(i).getId().equals(movie.getId()))
            {
                inFav=true;
                movie.setBack(checkList.get(i).getBack());
                movie.setPoster(checkList.get(i).getPoster());
                break;
            }
        }


        title = (TextView)findViewById(R.id.Title);
        overview =  (TextView)findViewById(R.id.Overview);
        release_date =(TextView)findViewById(R.id.relase_date);
        img = (ImageView)findViewById(R.id.imageView3);
        RatBar = (RatingBar)findViewById(R.id.ratingBar);
        imageButton=(ImageButton)findViewById(R.id.imageButton);

        title.setText(movie.getTitle());
        setTitle(title.getText());
        overview.setText(movie.getOverview());
        release_date.setText(movie.getRelease_date());
        RatBar.setRating(movie.getVote_average()/2);
        RatBar.setEnabled(false);
        Log.v("F",inFav+"");
        if(inFav)
          imageButton.setImageResource(android.R.drawable.btn_star_big_on);
        else
            imageButton.setImageResource(android.R.drawable.btn_star_big_off);
        if(!check) {

            //Save uri image to bitmap and set back image
            String uri = "http://image.tmdb.org/t/p/w780" + movie.getPoster_path();
            AsyncTaskLoadImage v = (AsyncTaskLoadImage) new AsyncTaskLoadImage().execute(uri);
            Bitmap bitmap = null;
            try {
                bitmap = v.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            movie.setPoster(bitmap);

            uri = "http://image.tmdb.org/t/p/w500" + movie.getBackdrop_path();

            v = (AsyncTaskLoadImage) new AsyncTaskLoadImage().execute(uri);
            bitmap = null;
            try {
                bitmap = v.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            movie.setBack(bitmap);
            img.setImageBitmap(bitmap);
        }
        else {
            img.setImageBitmap(movie.getBack());
            Log.v("img",movie.getBack()+"");
        }
    }
    public void open(View v)
    {

        Log.v("add",movie.getTitle());
        Log.v("add",movie.getOverview());
        Log.v("add",movie.getPoster()+"");
        Log.v("add",movie.getVote_average()+"");
        Log.v("add",movie.getBack()+"");
        Log.v("add",movie.getRelease_date());
        Log.v("add",movie.getId());
        Log.v("check",inFav+"");
        if(inFav) {
            db.delete(movie.getId());
            check = false;
            inFav = false;
            imageButton.setImageResource(android.R.drawable.btn_star_big_off);
            Toast.makeText(this,"Removed from Favorits",Toast.LENGTH_SHORT).show();

        }
        else {
            Log.v("img",movie.getBack()+" "+movie.getPoster());
            if(movie.getBack()==null&&movie.getPoster()==null)
            {
                Bitmap bitmap=BitmapFactory.decodeResource(getResources(),R.drawable.icon);
                movie.setBack(bitmap);
                movie.setPoster(bitmap);
            }
            db.insert(movie.getTitle(),
                    movie.getOverview(),
                    movie.getPoster(),
                    movie.getVote_average(),
                    movie.getBack(),
                    movie.getRelease_date(),
                    movie.getId());
            check=true;
            inFav=true;
            imageButton.setImageResource(android.R.drawable.btn_star_big_on);
            Toast.makeText(this,"Added to Favorits",Toast.LENGTH_SHORT).show();

        }
    }
    // back button code
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
