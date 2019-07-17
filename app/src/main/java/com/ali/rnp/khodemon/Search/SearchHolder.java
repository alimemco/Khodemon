package com.ali.rnp.khodemon.Search;

import android.view.View;

import com.ali.rnp.khodemon.Library.CircularImageView;
import com.ali.rnp.khodemon.MyLibrary.MyTextView;
import com.ali.rnp.khodemon.R;
import com.ali.rnp.khodemon.UtilsApp.UtilsApp;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import de.hdodenhof.circleimageview.CircleImageView;

class SearchHolder {


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
        ChildHolder(@NonNull View itemView) {
            super(itemView);
            nameTv = itemView.findViewById(R.id.rcv_search_category_nameTv);
            categoryTv = itemView.findViewById(R.id.rcv_search_category_categoryTv);
            imageView = itemView.findViewById(R.id.rcv_search_category_imageView);

        }

        void bind(ChildModel childModel){

            nameTv.setText(childModel.getName());
            categoryTv.setText(childModel.getCategory());



        }
    }
}
