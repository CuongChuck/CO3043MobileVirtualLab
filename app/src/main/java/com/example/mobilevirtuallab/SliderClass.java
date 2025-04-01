package com.example.mobilevirtuallab;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;

public class SliderClass {
    public static final int SLIDE_NONE = 0;
    public static final int SLIDE_ZOOM_IN = 1;
    public static final int SLIDE_ZOOM_OUT = 2;

    private int BUTTON_ALPHA = 200;
    private int LAYOUT_ALPHA = 200;
    private int OFFSET = 0;
    private Context mContext;
    private ViewGroup mLayout;
    private LayoutParams params;
    private int button_width, button_height;
    private boolean touch_state = false;
    private DrawCanvas draw;
    private Paint paint;
    private Bitmap button;

    private float prevY = 0;
    private int slideDirection = SLIDE_NONE;

    public interface OnSliderMovedListener {
        void onSliderMoved(float position);
    }

    private OnSliderMovedListener mListener;

    public void setOnSliderMovedListener(OnSliderMovedListener listener) {
        mListener = listener;
    }

    public SliderClass(Context context, ViewGroup layout, int button_res_id) {
        mContext = context;
        button = BitmapFactory.decodeResource(mContext.getResources(), button_res_id);
        button_width = button.getWidth();
        button_height = button.getHeight();
        draw = new DrawCanvas(mContext);
        paint = new Paint();
        mLayout = layout;
        params = mLayout.getLayoutParams();
    }

    public void drawSlider(MotionEvent arg1) {
        float centerX = params.width / 2;
        float y = arg1.getY();
        if (y < OFFSET + (button_height / 2)) {
            y = OFFSET + (button_height / 2);
        } else if (y > params.height - OFFSET - (button_height / 2)) {
            y = params.height - OFFSET - (button_height / 2);
        }

        if (arg1.getAction() == MotionEvent.ACTION_DOWN) {
            if (Math.abs(arg1.getX() - centerX) <= button_width) {
                draw.position(centerX, y);
                draw();
                touch_state = true;
                prevY = y;
                slideDirection = SLIDE_NONE;
                reportPosition(y);
            }
        } else if (arg1.getAction() == MotionEvent.ACTION_MOVE && touch_state) {
            if (Math.abs(y - prevY) > 5) {
                slideDirection = (y < prevY) ? SLIDE_ZOOM_IN : SLIDE_ZOOM_OUT;
                prevY = y;
            }

            draw.position(centerX, y);
            draw();
            reportPosition(y);
        } else if (arg1.getAction() == MotionEvent.ACTION_UP) {
            mLayout.removeView(draw);
            touch_state = false;
            slideDirection = SLIDE_NONE;
        }
    }

    private void reportPosition(float y) {
        if (mListener != null) {
            float bottomY = params.height - OFFSET - (button_height / 2);
            float topY = OFFSET + (button_height / 2);
            float range = bottomY - topY;
            float normalizedPos = 1.0f - ((y - topY) / range);

            mListener.onSliderMoved(normalizedPos);
        }
    }

    public int getSlideDirection() {
        return slideDirection;
    }

    public void setOffset(int offset) {
        OFFSET = offset;
    }

    public void setButtonAlpha(int alpha) {
        BUTTON_ALPHA = alpha;
        paint.setAlpha(alpha);
    }

    public void setLayoutAlpha(int alpha) {
        LAYOUT_ALPHA = alpha;
        mLayout.getBackground().setAlpha(alpha);
    }

    public void setButtonSize(int width, int height) {
        button = Bitmap.createScaledBitmap(button, width, height, false);
        button_width = button.getWidth();
        button_height = button.getHeight();
    }

    public void setLayoutSize(int width, int height) {
        params.width = width;
        params.height = height;
    }

    private void draw() {
        try {
            mLayout.removeView(draw);
        } catch (Exception e) { }
        mLayout.addView(draw);
    }

    private class DrawCanvas extends View {
        float x, y;

        private DrawCanvas(Context mContext) {
            super(mContext);
        }

        public void onDraw(Canvas canvas) {
            canvas.drawBitmap(button, x, y, paint);
        }

        private void position(float pos_x, float pos_y) {
            x = pos_x - (button_width / 2);
            y = pos_y - (button_height / 2);
        }
    }
}
