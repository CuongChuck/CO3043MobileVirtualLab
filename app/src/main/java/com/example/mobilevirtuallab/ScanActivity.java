package com.example.mobilevirtuallab;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraDevice;
import android.os.Bundle;
import android.app.Activity;
import android.view.MotionEvent;
import android.view.TextureView;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.RelativeLayout;
import android.Manifest;
import android.hardware.camera2.*;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

public class ScanActivity extends Activity {
    private TextureView textureView;
    private CameraDevice cameraDevice;
    SharedPreferences sp;
    RelativeLayout layout_joystick;
    RelativeLayout layout_slider;
    JoyStickClass js;
    SliderClass slider;

    @SuppressLint({"MissingInflatedId", "ClickableViewAccessibility"})
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        textureView = findViewById(R.id.textureView);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);
        } else {
            openCamera();
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

        slider = new SliderClass(getApplicationContext(), layout_slider, R.drawable.image_button);
        slider.setButtonSize(150, 150);
        slider.setLayoutSize(150, 500);
        slider.setLayoutAlpha(150);
        slider.setButtonAlpha(100);
        slider.setOffset(0);
        layout_slider.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                slider.drawSlider(arg1);
                return true;
            }
        });

    }
    private void openCamera() {
        CameraManager cameraManager = (CameraManager) getSystemService(CAMERA_SERVICE);
        try {
            String cameraId = cameraManager.getCameraIdList()[0]; // 0 for back camera
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