package com.hankkin.mycartdemo.chatview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import com.hankkin.mycartdemo.R;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hankkin on 16/12/11.
 * 注释:参考MyChartView原理相
 */

public class SingleView extends View {

    private Paint mPaint, mChartPaint;
    private Rect mBound;
    private int mStartWidth, mHeight, mWidth, mChartWidth, mSize;
    private int lineColor, leftColor, lefrColorBottom,selectLeftColor;
    private List<Float> list = new ArrayList<>();
    private getNumberListener listener;
    private int number = 1000;
    private int selectIndex = -1;
    private List<Integer> selectIndexRoles = new ArrayList<>();

    public void setList(List<Float> list) {
        this.list = list;
        mSize = getWidth() / 25;
        mStartWidth = getWidth() / 13;
        mChartWidth = getWidth() / 13 - mSize / 2;
        invalidate();
    }

    public SingleView(Context context) {
        this(context, null);
    }

    public SingleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SingleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.MyChartView, defStyleAttr, 0);
        int n = array.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = array.getIndex(i);
            switch (attr) {
                case R.styleable.MyChartView_xyColor:
                    lineColor = array.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.MyChartView_leftColor:
                    // 默认颜色设置为黑色
                    leftColor = array.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.MyChartView_leftColorBottom:
                    lefrColorBottom = array.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.MyChartView_selectLeftColor:
                    // 默认颜色设置为黑色
                    selectLeftColor = array.getColor(attr, Color.BLACK);
                    break;
                default:
                    bringToFront();
            }
        }
        array.recycle();
        init();
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
        mStartWidth = getWidth() / 13;
        mSize = mWidth / 25;
        mChartWidth = getWidth() / 13 - mSize / 2;
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mBound = new Rect();
        mChartPaint = new Paint();
        mChartPaint.setAntiAlias(true);
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if (hasWindowFocus) {

        }
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        if (visibility == VISIBLE) {
            mSize = getWidth() / 25;
            mStartWidth = getWidth() / 13;
            mChartWidth = getWidth() / 13 - mSize / 2;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(lineColor);
        for (int i = 0; i < 12; i++) {

            //画数字
            mPaint.setTextSize(35);
            mPaint.setTextAlign(Paint.Align.CENTER);
            mPaint.getTextBounds(String.valueOf(i + 1) + "", 0, String.valueOf(i).length(), mBound);
            canvas.drawText(String.valueOf(i + 1) + "月", mStartWidth - mBound.width() * 1 / 2,
                mHeight - 60 + mBound.height() * 1 / 2, mPaint);
            mStartWidth += getWidth() / 13;
        }

        for (int i = 0; i < 12; i++) {
            int size = mHeight / 120;
            mChartPaint.setStyle(Paint.Style.FILL);
            if (list.size() > 0) {
                if (selectIndexRoles.contains(i)){
                    mChartPaint.setShader(null);
                    mChartPaint.setColor(selectLeftColor);
                }
                else {
                    LinearGradient lg = new LinearGradient(mChartWidth, mChartWidth + mSize, mHeight - 100,
                        (float) (mHeight - 100 - list.get(i) * size), lefrColorBottom, leftColor, Shader.TileMode.MIRROR);
                    mChartPaint.setShader(lg);
                }
                //画柱状图
                RectF rectF = new RectF();
                rectF.left = mChartWidth;
                rectF.right = mChartWidth + mSize;
                rectF.bottom = mHeight - 100;
                rectF.top = mHeight - 100 - list.get(i) * size;
                canvas.drawRoundRect(rectF, 20, 20, mChartPaint);
                mChartWidth += getWidth() / 13;
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        int x = (int) ev.getX();
        int y = (int) ev.getY();
        int left = 0;
        int top = 0;
        int right = mWidth / 12;
        int bottom = mHeight - 100;
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                for (int i = 0; i < 12; i++) {
                    Rect rect = new Rect(left, top, right, bottom);
                    left += mWidth / 12;
                    right += mWidth / 12;
                    if (rect.contains(x, y)) {
                        if (listener != null){
                            listener.getNumber(i, x, y);
                            number = i;
                            selectIndex = i;
                            selectIndexRoles.clear();
                            selectIndexRoles.add(selectIndex);
                            invalidate();
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_UP:

                break;
        }
        return true;
    }

    public void setListener(getNumberListener listener) {
        this.listener = listener;
    }

    public interface getNumberListener {
        void getNumber(int number, int x, int y);
    }
}
