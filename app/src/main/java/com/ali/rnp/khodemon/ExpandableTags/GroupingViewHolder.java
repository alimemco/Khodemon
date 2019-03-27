package com.ali.rnp.khodemon.ExpandableTags;

import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.ali.rnp.khodemon.ExpandableRecylerView.Genre;
import com.ali.rnp.khodemon.ExpandableRecylerView.multicheck.MultiCheckGenre;
import com.ali.rnp.khodemon.ExpandableRecylerView.singlecheck.SingleCheckGenre;
import com.ali.rnp.khodemon.MyLibrary.MyTextView;
import com.ali.rnp.khodemon.R;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

import static android.view.animation.Animation.RELATIVE_TO_SELF;

public class GroupingViewHolder extends GroupViewHolder {

    private MyTextView groupName;
    private ImageView arrow;
    private ImageView icon;

    public GroupingViewHolder(View itemView) {
        super(itemView);
        groupName =  itemView.findViewById(R.id.list_item_group_name);
        arrow =  itemView.findViewById(R.id.list_item_group_arrow);
        icon =  itemView.findViewById(R.id.list_item_group_icon);
    }

    public void setGroupingTitle(ExpandableGroup group) {

        if (group instanceof SingleCheckGroup) {
            groupName.setText(group.getTitle());
            icon.setBackgroundResource(((SingleCheckGroup) group).getIconResId());
        }
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
