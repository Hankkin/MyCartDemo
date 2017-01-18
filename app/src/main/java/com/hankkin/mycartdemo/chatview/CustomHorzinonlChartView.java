package com.hankkin.mycartdemo.chatview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.hankkin.mycartdemo.R;

/**
 * Created by Administrator on 2017/1/13.
 */

public class CustomHorzinonlChartView extends View {

    private Paint mChartPaint;
    private int drawColor;
    private int mHeight, mWidth, mChartWidth;
    private int number;
    private static final String TAG = "CustomHorzinonlChartVie";
    private String leftText,rightText;
    private Paint textPaint;

    public void setNumber(int number) {
        this.number = number;
        invalidate();
    }


    public void setLeftText(String leftText){
        this.leftText = leftText;
        invalidate();
    }

    public void setRightText(String rightText){
        this.rightText = rightText;
        invalidate();
    }
    public CustomHorzinonlChartView(Context context) {
        this(context, null);
    }

    public CustomHorzinonlChartView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomHorzinonlChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CustomCharView);
        drawColor = ta.getColor(R.styleable.CustomCharView_drawColor, Color.GREEN);
        ta.recycle();
        initialize();
    }

    private void initialize() {
        mChartPaint = new Paint();
        mChartPaint.setAntiAlias(true);
        textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setStyle(Paint.Style.FILL);

        leftText = "左边文字";
        rightText = "右边文字";
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.e(TAG, "onLayout()");
        mWidth = getWidth();
        mHeight = getHeight();
    }

    /**
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.e(TAG, "onDraw()");
        mChartPaint.setColor(drawColor);
        mChartPaint.setStyle(Paint.Style.FILL);

        drawBg(canvas);
        drawPlan(canvas);
        drawText(canvas);
    }

    private void drawBg(Canvas canvas) {
        mChartPaint.setColor(getResources().getColor(R.color.xyColor));
        RectF rectFBg = new RectF();
        rectFBg.top = 0;
        rectFBg.bottom = mHeight;
        rectFBg.left = 0;
        rectFBg.right = mWidth;
        canvas.drawRoundRect(rectFBg, mHeight/2, mHeight/2, mChartPaint);
    }

    private void drawPlan(Canvas canvas) {
        RectF rectF = new RectF();
        rectF.top = 0;
        rectF.bottom = mHeight;
        rectF.left = 0;
        rectF.right = number * mWidth / 100.f;

        mChartPaint.setColor(drawColor);
        canvas.drawRoundRect(rectF, mHeight/2, mHeight/2, mChartPaint);
    }

    /**
     * 通过中心点得到基线
     * 指定文字在中间的位置，绘制文本公式为：
     * float baseLineY = centerY + (fontMetrics.bottom - fontMetrics.top) / 2  - fontMetrics.bottom;
     */
    private void drawText(Canvas canvas) {
        textPaint.setTextSize(mHeight/2);
        canvas.drawText(leftText,mHeight/2,getBaseLineY(mHeight / 2),textPaint);
        canvas.drawText(rightText,mWidth - textPaint.measureText(rightText)-(mHeight/2),getBaseLineY(mHeight / 2),textPaint);
    }

    private float getBaseLineY(float centerY){
        return centerY + (textPaint.getFontMetrics().bottom - textPaint.getFontMetrics().top) / 2 - textPaint.getFontMetrics().bottom;
    }
}
