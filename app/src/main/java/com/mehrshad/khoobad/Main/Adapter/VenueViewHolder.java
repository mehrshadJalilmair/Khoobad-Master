package com.mehrshad.khoobad.Main.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mehrshad.khoobad.R;

class VenueViewHolder extends RecyclerView.ViewHolder {

    ImageView imageView;
    TextView nameTv,distTv,categoryTv,rateTv;

    VenueViewHolder(View itemView) {
        super(itemView);

        imageView = itemView.findViewById(R.id.venueImg);

        nameTv = itemView.findViewById(R.id.ven_name);
        distTv = itemView.findViewById(R.id.ven_distance);
        categoryTv = itemView.findViewById(R.id.ven_category);
        rateTv = itemView.findViewById(R.id.ven_rating);
    }
}