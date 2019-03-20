package com.ali.rnp.khodemon.Adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ali.rnp.khodemon.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class UriAdapter extends RecyclerView.Adapter<UriAdapter.UriViewHolder> {

    private List<Uri> mUris;
    private List<String> mPaths;


    public void setData(List<Uri> uris, List<String> paths) {
        mUris = uris;
        mPaths = paths;
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
        Picasso.get().load(mUris.get(position)).resize(500,500).centerCrop().into(holder.mPic);

    }

    @Override
    public int getItemCount() {
        return mUris == null ? 0 : mUris.size();
    }

    static class UriViewHolder extends RecyclerView.ViewHolder {


        private ImageView mPic;

        UriViewHolder(View contentView) {
            super(contentView);

            mPic =  contentView.findViewById(R.id.uri_item_image);
        }
    }
}
