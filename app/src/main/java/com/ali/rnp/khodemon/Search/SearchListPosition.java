package com.ali.rnp.khodemon.Search;


import java.util.ArrayList;

public class SearchListPosition {

    public final static int EMPTY = 3;

    public final static int GROUP = 2;

    public final static int CHILD = 1;

    public int groupPos;

    public int childPos;

    public int type;

    /**
     * The position of the item in the flat list (optional, used internally when
     * the corresponding flat list position for the group or child is known)
     */
    int flatListPos;


    private static final int MAX_POOL_SIZE = 5;
    private static ArrayList<SearchListPosition> sPool =
            new ArrayList<>(MAX_POOL_SIZE);


    public static SearchListPosition obtain(int type, int groupPos, int childPos,
                                                int flatListPos) {
        SearchListPosition slp = getRecycledOrCreate();
        slp.type = type;
        slp.groupPos = groupPos;
        slp.childPos = childPos;
        slp.flatListPos = flatListPos;
        return slp;
    }

    private static SearchListPosition getRecycledOrCreate() {
        SearchListPosition slp;
        synchronized (sPool) {
            if (sPool.size() > 0) {
                slp = sPool.remove(0);
            } else {
                return new SearchListPosition();
            }
        }
        //elp.resetState();
        return slp;
    }

}
