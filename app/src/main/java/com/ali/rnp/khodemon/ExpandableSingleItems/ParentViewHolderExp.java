package com.ali.rnp.khodemon.ExpandableSingleItems;

import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatDelegate;

import com.ali.rnp.khodemon.MultiCheckExpand.MultiCheckGroup;
import com.ali.rnp.khodemon.MyLibrary.MyTextView;
import com.ali.rnp.khodemon.R;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

import static android.view.animation.Animation.RELATIVE_TO_SELF;

public class ParentViewHolderExp extends GroupViewHolder {

    private MyTextView groupName;
    private ImageView arrow;
    private ImageView icon;

    public ParentViewHolderExp(View itemView) {
        super(itemView);
        groupName =  itemView.findViewById(R.id.list_item_parent_exp_name);
        arrow =  itemView.findViewById(R.id.list_item_parent_exp_arrow);
        icon =  itemView.findViewById(R.id.list_item_parent_exp_icon);

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

    }

    public void setGroupingTitle(ExpandableGroup group) {


            groupName.setText(group.getTitle());
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
//        icon.setBackgroundResource(((SingleCheckItemsExp) group).getIconResId());

        if (group instanceof SingleCheckItemsExp) {
            icon.setBackgroundResource(((SingleCheckItemsExp) group).getIconResId());

        } else if (group instanceof MultiCheckGroup) {
            icon.setBackgroundResource(((MultiCheckGroup) group).getIconResId());

        }
        //TODO Cast Class Expetion



    }



    @Override
    public void expand() {
        animateExpand();
    }

    @Override
    public void collapse() {
        animateCollapse();
    }

    private void animateExpand() {
        RotateAnimation rotate =
                new RotateAnimation(360, 180, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(300);
        rotate.setFillAfter(true);
        arrow.setAnimation(rotate);
    }

    private void animateCollapse() {
        RotateAnimation rotate =
                new RotateAnimation(180, 360, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(300);
        rotate.setFillAfter(true);
        arrow.setAnimation(rotate);
    }
}
