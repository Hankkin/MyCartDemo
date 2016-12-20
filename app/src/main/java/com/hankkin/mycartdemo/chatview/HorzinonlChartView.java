package com.hankkin.mycartdemo.chatview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import com.hankkin.mycartdemo.R;

/**
 * Created by Hankkin on 16/12/11.
 * 注释:
 */

public class HorzinonlChartView extends View {

    private Paint mPaint,mChartPaint;
    private int lineColor ,leftColor;
    private int mStartWidth,mHeight,mWidth,mChartWidth,mSize;
    private int number;

    public void setNumber(int number) {
        this.number = number;
    }

    public HorzinonlChartView(Context context) {
        this(context, null);
    }

    public HorzinonlChartView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HorzinonlChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.MyChartView, defStyleAttr, 0);
        int n = array.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = array.getIndex(i);
            switch (attr) {
                case R.styleable.MyChartView_leftColor:
                    // 默认颜色设置为黑色
                    leftColor = array.getColor(attr, Color.BLACK);
                    break;
                default:
                    break;
            }
        }
        array.recycle();
        init();
    }

    private void init(){
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mChartPaint = new Paint();
        mChartPaint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width;
        int height;
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            width = widthSize * 1 / 2;
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            height = heightSize * 1 / 2;
        }

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mWidth = getWidth();
        mHeight = getHeight();
        mSize = mWidth/25;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mChartPaint.setColor(leftColor);
        mChartPaint.setStyle(Paint.Style.FILL);
        int size = mWidth / 110;

        RectF rectF = new RectF();
        rectF.top = 0;
        rectF.bottom = mSize;
        rectF.left = 0;
        rectF.right = number * size;
        canvas.drawRoundRect(rectF, 30, 30, mChartPaint);
    }
}
