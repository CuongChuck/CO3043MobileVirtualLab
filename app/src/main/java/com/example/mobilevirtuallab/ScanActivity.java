package com.example.mobilevirtuallab;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraDevice;
import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.TextureView;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.Manifest;
import android.hardware.camera2.*;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

public class ScanActivity extends Activity {
    private TextureView textureView;
    private CameraDevice cameraDevice;
    private SharedPreferences sp;
    RelativeLayout layout_joystick;
    RelativeLayout layout_slider;
    JoyStickClass js;
    SliderClass sd;
    private ImageView modelImageView;
    private float initialImageSize = 200f;
    private float minZoom = 0.5f;
    private float maxZoom = 2.0f;

    @SuppressLint({"MissingInflatedId", "ClickableViewAccessibility"})
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        textureView = findViewById(R.id.textureView);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("image")) {
            int imageResourceId = intent.getIntExtra("image", 0);
            modelImageView = findViewById(R.id.model_image_view);
            if (modelImageView != null && imageResourceId != 0) {
                modelImageView.setImageResource(imageResourceId);
            }
        }

        sp = getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);

        layout_joystick = (RelativeLayout)findViewById(R.id.layout_joystick);

        js = new JoyStickClass(getApplicationContext(), layout_joystick, R.drawable.image_button);
        js.setStickSize(150, 150);
        js.setLayoutSize(400, 400);
        js.setLayoutAlpha(150);
        js.setStickAlpha(100);
        js.setOffset(90);
        js.setMinimumDistance(50);
        js.initStickPosition();

        layout_joystick.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                js.drawStick(arg1);
                return true;
            }
        });

        layout_slider = (RelativeLayout)findViewById(R.id.layout_slider);
        sd = new SliderClass(getApplicationContext(), layout_slider, R.drawable.image_button);
        sd.setButtonSize(150, 150);
        sd.setLayoutSize(150, 500);
        sd.setLayoutAlpha(150);
        sd.setButtonAlpha(100);
        sd.setOffset(0);

        sd.setOnSliderMovedListener(new SliderClass.OnSliderMovedListener() {
            @Override
            public void onSliderMoved(float position) {
                if (modelImageView != null) {
                    float minScale = 0.5f;
                    float maxScale = 3.0f;
                    float scale = minScale + position * (maxScale - minScale);
                    modelImageView.setScaleX(scale);
                    modelImageView.setScaleY(scale);
                }
            }
        });

        layout_slider.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                sd.drawSlider(arg1);
                return true;
            }
        });

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);
        } else {
            openCamera();
        }
    }

    private void openCamera() {
        CameraManager cameraManager = (CameraManager) getSystemService(CAMERA_SERVICE);
        try {
            String cameraId = cameraManager.getCameraIdList()[0];
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            cameraManager.openCamera(cameraId, new CameraDevice.StateCallback() {
                @Override
                public void onOpened(@NonNull CameraDevice camera) {
                    cameraDevice = camera;
                }

                @Override
                public void onDisconnected(@NonNull CameraDevice camera) {
                    camera.close();
                }

                @Override
                public void onError(@NonNull CameraDevice camera, int error) {
                    camera.close();
                }
            }, null);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (cameraDevice != null) {
            cameraDevice.close();
        }
    }
}