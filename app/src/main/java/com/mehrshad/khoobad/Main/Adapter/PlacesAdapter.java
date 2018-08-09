package com.mehrshad.khoobad.Main.Adapter;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mehrshad.khoobad.Interface.OnRecyclerItemClickListener;
import com.mehrshad.khoobad.Khoobad;
import com.mehrshad.khoobad.Model.Place;
import com.mehrshad.khoobad.R;
import com.mehrshad.khoobad.Util.ImageHelper;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;

public class PlacesAdapter extends RecyclerView.Adapter<PlacesAdapter.PlaceViewHolder> {

    private ArrayList<Place> places;
    private OnRecyclerItemClickListener onRecyclerItemClickListener;

    public PlacesAdapter(ArrayList<Place> places , OnRecyclerItemClickListener onRecyclerItemClickListener ) {

        this.places = places;
        this.onRecyclerItemClickListener = onRecyclerItemClickListener;
    }

    public void addMorePlaces(ArrayList<Place> places)
    {
        this.places = places;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PlaceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.place_view_row_item, parent, false);
        return new PlaceViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull PlaceViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        Place.Venue venue = places.get(position).getVenue();

        if (venue.getPhotos().getCount() > 0)
        {
            Picasso.with(Khoobad.context)
                    .load(
                            venue.getPhotos().getGroups().get(0).getItems().get(0).getPrefix() +
                                    venue.getPhotos().getGroups().get(0).getItems().get(0).getSuffix())
                    .placeholder(R.drawable.no_image)
                    .error(R.drawable.no_image)
                    .transform(new Transformation() {
                        @Override
                        public Bitmap transform(Bitmap source) {

                            return  ImageHelper.getRoundedCornerBitmap(source , 80);
                        }

                        @Override
                        public String key() {
                            return null;
                        }
                    })
                    .into(holder.imageView);
        }

        holder.nameTv.setText(venue.getName());
        holder.distTv.setText(venue.getLocation().getDistance().toString());
        holder.categoryTv.setText(venue.getCategories().get(0).getName());

        if (venue.getRating() != null)
        {
            holder.rateTv.setVisibility(View.VISIBLE);
            holder.rateTv.setText(venue.getRating().toString());
            holder.rateTv.setBackgroundResource(R.drawable.rating_tv_corner);
            GradientDrawable drawable = (GradientDrawable) holder.rateTv.getBackground();
            drawable.setColor(Color.parseColor(venue.getRatingColor()));
        }

        holder.itemView.setOnClickListener(v -> onRecyclerItemClickListener.onItemClick(position));
    }

    @Override
    public int getItemCount() {

        if (places == null)return 0;
        return places.size();
    }

    class PlaceViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView nameTv,distTv,categoryTv,rateTv;

        PlaceViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.placeImg);

            nameTv = itemView.findViewById(R.id.ven_name);
            distTv = itemView.findViewById(R.id.ven_distance);
            categoryTv = itemView.findViewById(R.id.ven_category);
            rateTv = itemView.findViewById(R.id.ven_rating);
        }
    }
}
