package com.learn.faisal.cascadelayout.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.learn.faisal.cascadelayout.R;

/**
 * Created by TD-Android
 * on 7/12/2017.
 */
public class CascadeLayout extends ViewGroup {
    private int mHorizontalSpacing;
    private int mVerticalSpacing;

    public CascadeLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CascadeLayout);

        try {
            mHorizontalSpacing = ta.getDimensionPixelSize(R.styleable.CascadeLayout_horizontal_spacing
                    , R.dimen.cascade_horizontal_spacing);
            mVerticalSpacing = ta.getDimensionPixelSize(R.styleable.CascadeLayout_vertical_spacing,
                    R.dimen.cascade_vertical_spacing);
        } finally {
            ta.recycle();
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = 0;
        int height = getPaddingTop();
        final int childCount = getChildCount();

        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);

            measureChild(child, widthMeasureSpec, heightMeasureSpec);

            LayoutParams lp = (LayoutParams) child.getLayoutParams();
            width = getPaddingLeft() + mHorizontalSpacing * i;
            lp.x = width;
            lp.y = height;

            width += child.getMeasuredWidth();
            height += mVerticalSpacing;

        }

        width += getPaddingRight();
        height += getChildAt(childCount - 1).getMeasuredHeight() + getPaddingBottom();

        setMeasuredDimension(resolveSize(width, widthMeasureSpec), resolveSize(height, heightMeasureSpec));


    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
        final int childCount = getChildCount();

        for (int l = 0; l < childCount; l++) {

            View child = getChildAt(i);

            LayoutParams lp = (LayoutParams) child.getLayoutParams();
            child.layout(lp.x, lp.y, lp.x + child.getMeasuredWidth(), lp.y + child.getMeasuredHeight());
        }
    }

    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
//        return super.checkLayoutParams(p);
        return p instanceof LayoutParams;
    }

    @Override
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new LayoutParams(p.width, p.height);
    }

    @Override
    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }

    public static class LayoutParams extends ViewGroup.LayoutParams {

        int x, y;

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }
    }
}
