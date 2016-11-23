package org.shenkar.auval.codesamples;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Official training:
 * https://developer.android.com/training/custom-views/custom-drawing.html
 */
public class MyAnimatedGraphicsView extends View {

    int height;
    /**
     * the "brush"
     */
    Paint paint;
    int width;

    public MyAnimatedGraphicsView(Context context) {
        super(context);
        init();
    }

    public MyAnimatedGraphicsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyAnimatedGraphicsView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true); // smooth lines
        paint.setTextSize(24);
        paint.setStrokeWidth(1);
        paint.setStyle(Paint.Style.FILL);

    }

    /**
     * @param canvas the surface to draw to
     */
    @Override
    protected void onDraw(Canvas canvas) {

        paint.setColor(0xff_ff_44_44); // red

        int save = canvas.save();
        long now = System.currentTimeMillis();

        float rotation = calcMovement(now);

        canvas.rotate(rotation * 360, width / 2, height / 2);

        canvas.drawLine(0, height / 2, width, height / 2, paint);

        float dotPlace = calcDotPlace(now);

        canvas.drawCircle(dotPlace * width, height / 2, 10, paint);

        canvas.restoreToCount(save);

        paint.setColor(0xff_dd_dd_dd); // light grey

        canvas.drawText("Low Level Animation", 10, 50, paint);

        postInvalidateOnAnimation();
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        height = getHeight();
        width = getWidth();
    }

    /**
     * I want the red dot to go from one end of the line (0) to the other (w) and back during one cycle
     */
    private float calcDotPlace(long now) {
        // I want the dot to go in one direction for 2 seconds, and then 2 seconds the other way
        int position = (int) (now % 4000);
        if (position < 2000) {
            return position / 2000f;
        }
        return (4000 - position) / 2000f;
    }

    /**
     * @return 0..1
     */
    private float calcMovement(long now) {
        // I want a full  sine cycle every 5 seconds
        // it will be a function of time
        // value 0..1
        float position = (now % 5000) / 5000f; // 5000 milliseconds = 5 seconds

        return position;
    }

}