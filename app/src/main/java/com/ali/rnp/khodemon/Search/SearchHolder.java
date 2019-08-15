package com.ali.rnp.khodemon.Search;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.ali.rnp.khodemon.DataModel.LocationPeople;
import com.ali.rnp.khodemon.Helper.StringHighlight;
import com.ali.rnp.khodemon.MyLibrary.MyTextView;
import com.ali.rnp.khodemon.R;
import com.ali.rnp.khodemon.UtilsApp.UtilsApp;

import de.hdodenhof.circleimageview.CircleImageView;

class SearchHolder {


    public SearchHolder() {

    }

    static class ParentHolder extends RecyclerView.ViewHolder {
        MyTextView titleTv;
        MyTextView statusTv;

        ParentHolder(@NonNull View itemView) {
            super(itemView);
            titleTv = itemView.findViewById(R.id.search_parent_title_tv);
            statusTv = itemView.findViewById(R.id.search_parent_status_tv);

        }

        void bind(GroupModel groupModel) {
            String txt = groupModel.getItems().size() + " نتیجه ";

            titleTv.setText(groupModel.getTitle());
            statusTv.setText(txt);

        }

    }

    static class ChildHolder extends RecyclerView.ViewHolder {
        MyTextView nameTv;
        MyTextView categoryTv;
        CircleImageView imageView;
        MyTextView ad;
        MyTextView score;
        View line;
        Context context;


        ChildHolder(@NonNull View itemView) {
            super(itemView);
            this.context = itemView.getContext();


            nameTv = itemView.findViewById(R.id.rcv_search_category_nameTv);
            categoryTv = itemView.findViewById(R.id.rcv_search_category_categoryTv);
            imageView = itemView.findViewById(R.id.rcv_search_category_imageView);
            ad = itemView.findViewById(R.id.rcv_search_category_isAd);
            score = itemView.findViewById(R.id.rcv_search_category_score);
            line = itemView.findViewById(R.id.rcv_search_category_line);
        }


        void bind(LocationPeople childModel, String typed, boolean isHidden) {

            if (typed == null) {
                typed = "";
            }

            nameTv.setText(StringHighlight.highlight(childModel.getName(), typed, ContextCompat.getColor(context, R.color.red400)));

            categoryTv.setText(childModel.getTag());

            score.setText(String.valueOf(UtilsApp.randomFloat(0, 5)));

            UtilsApp.getImageResize(childModel.getOriginalPic(), childModel.getImageThumb150(), imageView);

            ad.setVisibility(childModel.getIsAd().equals("true") ? View.VISIBLE : View.INVISIBLE);
            score.setVisibility(childModel.getIsAd().equals("true") ? View.INVISIBLE : View.VISIBLE);
            line.setVisibility(isHidden ? View.INVISIBLE : View.VISIBLE);


        }

    }

    static class EmptyHolder extends RecyclerView.ViewHolder {


        EmptyHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    static class SearchingHolder extends RecyclerView.ViewHolder {
        SearchingHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
