package com.example.mobilevirtuallab;

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

    /**
     * Constructor
     * @param context The context of the application
     * @param layout The layout where the slider will be placed
     * @param button_res_id The resource ID for the button image
     */
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

    /**
     * Process touch events and update button position
     * @param arg1 The motion event
     */
    public void drawSlider(MotionEvent arg1) {
        float centerX = params.width / 2;
        float y = arg1.getY();

        // Clamp Y position within bounds
        if (y < OFFSET + (button_height / 2)) {
            y = OFFSET + (button_height / 2);
        } else if (y > params.height - OFFSET - (button_height / 2)) {
            y = params.height - OFFSET - (button_height / 2);
        }

        if (arg1.getAction() == MotionEvent.ACTION_DOWN) {
            // Check if touch is within horizontal bounds of the slider
            if (Math.abs(arg1.getX() - centerX) <= button_width) {
                draw.position(centerX, y);
                draw();
                touch_state = true;
            }
        } else if (arg1.getAction() == MotionEvent.ACTION_MOVE && touch_state) {
            draw.position(centerX, y);
            draw();
        } else if (arg1.getAction() == MotionEvent.ACTION_UP) {
            mLayout.removeView(draw);
            touch_state = false;
        }
    }

    /**
     * Set the offset from the edge of the layout
     * @param offset The offset value
     */
    public void setOffset(int offset) {
        OFFSET = offset;
    }

    /**
     * Set the alpha (transparency) of the button
     * @param alpha The alpha value (0-255)
     */
    public void setButtonAlpha(int alpha) {
        BUTTON_ALPHA = alpha;
        paint.setAlpha(alpha);
    }

    /**
     * Set the alpha (transparency) of the layout background
     * @param alpha The alpha value (0-255)
     */
    public void setLayoutAlpha(int alpha) {
        LAYOUT_ALPHA = alpha;
        mLayout.getBackground().setAlpha(alpha);
    }

    /**
     * Set the size of the button
     * @param width The width of the button
     * @param height The height of the button
     */
    public void setButtonSize(int width, int height) {
        button = Bitmap.createScaledBitmap(button, width, height, false);
        button_width = button.getWidth();
        button_height = button.getHeight();
    }

    /**
     * Set the size of the layout
     * @param width The width of the layout
     * @param height The height of the layout
     */
    public void setLayoutSize(int width, int height) {
        params.width = width;
        params.height = height;
    }

    public float getPosition() {
        return draw.y + (button_height / 2);
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
