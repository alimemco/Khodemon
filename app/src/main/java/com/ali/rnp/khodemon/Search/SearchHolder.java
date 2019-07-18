package com.ali.rnp.khodemon.Search;

import android.content.Context;
import android.view.View;

import com.ali.rnp.khodemon.Helper.StringHighlight;
import com.ali.rnp.khodemon.Library.CircularImageView;
import com.ali.rnp.khodemon.MyLibrary.MyTextView;
import com.ali.rnp.khodemon.R;
import com.ali.rnp.khodemon.UtilsApp.UtilsApp;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import de.hdodenhof.circleimageview.CircleImageView;

class SearchHolder {

     Context context;

    public SearchHolder(Context context) {
        this.context = context;
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
        Context context;
         String txt;

        public ChildHolder(@NonNull View itemView, Context context,String txt) {
            super(itemView);
            this.context = context;
            this.txt = txt ;

            nameTv = itemView.findViewById(R.id.rcv_search_category_nameTv);
            categoryTv = itemView.findViewById(R.id.rcv_search_category_categoryTv);
            imageView = itemView.findViewById(R.id.rcv_search_category_imageView);
        }


        void bind(ChildModel childModel){

          //  nameTv.setText(childModel.getName());

            nameTv.setText(StringHighlight.highlight(childModel.getName(), txt,
                    ContextCompat.getColor(context,R.color.red400)));

            categoryTv.setText(childModel.getCategory());

            UtilsApp.getImageResize(childModel.getOriginalPic(),childModel.getThumb_pic(),imageView);



        }
    }
}
