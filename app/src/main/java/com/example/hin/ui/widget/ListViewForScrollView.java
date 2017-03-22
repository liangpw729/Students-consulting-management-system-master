package com.example.hin.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by WWF on 2016/6/6.
 */
public class ListViewForScrollView extends ListView {

    public ListViewForScrollView(Context context) {
        this(context, null);
    }

    public ListViewForScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ListViewForScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
