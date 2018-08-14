package com.mehrshad.khoobad.Main.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mehrshad.khoobad.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ImageSliderAdapter extends PagerAdapter {

    private Context context;
    private ArrayList<String> imageUrls;

    public ImageSliderAdapter(Context context , ArrayList<String> imageUrls) {

        this.context = context;
        this.imageUrls = imageUrls;
    }

    @Override
    public int getCount() {
        return imageUrls.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @SuppressLint("InflateParams")
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = null;

        if (layoutInflater != null) {
            view = layoutInflater.inflate(R.layout.viewpager_image_item, null);
        }
        ImageView imageView = null;
        if (view != null) {
            imageView = view.findViewById(R.id.imageView);
        }

        if (imageView != null)
        {
            Picasso.with(view.getContext())
                    .load(imageUrls.get(position))
                    .error(R.drawable.no_image)
                    .into(imageView);
        }

        ViewPager vp = (ViewPager) container;
        vp.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);
    }
}
