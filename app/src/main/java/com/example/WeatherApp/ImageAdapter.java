package com.example.WeatherApp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImagesViewHolder> {

    public static class ImagesViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        ImageView city_photo;

        ImagesViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            city_photo = (ImageView) itemView.findViewById(R.id.city_photo);
        }
    }

    List<Images> images;
    private Context context;

    ImageAdapter(Context context, List<Images> images){
        this.images = images;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    @Override
    public ImagesViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.image_data, viewGroup, false);
        ImagesViewHolder ivh = new ImagesViewHolder(v);
        return ivh;
    }

    @Override
    public void onBindViewHolder(ImagesViewHolder imagesViewHolder, int i) {
        Picasso.with(context).load(images.get(i).photo_url).fit().into(imagesViewHolder.city_photo);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
