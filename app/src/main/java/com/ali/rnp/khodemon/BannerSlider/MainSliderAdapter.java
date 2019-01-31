package com.ali.rnp.khodemon.BannerSlider;

import com.ali.rnp.khodemon.R;

import ss.com.bannerslider.adapters.SliderAdapter;
import ss.com.bannerslider.viewholder.ImageSlideViewHolder;

public class MainSliderAdapter extends SliderAdapter {
    @Override
    public int getItemCount() {
        return 6;
    }

    @Override
    public void onBindImageSlide(int position, ImageSlideViewHolder imageSlideViewHolder) {

        switch (position) {
            case 0:
               imageSlideViewHolder.bindImageSlide("https://fecdn.cafebazaar.ir/promos/promo2938_xl_fa.jpg",R.drawable.holder_banner,R.drawable.holder_banner);
                break;

            case 1:
                imageSlideViewHolder.bindImageSlide("https://fecdn.cafebazaar.ir/promos/promo2940_xl_fa.jpg",R.drawable.holder_banner,R.drawable.holder_banner);
                break;


            case 2:
                imageSlideViewHolder.bindImageSlide("https://fecdn.cafebazaar.ir/promos/promo2945_xl_fa.jpg",R.drawable.holder_banner,R.drawable.holder_banner);
                break;


            case 3:
                imageSlideViewHolder.bindImageSlide("https://fecdn.cafebazaar.ir/promos/promo2661_xl_fa.jpg",R.drawable.holder_banner,R.drawable.holder_banner);
                break;


            case 4:
                imageSlideViewHolder.bindImageSlide("https://fecdn.cafebazaar.ir/promos/promo240_xl_fa.jpg",R.drawable.holder_banner,R.drawable.holder_banner);
                break;


            case 5:
                imageSlideViewHolder.bindImageSlide("https://fecdn.cafebazaar.ir/promos/promo683_xl_fa.jpg",R.drawable.holder_banner,R.drawable.holder_banner);
                break;

        }
    }
}
