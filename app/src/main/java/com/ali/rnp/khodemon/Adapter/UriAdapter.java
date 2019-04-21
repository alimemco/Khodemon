package com.ali.rnp.khodemon.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.ali.rnp.khodemon.MyLibrary.MyTextView;
import com.ali.rnp.khodemon.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class UriAdapter extends RecyclerView.Adapter<UriAdapter.UriViewHolder> {

    private List<Uri> mUris;
    private List<String> mPaths;
    private OnItemChooseRecyclerClicked onItemChooseRecyclerClicked;
    private boolean isChooser;
    private int positionOriginalPhoto = 0;
    Context context;

    public UriAdapter(Context context) {
        this.context = context;
    }


    public void setData(List<Uri> uris, boolean isChooser, OnItemChooseRecyclerClicked onItemChooseRecyclerClicked) {
        mUris = uris;
        // mPaths = paths;
        this.isChooser = isChooser;
        this.onItemChooseRecyclerClicked = onItemChooseRecyclerClicked;
        notifyDataSetChanged();
    }

    @Override
    public UriAdapter.UriViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new UriAdapter.UriViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.uri_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull UriAdapter.UriViewHolder holder, int position) {
        /*
        holder.mUri.setText(mUris.get(position).toString());
        holder.mPath.setText(mPaths.get(position));
        */

        Picasso.get().load(mUris.get(position)).placeholder(R.drawable.add_photo_text).resize(500, 500).centerCrop().into(holder.mPic);


        if (positionOriginalPhoto == position && !isChooser ){
            holder.mText.setVisibility(View.VISIBLE);
        }else {
            holder.mText.setVisibility(View.INVISIBLE);
        }

        holder.itemView.setOnClickListener(v -> {

            if (isChooser && position == 0) {
                onItemChooseRecyclerClicked.onRecyclerClicked();

            } else {
                positionOriginalPhoto = position;
                holder.mText.setVisibility(View.VISIBLE);
                notifyDataSetChanged();
            }

        });


    }

    @Override
    public int getItemCount() {
        return mUris == null ? 0 : mUris.size();
    }

    static class UriViewHolder extends RecyclerView.ViewHolder {


        private ImageView mPic;
        private MyTextView mText;

        UriViewHolder(View contentView) {
            super(contentView);

            mPic = contentView.findViewById(R.id.uri_item_image);
            mText = contentView.findViewById(R.id.uri_item_textView);
        }


    }

    public interface OnItemChooseRecyclerClicked {
        void onRecyclerClicked();
    }
}
