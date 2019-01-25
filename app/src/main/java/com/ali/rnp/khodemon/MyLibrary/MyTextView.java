package com.ali.rnp.khodemon.MyLibrary;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.ali.rnp.khodemon.MyApplication;
import com.ali.rnp.khodemon.R;

import androidx.appcompat.widget.AppCompatTextView;

public class MyTextView extends AppCompatTextView {

    private static final int BYEKAN_FONT = 0;
    private static final int IRAN_SANS_FONT = 1;
    private static final int IRAN_SANS_BOLD_FONT = 2;

    public MyTextView(Context context) {
        super(context);
        setupFont(null);
    }

    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupFont(attrs);
    }

    public MyTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setupFont(attrs);
    }

    private void setupFont(AttributeSet attrs) {
        if (attrs!=null){
            TypedArray attributes = getContext().obtainStyledAttributes(attrs, R.styleable.MyTextView);

            try {
                int Font  = attributes.getInteger(R.styleable.MyTextView_fontCustom,BYEKAN_FONT);

                switch (Font){
                    case BYEKAN_FONT:
                        setTypeface(MyApplication.getBYekan(getContext()));
                        break;

                    case IRAN_SANS_FONT:
                        setTypeface(MyApplication.getIranSans(getContext()));
                        break;

                    case IRAN_SANS_BOLD_FONT:
                        setTypeface(MyApplication.getIranSansBold(getContext()));
                        break;
                }

            }finally {
                invalidate();
                requestLayout();
                attributes.recycle();
            }
        }
    }
}
