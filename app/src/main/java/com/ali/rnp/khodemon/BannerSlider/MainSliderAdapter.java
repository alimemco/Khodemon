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
               imageSlideViewHolder.bindImageSlide("http://hph.co.ir/data/upload/mirasmlm/slide/40b96ae72e26a25d2dbc27fd4958956f1548244027.jpg",R.drawable.banner_holder,R.drawable.banner_holder);
                break;

            case 1:
                imageSlideViewHolder.bindImageSlide("http://hph.co.ir/data/upload/mirasmlm/slide/436722102e665b7f36c5f3796b13519f1548050219.jpg",R.drawable.banner_holder,R.drawable.banner_holder);
                break;


            case 2:
                imageSlideViewHolder.bindImageSlide("http://hph.co.ir/data/upload/mirasmlm/slide/91dc5d2d0c2d3c56447366aa85aaa7481547302303.jpg",R.drawable.banner_holder,R.drawable.banner_holder);
                break;


            case 3:
                imageSlideViewHolder.bindImageSlide("http://hph.co.ir/data/upload/mirasmlm/slide/0b0e30e39d7ff8c00498acc298afc64a1533557477.jpg",R.drawable.banner_holder,R.drawable.banner_holder);
                break;


            case 4:
                imageSlideViewHolder.bindImageSlide("http://hph.co.ir/data/upload/mirasmlm/slide/91dc5d2d0c2d3c56447366aa85aaa7481547302303.jpg",R.drawable.banner_holder,R.drawable.banner_holder);
                break;


            case 5:
                imageSlideViewHolder.bindImageSlide("http://hph.co.ir/data/upload/mirasmlm/slide/9f3918773b6fc94b3680a90895fa8cce1548050584.jpg",R.drawable.banner_holder,R.drawable.banner_holder);
                break;

        }
    }
}
