package com.mehrshad.khoobad.Main.View.Activity;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mehrshad.khoobad.Khoobad;
import com.mehrshad.khoobad.Main.Adapter.ImageSliderAdapter;
import com.mehrshad.khoobad.Model.VenueDetails;
import com.mehrshad.khoobad.R;
import com.mehrshad.khoobad.Util.GeneralFunctions;
import com.squareup.picasso.Picasso;

public class ActVenue extends AppCompatActivity {

    private VenueDetails venueDetails;

    private ViewPager imageSliderVP;
    private ImageView categoryImg,verifiedImg;
    private TextView nameTv;
    private RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_venue);

        venueDetails = GeneralFunctions.jsonOf(getIntent().getStringExtra("venue"));
        initializeUI();
    }

    private void initializeUI() {

        imageSliderVP = findViewById(R.id.image_slider);
        categoryImg = findViewById(R.id.ven_cat_img);
        verifiedImg = findViewById(R.id.ven_verified);
        ratingBar = findViewById(R.id.ratingBar);
        nameTv = findViewById(R.id.ven_name);

        ImageSliderAdapter viewPagerAdapter =
                new ImageSliderAdapter(this , venueDetails.getPhotosUrls());
        imageSliderVP.setAdapter(viewPagerAdapter);

        categoryImg.setColorFilter(ContextCompat.getColor(Khoobad.context,
                R.color.WHITE), android.graphics.PorterDuff.Mode.MULTIPLY);
        if (venueDetails.getCategories().size() > 0)
        {
            Picasso.with(Khoobad.context)
                    .load(venueDetails.getCategories().get(0).getIcon().getCatUrl())
                    .into(categoryImg);
        }

        venueDetails.getResponse().getVenue().setVerified(true);
        if (venueDetails.getResponse().getVenue().getVerified())
        {
            verifiedImg.setVisibility(View.VISIBLE);
        }

        Float rating = 3.5f;//venueDetails.getResponse().getVenue().getRating();
        if (rating != null)
        {
            ratingBar.setRating(rating);
        }

        nameTv.setText(venueDetails.getResponse().getVenue().getName());
    }
}
