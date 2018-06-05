package com.immortal.volloy;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.sip.SipAudioCall;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.net.URI;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by IMMORTAl on 7/19/2017.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>{
    List<Movie> LMovie;
    Context context ;
    MovieListener listner;
    MovieAdapter(Context con, List<Movie> l, MovieListener lis)
    {
        context=con;
        LMovie=l;
        listner=lis;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.items,parent,false);
       return new ViewHolder(v);
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
            holder.bind(LMovie.get(position));
    }

    @Override
    public int getItemCount() {
        return LMovie.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView img ;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            img = (ImageView) itemView.findViewById(R.id.imageView);
        }

        @Override
        public void onClick(View v) {
            listner.onMovieClicked(getLayoutPosition());
        }

        public void bind(Movie mov)
        {
            if(mov.getPoster()==null) {
                String uri = "http://image.tmdb.org/t/p/w780" + mov.getPoster_path();
                AsyncTaskLoadImage v = (AsyncTaskLoadImage) new AsyncTaskLoadImage().execute(uri);
                Bitmap bitmap = null;
                try {
                    bitmap = v.get();
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
                mov.setPoster(bitmap);
                img.setImageBitmap(bitmap);
            }
            else
                img.setImageBitmap(mov.getPoster());
        }
    }
}
