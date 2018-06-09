package com.android.graphictools;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Bundle;

public class HwcDumpLayerActivity extends Activity {
    private static final String TAG = "GraphicTools::HwcDumpLayerActivity";
    private GraphicUtils mGU;
    private RadioGroup mRadioGroupVersion;
    private String mAndroidVersion = null;
    private int mStartDelay = 0;
    private int mHwcDumpFrameCnt = 0;
    private EditText mEditTextStartDelay;
    private EditText mEditTextDumpFrameNum;
    private TextView mTextViewDstDumpDir;
    private String mHwcDir = null;

    private boolean startHwcDump() {
        if (mAndroidVersion.equals("Android P")) {
            System.setProperty("vendor.debug.gralloc_afbc", "0");
            System.setProperty("vendor.debug.gralloc_afbce", "0");
            mGU.dumpsys("SurfaceFlinger");
            System.setProperty("vendor.debug.hwc_dump_en", "1");
            System.setProperty("vendor.debug.frame_dump_cnt", "1");
            mGU.dumpsys("SurfaceFlinger");
        } else if (mAndroidVersion.equals("Android O")) {
            System.setProperty("debug.gralloc_afbc", "0");
            System.setProperty("debug.gralloc_afbce", "0");
            mGU.dumpsys("SurfaceFlinger");
            System.setProperty("debug.hwc_dump_en", "1");
            System.setProperty("debug.frame_dump_cnt", "1");
            mGU.dumpsys("SurfaceFlinger");
        } else {
            System.setProperty("vendor.debug.gralloc_afbc", "0");
            System.setProperty("vendor.debug.gralloc_afbce", "0");
            mGU.dumpsys("SurfaceFlinger");
            System.setProperty("vendor.debug.hwc_dump_en", "1");
            System.setProperty("vendor.debug.frame_dump_cnt", "1");
            mGU.dumpsys("SurfaceFlinger");
        }
        return true;
    }
    private void showDstDumpPath() {
        mTextViewDstDumpDir.setText(mHwcDir);
    }
    private boolean getUserSettings() {
        mTextViewDstDumpDir = (TextView) findViewById(R.id.textViewDstDumpDir);

        mEditTextStartDelay = (EditText) findViewById(R.id.editTextStartDelay);
        mStartDelay = Integer.valueOf(mEditTextStartDelay.getText().toString());

        mEditTextDumpFrameNum = (EditText) findViewById(R.id.editTextDumpFrameNum);
        mHwcDumpFrameCnt = Integer.valueOf(mEditTextDumpFrameNum.getText().toString());

        RadioButton radioButton;
        mRadioGroupVersion = (RadioGroup) findViewById(R.id.radioGroupVersion);
        radioButton = (RadioButton) findViewById(mRadioGroupVersion.getCheckedRadioButtonId());
        mAndroidVersion = radioButton.getText().toString();
        if (mAndroidVersion.equals("Android P")) {
            mHwcDir = "/data/vendor/hwcdump/";
        } else if (mAndroidVersion.equals("Android O")) {
            mHwcDir = "/data/hwcdump/";
        } else {
            mHwcDir = "/data/vendor/hwcdump/";
        }

        Log.d(TAG,"mStartDelay:" + mStartDelay);
        Log.d(TAG, "mHwcDumpFrameCnt:" + mHwcDumpFrameCnt);
        Log.d(TAG, "mAndroidVersion:" + mAndroidVersion);
        Log.d(TAG, "mHwcDir:" + mHwcDir);
        return true;
    }
    private void startDumpButton() {
        Button btn = (Button) findViewById(R.id.buttonStartDump);
        btn .setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (false == getUserSettings()) return ;
                if (false == mGU.mkdirForHwcDump(mHwcDir)) {
                    Toast.makeText(HwcDumpLayerActivity.this,
                            "Can't create " + mHwcDir, Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Toast.makeText(HwcDumpLayerActivity.this,
                            "Dst directory " + mHwcDir, Toast.LENGTH_SHORT).show();
                }
                String message = null;
                if (startHwcDump()) {
                    message = "Dump hwc layer success!";
                } else {
                    message = "Dump hwc layer fail!";
                }
                Toast.makeText(HwcDumpLayerActivity.this, message, Toast.LENGTH_SHORT).show();
                showDstDumpPath();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hwc_dump_layer);

        mGU = new GraphicUtils();
        startDumpButton();
    }
}
