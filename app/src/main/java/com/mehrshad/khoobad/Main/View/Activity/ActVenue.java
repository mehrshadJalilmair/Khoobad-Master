package com.mehrshad.khoobad.Main.View.Activity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.mehrshad.khoobad.Main.Adapter.ImageSliderAdapter;
import com.mehrshad.khoobad.Model.VenueDetails;
import com.mehrshad.khoobad.R;
import com.mehrshad.khoobad.Util.GeneralFunctions;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class ActVenue extends AppCompatActivity {

    private VenueDetails venueDetails;
    private Timer timer;
    private ViewPager imageSliderVP;
    private int imageUrlsCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_venue);

        venueDetails = GeneralFunctions.jsonOf(getIntent().getStringExtra("venue"));
        initializeUI();
    }

    private void initializeUI() {

        imageSliderVP = findViewById(R.id.image_slider);
        ImageView categoryImg = findViewById(R.id.ven_cat_img);
        ImageView verifiedImg = findViewById(R.id.ven_verified);
        RatingBar ratingBar = findViewById(R.id.ratingBar);
        TextView nameTv = findViewById(R.id.ven_name);

        TextView descTv = findViewById(R.id.ven_desc);
        TextView openingTv = findViewById(R.id.ven_opening);
        TextView phoneTv = findViewById(R.id.ven_phone);
        TextView addressTv = findViewById(R.id.ven_address);

        //init image slider
        ArrayList<String>urls = venueDetails.getPhotosUrls();
        imageUrlsCount = urls.size();
        ImageSliderAdapter viewPagerAdapter =
                new ImageSliderAdapter(this, urls);
        imageSliderVP.setAdapter(viewPagerAdapter);

        if (imageUrlsCount > 1)
        {
            timer = new Timer();
            timer.scheduleAtFixedRate(new SliderTimer(), 3000, 4000);
        }

        //init category image
        categoryImg.setColorFilter(ContextCompat.getColor(this,
                R.color.WHITE), android.graphics.PorterDuff.Mode.MULTIPLY);
        if (venueDetails.getCategories().size() > 0)
        {
            Picasso.with(getApplicationContext())
                    .load(venueDetails.getCategories().get(0).getIcon().getCatUrl())
                    .fit()
                    .into(categoryImg);
        }

        //verfied = true,false
        //venueDetails.getResponse().getVenue().setVerified(true);
        if (venueDetails.getResponse().getVenue().getVerified())
        {
            verifiedImg.setVisibility(View.VISIBLE);
        }

        //rating
        float rating = (float)venueDetails.getResponse().getVenue().getRating();
        ratingBar.setRating(rating);

        //name
        nameTv.setText(venueDetails.getResponse().getVenue().getName());

        //description
        if (venueDetails.getResponse().getVenue().getDescription() != null &&
                venueDetails.getResponse().getVenue().getDescription() != null)
        {
            descTv.setVisibility(View.VISIBLE);
            descTv.setText(venueDetails.getResponse().getVenue().getDescription());
        }

        //is open = true,false
        if (venueDetails.getResponse().getVenue().getHours() != null)
        {
            if (venueDetails.getResponse().getVenue().getHours().getOpen())
            {
                openingTv.setText(R.string.TXT_VENUE_IS_OPEN);
            }
            else
            {
                openingTv.setText(R.string.TXT_VENUE_IS_CLOSED);
            }
        }

        //full address
        if (venueDetails.getResponse().getVenue().getLocation().getAddress() != null)
            addressTv.setText(venueDetails.getResponse().getVenue().getLocation().getAddress());

        //contact number
        if (venueDetails.getResponse().getVenue().getContact() != null
                && venueDetails.getResponse().getVenue().getContact().getPhone() != null)
            phoneTv.setText(venueDetails.getResponse().getVenue().getContact().getPhone());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (timer != null)
            timer.cancel();
    }

    private class SliderTimer extends TimerTask {

        @Override
        public void run() {
            ActVenue.this.runOnUiThread(() -> {
                if (imageSliderVP.getCurrentItem() < imageUrlsCount - 1) {
                    imageSliderVP.setCurrentItem(imageSliderVP.getCurrentItem() + 1);
                } else {
                    imageSliderVP.setCurrentItem(0);
                }
            });
        }
    }
}
