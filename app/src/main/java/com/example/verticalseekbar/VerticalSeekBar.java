package com.example.verticalseekbar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class VerticalSeekBar extends android.support.v7.widget.AppCompatSeekBar {

    Paint linePaint= new Paint();
    Paint textPaint= new Paint(Paint.LINEAR_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);

    public VerticalSeekBar(Context context) {
        super(context);
    }

    public VerticalSeekBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public VerticalSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(h, w, oldh, oldw);
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(heightMeasureSpec, widthMeasureSpec);
        setMeasuredDimension(getMeasuredHeight(), getMeasuredWidth());
    }


    protected void onDraw(Canvas c) {
        int textSize=30;
        int numOfTicks=100;
        int lineLength=70/2;

        setProgressDrawable(null);
        linePaint.setColor(Color.BLACK);
        textPaint.setColor(Color.BLACK);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTextSize(textSize);

        int center=getMeasuredWidth()/2;
        float height=getHeight()-(getPaddingStart()+getPaddingEnd());
        float padding=height/(float)numOfTicks;
        float startY=getPaddingStart();
        for(int i=0;i<numOfTicks;i++){
            if(i%10==0){
                linePaint.setStrokeWidth(4f);
                c.drawLine(center-lineLength,startY,center+lineLength,startY,linePaint);
                c.drawText((100-i)+"",center-100,startY+(float)textSize/3,textPaint);
            }else{
                linePaint.setStrokeWidth(2f);
                c.drawLine(center-(float)(lineLength/2),startY,center+(float)lineLength/2,startY,linePaint);
            }
            startY+=padding;
        }
        linePaint.setStrokeWidth(4f);
        c.drawText("0",center-100,getHeight()-getPaddingEnd()+(float)textSize/3,textPaint);
        c.drawLine(center-lineLength,getHeight()-getPaddingEnd(),center+lineLength,getHeight()-getPaddingEnd(),linePaint);

        c.rotate(-90);
        c.translate(-getHeight(),0);
        super.onDraw(c);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!isEnabled()) {
            return false;
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:
            	int i=0;
            	i=getMax() - (int) (getMax() * event.getY() / getHeight());
                setProgress(i);
                onSizeChanged(getWidth(), getHeight(), 0, 0);
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        return true;
    }
    
}