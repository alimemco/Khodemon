package com.ali.rnp.khodemon.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ali.rnp.khodemon.DataModel.PictureUpload;
import com.ali.rnp.khodemon.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import it.sephiroth.android.library.imagezoom.ImageViewTouchBase;

public class ImageGalleryRcvAdapter extends RecyclerView.Adapter<ImageGalleryRcvAdapter.ImageGalleryHolder>
     {

   // private Context context;
    private ArrayList<PictureUpload> pictureUploadList;
    private RecyclerView parentRecycler;

    private static final String TAG = "ImageGalleryRcvAdapter";

    public ImageGalleryRcvAdapter(ArrayList<PictureUpload> pictureUploadList) {
       // this.context = context;
        this.pictureUploadList = pictureUploadList;
    }

    public void setImages() {

        notifyDataSetChanged();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        parentRecycler = recyclerView;
    }


    @NonNull
    @Override
    public ImageGalleryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_image_gallery, parent, false);

        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.MATCH_PARENT);



        view.setLayoutParams(lp);

        return new ImageGalleryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageGalleryHolder holder, int position) {
        


        if (!pictureUploadList.get(position).getPic_address().equals("")) {

            boolean isLarge;
            isLarge = pictureUploadList.get(position).getWidth() >= 1000;
            String IMG_ADDRESS ;

            IMG_ADDRESS = (isLarge) ?
                    pictureUploadList.get(position).getThumb_1000()
                    : pictureUploadList.get(position).getPic_address();


            Picasso.get()
                    .load(pictureUploadList.get(position).getThumb_150())
                    .placeholder(R.drawable.holder_banner)
                    .into(holder.imageView, new Callback() {


                        @Override
                        public void onSuccess() {
                            Picasso.get()
                                    .load(IMG_ADDRESS)
                                    .placeholder(holder.imageView.getDrawable())
                                    .into(holder.imageView);


                        }

                        @Override
                        public void onError(Exception e) {

                        }

                    });

        }

    }

    @Override
    public int getItemCount() {
        return pictureUploadList != null ? pictureUploadList.size() : 0;
    }



    class ImageGalleryHolder extends RecyclerView.ViewHolder {

        ImageView imageView;


        ImageGalleryHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.recycler_view_image_gallery_imageView);

            itemView.setOnClickListener(v -> parentRecycler.smoothScrollToPosition(getAdapterPosition()));

        }
    }
}
