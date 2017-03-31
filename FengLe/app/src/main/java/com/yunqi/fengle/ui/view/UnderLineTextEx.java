package com.yunqi.fengle.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.TextView;

import com.yunqi.fengle.util.DensityUtil;

/**
 * @Author: Huangweicai
 * @date 2017-02-17 22:43
 * @Description: 下划线输入框
 */

public class UnderLineTextEx extends TextView {

    private Paint mPaint;

    public UnderLineTextEx(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.parseColor("#ffdededf"));
        setPadding(0,DensityUtil.dip2px(context,5),0, DensityUtil.dip2px(context,5));
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 画底线
        canvas.drawLine(0, this.getHeight() - 1, this.getWidth() - 1, this.getHeight() - 1, mPaint);
    }
}
