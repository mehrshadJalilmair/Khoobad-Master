package com.mehrshad.khoobad.Main.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mehrshad.khoobad.Interface.OnRecyclerItemClickListener;
import com.mehrshad.khoobad.Model.Venue;
import com.mehrshad.khoobad.Model.VenueCategory;
import com.mehrshad.khoobad.R;
import com.mehrshad.khoobad.Util.GeneralFunctions;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;

public class VenuesAdapter extends RecyclerView.Adapter<VenueViewHolder> {

    private ArrayList<Venue> venues;
    private OnRecyclerItemClickListener onRecyclerItemClickListener;
    private Context context;

    public VenuesAdapter(Context context, ArrayList<Venue> venues, OnRecyclerItemClickListener onRecyclerItemClickListener ) {

        this.context = context;
        this.venues = venues;
        this.onRecyclerItemClickListener = onRecyclerItemClickListener;
    }

    public void addMoreVenues(ArrayList<Venue> venues)
    {
        if (this.venues != null)
        {
            this.venues.clear();
            this.notifyDataSetChanged();
        }
        this.venues = venues;
        Collections.sort(this.venues
                , (o1, o2) -> o1.getBaseVenue().getLocation().getDistance().compareTo(o2.getBaseVenue().getLocation().getDistance()));
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

//        if (venue.getBaseVenue().getCategories() != null && venue.getBaseVenue().getCategories().size() > 0)
//        {
//            if (holder.imageView != null)
//            {
//                VenueCategory.Icon ic = venue.getBaseVenue().getCategories().get(0).getIcon();
//                if (ic != null && ic.getCatUrl() != null)
//                {
//                    holder.imageView.setColorFilter(ContextCompat.getColor(context,
//                            R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.MULTIPLY);
//
//                    Picasso.with(context)
//                            .load(ic.getCatUrl())
//                            .placeholder(R.drawable.no_image)
//                            .error(R.drawable.no_image)
//                            .into(holder.imageView);
//                }
//
//            }
//        }

        if (venue.getBaseVenue().getName() != null)
            holder.nameTv.setText(venue.getBaseVenue().getName());
        if (venue.getBaseVenue().getLocation() !=null)
            holder.distTv.setText(GeneralFunctions.english_to_persian(venue.getBaseVenue().getLocation().getDistance()));
        if (venue.getBaseVenue().getCategories() != null)
            holder.categoryTv.setText(venue.getBaseVenue().getCategories().get(0).getName());

        holder.itemView.setOnClickListener(v -> onRecyclerItemClickListener.onItemClick(venue.getBaseVenue().getId()));
    }

    @Override
    public int getItemCount() {

        if (venues == null)return 0;
        return venues.size();
    }
}
