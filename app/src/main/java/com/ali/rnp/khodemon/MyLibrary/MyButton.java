package com.ali.rnp.khodemon.MyLibrary;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.ali.rnp.khodemon.MyApplication;
import com.ali.rnp.khodemon.R;

import androidx.appcompat.widget.AppCompatButton;

public class MyButton extends AppCompatButton {

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


    private Drawable drawableRight;
    private Drawable drawableLeft;
    private Drawable drawableTop;
    private Drawable drawableBottom;

    int actionX, actionY;

    private DrawableClickListener clickListener;

    public MyButton(Context context) {
        super(context);
        setupFont(null);

    }

    public MyButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupFont(attrs);
    }

    public MyButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setupFont(attrs);
    }

    private void setupFont(AttributeSet attrs) {


        if (attrs != null && !isInEditMode()) {

            TypedArray attributes = getContext().obtainStyledAttributes(attrs, R.styleable.MyViewFont);

            try {
                int Font = attributes.getInteger(R.styleable.MyViewFont_fontCustom, SHP_IRAN_SANS_MOBILE);

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


    @Override
    public void setCompoundDrawables(Drawable left, Drawable top,
                                     Drawable right, Drawable bottom) {
        if (left != null) {
            drawableLeft = left;
        }
        if (right != null) {
            drawableRight = right;
        }
        if (top != null) {
            drawableTop = top;
        }
        if (bottom != null) {
            drawableBottom = bottom;
        }
        super.setCompoundDrawables(left, top, right, bottom);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Rect bounds;
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            actionX = (int) event.getX();
            actionY = (int) event.getY();
            if (drawableBottom != null
                    && drawableBottom.getBounds().contains(actionX, actionY)) {
                clickListener.onClick(DrawableClickListener.DrawablePosition.BOTTOM);
                return super.onTouchEvent(event);
            }

            if (drawableTop != null
                    && drawableTop.getBounds().contains(actionX, actionY)) {
                clickListener.onClick(DrawableClickListener.DrawablePosition.TOP);
                return super.onTouchEvent(event);
            }

            // this works for left since container shares 0,0 origin with bounds
            if (drawableLeft != null) {
                bounds = null;
                bounds = drawableLeft.getBounds();

                int x, y;
                int extraTapArea = (int) (13 * getResources().getDisplayMetrics().density  + 0.5);

                x = actionX;
                y = actionY;

                if (!bounds.contains(actionX, actionY)) {
                    /* Gives the +20 area for tapping. */
                    x = (int) (actionX - extraTapArea);
                    y = (int) (actionY - extraTapArea);

                    if (x <= 0)
                        x = actionX;
                    if (y <= 0)
                        y = actionY;

                    /** Creates square from the smallest value */
                    if (x < y) {
                        y = x;
                    }
                }

                if (bounds.contains(x, y) && clickListener != null) {
                    clickListener
                            .onClick(DrawableClickListener.DrawablePosition.LEFT);
                    event.setAction(MotionEvent.ACTION_CANCEL);
                    return false;

                }
            }

            if (drawableRight != null) {

                bounds = null;
                bounds = drawableRight.getBounds();

                int x, y;
                int extraTapArea = 30;

                /*
                 * IF USER CLICKS JUST OUT SIDE THE RECTANGLE OF THE DRAWABLE
                 * THAN ADD X AND SUBTRACT THE Y WITH SOME VALUE SO THAT AFTER
                 * CALCULATING X AND Y CO-ORDINATE LIES INTO THE DRAWBABLE
                 * BOUND. - this process help to increase the tappable area of
                 * the rectangle.
                 */
                x = (int) (actionX + extraTapArea);
                y = (int) (actionY - extraTapArea);

                /*Since this is right drawable subtract the value of x from the width
                 * of view. so that width - tappedarea will result in x co-ordinate in drawable bound.
                 */
                x = getWidth() - x;

                /*x can be negative if user taps at x co-ordinate just near the width.
                 * e.g views width = 300 and user taps 290. Then as per previous calculation
                 * 290 + 13 = 303. So subtract X from getWidth() will result in negative value.
                 * So to avoid this add the value previous added when x goes negative.
                 */

                if(x <= 0){
                    x += extraTapArea;
                }

                /* If result after calculating for extra tappable area is negative.
                 * assign the original value so that after subtracting
                 * extratapping area value doesn't go into negative value.
                 */

                if (y <= 0)
                    y = actionY;

                /*If drawble bounds contains the x and y points then move ahead.*/
                if (bounds.contains(x, y) && clickListener != null) {
                    clickListener.onClick(DrawableClickListener.DrawablePosition.RIGHT);
                    event.setAction(MotionEvent.ACTION_CANCEL);

                    return false;

                }
                return super.onTouchEvent(event);
            }

        }

        return super.onTouchEvent(event);

    }


    @Override
    protected void finalize() throws Throwable {
        drawableRight = null;
        drawableBottom = null;
        drawableLeft = null;
        drawableTop = null;
        super.finalize();
    }


    public void setDrawableClickListener(MyButton.DrawableClickListener listener) {
        this.clickListener = listener;
    }

    public interface DrawableClickListener {

        public static enum DrawablePosition { TOP, BOTTOM, LEFT, RIGHT };
        public void onClick(MyButton.DrawableClickListener.DrawablePosition target);
    }
}
