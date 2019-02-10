package com.ali.rnp.khodemon.MyLibrary;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.ali.rnp.khodemon.MyApplication;
import com.ali.rnp.khodemon.R;

import androidx.appcompat.widget.AppCompatEditText;

public class MyEditText extends AppCompatEditText {

    private static final int BYEKAN_FONT = 0;
    private static final int IRAN_SANS_FONT = 1;
    private static final int IRAN_SANS_BOLD_FONT = 2;
    private static final int SHP_IRAN_SANS = 3;
    private static final int SHP_IRAN_SANS_BOLD = 4;
    private static final int SHP_IRAN_SANS_LIGHT = 5;
    private static final int SHP_IRAN_SANS_MOBILE = 6;
    private static final int SHP_IRAN_SANS_MOBILE_BOLD = 7;
    private static final int SHP_IRAN_SANS_MOBILE_LIGHT = 8;
    private static final int SHP_MATERIAL_DRAWER = 9;

    public MyEditText(Context context) {
        super(context);
        setupFont(null);
    }

    public MyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupFont(attrs);
    }

    public MyEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setupFont(attrs);
    }

    private void setupFont(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray attributes = getContext().obtainStyledAttributes(attrs, R.styleable.MyViewFont);

            try {
                int Font = attributes.getInteger(R.styleable.MyViewFont_fontCustom, BYEKAN_FONT);

                switch (Font) {
                    case BYEKAN_FONT:
                        setTypeface(MyApplication.getBYekan(getContext()));
                        break;

                    case IRAN_SANS_FONT:
                        setTypeface(MyApplication.getIranSans(getContext()));
                        break;

                    case IRAN_SANS_BOLD_FONT:
                        setTypeface(MyApplication.getIranSansBold(getContext()));
                        break;

                    case SHP_IRAN_SANS:
                        setTypeface(MyApplication.getShpIranSans(getContext()));
                        break;

                    case SHP_IRAN_SANS_BOLD:
                        setTypeface(MyApplication.getShpIranSansBold(getContext()));
                        break;


                    case SHP_IRAN_SANS_LIGHT:
                        setTypeface(MyApplication.getShpIranSansLight(getContext()));
                        break;


                    case SHP_IRAN_SANS_MOBILE:
                        setTypeface(MyApplication.getShpIranSansMoblie(getContext()));
                        break;


                    case SHP_IRAN_SANS_MOBILE_BOLD:
                        setTypeface(MyApplication.getShpIranSansMobileBold(getContext()));
                        break;


                    case SHP_IRAN_SANS_MOBILE_LIGHT:
                        setTypeface(MyApplication.getShpIranSansMobileLight(getContext()));
                        break;

                    case SHP_MATERIAL_DRAWER:
                        setTypeface(MyApplication.getShpMaterialDrawer(getContext()));
                        break;
                }

            } finally {
                invalidate();
                requestLayout();
                attributes.recycle();
            }
        }
    }
}
