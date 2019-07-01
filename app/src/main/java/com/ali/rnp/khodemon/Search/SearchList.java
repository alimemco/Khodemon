package com.ali.rnp.khodemon.Search;

import java.util.ArrayList;

public class SearchList {

    private static final String TAG = "SearchList";
    private ArrayList<? extends GroupModel> groupList;

    public SearchList(ArrayList<? extends GroupModel> groupList) {
        this.groupList = groupList;
    }


    private int numberOfItemsInGroup(int groupIndex) {
        return groupList.get(groupIndex).getItemCount() + 1;
    }


    public int getItemCount() {
        int count = 0;
        for (int i = 0; i < groupList.size(); i++) {
            count += numberOfItemsInGroup(i);
        }

        return count;
    }


    public SearchListPosition getUnflattenedPosition(int flPos) {
        int groupItemCount;
        int adapted = flPos;
        for (int i = 0; i < groupList.size(); i++) {
            groupItemCount = numberOfItemsInGroup(i);
            if (adapted == 0) {
                return SearchListPosition.obtain(SearchListPosition.GROUP, i, -1, flPos);
            } else if (adapted < groupItemCount) {
                return SearchListPosition.obtain(SearchListPosition.CHILD, i, adapted - 1, flPos);
            }
            adapted -= groupItemCount;
        }
        throw new RuntimeException("Unknown state");
    }


    public GroupModel getGroup(SearchListPosition listPosition) {
        return groupList.get(listPosition.groupPos);
    }

}
