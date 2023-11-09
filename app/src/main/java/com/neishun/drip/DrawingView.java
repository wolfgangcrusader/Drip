package com.neishun.drip;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class DrawingView extends View {
    private Paint paint;
    private Path path;
    private Bitmap bitmap;
    private Canvas bitmapCanvas;
    private OnDrawingDoneListener listener;

    public DrawingView(Context context) {
        super(context);
        init(null, 0);
    }

    public DrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public DrawingView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        paint = new Paint();
        paint.setColor(Color.BLACK); // Default color
        paint.setAntiAlias(true);
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        path = new Path();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        // Create a new bitmap and canvas for drawing
        bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        bitmapCanvas = new Canvas(bitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // Draw the path so far
        canvas.drawBitmap(bitmap, 0, 0, null);
        canvas.drawPath(path, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                path.moveTo(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(x, y);
                break;
            case MotionEvent.ACTION_UP:
                // Draw the path onto the canvas
                bitmapCanvas.drawPath(path, paint);
                // Call the listener's method
                if (listener != null) {
                    listener.onDrawingDone(getBitmap());
                }
                // Reset the path so we don't draw it again
                path.reset();
                break;
            default:
                return false;
        }

        // Request a redraw
        invalidate();
        return true;
    }

    public void setOnDrawingDoneListener(OnDrawingDoneListener listener) {
        this.listener = listener;
    }

    // Interface for the listener
    public interface OnDrawingDoneListener {
        void onDrawingDone(Bitmap bitmap);
    }

    // Method to capture the drawing as a Bitmap
    public Bitmap getBitmap() {
        return bitmap.copy(bitmap.getConfig(), true);
    }
}
