package com.android.graphictools;

import android.app.Activity;
import android.os.Bundle;

import android.graphics.Bitmap;
import android.widget.Button;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;

public class FileDecodeActivity extends Activity {
    private static final String TAG = "GraphicTools::FileDecodeActivity";
    String mSrcFilePath = null;
    String mDstFilePath = null;
    int mSampleSize = 1;
    Bitmap.Config mPreColorType = Bitmap.Config.ARGB_8888;
    boolean mScale = false;
    boolean mShowBitmap = false;

    GraphicUtils mGU;
    EditText mEditTextSrcFileName;
    TextView mTextViewDstFilePath;
    TextView mTextViewPerformance;
    RadioGroup mRadioGroupSampleSize;
    RadioGroup mRadioGroupPreColorType;
    RadioGroup mRadioGroupScale;
    RadioGroup mRadioGroupShowBitmap;

    private void showDstFilePath() {
        mTextViewDstFilePath.setText(mDstFilePath);
    }
    private void showDecodePerformance() {
        String message = String.valueOf(mGU.getCostTime(true));
        mTextViewPerformance.setText(message);
    }
    private boolean getUserSettings() {
        mTextViewDstFilePath = (TextView) findViewById(R.id.textViewDstFilePath);
        mTextViewPerformance = (TextView) findViewById(R.id.textViewPerformance);

        mEditTextSrcFileName = (EditText) findViewById(R.id.editTextSrcFileName);
        mSrcFilePath = mGU.getSrcFilePath(mEditTextSrcFileName.getText().toString());
        if (mSrcFilePath == null) {
            String message = "Invalid source file name "
                    + mEditTextSrcFileName.getText().toString();
            Toast.makeText(FileDecodeActivity.this, message, Toast.LENGTH_SHORT).show();
            return false;
        }

        RadioButton radioButton;
        mRadioGroupSampleSize = (RadioGroup) findViewById(R.id.radioGroupSampleSize);
        radioButton = (RadioButton) findViewById(mRadioGroupSampleSize.getCheckedRadioButtonId());
        mSampleSize = mGU.getSampleSize(radioButton.getText().toString());

        mRadioGroupPreColorType = (RadioGroup) findViewById(R.id.radioGroupPreColorType);
        radioButton = (RadioButton) findViewById(mRadioGroupPreColorType.getCheckedRadioButtonId());
        mPreColorType = mGU.getPreColorType(radioButton.getText().toString());

        mRadioGroupScale = (RadioGroup) findViewById(R.id.radioGroupScale);
        radioButton = (RadioButton) findViewById(mRadioGroupScale.getCheckedRadioButtonId());
        mScale = mGU.getScale(radioButton.getText().toString());

        mRadioGroupShowBitmap = (RadioGroup) findViewById(R.id.radioGroupShowBitmap);
        radioButton = (RadioButton) findViewById(mRadioGroupShowBitmap.getCheckedRadioButtonId());
        mShowBitmap = mGU.getShowBitmap(radioButton.getText().toString());

        Log.d(TAG,"mSrcFilePath:" + mSrcFilePath);
        Log.d(TAG, "mSampleSize:" + mSampleSize);
        Log.d(TAG, "mPreColorType:" + mPreColorType);
        Log.d(TAG, "mScale:" + mScale);
        Log.d(TAG, "mShowBitmap:" + mShowBitmap);
        return true;
    }
    private void decodeAndSaveBitmapFile(String srcFilePath, int sampleSize, Bitmap.Config preColorType,
                               boolean scale, boolean showBitmap) {
        String message = null;
        Bitmap bmp = mGU.decodeFile(srcFilePath, sampleSize,preColorType, scale);
        if (null == bmp) {
            message = "Decode Bitmap fail!";
            Toast.makeText(FileDecodeActivity.this, message, Toast.LENGTH_SHORT).show();
            return ;
        }
        String outBitmapFileName = mGU.generateBitmapFileName(bmp);
        mDstFilePath = mGU.getDstFilePath(outBitmapFileName);
        boolean ret = mGU.writeBitmapToFile(bmp, mDstFilePath);
        if (ret) {
            message = "Decode file success!";
        } else {
            message = "Write to file " + mDstFilePath + " fail!";
        }
        Toast.makeText(FileDecodeActivity.this, message, Toast.LENGTH_SHORT).show();
        return ;
    }
    private void startDecodeButton() {
        Button btn = (Button) findViewById(R.id.buttonStartDecode);
        btn .setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (false == getUserSettings()) return ;
                decodeAndSaveBitmapFile(
                        mSrcFilePath,
                        mSampleSize,
                        mPreColorType,
                        mScale,
                        mShowBitmap
                );
                showDstFilePath();
                showDecodePerformance();
            }
        });
    }
    private void startClearButton() {
        Button btn = (Button) findViewById(R.id.buttonClearCacheFile);
        btn .setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String dstPath = mGU.deleteDirectory();
                String message;
                if (null == dstPath) {
                    message = "Dst dir already not exist!";
                } else {
                    message = "Delete " + dstPath + " success!";
                }
                Log.d(TAG, message);
                Toast.makeText(FileDecodeActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_decode);

        mGU = new GraphicUtils();
        startDecodeButton();
        startClearButton();
    }
}
