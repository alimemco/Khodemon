package com.ali.rnp.khodemon.Search;

import android.content.Context;
import android.view.View;

import com.ali.rnp.khodemon.Helper.StringHighlight;
import com.ali.rnp.khodemon.MyLibrary.MyTextView;
import com.ali.rnp.khodemon.R;
import com.ali.rnp.khodemon.UtilsApp.UtilsApp;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
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

        void bind(GroupModel groupModel){
             String txt = groupModel.getItems().size()+ " نتیجه ";

             titleTv.setText(groupModel.getTitle());
             statusTv.setText(txt);

        }

    }

    static class ChildHolder extends RecyclerView.ViewHolder {
        MyTextView nameTv;
        MyTextView categoryTv;
        CircleImageView imageView;
        MyTextView ad;
        Context context;


        ChildHolder(@NonNull View itemView, Context context) {
            super(itemView);
            this.context = context;


            nameTv = itemView.findViewById(R.id.rcv_search_category_nameTv);
            categoryTv = itemView.findViewById(R.id.rcv_search_category_categoryTv);
            imageView = itemView.findViewById(R.id.rcv_search_category_imageView);
            ad = itemView.findViewById(R.id.rcv_search_category_isAd);
        }


        void bind(ChildModel childModel, String typed, boolean isEmpty) {

            if (typed == null) {
                typed = "";
            }

            if (isEmpty) {
                nameTv.setText(childModel.getName());

            }else {
                nameTv.setText(StringHighlight.highlight(childModel.getName(), typed,
                        ContextCompat.getColor(context, R.color.red400)));

                categoryTv.setText(childModel.getCategory());

                ad.setVisibility(childModel.isAd().equals("true") ? View.VISIBLE : View.INVISIBLE);

                UtilsApp.getImageResize(childModel.getOriginalPic(), childModel.getThumb_pic(), imageView);

            }



        }


    }

    static class EmptyHolder extends RecyclerView.ViewHolder {
        MyTextView titleTv;
        Context context;


        EmptyHolder(@NonNull View itemView, Context context) {
            super(itemView);
            this.context = context;

            titleTv = itemView.findViewById(R.id.rcv_search_empty_title);

        }

/*
        void bind(String title){
                titleTv.setText(title);
        }*/


    }
}
