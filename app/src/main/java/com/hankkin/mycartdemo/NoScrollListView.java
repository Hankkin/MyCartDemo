package com.hankkin.mycartdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * 自定义ListView，<span style="font-family: Arial, Helvetica, sans-serif;">解决ScrollView嵌套冲突问题</span>
 * 
 * @author king
 * 
 */

public class NoScrollListView extends ListView {
	public NoScrollListView(Context context) {
		super(context);
	}

	public NoScrollListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public NoScrollListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}


	/**
	 *  重写Listview
	 *  覆盖下面的方法即可：
	 * @param widthMeasureSpec
	 * @param heightMeasureSpec
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(
			Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
}