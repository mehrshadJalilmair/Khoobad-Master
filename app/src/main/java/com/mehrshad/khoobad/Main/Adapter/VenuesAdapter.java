package com.mehrshad.khoobad.Main.Adapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mehrshad.khoobad.Interface.OnRecyclerItemClickListener;
import com.mehrshad.khoobad.Khoobad;
import com.mehrshad.khoobad.Model.Venue;
import com.mehrshad.khoobad.R;
import com.mehrshad.khoobad.Util.GeneralFunctions;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.view.View.VISIBLE;

public class VenuesAdapter extends RecyclerView.Adapter<VenueViewHolder> {

    private ArrayList<Venue> venues;
    private OnRecyclerItemClickListener onRecyclerItemClickListener;


    public VenuesAdapter(ArrayList<Venue> venues, OnRecyclerItemClickListener onRecyclerItemClickListener ) {

        this.venues = venues;
        this.onRecyclerItemClickListener = onRecyclerItemClickListener;
    }

    public void addMoreVenues(ArrayList<Venue> venues)
    {
        this.venues = venues;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VenueViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_venue_row, parent, false);
        return new VenueViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull VenueViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        Venue venue = venues.get(position);

        if (venue.getBaseVenue().getCategories().size() > 0)
        {
            holder.imageView.setColorFilter(ContextCompat.getColor(Khoobad.context,
                    R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.MULTIPLY);
            Picasso.with(Khoobad.context)
                    .load(venue.getBaseVenue().getCategories().get(0).getIcon().getCatUrl())
                    .placeholder(R.drawable.no_image)
                    .error(R.drawable.no_image)
                    .fit()
                    .into(holder.imageView);
        }

        holder.nameTv.setText(venue.getBaseVenue().getName());
        holder.distTv.setText(GeneralFunctions.english_to_persian(venue.getBaseVenue().getLocation().getDistance().toString()));
        holder.categoryTv.setText(venue.getBaseVenue().getCategories().get(0).getName());

        if (venue.getBaseVenue().getRating() != null)
        {
            holder.rateTv.setVisibility(VISIBLE);
            holder.rateTv.setText(venue.getBaseVenue().getRating().toString());
            holder.rateTv.setBackgroundResource(R.drawable.rating_tv_corner);
            GradientDrawable drawable = (GradientDrawable) holder.rateTv.getBackground();
            drawable.setColor(Color.parseColor(venue.getBaseVenue().getRatingColor()));
        }

        holder.itemView.setOnClickListener(v -> onRecyclerItemClickListener.onItemClick(venue.getBaseVenue().getId()));
    }

    @Override
    public int getItemCount() {

        if (venues == null)return 0;
        return venues.size();
    }
}
